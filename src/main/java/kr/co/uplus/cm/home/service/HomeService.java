package kr.co.uplus.cm.home.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;

@Service
public class HomeService {

	@Autowired
	private GeneralDao generalDao;

	@Autowired
	private CommonService commonService;

	/**
	 * 프로젝트 정보 조회
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public RestResult<Object> selectProjectInfo(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		Map<String, Object> rtnObj = new HashMap<String, Object>();

		Map<String, Object> projectInfo = (Map<String, Object>) generalDao
				.selectGernalObject(DB.QRY_SELECT_DASH_PROJECT_INFO, params);

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(CommonUtils.getString(projectInfo.get("useChGrpInfo")));
		JSONObject jsonObj = (JSONObject) obj;

		String rcsYn = CommonUtils.getString(jsonObj.get("RCS"));
		String smsmmsYn = CommonUtils.getString(jsonObj.get("SMS/MMS"));
		String pushYn = CommonUtils.getString(jsonObj.get("PUSH"));
		String kakaoYn = CommonUtils.getString(jsonObj.get("KKO"));
		String moYn = CommonUtils.getString(jsonObj.get("MO"));

		StringBuffer sb = new StringBuffer();
		int cnt = 0;

		if ("Y".equals(pushYn)) {
			sb.append("PUSH");
			cnt++;
		}
		if ("Y".equals(kakaoYn)) {
			if (cnt == 0) {
				sb.append("카카오톡");
			} else {
				sb.append(", 카카오톡");
			}
			cnt++;
		}
		if ("Y".equals(rcsYn)) {
			if (cnt == 0) {
				sb.append("RCS");
			} else {
				sb.append(", RCS");
			}
			cnt++;
		}
		if ("Y".equals(smsmmsYn)) {
			if (cnt == 0) {
				sb.append("SMS/MMS");
			} else {
				sb.append(", SMS/MMS");
			}
			cnt++;
		}
		if ("Y".equals(moYn)) {
			if (cnt == 0) {
				sb.append("MO");
			} else {
				sb.append(", MO");
			}
			cnt++;
		}
		projectInfo.put("useService", sb.toString());

		rtnObj.put("projectInfo", projectInfo);
		rtn.setData(rtnObj);
		return rtn;
	}
}
