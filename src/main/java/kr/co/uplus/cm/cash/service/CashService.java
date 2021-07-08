package kr.co.uplus.cm.cash.service;

import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.config.ApiConfig;
import kr.co.uplus.cm.utils.ApiInterface;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CashService {
	
	@Autowired
	private GeneralDao generalDao;
	
	@Autowired
	ApiInterface apiInterface;
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public RestResult<Object> insertWebCashInfo(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		Map<String, Object> map = (Map<String, Object>) generalDao.selectGernalObject(DB.QRY_SELECT_CORP_INFO, params);
		
		//결제번호 등
		String orderId	= CommonUtils.getCommonId("pg", 6);
		String corpId	= CommonUtils.getString(map.get("corpId"));
		map.put("order_id"		, orderId);
		map.put("payMtd"		, "card");
		map.put("orderName"		, map.get("corpName") + " 선불충전");
		map.put("customerName"	, map.get("corpName"));
		
		//금액
		map.put("amount"		, params.get("amount"));
		
		//cash_id 
		String cashId = CommonUtils.getString(generalDao.selectGernalObject(DB.QRY_SELECT_WEB_CASH_ID, params));
		
		if( "".equals(cashId) ) {
			Map<String, Object> headerMap = new HashMap<String, Object>();
			headerMap.put("type",		"C");
			
			Map<String, Object> apiBodyMap = new HashMap<>();
			apiBodyMap.put("cashType",		"C");	// C 일반 캐시  E 이벤트 캐시
			apiBodyMap.put("cashBalance",	params.get("amount"));
			apiBodyMap.put("startDt",		"");
			apiBodyMap.put("expDt",			"");
			apiBodyMap.put("memo",			"캐시 충전");
			
			// API 통신 처리
			Map<String, Object> result =  apiInterface.etcPost(ApiConfig.CASH_SERVER_DOMAIN + "/console/v1/cash/cashInfo/" + corpId, apiBodyMap, headerMap);
			
			System.out.println("------------------------------------------------- cashInfo C result : " + result);
			
			cashId = CommonUtils.getString(((Map<String, Object>)result.get("data")).get("error"));
			
			// 성공인지 실패인지 체크
			if( "10000".equals(result.get("rslt")) ) {
			} else if ( "500100".equals(result.get("rslt")) ) {
				String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
				throw new Exception(errMsg);
			} else {
				String errMsg = CommonUtils.getString(result.get("rsltDesc"));
				throw new Exception(errMsg);
			}
		}
		
		
		map.put("cashId"		, cashId);
		
		//승인상태 0 : 승인요청
		map.put("status"		, "0");
		
		//웹캐시 결제정보에 데이터 입력
		generalDao.insertGernal(DB.QRY_INSERT_WEB_CASH_INFO, map);
		
		map.put("clientKey", ApiConfig.CASH_CLIENT_KEY);
		
		rtn.setData(map);
		
		return rtn;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public RestResult<Object> cardSuccess(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		String				paymentKey	= CommonUtils.getString(params.get("paymentKey"));
		String				orderId		= CommonUtils.getString(params.get("orderId"));
		int					amount		= CommonUtils.getInt(params.get("amount"));
		
		Map<String, Object>	dbInfo		= (Map<String, Object>) generalDao.selectGernalObject(DB.QRY_SELECT_WEB_CASH_INFO, params);
		int					dbAmount	= CommonUtils.getInt(dbInfo.get("amount"));
		
		if(amount != dbAmount) {
			rtn.setSuccess(false);
			rtn.setMessage("결제에 실패하였습니다.");
			
			return rtn;
		}
		
		Map<String, Object> headerMap = new HashMap<String, Object>();
		
		String text = ApiConfig.CASH_SECRET_KEY + ":";
		byte[] targetBytes = text.getBytes();
		Encoder encoder = Base64.getEncoder();
		byte[] encodedBytes = encoder.encode(targetBytes);
		
		headerMap.put("Authorization", "Basic " + new String(encodedBytes));
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("orderId", orderId);
		dataMap.put("amount", amount);
		
		Map<String, Object> postResult	= apiInterface.etcPost("https://api.tosspayments.com/v1/payments/" + paymentKey, dataMap, headerMap);
		Map<String, Object> card		= (Map<String, Object>) postResult.get("card");
		
		Map<String, Object> updateMap = new HashMap<>();
		updateMap.put("orderId"			, CommonUtils.getString(postResult.get("orderId")));
		updateMap.put("approvalNumber"	, CommonUtils.getString(postResult.get("paymentKey")));	//승인번호
		updateMap.put("cardCompany"		, CommonUtils.getString(card.get("company")));			//카드사
		updateMap.put("receiptUrl"		, CommonUtils.getString(card.get("receiptUrl")));		//영수증 링크
		updateMap.put("status"			, "1");													//상태
		
		generalDao.updateGernal(DB.QRY_UPDATE_WEB_CASH_INFO, updateMap);
		
		// 결제정보 api 필요
		// cm_web_cash_hist 테이블에서 payment_id = orderId 로 검색해서 corpId, cashId 조회
		Map<String, Object> cashHistInfo = (Map<String, Object>) generalDao.selectGernalObject("cash.selectCashHistInfoForPaymentId", params);
		String corpId = CommonUtils.getString(cashHistInfo.get("corpId"));
		String cashId = CommonUtils.getString(cashHistInfo.get("cashId"));
		
		Map<String, Object> headerMap2 = new HashMap<String, Object>();
		headerMap2.put("corpId",		corpId);
		headerMap2.put("cashId",		cashId);
		
		Map<String, Object> apiBodyMap2 = new HashMap<>();
		apiBodyMap2.put("inOut",	"I");
		apiBodyMap2.put("amount",	params.get("amount"));
		apiBodyMap2.put("reason",	"캐시 충전");
		
		// API 통신 처리
		Map<String, Object> result =  apiInterface.etcPost(ApiConfig.CASH_SERVER_DOMAIN + "/console/v1/cash/amount/" + corpId + "/" + cashId, apiBodyMap2, headerMap2);
		
		System.out.println("------------------------------------------------- cashInfo C U result : " + result);
		
		// 성공인지 실패인지 체크
		if( "10000".equals(result.get("rslt")) ) {
		} else if ( "500100".equals(result.get("rslt")) ) {
			String errMsg = CommonUtils.getString(((Map<String, Object>)((Map<String, Object>)result.get("data")).get("error")).get("message"));
			throw new Exception(errMsg);
		} else {
			String errMsg = CommonUtils.getString(result.get("rsltDesc"));
			throw new Exception(errMsg);
		}
		
		return rtn;
	}
	
	@SuppressWarnings("unchecked")
	public RestResult<Object> selectCashHist(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		if(params.containsKey("pageNo")
				&& CommonUtils.isNotEmptyObject(params.get("pageNo"))
				&& params.containsKey("listSize")
				&& CommonUtils.isNotEmptyObject(params.get("listSize"))) {
			rtn.setPageProps(params);
			if(rtn.getPageInfo() != null) {
				//카운트 쿼리 실행
				int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_CASH_HIST_COUNT, params);
				rtn.getPageInfo().put("totCnt", listCnt);
			}
		}

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_CASH_HIST, params);
		rtn.setData(rtnList);
		
		return rtn;
	}
	
	@SuppressWarnings("unchecked")
	public RestResult<Object> selectCashBalance(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		int cashBalance = 0;
		int eventCashBalance = 0;
		
		Map<String, Object> cashInfo = (Map<String, Object>) generalDao.selectGernalObject("cash.selectCashInfoForCorpIdCashType", params);
		String corpId = CommonUtils.getString(cashInfo.get("corpId"));
		String cashId = CommonUtils.getString(cashInfo.get("cashId"));
		
		
		// API 통신 처리
//		Map<String, Object> result =  apiInterface.get(ApiConfig.CASH_SERVER_DOMAIN + "/console/v1/cash/cashInfo/" + corpId + "?cashId=" + cashId, null);
		Map<String, Object> result =  apiInterface.request("GET", ApiConfig.CASH_SERVER_DOMAIN + "/console/v1/cash/cashInfo/" + corpId + "?cashId=" + cashId, null, null, null);
		
		System.out.println("------------------------------------------------- selectCashBalance : " + result);
		
		// 성공인지 실패인지 체크
		if( "10000".equals(result.get("rslt")) ) {
			List cashInfoList = (List) ((Map<String, Object>)result.get("data")).get("cashInfo");
			Map<String, Object> cashInfoListMap = (Map<String, Object>) cashInfoList.get(0);
			cashBalance = Integer.parseInt( CommonUtils.getString(cashInfoListMap.get("cashBalance")).replace(".0", "") );
			
			System.out.println("------------------------------------------------- result.get(\"data\") : " + cashBalance);
			
			
		} else {
			cashBalance = 0;
		}
		
		eventCashBalance = Integer.parseInt(CommonUtils.getString(generalDao.selectGernalObject("cash.selectEventCashBalance", params)));
		
		Map<String, Object> cashMap = new HashMap<>();
		 
		cashMap.put("cashBalance", cashBalance);
		cashMap.put("eventCashBalance", eventCashBalance);
		cashMap.put("balance", cashBalance + eventCashBalance);
		
		rtn.setData(cashMap);
		
		return rtn;
	}
	
	public RestResult<Object> selectUcubeInfo(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		List<Object> list = generalDao.selectGernalList(DB.QRY_SELECT_UCUBE_INFO, params);
		
		rtn.setData(list);
		
		return rtn;
	}
	
	public RestResult<Object> selectProjectInfo(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		int count = generalDao.selectGernalCount(DB.QRY_SELECT_PROJECT_INFO_COUNT, params);
		List<Object> list = generalDao.selectGernalList(DB.QRY_SELECT_PROJECT_INFO, params);
		
		Map<String, Object> map = new HashMap<>();
		map.put("count", count);
		map.put("list", list);
		
		rtn.setData(map);
		
		return rtn;
	}
	
	public RestResult<Object> selectProjectSubBillCode(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		int count = generalDao.selectGernalCount(DB.QRY_SELECT_PROJECT_SUB_BILL_CODE_COUNT, params);
		List<Object> list = generalDao.selectGernalList(DB.QRY_SELECT_PROJECT_SUB_BILL_CODE, params);
		
		Map<String, Object> map = new HashMap<>();
		map.put("count", count);
		map.put("list", list);
		
		rtn.setData(map);
		
		return rtn;
	}
	
	public RestResult<Object> insertUbubeInfo(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		//api로 유큐브 정보 불러와서 입력해야 함
		
		Map<String, Object> map = new HashMap<>();
		map.put("a", 8);
		map.put("b", 88);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(map);
		
		params.put("billId", "8888");
		params.put("ucubeInfo", json);
		
		List<Object> list = generalDao.selectGernalList(DB.QRY_INSERT_UCUBE_INFO, params);
		rtn.setData(list);
		
		return rtn;
	}
	
	public RestResult<Object> updateProjectBillId(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		generalDao.updateGernal(DB.QRY_UPDATE_PROJECT_BILL_ID, params);
		
		return rtn;
	}
	
	public RestResult<Object> saveProjectSubBillCode(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		String state = CommonUtils.getString(params.get("state"));
		
		if(state.equals("add")) {
			generalDao.insertGernal(DB.QRY_INSERT_PROJECT_SUB_BILL_CODE, params);
		} else if(state.equals("edit")) {
			generalDao.updateGernal(DB.QRY_UPDATE_PROJECT_SUB_BILL_CODE, params);
		} else {
			throw new Exception();
		}
		
		return rtn;
	}
	
	public RestResult<Object> deleteProjectSubBillCode(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		generalDao.deleteGernal(DB.QRY_DELETE_PROJECT_SUB_BILL_CODE, params);
		
		return rtn;
	}
	
}
