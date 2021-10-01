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
		map.put("payMtd"		, "카드");
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
			apiBodyMap.put("cashBalance",	0);
			apiBodyMap.put("startDt",		"");
			apiBodyMap.put("expDt",			"");
			apiBodyMap.put("memo",			"캐시 생성");
			
			// API 통신 처리
			Map<String, Object> result =  apiInterface.etcPost(ApiConfig.CASH_SERVER_DOMAIN + "/console/v1/cash/cashInfo/" + corpId, apiBodyMap, headerMap);
			
			//System.out.println("------------------------------------------------- cashInfo C result : " + result);
			
			cashId = CommonUtils.getString(((Map<String, Object>)result.get("data")).get("cashId"));
			
			// 성공인지 실패인지 체크
			if( "10000".equals(result.get("code")) ) {
			} else if ( "500100".equals(result.get("code")) ) {
				String errMsg = CommonUtils.getString(result.get("message"));
				throw new Exception(errMsg);
			} else {
				String errMsg = CommonUtils.getString(result.get("message"));
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
		
		//System.out.println("------------------------------------------------- cashInfo C U result : " + result);
		
		// 성공인지 실패인지 체크
		if( "10000".equals(result.get("code")) ) {
		} else if ( "500100".equals(result.get("code")) ) {
			String errMsg = CommonUtils.getString(result.get("message"));
			throw new Exception(errMsg);
		} else {
			String errMsg = CommonUtils.getString(result.get("message"));
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
		
		double cashBalance = 0;
		double eventCashBalance = 0;
		
		Map<String, Object> cashInfo = (Map<String, Object>) generalDao.selectGernalObject("cash.selectCashInfoForCorpIdCashType", params);
		
		String corpId = CommonUtils.getString(params.get("corpId"));
		
		if(cashInfo == null || cashInfo.size() == 0) {
			Map<String, Object> apiBodyMap = new HashMap<>();
			apiBodyMap.put("cashType",		"C");	// C 일반 캐시  E 이벤트 캐시
			apiBodyMap.put("cashBalance",	0);
			apiBodyMap.put("memo",			"캐시 생성");
			
			// API 통신 처리
			Map<String, Object> result =  apiInterface.etcPost(ApiConfig.CASH_SERVER_DOMAIN + "/console/v1/cash/cashInfo/" + corpId, apiBodyMap, null);
			
			// 성공인지 실패인지 체크
			if( "10000".equals(result.get("code")) ) {
			} else {
				rtn.setMessage(CommonUtils.getString(result.get("message")));
				return rtn;
			}
		}
		
		// API 통신 처리
		Map<String, Object> result =  apiInterface.request("GET", ApiConfig.CASH_SERVER_DOMAIN + "/console/v1/cash/cashInfo/" + corpId, null, null, null);
		
		// 성공인지 실패인지 체크
		if( "10000".equals(result.get("code")) ) {
			List<Map<String, Object>> cashInfoList = (List) ((Map<String, Object>)result.get("data")).get("cashInfo");
			
			for (Map<String, Object> map : cashInfoList) {
				if(map.get("cashType").equals("C")) {
					cashBalance = Double.parseDouble( CommonUtils.getString(map.get("cashBalance")) );
				} else {
					eventCashBalance += Double.parseDouble( CommonUtils.getString(map.get("cashBalance")) );
				}
			}
		}
		
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
	
	public RestResult<Object> selectUcubePopInfo(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		List<Object> list = generalDao.selectGernalList("cash.selectUcubePopInfo", params);
		
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
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public RestResult<Object> saveProjectSubBillCode(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		String state = CommonUtils.getString(params.get("state"));
		String corpId = CommonUtils.getString(params.get("corpId"));
		String deptCode = CommonUtils.getString(params.get("deptCode"));
		
		if(state.equals("add")) {
			// 고객번호 가져오기
			String custNo	= CommonUtils.getString(generalDao.selectGernalObject("project.selectCustNoForSaveProject", params));
			String regNo	= CommonUtils.getString(generalDao.selectGernalObject("project.selectRegNoForSaveProject", params));
			String salesId	= CommonUtils.getString(generalDao.selectGernalObject("project.selectSalesIdForSaveProject", params));
			
			Map<String, Object> apiCustomerInfo = (Map<String, Object>) apiInterface.get("/console/v1/ucube/customer/" + custNo, null).get("data");
			
			Map<String, Object> joinMap = new HashMap<>();
			
			joinMap.put("custNo",		custNo);
			joinMap.put("logid",		corpId + "_BC_" + deptCode);
			joinMap.put("indcId",		salesId);
			joinMap.put("mngrId",		salesId);
			
			// 유큐브 서비스 등록 
			Map<String, Object> ucubeBillInfoVo = new HashMap<>();
			String billEmail = CommonUtils.getString(apiCustomerInfo.get("emailAddr"));
			if( "".equals(billEmail) ) {
				billEmail = CommonUtils.getString(generalDao.selectGernalObject("project.selectEmailForSaveProject", params));
			}
			
			ucubeBillInfoVo.put("billAcntNo",	null);										// 청구계정 번호 (등록시엔 공백으로 입력)
			ucubeBillInfoVo.put("billEmail",	billEmail);									// 청구이메일주소 ?
			ucubeBillInfoVo.put("billKind",		"N");										// 청구서유형코드 Y 이메일 N 우편
			ucubeBillInfoVo.put("billRegNo",	regNo);										// 청구사업자번호
			ucubeBillInfoVo.put("billZip",		apiCustomerInfo.get("custAddrZip"));		// 청구우편번호
			ucubeBillInfoVo.put("billJuso",		apiCustomerInfo.get("custVilgAbvAddr"));	// 청구주소
			ucubeBillInfoVo.put("billJuso2",	apiCustomerInfo.get("custVilgBlwAddr"));	// 청구주소상세
			
			Map<String, Object> ucubePymInfoVO = new HashMap<>();
			
			ucubePymInfoVO.put("napCustKdCd",	apiCustomerInfo.get("custKdCd"));			// 납부자고객구분코드
			ucubePymInfoVO.put("napCmpNm",		apiCustomerInfo.get("custNm"));				// 납부자명 ???
			ucubePymInfoVO.put("napJumin",		regNo);										// 생년월일/사업자번호 ????
			
			// 서비스 정보
			Map<String, Object> serviceInfo = new HashMap<>();
			Map<String, Object> ownerMap	= (Map<String, Object>) generalDao.selectGernalObject("project.selectOwnerForApi", params);
			
			serviceInfo.put("cmpNm",			apiCustomerInfo.get("bizCompNm"));			// 가입자(상호)명 
			serviceInfo.put("ceoNm",			apiCustomerInfo.get("custNm"));				// 대표자 성명 (필수)
			serviceInfo.put("damNm",			ownerMap.get("damNm"));						// 담당자명
			serviceInfo.put("deptNm",			ownerMap.get("deptNm"));					// 담당자부서명
			serviceInfo.put("phoneNum",			ownerMap.get("phoneNum"));					// 담당자 전화번호
			serviceInfo.put("cellNum",			ownerMap.get("cellNum"));					// 담당자 핸드폰번호
			serviceInfo.put("faxNum",			ownerMap.get("faxNum"));					// 담당자FAX번호
			serviceInfo.put("damEmail",			ownerMap.get("damEmail"));					// 담당자 Email
			
			// 채널별 단가객체
			Map<String, Object> cmPriceInfoMap = new HashMap<>();
			
			List<Object> priceList = generalDao.selectGernalList("use.selectUseHistProductUnit", params);
			
			for( int j = 0; j < priceList.size(); j++ ) {
				Map<String, Object> priceMap = (Map<String, Object>) priceList.get(j);
				String productCode = CommonUtils.getString(priceMap.get("productCode"));
				
				// 가격정보
				Map<String, Object> priceInfoMap = new HashMap<>();
				
				// 후불제 가격 정보
				kong.unirest.json.JSONArray postFeeInfoArr = new kong.unirest.json.JSONArray(CommonUtils.getString(priceMap.get("postFeeInfo")));
				
//				if( postFeeInfoArr.length() == 1 ) {
				priceInfoMap.put("unitPrice", postFeeInfoArr.getJSONObject(0).get("POST_FEE"));
				priceInfoMap.put("slideInfo", null);
//				} else 
				// 스라이딩인 경우
				if ( postFeeInfoArr.length() == 3 ) {
					Map<String, Object> slideInfoMap = new HashMap<>();
					
					slideInfoMap.put("cntMore",			postFeeInfoArr.getJSONObject(2).get("FEE_START_CNT"));
					slideInfoMap.put("cntBeLow",		postFeeInfoArr.getJSONObject(0).get("FEE_END_CNT"));
					slideInfoMap.put("priceMore",		postFeeInfoArr.getJSONObject(0).get("POST_FEE"));
					slideInfoMap.put("priceBetween",	postFeeInfoArr.getJSONObject(1).get("POST_FEE"));
					slideInfoMap.put("priceBeLow",		postFeeInfoArr.getJSONObject(2).get("POST_FEE"));
					
					priceInfoMap.put("unitPrice", postFeeInfoArr.getJSONObject(0).get("POST_FEE"));
					priceInfoMap.put("slideInfo", slideInfoMap);
				}
				
				// 채널
				if( "PUSH".equals(productCode) ) {
					// 푸시
					cmPriceInfoMap.put("push", priceInfoMap);
				} else if ( "KALT1".equals(productCode) ) {
					// 알림톡
					cmPriceInfoMap.put("alimtalk", priceInfoMap);
				} else if ( "KFRT1".equals(productCode) ) {
					// 친구톡 TEXT
					cmPriceInfoMap.put("friendtalkTxt", priceInfoMap);
				} else if ( "KFRM2".equals(productCode) ) {
					// 친구톡 이미지
					cmPriceInfoMap.put("friendtalkImg", priceInfoMap);
				} else if ( "KFRM3".equals(productCode) ) {
					// 친구톡 와이드
					cmPriceInfoMap.put("friendtalkWide", priceInfoMap);
				} else if ( "RTPL".equals(productCode) ) {
					// RCS 템플릿
					cmPriceInfoMap.put("rcsTmplt", priceInfoMap);
				} else if ( "RSMS".equals(productCode) ) {
					// RCS SMS
					cmPriceInfoMap.put("rcsSms", priceInfoMap);
				} else if ( "RLMS".equals(productCode) ) {
					// RCS LMS
					cmPriceInfoMap.put("rcsLms", priceInfoMap);
				} else if ( "RMMS".equals(productCode) ) {
					// RCS MMS
					cmPriceInfoMap.put("rcsMms", priceInfoMap);
				} else if ( "SMS".equals(productCode) ) {
					// SMS
					cmPriceInfoMap.put("sms", priceInfoMap);
				} else if ( "LMS".equals(productCode) ) {
					// LMS
					cmPriceInfoMap.put("lms", priceInfoMap);
				} else if ( "MMS".equals(productCode) ) {
					// MMS
					cmPriceInfoMap.put("mms", priceInfoMap);
				}
			}

			joinMap.put("billInfoVo",		ucubeBillInfoVo);
			joinMap.put("pymInfoVO",		ucubePymInfoVO);
			joinMap.put("serviceInfo",		serviceInfo);
			joinMap.put("cmPriceInfo",	cmPriceInfoMap);
			
			kong.unirest.json.JSONObject json2222 =  new kong.unirest.json.JSONObject(joinMap);
			
			//System.out.println("-------------------------------------------!!!!!!!!! requset body json : " + json2222);
			
			// API 통신처리
			Map<String, Object> result =  apiInterface.post("/console/v1/ucube/service/join/cm", joinMap, null);
			
//			성공 시
//			{
//				"code": "10000",
//				"message": "성공",
//				"data": {
//					"serviceId": "SB1099",
//					"resultCode": "N0000",
//					"resultMsg": "Success",
//					"resultList": [{
//						"entrNo": "500232358675", (가입번호)
//						"billAcntNo": "532109196140" (청구계정번호)
//					}]
//				}
//			 }
			
			String serviceId = "";
			String billId = "";
			
			Map<String, Object> resultData = (Map<String, Object>) result.get("data");
			List<Map<String, Object>> resultList = (List<Map<String, Object>>) resultData.get("resultList");

			if( "10000".equals(result.get("code")) ) {
				serviceId = CommonUtils.getString(resultList.get(0).get("entrNo"));
				billId = CommonUtils.getString(resultList.get(0).get("billAcntNo"));
				kong.unirest.json.JSONObject ucubeInfo =  new kong.unirest.json.JSONObject(resultData);
				
				params.put("serviceId", serviceId);
				params.put("billId", billId);
				params.put("ucubeInfo", ucubeInfo.toString());
				
				// 유큐브 정보 인서트
				generalDao.insertGernal("project.insertCmUcube", params);
				
			} else if ( "500100".equals(result.get("code")) ) {
				String errMsg = CommonUtils.getString(result.get("message")) + " : " + CommonUtils.getString(result.get("data"));
				throw new Exception(errMsg);
			} else {
//				 {code=21400, message=유큐브 연동 오류, data={serviceId=SB1099, resultCode=icm.err.074
//						, resultMsg=입력한 유치자( juoh )에 해당하는 대리점 정보가 존재하지 않습니다., resultList=null}}
				String errMsg = CommonUtils.getString(result.get("message"));
				String msg = CommonUtils.getString(((Map<String, Object>) result.get("data")).get("resultMsg"));
				if( !"".equals(msg) ) {
					throw new Exception(errMsg + " : " + msg);
				} else {
					throw new Exception(errMsg);
				}
			}
			
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
