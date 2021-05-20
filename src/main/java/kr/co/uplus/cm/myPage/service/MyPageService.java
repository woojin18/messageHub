package kr.co.uplus.cm.myPage.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.crypto.Sha256PasswordEncoder;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;

@Service
public class MyPageService {
	
	@Autowired
	private GeneralDao generalDao;

	@Autowired
	private Sha256PasswordEncoder sha256;
	
	public RestResult<Object> selectMemberInfo(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		Object rtnObj = generalDao.selectGernalObject(DB.QRY_SELECT_MEMBER_INFO, params);
		rtn.setData(rtnObj);

		return rtn;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public RestResult<Object> saveMemberInfo(Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		
		String loginPwd = CommonUtils.getString(params.get("loginPwd"));
		
		try {
			params.put("loginPwd", sha256.encode(loginPwd));
			generalDao.updateGernal(DB.QRY_UPDATE_MEMBER_INFO, params);
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

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public RestResult<Object> deleteQnaInfo(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		generalDao.deleteGernal(DB.QRY_DELETE_QUEST_BOARD, params);
		
		return rtn;
	}
}
