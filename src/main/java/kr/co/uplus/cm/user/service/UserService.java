package kr.co.uplus.cm.user.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.signUp.service.SignUpService;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;

@Service
public class UserService {

	@Autowired
	private GeneralDao generalDao;
	
	@Autowired SignUpService signUpSvc;
	@Autowired CommonService commonService;
	
	/**
	 * 사용자 리스트 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectUserList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		if(params.containsKey("pageNo")
				&& CommonUtils.isNotEmptyObject(params.get("pageNo"))
				&& params.containsKey("listSize")
				&& CommonUtils.isNotEmptyObject(params.get("listSize"))) {
			rtn.setPageProps(params);
			if(rtn.getPageInfo() != null) {
				//카운트 쿼리 실행
				int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_USER_LIST_CNT, params);
				rtn.getPageInfo().put("totCnt", listCnt);
			}
		}

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_USER_LIST, params);
		rtn.setData(rtnList);
		return rtn;
	}
	
	/**
	 * 사용자 이용정지
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> stopUser(Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		
		int resultCnt = generalDao.updateGernal(DB.QRY_UPDATE_STOP_USER, params);
		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
			rtn.setData(params);
		}
			return rtn;
	}
	
	/**
	 * 사용자 이용정지 해제
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> releaseUser(Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		
		int resultCnt = generalDao.updateGernal(DB.QRY_UPDATE_RELEASE_USER, params);
		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
			rtn.setData(params);
		}
			return rtn;
	}
	
	/**
	 * 사용자 삭제
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> deleteUser(Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		
		int resultCnt = generalDao.updateGernal(DB.QRY_DELETE_USER, params);
		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
			rtn.setData(params);
		}
			return rtn;
	}
	
	/**
	 * 사용자 수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public RestResult<Object> modifyUser(Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.putAll(params);
		
		String roleCd = (String)map.get("roleCd");
		int resultCnt = generalDao.updateGernal(DB.QRY_UPDATE_USER, map);
		String isOwnerSelf = (String)map.get("isOwnerSelf");
		
		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			return rtn;
		}
		
		// OWNER 권한 변경. 기존 OWNER를 ADMIN으로 변경
		if(!StringUtils.isEmpty(roleCd) && roleCd.equalsIgnoreCase("OWNER") && isOwnerSelf.equalsIgnoreCase("F")) {
			resultCnt = generalDao.updateGernal(DB.QRY_UPDATE_USER_ROLE_ADMIN, map);
		}

		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
			rtn.setData(params);
		}
			return rtn;
	}

	/**
	 * 사용자 등록
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public RestResult<Object> registerUser(Map<String, Object> params) {
		
		RestResult<Object> rtn = new RestResult<Object>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		// UserId 생성
		String userId = CommonUtils.getCommonId("MBR", 5);
		map.putAll(params);
		map.put("userId", userId);
		map.put("email", params.get("loginId"));

		try {
			// 기존 삭제처리된 사용자 LOGIN 아이디 변경
			generalDao.updateGernal(DB.QRY_UPDATE_DEL_USER_LOGINID, params);
			
			generalDao.insertGernal(DB.QRY_INSERT_USER, map);
			// 난수 생성
			String randomNum = CommonUtils.randomGeneration(10);
			map.put("authKey", randomNum);

			// 위치 설정
			map.put("location",  "/login/setUserPwd");
			
			// 시간 설정
			SimpleDateFormat	sdformat	= new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			Calendar			cal			= Calendar.getInstance();
			cal.add(Calendar.HOUR, 1);
			String				time		= sdformat.format(cal.getTime());
			map.put("time", time);
			
			// 메일 인증 table insert
			generalDao.insertGernal(DB.QRY_INSERT_MAIL_CERTIFY, map);
			
			// 메일 전송
//			signUpSvc.sendMail(map, "/login/setUserPwd");
			commonService.sendNoti("mail", map);
			
			rtn.setSuccess(true);
			rtn.setData(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
		}
		
		return rtn;
	}
	
	/**
	 * 사용자 중복체크
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> checkDupcUser(Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		
		int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_USER_DUPC_CNT, params);
		
		if(listCnt >= 1) {
			rtn.setSuccess(false);
		} else {
			rtn.setSuccess(true);
		}
		
		return rtn;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public RestResult<Object> sendCertifyMail(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		String randomNum = CommonUtils.randomGeneration(10);
		params.put("authKey", randomNum);
		
		SimpleDateFormat	sdformat	= new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Calendar			cal			= Calendar.getInstance();
		cal.add(Calendar.HOUR, 1);
		String				time		= sdformat.format(cal.getTime());
		params.put("time", time);
		
		try {
			// 메일 인증 table insert
			generalDao.insertGernal(DB.QRY_INSERT_MAIL_CERTIFY, params);
			
			// 메일 전송
//			signUpSvc.sendMail(params, location);
			commonService.sendNoti("mail", params);
			
			rtn.setSuccess(true);
		} catch (MessagingException e) {
			rtn.setSuccess(false);
			rtn.setMessage("메일 전송 중 오류가 발생했습니다.");
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("오류가 발생했습니다.");
		}
			
		return rtn;
	}
}
