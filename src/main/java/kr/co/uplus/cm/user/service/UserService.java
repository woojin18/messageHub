package kr.co.uplus.cm.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;

@Service
public class UserService {

	@Autowired
	private GeneralDao generalDao;
	
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
	public RestResult<Object> registerUser(Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		// UserId 생성
		String userId = CommonUtils.getCommonId("MBR", 5);
		map.putAll(params);
		map.put("userId", userId);
					
		int resultCnt = generalDao.insertGernal(DB.QRY_INSERT_USER, map);
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
}
