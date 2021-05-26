package kr.co.uplus.cm.project.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;

@Service
public class BaseInfoService {

	@Autowired
	private GeneralDao generalDao;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 프로젝트 기본정보 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectProjectBaseInfo(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		Object rtnObj = generalDao.selectGernalObject(DB.QRY_SELECT_PROJECT_BASE_INFO, params);
		rtn.setData(rtnObj);
		return rtn;
	}
	
	/**
	 * 프로젝트 기본정보 저장
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> saveProjectBaseInfo(Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		int resultCnt = 0;
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.putAll(params);
		
		// 사용체널 JSON 값 처리
		String jsonInfo = "";
		jsonInfo += "{";
		jsonInfo += "	\"RCS\"		: \"" + CommonUtils.getString(params.get("radioRcs")) + "\",";
		jsonInfo += "	\"SMSMMS\"	: \"" + CommonUtils.getString(params.get("radioMms")) + "\",";
		jsonInfo += "	\"PUSH\"	: \"" + CommonUtils.getString(params.get("radioPush")) + "\",";
		jsonInfo += "	\"KAKAO\"	: \"" + CommonUtils.getString(params.get("radioKko")) + "\",";
		jsonInfo += "	\"MO\"		: \"" + CommonUtils.getString(params.get("radioMo")) + "\"";
		jsonInfo += "}";
		
		map.put("jsonInfo", jsonInfo);
		
		resultCnt = generalDao.updateGernal(DB.QRY_UPDATE_PROJECT_BASE_INFO, map);
		
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
