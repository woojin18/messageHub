package kr.co.uplus.cm.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cm.common.consts.Const.CmdTgt;
import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;
import yoyozo.security.SHA;

@Service
public class BaseInfoService {

	@Autowired
	private GeneralDao generalDao;

	@Autowired
	private CommonService commonService;

//	@Autowired
//	private Sha256PasswordEncoder sha256;
	/**
	 * 프로젝트 기본정보 조회
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public RestResult<Object> selectProjectBaseInfo(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		Map<String, Object> rtnObj = new HashMap<String, Object>();

		Map<String, Object> baseInfo = (Map<String, Object>) generalDao
				.selectGernalObject(DB.QRY_SELECT_PROJECT_BASE_INFO, params);

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(CommonUtils.getString(baseInfo.get("useChGrpInfo")));
		JSONObject jsonObj = (JSONObject) obj;

		String rcsYn = CommonUtils.getString(jsonObj.get("RCS"));
		String smsmmsYn = CommonUtils.getString(jsonObj.get("SMS/MMS"));
		String pushYn = CommonUtils.getString(jsonObj.get("PUSH"));
		String kakaoYn = CommonUtils.getString(jsonObj.get("KKO"));
		String moYn = CommonUtils.getString(jsonObj.get("MO"));

		baseInfo.put("rcsYn", rcsYn);
		baseInfo.put("smsmmsYn", smsmmsYn);
		baseInfo.put("pushYn", pushYn);
		baseInfo.put("kakaoYn", kakaoYn);
		baseInfo.put("moYn", moYn);

		List<Object> apiKeyList = generalDao.selectGernalList(DB.QRY_SELECT_APIKEY_LIST, params);

		rtnObj.put("baseInfo", baseInfo);
		rtnObj.put("apiKeyList", apiKeyList);
		rtn.setData(rtnObj);
		return rtn;
	}

	/**
	 * 프로젝트 기본정보 저장
	 * 
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
		jsonInfo += "	\"SMS/MMS\"	: \"" + CommonUtils.getString(params.get("radioMms")) + "\",";
		jsonInfo += "	\"PUSH\"	: \"" + CommonUtils.getString(params.get("radioPush")) + "\",";
		jsonInfo += "	\"KKO\"		: \"" + CommonUtils.getString(params.get("radioKko")) + "\",";
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
	 * 
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
	 * 
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
		SHA sha512 = new SHA(512);

		String saveStatus = CommonUtils.getString(params.get("saveStatus"));
		String ipChkYn = CommonUtils.getString(params.get("ipChkYn"));
		String webSenderYn = CommonUtils.getString(params.get("webSenderYn"));
		
		map.putAll(params);
		if (CommonUtils.isNotEmptyObject(params.get("apiPwd"))) {
			map.put("apiPwd", sha512.encryptToBase64(CommonUtils.getString(params.get("apiPwd"))));
		}

		// Update API Password 확인
		// 웹 사용일 경우 발송 체크만 수정이 가능하도록 처리
		if (saveStatus.equals("U") && webSenderYn.equals("N")) {
			int cnt = generalDao.selectGernalCount(DB.QRY_SELECT_APIKEY_PASSWORD, map);

			if (cnt == 0) {
				rtn.setSuccess(false);
				rtn.setMessage("비밀번호가 일치하지 않습니다.");

				return rtn;
			}
		}

		// IPLIST JSON 값 처리
		if (ipChkYn.equals("Y")) {
			ipList = (ArrayList<String>) params.get("ipList");
			String jsonInfo = "";
			String ip = "";
			jsonInfo += "[";

			for (int i = 0; i < ipList.size(); i++) {
				ip = ipList.get(i);
				if (i == 0) {
					jsonInfo += ("\"" + ip + "\"");
				} else {
					jsonInfo += (", \"" + ip + "\"");
				}
			}
			jsonInfo += "]";
			map.put("jsonInfo", jsonInfo);
		}

		if (saveStatus.equals("R")) {
			// api key 5개 이상인지 체크
			int apiKeyCnt = generalDao.selectGernalList(DB.QRY_SELECT_APIKEY_LIST, params).size();
			
			if( apiKeyCnt >= 5 ) {
				rtn.setSuccess(false);
				rtn.setMessage("API Key는 5개까지 발급됩니다.");
			}
			
			apiKey = CommonUtils.getCommonId("API", 5); // APIKEY 생성
			map.put("apiKey", apiKey);

			resultCnt = generalDao.insertGernal(DB.QRY_INSERT_APIKEY, map);
		} else {
			// 변경 비밀번호가 있을경우
			String changeApiPwd = CommonUtils.getString(params.get("changeApiPwd"));
			if( !"".equals(changeApiPwd) ) {
				map.put("apiPwd", sha512.encryptToBase64(changeApiPwd));
			}
			
			// 웹 사용 여부가 Y인 경우 수정은 발송체크만 가능하도록 처리
			if(webSenderYn.equals("N")) {
				resultCnt = generalDao.insertGernal(DB.QRY_UPDATE_APIKEY, map);
			} else {
				resultCnt = generalDao.insertGernal(DB.QRY_UPDATE_APIKEY_WEBSENDER, map);
			}
		}

		// redis 테이블 처리
		commonService.updateCmCmdForRedis(CmdTgt.APIKEY);

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
