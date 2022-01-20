package kr.co.uplus.cm.home.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.crypto.AesEncryptor;
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

		if ("Y".equals(smsmmsYn))
			sb.append("SMS/MMS,");
		if ("Y".equals(rcsYn))
			sb.append("RCS,");
		if ("Y".equals(kakaoYn))
			sb.append("카카오톡,");
		if ("Y".equals(pushYn))
			sb.append("PUSH,");
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

			if ("Y".equals(smsmmsYn))
				sb.append("SMS/MMS,");
			if ("Y".equals(rcsYn))
				sb.append("RCS,");
			if ("Y".equals(kakaoYn))
				sb.append("카카오톡,");
			if ("Y".equals(pushYn))
				sb.append("PUSH,");
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
		rtn.setData(rtnList);
		int rcnt = rtnList.size();
		
		if (rcnt == 0) return rtn; // 코드값이 없으면 끝낸다. 
		
		params.put("rtnList", rtnList);
		List<Object> subRtnList = generalDao.selectGernalList(DB.QRY_SELECT_CH_FAIL_CODE_COUNT_LIST, params);

		int idx = 0;
		int scnt = subRtnList.size();
		for (int i = 0; i < rcnt; i++) {
			Map<String, Object> rtnMap = (Map<String, Object>) rtnList.get(i);

			for (int j = idx; j < scnt; j++) {
				Map<String, Object> subMap = (Map<String, Object>) subRtnList.get(j);
//				log.info("Sub"+j+":"+rtnMap.get("resultCode")+":"+subMap.get("resultCode"));
				if (!rtnMap.get("resultCode").equals(subMap.get("resultCode"))) {
//					log.info("===================>"+rtnMap.get("resultCode"));
//					log.info(subRtnList.subList(idx, j));
					rtnMap.put("failCodeCnt", subRtnList.subList(idx, j));
					idx = j;
					break;
				}
			}
			
			if (i == rcnt-1) {
//				log.info("===================>"+rtnMap.get("resultCode"));
//				log.info(subRtnList.subList(idx, scnt));
				rtnMap.put("failCodeCnt", subRtnList.subList(idx, scnt));
			}
		}

		return rtn;
	}

	/**
	 * 대시보드(User Console) 당일 실시간 이용현황 시간대 조회
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectRtUsedTimeLineList(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_RT_USED_TIME_LINE_LIST, params);
		rtn.setData(rtnList);

		return rtn;
	}

	/**
	 * 대시보드(User Console) 당일 실시간 이용현황 조회
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectRtUsedDataList(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_RT_USED_DATA_LIST, params);
		rtn.setData(rtnList);

		return rtn;
	}

	/**
	 * 대시보드(Admin Console) 당월 이용현황 시간대 조회
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectMonthUsedTimeLineList(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_MONTH_USED_TIME_LINE_LIST, params);
		rtn.setData(rtnList);

		return rtn;
	}

	/**
	 * 대시보드(Admin Console) 당월 이용현황 조회
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectMonthUsedDataList(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_MONTH_USED_DATA_LIST, params);
		rtn.setData(rtnList);

		return rtn;
	}

	/**
	 * 대시보드(Admin Console) 최근 6개월간 이용현황 시간대 조회
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectSixMonthUsedTimeLineList(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_SIX_MONTH_USED_TIME_LINE_LIST, params);
		rtn.setData(rtnList);

		return rtn;
	}

	/**
	 * 대시보드(Admin Console) 최근 6개월간 이용현황 조회
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectSixMonthUsedDataList(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_SIX_MONTH_USED_DATA_LIST, params);
		rtn.setData(rtnList);

		return rtn;
	}
	
	public RestResult<Object> selectChMinList(Map<String, Object> params) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_CH_MIN_LIST, params);
		
		int smsCnt = 0;
		int lmsCnt = 0;
		int mmsCnt = 0;
		int pushCnt = 0;
		int alimTalkCnt = 0;
		int friendTalkCnt = 0;
		int rcsCnt = 0;
		
		// Map 형태로 통계 데이터 파싱
		for(Object rtnMap : rtnList) {
			Map<String, Object> objMap = (Map<String, Object>) rtnMap;
			String productCode = CommonUtils.getString(objMap.get("productCode"));
			if("SMS".equals(productCode)) {
				returnMap.put("smsTotCnt", objMap.get("totCnt"));
				returnMap.put("smsSendingCnt", objMap.get("sendingCnt"));
				returnMap.put("smsWaitRptCnt", objMap.get("waitRptCnt"));
				returnMap.put("smsSuccCnt", objMap.get("succCnt"));
				returnMap.put("smsFailCnt", objMap.get("failCnt"));
				smsCnt++;
			} else if("LMS".equals(productCode)) {
				returnMap.put("lmsTotCnt", objMap.get("totCnt"));
				returnMap.put("lmsSendingCnt", objMap.get("sendingCnt"));
				returnMap.put("lmsWaitRptCnt", objMap.get("waitRptCnt"));
				returnMap.put("lmsSuccCnt", objMap.get("succCnt"));
				returnMap.put("lmsFailCnt", objMap.get("failCnt"));
				lmsCnt++;
			} else if("MMS".equals(productCode)) {
				returnMap.put("mmsTotCnt", objMap.get("totCnt"));
				returnMap.put("mmsSendingCnt", objMap.get("sendingCnt"));
				returnMap.put("mmsWaitRptCnt", objMap.get("waitRptCnt"));
				returnMap.put("mmsSuccCnt", objMap.get("succCnt"));
				returnMap.put("mmsFailCnt", objMap.get("failCnt"));
				mmsCnt++;
			} else if("PUSH".equals(productCode)) {
				returnMap.put("pushTotCnt", objMap.get("totCnt"));
				returnMap.put("pushSendingCnt", objMap.get("sendingCnt"));
				returnMap.put("pushWaitRptCnt", objMap.get("waitRptCnt"));
				returnMap.put("pushSuccCnt", objMap.get("succCnt"));
				returnMap.put("pushFailCnt", objMap.get("failCnt"));
				pushCnt++;
			} else if("ALIMTALK".equals(productCode)) {
				returnMap.put("alimTotCnt", objMap.get("totCnt"));
				returnMap.put("alimSendingCnt", objMap.get("sendingCnt"));
				returnMap.put("alimWaitRptCnt", objMap.get("waitRptCnt"));
				returnMap.put("alimSuccCnt", objMap.get("succCnt"));
				returnMap.put("alimFailCnt", objMap.get("failCnt"));
				alimTalkCnt++;
			} else if("FRIENDTALK".equals(productCode)) {
				returnMap.put("friendTotCnt", objMap.get("totCnt"));
				returnMap.put("friendSendingCnt", objMap.get("sendingCnt"));
				returnMap.put("friendWaitRptCnt", objMap.get("waitRptCnt"));
				returnMap.put("friendSuccCnt", objMap.get("succCnt"));
				returnMap.put("friendFailCnt", objMap.get("failCnt"));
				friendTalkCnt++;
			} else if("RCS".equals(productCode)) {
				returnMap.put("rcsTotCnt", objMap.get("totCnt"));
				returnMap.put("rcsSendingCnt", objMap.get("sendingCnt"));
				returnMap.put("rcsWaitRptCnt", objMap.get("waitRptCnt"));
				returnMap.put("rcsSuccCnt", objMap.get("succCnt"));
				returnMap.put("rcsFailCnt", objMap.get("failCnt"));
				rcsCnt++;
			}
		}
		
		if(smsCnt==0) {
			returnMap.put("smsTotCnt", 0);
			returnMap.put("smsSendingCnt", 0);
			returnMap.put("smsWaitRptCnt", 0);
			returnMap.put("smsSuccCnt", 0);
			returnMap.put("smsFailCnt", 0);
		} 
		if(lmsCnt==0) {
			returnMap.put("lmsTotCnt", 0);
			returnMap.put("lmsSendingCnt", 0);
			returnMap.put("lmsWaitRptCnt", 0);
			returnMap.put("lmsSuccCnt", 0);
			returnMap.put("lmsFailCnt", 0);
		} 
		if(mmsCnt==0) {
			returnMap.put("mmsTotCnt", 0);
			returnMap.put("mmsSendingCnt", 0);
			returnMap.put("mmsWaitRptCnt", 0);
			returnMap.put("mmsSuccCnt", 0);
			returnMap.put("mmsFailCnt", 0);
		} 
		if(pushCnt==0) {
			returnMap.put("pushTotCnt", 0);
			returnMap.put("pushSendingCnt", 0);
			returnMap.put("pushWaitRptCnt", 0);
			returnMap.put("pushSuccCnt", 0);
			returnMap.put("pushFailCnt", 0);
		}
		if(alimTalkCnt==0) {
			returnMap.put("alimTotCnt", 0);
			returnMap.put("alimSendingCnt", 0);
			returnMap.put("alimWaitRptCnt", 0);
			returnMap.put("alimSuccCnt", 0);
			returnMap.put("alimFailCnt", 0);
		} 
		if(friendTalkCnt==0){
			returnMap.put("friendTotCnt", 0);
			returnMap.put("friendSendingCnt", 0);
			returnMap.put("friendWaitRptCnt", 0);
			returnMap.put("friendSuccCnt", 0);
			returnMap.put("friendFailCnt", 0);
		} 
		if(rcsCnt==0){
			returnMap.put("rcsTotCnt", 0);
			returnMap.put("rcsSendingCnt", 0);
			returnMap.put("rcsWaitRptCnt", 0);
			returnMap.put("rcsSuccCnt", 0);
			returnMap.put("rcsFailCnt", 0);
		}
		
		rtn.setData(returnMap);
		
		return rtn;
	}
	
	public RestResult<Object> selectCorpInfo(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		Object data = generalDao.selectGernalObject("dashboard.selectCorpInfo", params);
		rtn.setData(data);
		return rtn;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void saveBill(Map<String, Object> params) throws Exception{
		params.put("billStatus", "REQ");
		AesEncryptor encrypt = new AesEncryptor(); // 암호화
		if (StringUtils.isNotEmpty((String) params.get("napJumin"))) {
			params.put("napJumin", encrypt.encrypt((String) params.get("napJumin")));
		}
		if (StringUtils.isNotEmpty((String) params.get("cardValdEndYymm"))) {
			params.put("cardValdEndYymm", encrypt.encrypt((String) params.get("cardValdEndYymm")));
		}
		if (StringUtils.isEmpty((String) params.get("billId"))) {
			params.put("billId", "0");
			generalDao.insertGernal("signUp.insertBill", params);
		} else {
			generalDao.updateGernal("signUp.updateBill", params);	
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void delBill(Map<String, Object> params) throws Exception{
		int cnt = generalDao.deleteGernal("dashboard.deleteBill", params);
	}
	
	public RestResult<Object> selectBill(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		Map data = (Map) generalDao.selectGernalObject("dashboard.selectBill", params);
		if (data != null) {
			AesEncryptor encrypt = new AesEncryptor(); // 암호화
			if (StringUtils.isNotEmpty((String) data.get("napJumin"))) {
				data.put("napJumin", encrypt.decrypt((String) data.get("napJumin")));
			}
			if (StringUtils.isNotEmpty((String) data.get("cardValdEndYymm"))) {
				data.put("cardValdEndYymm", encrypt.decrypt((String) data.get("cardValdEndYymm")));
			}
		}
		rtn.setData(data);
		return rtn;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void saveSenderUp(Map<String, Object> params) throws Exception{
		params.put("status", "REQ");
		generalDao.deleteGernal("dashboard.deleteSenderUpSeq", params);
		generalDao.insertGernal("dashboard.insertSenderUpSeq", params);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void delSenderUp(Map<String, Object> params) throws Exception{
		int cnt = generalDao.deleteGernal("dashboard.deleteSenderUpSeq", params);
	}

}
