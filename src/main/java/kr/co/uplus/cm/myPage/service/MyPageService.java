package kr.co.uplus.cm.myPage.service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.crypto.Sha256PasswordEncoder;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.utils.ApiInterface;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;
import yoyozo.security.SHA;

@Service
public class MyPageService {
	
	@Autowired
	private GeneralDao generalDao;

	@Autowired
	private Sha256PasswordEncoder sha256;

	@Autowired
	private ApiInterface apiInterface;
	
	@Autowired
	private CommonService commonService;
	
	public RestResult<Object> selectMemberInfo(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		Object rtnObj = generalDao.selectGernalObject(DB.QRY_SELECT_MEMBER_INFO, params);
		rtn.setData(rtnObj);

		return rtn;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public RestResult<Object> saveMemberInfo(Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.putAll(params);
		
		try {
			// 인증번호 check
			if(!"".equals(params.get("certifyNumber")) && params.get("certifyNumber") != null) {
				String certifyNumb = CommonUtils.getString(params.get("certifyNumber"));
				String chkCertifyNumb = (String) generalDao.selectGernalObject(DB.QRY_SELECT_SMS_CERTIFY_NUMBER, paramMap);
				
				if(!chkCertifyNumb.equals(certifyNumb)) {
					rtn.setSuccess(false);
					rtn.setMessage("인증번호를 정확히 입력해주세요.");
					
					return rtn;
				}
			}
			
			// 비밀번호 암호화
			if(!"".equals(params.get("loginPwd")) && params.get("loginPwd") != null) {
				String loginPwd = CommonUtils.getString(params.get("loginPwd"));
				// 사용자 비밀번호 암호화
				SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
				byte[] bytes = new byte[16];
				random.nextBytes(bytes);
				String salt = new String(Base64.getEncoder().encode(bytes));
				paramMap.put("salt", salt);

				SHA sha256 = new SHA(256);
				// 기존 비밀번호 비교
				String exSalt = CommonUtils.getString(generalDao.selectGernalObject(DB.QRY_SELECT_SALT_INFO_BY_USERID, params));
				String rtnPwd = "";
				if(exSalt != null && !"".equals(exSalt)) {
					rtnPwd = sha256.encryptToBase64(exSalt + loginPwd);
				} else {
					rtnPwd = sha256.encryptToBase64(loginPwd);
				}
				
				String exPwd = CommonUtils.getString(generalDao.selectGernalObject(DB.QRY_SELECT_EX_LOGIN_PWD, paramMap));
				if(exPwd.equals(rtnPwd)) {
					rtn.setSuccess(false);
					rtn.setMessage("기존과 동일한 비밀번호는 사용할 수 없습니다.");
					return rtn;
				}
				
				String encPwd = sha256.encryptToBase64(salt + loginPwd);
				paramMap.put("loginPwd", encPwd);
			}
			
			// 회원정보 update
			generalDao.updateGernal(DB.QRY_UPDATE_MEMBER_INFO, paramMap);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("저장에 실패하였습니다.");
		}
		
		return rtn;
	}
	
	@SuppressWarnings("unchecked")
	public RestResult<Object> selectQnaList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		Map<String, Object> pageInfo = (Map<String, Object>) params.get("pageInfo");
		
		if (pageInfo != null && !pageInfo.isEmpty()) {
			int rowNum = generalDao.selectGernalCount(DB.QRY_SELECT_QNA_LIST_COUNT, params);
			pageInfo.put("rowNum", rowNum);
			
			rtn.setPageInfo(pageInfo);
		}
		
		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_QNA_LIST, params);
		rtn.setData(rtnList);

		return rtn;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public RestResult<Object> saveQnaInfo(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		String questBoardId = CommonUtils.getString(params.get("questBoardId"));
			if(questBoardId.equals("")) {
				questBoardId = CommonUtils.getCommonId("QBD", 5);
				params.put("questBoardId", questBoardId);
				generalDao.insertGernal(DB.QRY_INSERT_QUEST_BOARD, params);
			} else {
				generalDao.updateGernal(DB.QRY_UPDATE_QUEST_BOARD, params);
			}
		return rtn;
	}
	
	public RestResult<Object> checkPassword(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.putAll(params);
		
		//salt
		String salt = "";
		salt = CommonUtils.getString(generalDao.selectGernalObject(DB.QRY_SELECT_SALT_INFO_BY_USERID, params));
		if(salt != null && !"".equals(salt)) {
			String password = CommonUtils.getString(salt + paramMap.get("password"));
			paramMap.put("password", sha256.encode(password));
			
		} else {
			String password = CommonUtils.getString(paramMap.get("password"));
			paramMap.put("password", sha256.encode(password));
		}
		
		int cnt = generalDao.selectGernalCount(DB.QRY_CHK_PASSWORD, paramMap);
		
		if(cnt == 0) {
			rtn.setSuccess(false);
			rtn.setMessage("비밀번호가 일치하지않습니다.");
		}
		
		return rtn;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public RestResult<Object> deleteQnaInfo(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		generalDao.deleteGernal(DB.QRY_DELETE_QUEST_BOARD, params);
		
		return rtn;
	}

	@SuppressWarnings("unchecked")
	public RestResult<Object> selectCorpInfo(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		Map<String, Object> headerMap = new HashMap<String, Object>();
		
		Map<String, Object> rtnObj = (Map<String, Object>) generalDao.selectGernalObject(DB.QRY_SELECT_CORP_INFO, params);
		
//		String custNo = CommonUtils.getString(rtnObj.get("custNo"));
		
		// API 통신 처리
//		headerMap.put("custNo", custNo);
//		Map<String, Object> result = apiInterface.get("/console/v1/ucube/customer/"+custNo, headerMap);
//		
//		if("10000".equals(result.get("code"))) {
//			Map<String, Object> data = (Map<String, Object>) result.get("data");
//			rtnObj.putAll(data);
//			rtnObj.put("custNm", CommonUtils.setMaskingUserNm(CommonUtils.getString(rtnObj.get("custNm"))));
			rtn.setData(rtnObj);
//		} else {
//			throw new Exception(CommonUtils.getString(result.get("message")));
//		}
		
		return rtn;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public RestResult<Object> saveCorpInfo(Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		
		try {
			// 정보 update
			generalDao.updateGernal(DB.QRY_UPDATE_CORP_INFO, params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("고객사 정보 저장에 실패하였습니다.");
		}
		
		return rtn;
	}

	public RestResult<Object> selectProejctList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> list = generalDao.selectGernalList(DB.QRY_SELECT_PROJECT_LIST_BY_USERID, params);
		rtn.setData(list);

		return rtn;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> selectAttachFileInfo(Map<String, Object> params) throws Exception {
		return (Map<String, Object>) generalDao.selectGernalObject(DB.QRY_SELECT_ATTACH_FILE_INFO, params);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void setCertifyNumber(Map<String, Object> params) throws Exception {
		String certifyNumb = RandomStringUtils.randomNumeric(6);
		params.put("certifyNumb", certifyNumb);
		params.put("phone", params.get("chgHpNumber"));
		
		generalDao.updateGernal(DB.QRY_UPDATE_USER_SMS_CERTIFY_NUMB, params);
		
		commonService.sendNoti("sms", params);
	}
}
