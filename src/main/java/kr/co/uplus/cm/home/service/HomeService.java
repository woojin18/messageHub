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
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;

@Service
public class HomeService {

	@Autowired
	private GeneralDao generalDao;

	/**
	 * 대시보드 프로젝트 정보 조회
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

		if ("Y".equals(pushYn))
			sb.append("PUSH,");
		if ("Y".equals(kakaoYn))
			sb.append("카카오톡,");
		if ("Y".equals(rcsYn))
			sb.append("RCS,");
		if ("Y".equals(smsmmsYn))
			sb.append("SMS/MMS,");
		if ("Y".equals(moYn))
			sb.append("MO,");

		String useChStr = sb.toString();

		if (useChStr.length() > 0) {
			useChStr = useChStr.substring(0, useChStr.length() - 1);
		}

		projectInfo.put("useService", useChStr);

		rtnObj.put("projectInfo", projectInfo);
		rtn.setData(rtnObj);
		return rtn;
	}

	/**
	 * 대시보드 프로젝트 리스트 조회
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public RestResult<Object> selectProjectList(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_DASH_PROJECT_LIST, params);
		for (int i = 0; i < rtnList.size(); i++) {
			Map<String, Object> rtnMap = (Map<String, Object>) rtnList.get(i);
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(CommonUtils.getString(rtnMap.get("useChGrpInfo")));
			JSONObject jsonObj = (JSONObject) obj;

			String rcsYn = CommonUtils.getString(jsonObj.get("RCS"));
			String smsmmsYn = CommonUtils.getString(jsonObj.get("SMS/MMS"));
			String pushYn = CommonUtils.getString(jsonObj.get("PUSH"));
			String kakaoYn = CommonUtils.getString(jsonObj.get("KKO"));
			String moYn = CommonUtils.getString(jsonObj.get("MO"));

			StringBuffer sb = new StringBuffer();

			if ("Y".equals(pushYn))
				sb.append("PUSH,");
			if ("Y".equals(kakaoYn))
				sb.append("카카오톡,");
			if ("Y".equals(rcsYn))
				sb.append("RCS,");
			if ("Y".equals(smsmmsYn))
				sb.append("SMS/MMS,");
			if ("Y".equals(moYn))
				sb.append("MO,");

			String useChStr = sb.toString();

			if (useChStr.length() > 0) {
				useChStr = useChStr.substring(0, useChStr.length() - 1);
			}

			rtnMap.put("useService", useChStr);
		}
		rtn.setData(rtnList);

		return rtn;
	}

	/**
	 * 대시보드 공지사항 리스트 조회
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectNoticeList(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_DASH_NOTICE_LIST, params);
		rtn.setData(rtnList);

		return rtn;
	}

	/**
	 * 대시보드 채널별 성공/실패 카운트 조회
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public RestResult<Object> selectChTotCntInfo(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();
		Map<String, Object> rtnObj = new HashMap<String, Object>();

		Map<String, Object> chTotCntInfo = (Map<String, Object>) generalDao
				.selectGernalObject(DB.QRY_SELECT_CH_TOTAL_COUNT_INFO, params);

		rtnObj.put("chTotCntInfo", chTotCntInfo);
		rtn.setData(rtnObj);

		return rtn;
	}

	/**
	 * 대시보드 일자별 채널 성공/실패 카운트 조회
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectChSuccFailCntList(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_CH_SUCC_FAIL_COUNT_LIST, params);
		rtn.setData(rtnList);

		return rtn;
	}

	/**
	 * 대시보드 일자별 실패코드 조회
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectChFailCodeList(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_CH_FAIL_CODE_LIST, params);

		for (int i = 0; i < rtnList.size(); i++) {
			Map<String, Object> rtnMap = (Map<String, Object>) rtnList.get(i);
			params.put("resultCode", rtnMap.get("resultCode"));

			// 대시보드 일자별 실패코드 카운트 조회
			List<Object> subRtnList = generalDao.selectGernalList(DB.QRY_SELECT_CH_FAIL_CODE_COUNT_LIST, params);

			rtnMap.put("failCodeCnt", subRtnList);
		}
		rtn.setData(rtnList);

		return rtn;
	}

	/**
	 * 대시보드 채널별 성공/실패현황 조회
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectDayStatsList(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_DAY_STATS_LIST, params);
		rtn.setData(rtnList);

		return rtn;
	}

}
