package kr.co.uplus.cm.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;

@Service
public class MemberService {

	@Autowired
	private GeneralDao generalDao;
	
	/**
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectProjectMemberList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		if(params.containsKey("pageNo")
				&& CommonUtils.isNotEmptyObject(params.get("pageNo"))
				&& params.containsKey("listSize")
				&& CommonUtils.isNotEmptyObject(params.get("listSize"))) {
			rtn.setPageProps(params);
			if(rtn.getPageInfo() != null) {
				//카운트 쿼리 실행
				int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_PROJECT_MEMBER_LIST_CNT, params);
				rtn.getPageInfo().put("totCnt", listCnt);
			}
		}
		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_PROJECT_MEMBER_LIST, params);
		rtn.setData(rtnList);
		return rtn;
	}
	
	/**
	 * 멤버 추가
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> saveProjectMember(Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		
		int resultCnt = 0;
		resultCnt = generalDao.insertGernal(DB.QRY_INSERT_PROJECT_MEMBER, params);
		
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
	 * 멤버 삭제
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> deleteProjectMember(Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		
		int resultCnt = 0;
		resultCnt = generalDao.deleteGernal(DB.QRY_DELETE_PROJECT_MEMBER, params);
		
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
	 * 사용자 목록 조회
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
				int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_PROJECT_USER_LIST_CNT, params);
				rtn.getPageInfo().put("totCnt", listCnt);
			}
		}
		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_PROJECT_USER_LIST, params);
		rtn.setData(rtnList);
		return rtn;
	}
	
	/**
	 * 프로젝트 멤버 등록
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public RestResult<Object> registerMember(Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		int resultCnt = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.putAll(params);
		
		ArrayList<String> userList = (ArrayList<String>)map.get("userList");
		
		for (String userId : userList) {
			map.put("userId", userId);
			resultCnt = generalDao.insertGernal(DB.QRY_INSERT_PROJECT_MEMBER, map);
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
}
