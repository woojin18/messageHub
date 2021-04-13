package kr.co.uplus.cloud.cash.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
			rtn.setMessage("");
			
			return rtn;
		}
		
		
		
		
		return rtn;
	}
}
