package kr.co.uplus.cm.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.crypto.Sha256PasswordEncoder;
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
	
	@Autowired
	private Sha256PasswordEncoder sha256;
	/**
	 * 프로젝트 기본정보 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectProjectBaseInfo(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		Map<String, Object> rtnObj = new HashMap<String, Object>();
		
		Object baseInfo = generalDao.selectGernalObject(DB.QRY_SELECT_PROJECT_BASE_INFO, params);
		List<Object> apiKeyList = generalDao.selectGernalList(DB.QRY_SELECT_APIKEY_LIST, params);
		
		rtnObj.put("baseInfo", baseInfo);
		rtnObj.put("apiKeyList", apiKeyList);
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
	
	/**
	 * APIKEY 목록조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectApiKeyList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
	
		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_APIKEY_LIST, params);
		rtn.setData(rtnList);
		return rtn;
	}
	
	/**
	 * APIKEY 저장/수정
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public RestResult<Object> saveApiKey(Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		int resultCnt = 0;
		ArrayList<String> ipList = null;
		String apiKey = "";
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.putAll(params);
		
		String saveStatus = CommonUtils.getString(params.get("saveStatus"));
		String ipChkYn= CommonUtils.getString(params.get("ipChkYn"));
		
		// PassWord (임시) : SHA512로 바꿀것
		map.put("apiPwd", sha256.encode(CommonUtils.getString(params.get("apiPwd"))));
		
		// IPLIST JSON 값 처리
		if(ipChkYn.equals("Y")) {
			ipList = (ArrayList<String>) params.get("ipList");
			String jsonInfo = "";
			String ip = "";
			jsonInfo += "[";
			
			for (int i = 0; i < ipList.size(); i++) {
				ip = ipList.get(i);
				if(i==0) {
					jsonInfo += ("\"" + ip + "\"");
				} else {
					jsonInfo += (", \"" + ip + "\"");
				}
			}
			jsonInfo += "]";
			map.put("jsonInfo", jsonInfo);
		}
		
		if(saveStatus.equals("R")) {
			apiKey = CommonUtils.getCommonId("API", 5); // APIKEY 생성
			map.put("apiKey", apiKey);
			
			resultCnt = generalDao.insertGernal(DB.QRY_INSERT_APIKEY, map);
		} else {
			resultCnt = generalDao.insertGernal(DB.QRY_UPDATE_APIKEY, map);
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
