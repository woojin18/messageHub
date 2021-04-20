package kr.co.uplus.cloud.cash.service;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uplus.cloud.utils.ApiInterface;
import kr.co.uplus.cloud.utils.CommonUtils;
import kr.co.uplus.cloud.utils.GeneralDao;
import kr.co.uplus.cloud.common.consts.DB;
import kr.co.uplus.cloud.common.dto.RestResult;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CashService {
	
	@Autowired
	private GeneralDao generalDao;
	
	@Autowired
	ApiInterface apiInterface;
	
	@Value("${cash.pg.clientKey}")
	String pgClientKey;
	
	@Value("${cash.pg.secretKey}")
	String pgSecretKey;
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public RestResult<Object> insertWebCashInfo(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		Map<String, Object> map = (Map<String, Object>) generalDao.selectGernalObject(DB.QRY_SELECT_CORP_INFO, params);
		
		//결제번호 등
		String	orderId = CommonUtils.getCommonId("pg", 6);
		map.put("order_id"		, orderId);
		map.put("payMtd"		, "card");
		map.put("orderName"		, map.get("corpName") + " 선불충전");
		map.put("customerName"	, map.get("corpName"));
		
		//금액
		map.put("amount"		, params.get("amount"));
		
		//cash_id api로 받아와야함
		map.put("cashId"		, map.get("corpId"));
		
		//승인상태 0 : 승인요청
		map.put("status"		, "0");
		
		//웹캐시 결제정보에 데이터 입력
		generalDao.insertGernal(DB.QRY_INSERT_WEB_CASH_INFO, map);
		
		map.put("clientKey", this.pgClientKey);
		
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
		
		String text = this.pgSecretKey + ":";
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
		updateMap.put("orderId"			, orderId);
		updateMap.put("approvalNumber"	, paymentKey);										//승인번호
		updateMap.put("cardCompany"		, CommonUtils.getString(card.get("company")));		//카드사
		updateMap.put("receiptUrl"		, CommonUtils.getString(card.get("receiptUrl")));	//영수증 링크
		updateMap.put("status"			, "1");												//상태
		
		generalDao.updateGernal(DB.QRY_UPDATE_WEB_CASH_INFO, updateMap);
		
		rtn.setSuccess(false);
		rtn.setMessage("결제에 성공하였습니다.");
		
		return rtn;
	}
	
	@SuppressWarnings("unchecked")
	public RestResult<Object> selectCashHist(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		Map<String, Object> pageInfo = (Map<String, Object>) params.get("pageInfo");
		
		if (pageInfo != null && !pageInfo.isEmpty()) {
			int rowNum = generalDao.selectGernalCount(DB.QRY_SELECT_CASH_HIST_COUNT, params);
			pageInfo.put("rowNum", rowNum);
			
			rtn.setPageInfo(pageInfo);
		}
		
		List<Object> list = generalDao.selectGernalList(DB.QRY_SELECT_CASH_HIST, params);
		
		rtn.setData(list);
		
		return rtn;
	}
}
