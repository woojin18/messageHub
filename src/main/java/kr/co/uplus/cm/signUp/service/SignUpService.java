package kr.co.uplus.cm.signUp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SignUpService {

	@Autowired
	private GeneralDao generalDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void insertSignUp(Map<String, Object> params) throws Exception {
		
		System.out.println("+++++++++++++" + params);
		
		// 고객사 등록
//		generalDao.insertGernal(DB.QRY_INSERT_CM_CORP, params);
		// owner 등록 (이메일 발송때 insert된 data에 update 처리)
//		generalDao.updateGernal(DB.QRY_UPDATE_CM_USER, params);
		
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void insertEmailUser(Map<String, Object> params) throws Exception {
		// 난수 생성은 이전 로직에서 처리해야됨 이메일 발송모듈 추가후 삭제 예정 _서동욱
		String randomNum = CommonUtils.randomGeneration(10);
		params.put("ranDomNum", randomNum);
		params.put("cmRole", "OWNER");
		
		generalDao.insertGernal(DB.QRY_INSERT_CM_USER, params);
		
		
	}

	public RestResult<?> selectUseTerms(Map<String, Object> params) throws Exception {
		RestResult<Object>	rtn = new RestResult<Object>();
		Map<String, Object>	rtnResult = new HashMap<String, Object>();
		String				srcTermsCd = CommonUtils.getString(params.get("srcTermsCd"));
		// 현재 약관
		Object rtnMap = generalDao.selectGernalObject(DB.QRY_SELECT_CM_USE_TERMS, params);
		rtnResult.put("useTerms", rtnMap);
		
		// 개인정보수집 일경우에만 예전 약관을 가져오도록 처리
		if("PRI".equals(srcTermsCd)) {
			// 예전 약관
			List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_CM_USE_TERMS_PRI_VERSION, params);
			rtnResult.put("preUseTerms", rtnList);
		}
		
		rtn.setData(rtnResult);

		return rtn;
		
	}
}
