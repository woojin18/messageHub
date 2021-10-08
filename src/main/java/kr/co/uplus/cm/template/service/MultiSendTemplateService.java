package kr.co.uplus.cm.template.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.consts.Const.CmdTgt;
import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.config.ApiConfig;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

/**
 * <pre>
 * TemplateService
 * 각 채널별 템플릿 생성 및 템플릿 리스트 관리 Service
 * </pre>
 *
 * @author liam (Lee Sung Min)
 * @Date : 2021.03.25.
 * @Version : 1.0 Copyright 2020 LG Uplus Corp. All Rights Reserved.
 */
@Log4j2
@Service
public class MultiSendTemplateService {

	@Autowired
	private CommonService commonService;

	@Autowired
	private GeneralDao generalDao;

	/**
	 * 통합 템플릿 리스트 조회
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectMultiSendTemplateList(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		if (params.containsKey("pageNo") && CommonUtils.isNotEmptyObject(params.get("pageNo"))
				&& params.containsKey("listSize") && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
			rtn.setPageProps(params);
			if (rtn.getPageInfo() != null) {
				// 카운트 쿼리 실행
				int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_MULTISEND_TMPLT_LIST_CNT, params);
				rtn.getPageInfo().put("totCnt", listCnt);
			}
		}

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_MULTISEND_TMPLT_LIST, params);
		rtn.setData(rtnList);

		return rtn;
	}

	/**
	 * 통합 템플릿 정보 조회
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectMultiSendTemplateInfo(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_MULTISEND_TMPLT_DETAIL, params);

		for (int i = 0; i < rtnList.size(); i++) {
			Map<String, Object> rtnMap = (Map<String, Object>) rtnList.get(i);
			String chTypeList = (String) rtnMap.get("chTypeList");
			String msgKind = (String) rtnMap.get("msgKind");

			if (chTypeList.contains("PUSH")) {
				if ("A".equals(msgKind)) {
					String pushTitle = (String) rtnMap.get("pushTitle");
					String pushMsg = (String) rtnMap.get("pushMsg");
					String pushRcvblcInput = (String) rtnMap.get("pushRcvblcInput");

					if (!"".equals(pushTitle)) {
						pushTitle = pushTitle.replace("(광고)", "");
						pushMsg = pushMsg.replace("\n"+pushRcvblcInput, "");
						rtnMap.put("pushTitle", pushTitle);
					} else {
						pushMsg = pushMsg.replace("(광고)", "");
						pushMsg = pushMsg.replace("\n"+pushRcvblcInput, "");
					}
					rtnMap.put("pushMsg", pushMsg);
				}
			}
			if (chTypeList.contains("RCS")) {
				String rcsButton0Data = (String) rtnMap.get("rcsButton0Data");
				if (!"".equals(rcsButton0Data) && rcsButton0Data != null) {
					JSONParser parser = new JSONParser();
					JSONObject obj = (JSONObject) parser.parse(rcsButton0Data);
					JSONArray jsonArr = null;
					jsonArr = (JSONArray) obj.get("suggestions");
					rtnMap.put("rcsButton0Data", jsonArr);
				}
				String rcsButton1Data = (String) rtnMap.get("rcsButton1Data");
				if (!"".equals(rcsButton1Data) && rcsButton1Data != null) {
					JSONParser parser = new JSONParser();
					JSONObject obj = (JSONObject) parser.parse(rcsButton1Data);
					JSONArray jsonArr = null;
					jsonArr = (JSONArray) obj.get("suggestions");
					rtnMap.put("rcsButton1Data", jsonArr);
				}
				String rcsButton2Data = (String) rtnMap.get("rcsButton2Data");
				if (!"".equals(rcsButton2Data) && rcsButton2Data != null) {
					JSONParser parser = new JSONParser();
					JSONObject obj = (JSONObject) parser.parse(rcsButton2Data);
					JSONArray jsonArr = null;
					jsonArr = (JSONArray) obj.get("suggestions");
					rtnMap.put("rcsButton2Data", jsonArr);
				}
				String rcsButton3Data = (String) rtnMap.get("rcsButton3Data");
				if (!"".equals(rcsButton3Data) && rcsButton3Data != null) {
					JSONParser parser = new JSONParser();
					JSONObject obj = (JSONObject) parser.parse(rcsButton3Data);
					JSONArray jsonArr = null;
					jsonArr = (JSONArray) obj.get("suggestions");
					rtnMap.put("rcsButton3Data", jsonArr);
				}
				String rcsButton4Data = (String) rtnMap.get("rcsButton4Data");
				if (!"".equals(rcsButton4Data) && rcsButton4Data != null) {
					JSONParser parser = new JSONParser();
					JSONObject obj = (JSONObject) parser.parse(rcsButton4Data);
					JSONArray jsonArr = null;
					jsonArr = (JSONArray) obj.get("suggestions");
					rtnMap.put("rcsButton4Data", jsonArr);
				}
				String rcsButton5Data = (String) rtnMap.get("rcsButton5Data");
				if (!"".equals(rcsButton5Data) && rcsButton5Data != null) {
					JSONParser parser = new JSONParser();
					JSONObject obj = (JSONObject) parser.parse(rcsButton5Data);
					JSONArray jsonArr = null;
					jsonArr = (JSONArray) obj.get("suggestions");
					rtnMap.put("rcsButton5Data", jsonArr);
				}
				if ("CELL".equals(rtnMap.get("rcsPrdType"))) {
					String content = (String) rtnMap.get("rcsStyleContent");
					JSONParser parser = new JSONParser();
					JSONObject obj = (JSONObject) parser.parse(content);
					JSONArray jsonArr = null;
					jsonArr = (JSONArray) obj.get("content");
					String[] styleInput = new String[jsonArr.size()];
					String[] styleInputSec = new String[jsonArr.size()];
					Boolean[] rcsStyleChk = new Boolean[jsonArr.size()];
					for (int j = 0; j < jsonArr.size(); j++) {
						JSONObject styleInputObj = (JSONObject) jsonArr.get(j);
						styleInput[j] = CommonUtils.getString(styleInputObj.get("description0"));
						styleInputSec[j] = CommonUtils.getString(styleInputObj.get("description1"));
						if ("".equals(CommonUtils.getString(styleInputObj.get("description1")))) {
							rcsStyleChk[j] = true;
						} else {
							rcsStyleChk[j] = false;
						}
					}
					rtnMap.put("rcsStyleInput", styleInput);
					rtnMap.put("rcsStyleInputSec", styleInputSec);
					rtnMap.put("rcsStyleChk", rcsStyleChk);
				}
			}
			if (chTypeList.contains("SMSMMS")) {
				String smsSendType = (String) rtnMap.get("smsSendType");
				if ("A".equals(msgKind)) {
					String smsTitle = (String) rtnMap.get("smsTitle");
					String smsContent = (String) rtnMap.get("smsContent");
					String smsRcvblcNumber = (String) rtnMap.get("smsRcvblcNumber");
					if ("S".equals(smsSendType)) {
						smsContent = smsContent.replace("(광고)", "");
						smsContent = smsContent.replace("\n"+smsRcvblcNumber, "");
						rtnMap.put("smsContent", smsContent);
					} else if ("M".equals(smsSendType)) {
						smsTitle = smsTitle.replace("(광고)", "");
						smsContent = smsContent.replace("\n"+smsRcvblcNumber, "");
						rtnMap.put("smsTitle", smsTitle);
						rtnMap.put("smsContent", smsContent);
					}
				}
			}
		}

		rtn.setData(rtnList);

		return rtn;
	}

	/**
	 * 통합 템플릿 저장 처리
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public RestResult<Object> insertMultiSendTemplate(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		int resultCnt = 0;
		ArrayList<String> arrChannelList = (ArrayList<String>) params.get("checkedChannel");
		String[] checkChannelArr = new String[arrChannelList.size()];
		int size = 0;
		for (String channel : arrChannelList) {
			checkChannelArr[size++] = channel;
		}

		StringBuffer sb = new StringBuffer();
		sb.append("[");
		StringBuffer sbChannel = new StringBuffer();
		String chTypeList = "";
		for (int i = 0; i < checkChannelArr.length; i++) {
			chTypeList = chTypeList + (checkChannelArr[i]);
			if (i < checkChannelArr.length - 1)
				chTypeList = chTypeList + ",";
		}

		for (int i = 0; i < checkChannelArr.length; i++) {
			sb.append("{");
			sb.append(sbChannel.toString()); // 채널순서를 기억하기 위해 추가
			if (checkChannelArr[i].equalsIgnoreCase("PUSH")) {
				// PUSH ====================================================================
				LinkedHashMap<String, String> pushImgInfoMap = (LinkedHashMap<String, String>) params
						.get("pushImgInfo");

				sb.append("\"chTypeList\" : \"" + chTypeList + "\",");
				sb.append("\"ch\" : \"PUSH\",");// 발송채널

				sb.append("\"data\" : { ");

				if ("A".equals(params.get("msgKind"))) {
					if (!"".equals(params.get("pushTitle"))) {
						sb.append("\"title\" : \"(광고)" + params.get("pushTitle") + "\","); // 제목
						sb.append("\"msg\" : \"" + JSONObject.escape((String) params.get("pushContent")) + JSONObject.escape("\n" + (String) params.get("pushHowToDenyReceipt")) + "\",");
					} else {
						sb.append("\"title\" : \"" + params.get("pushTitle") + "\","); // 제목
						sb.append("\"msg\" : \"(광고)" + JSONObject.escape((String) params.get("pushContent")) + JSONObject.escape("\n" + (String) params.get("pushHowToDenyReceipt")) + "\",");
					}
				} else {
					sb.append("\"title\" : \"" + params.get("pushTitle") + "\","); // 제목
					sb.append("\"msg\" : \"" + JSONObject.escape((String) params.get("pushContent")) + "\","); // 메시지
				}
				sb.append("\"appId\" : \"" + params.get("pushAppId") + "\","); // APP ID
				sb.append("\"serviceCode\" : \"" + params.get("pushSend") + "\","); // 발송타입(ALL, FCM, APNS)
				sb.append("\"rcvblcInput\" : \"" + params.get("pushHowToDenyReceipt") + "\""); // 수신거부방법

				if (CommonUtils.isNotEmptyObject(params.get("adtnInfo"))) {
					sb.append(",\"ext\": { ");
					if (CommonUtils.isNotEmptyObject(pushImgInfoMap.get("imgUrl"))) {
						sb.append("\"imageUrl\" : \"" + pushImgInfoMap.get("imgUrl") + "\","); // 부가정보
					}
					sb.append("\"data1\" : \"" + params.get("adtnInfo") + "\""); // 부가정보
					sb.append("} ");
				}

				if (pushImgInfoMap.containsKey("fileId")) {
					sb.append(",\"fileId\" : \"" + pushImgInfoMap.get("fileId") + "\""); // 이미지 파일ID
				}
				sb.append("} ");
			} else if (checkChannelArr[i].equalsIgnoreCase("RCS")) {
				System.out.println(">>>>service 003  RCS 001 : " + (int) params.get("rcsTemplateTable"));
				// RCS ====================================================================
				sb.append("\"chTypeList\" : \"" + chTypeList + "\",");
				sb.append("\"ch\" : \"RCS\",");// 발송채널

				sb.append("\"data\" : { ");
				sb.append("\"callback\": \"" + params.get("callback") + "\","); // 발신번호
				if ("I".equals(params.get("msgKind"))) {
					sb.append("\"header\" : \"0\","); // 정보성 메시지
				} else if ("A".equals(params.get("msgKind"))) {
					sb.append("\"header\" : \"1\","); // 광고성 메시지
					sb.append("\"footer\" : \"" + params.get("rcsBlockNumber") + "\","); // 무료수신거부 번호, header의 값이 광고성일 때 footer 값을 포함하지 않고 발송하면 실패처리
				}
				sb.append("\"copyAllowed\" : \"false\","); // 복사/공유 허용여부
				sb.append("\"expiryOption\" : \"2\","); // expire 옵션(1:72시간, 2:30초)
				sb.append("\"agencyId\" : \"lguplus\","); // 대행사 아이디
				sb.append("\"brandNm\" : \"" + params.get("brandNm") + "\", "); //

				if ((int) params.get("rcsTemplateTable") == 0) {//
					// RCS FREE TYPE
					// ====================================================================
					sb.append("\"rcsPrdType\" : \"FREE\","); // RCS상품타입(프리 템플릿) rcsTemplateTable => 0
					String messageBaseId = selectMessageBaseId(params);
					if ("".equals(messageBaseId) || "null".equals(messageBaseId) || messageBaseId == null) {
						throw new Exception("템플릿 상품에 해당하는 메시지베이스ID가 존재하지 않습니다.");
					}
					sb.append("\"messagebaseId\": \"" + messageBaseId + "\","); // cm.CM_RCS_MSGBASE의 MESSAGEBASE_ID값을 설정

					sb.append("\"mergeData\": [{ ");
					sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcs0Content")) + "\""); // 메시지
					sb.append("	}]");
				} else if ((int) params.get("rcsTemplateTable") == 1) {
					// RCS DESCRIPTION TYPE
					// ====================================================================
					sb.append("\"rcsPrdType\" : \"DESCRIPTION\","); // RCS상품타입(서술 승인템플릿) rcsTemplateTable => 1
					sb.append("\"messagebaseId\": \"" + params.get("rcsDesMessagebaseId") + "\","); // 서술형 템플릿의 RCS MESSAGEBASE_ID 를 설정
					sb.append("\"messagebaseformId\": \"" + params.get("rcsDesMessagebaseformId") + "\","); // 서술형 템플릿의 RCS MESSAGEBASEFORM_ID 를 설정
					sb.append("\"rcsDesFormNm\": \"" + params.get("rcsDesFormNm") + "\","); // 서술형 템플릿의 RCS MESSAGEBASEFORM_ID 를 설정

					sb.append("\"mergeData\": [{ ");
					sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcs0Content")) + "\" "); // 메시지
					sb.append("	}]");

					List<Map<String, Object>> buttonInfoList = null;
					if (params.containsKey("rcsDesButtons")) {
						buttonInfoList = (List<Map<String, Object>>) params.get("rcsDesButtons");
					}

					if (buttonInfoList != null && buttonInfoList.size() > 0) {
						sb.append(newButtonAddStr(buttonInfoList));
					} else {
						sb.append(",\"buttons\": []");
					}
				} else if ((int) params.get("rcsTemplateTable") == 2) {
					// RCS CELL(STYLE) TYPE
					// ====================================================================
					sb.append("\"rcsPrdType\" : \"CELL\","); // RCS상품타입(스타일 승인템플릿) rcsTemplateTable => 2
					sb.append("\"messagebaseId\": \"" + params.get("rcsStyleMessagebaseId") + "\","); // 스타일형 템플릿의 RCS MESSAGEBASE_ID 를 설정
					sb.append("\"messagebaseformId\": \"" + params.get("rcsStyleMessagebaseformId") + "\","); // 스타일형 템플릿의 RCS MESSAGEBASEFORM_ID 를 설정
					sb.append("\"rcsStyleFormNm\": \"" + params.get("rcsStyleFormNm") + "\","); // 서술형 템플릿의 RCS MESSAGEBASEFORM_ID 를 설정

					ArrayList<String> rcsStyleInput = (ArrayList<String>) params.get("rcsStyleInput");
					ArrayList<String> rcsStyleInputSec = (ArrayList<String>) params.get("rcsStyleInputSec");
					sb.append("\"mergeData\": [{\"content\":[");
					for (int j = 0; j < Integer.parseInt(params.get("rcsStyleContentCnt").toString()); j++) {
						sb.append("{");
						sb.append("	\"description0\" : \"" + JSONObject.escape(rcsStyleInput.get(j)) + "\" "); // 메시지
						if (!"".equals(rcsStyleInputSec.get(j)) && rcsStyleInputSec.get(j) != null) {
							sb.append("	,\"description1\" : \"" + JSONObject.escape(rcsStyleInputSec.get(j)) + "\" "); // 메시지
						}
						sb.append("}");
						if (j < Integer.parseInt(params.get("rcsStyleContentCnt").toString()) - 1)
							sb.append(", ");
					}
					sb.append("]}]");

					List<Map<String, Object>> buttonInfoList = null;
					if (params.containsKey("rcsStyleButtons")) {
						buttonInfoList = (List<Map<String, Object>>) params.get("rcsStyleButtons");
					}

					if (buttonInfoList != null && buttonInfoList.size() > 0) {
						sb.append(newButtonAddStr(buttonInfoList));
					} else {
						sb.append(",\"buttons\": []");
					}

				} else if ((int) params.get("rcsTemplateTable") == 3) {
					// RCS SMS TYPE
					// ====================================================================
					sb.append("\"rcsPrdType\" : \"SMS\","); // RCS상품타입(SMS 템플릿) rcsTemplateTable => 3
					sb.append("\"messagebaseId\": \"SS000000\","); // SMS 템플릿의 RCS MESSAGEBASE_ID 를 설정

					sb.append("\"mergeData\": [{ ");
					sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcs0Content")) + "\" "); // 메시지
					sb.append("	}]");

					List<Map<String, Object>> buttonInfoList = null;
					if (params.containsKey("rcsSMSButtons")) {
						buttonInfoList = (List<Map<String, Object>>) params.get("rcsSMSButtons");
					}

					if (buttonInfoList != null && buttonInfoList.size() > 0) {
						sb.append(newButtonAddStr(buttonInfoList));
					} else {
						sb.append(",\"buttons\": [{}]");
					}

				} else if ((int) params.get("rcsTemplateTable") == 4) {
					// RCS LMS TYPE
					// ====================================================================
					sb.append("\"rcsPrdType\" : \"LMS\","); // RCS상품타입(LMS 템플릿) rcsTemplateTable => 4
					sb.append("\"messagebaseId\": \"SL000000\","); // LMS 템플릿의 RCS MESSAGEBASE_ID 를 설정

					sb.append("\"mergeData\": [{ ");
					sb.append("	\"title\" : \"" + JSONObject.escape((String) params.get("rcs0Title")) + "\", "); // 제목
					sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcs0Content")) + "\" "); // 메시지
					sb.append("	}]");

					List<Map<String, Object>> buttonInfoList = null;
					if (params.containsKey("rcsLMSButtons")) {
						buttonInfoList = (List<Map<String, Object>>) params.get("rcsLMSButtons");
					}

					if (buttonInfoList != null && buttonInfoList.size() > 0) {
						sb.append(newButtonAddStr(buttonInfoList));
					} else {
						sb.append(",\"buttons\": [{}]");
					}

				} else if ((int) params.get("rcsTemplateTable") == 5) {
					// RCS SHORT TYPE
					// ====================================================================
					List<Map<String, Object>> rcsShortImgInfoList = null;

					sb.append("\"rcsPrdType\" : \"SHORT\","); // RCS상품타입(세로형[SHORT] 템플릿) rcsTemplateTable => 5
					sb.append("\"messagebaseId\": \"SMwThM00\","); // 세로형(SHORT) 템플릿의 RCS MESSAGEBASE_ID 를 설정

					sb.append("\"mergeData\": [{ ");
					sb.append("	\"title\" : \"" + JSONObject.escape((String) params.get("rcs0Title")) + "\", "); // 제목
					sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcs0Content")) + "\" "); // 메시지
					if (params.containsKey("rcsShortImgInfoList")) {
						rcsShortImgInfoList = (List<Map<String, Object>>) params.get("rcsShortImgInfoList");
						if (rcsShortImgInfoList.size() > 0) {
							Map<String, Object> imgInfo = rcsShortImgInfoList.get(0);// rcs에서 SHORT, TALL에는 이미지가 1개만
																						// 들어온다
							if (imgInfo.containsKey("fileId")) {
								sb.append("	,\"mediaUrl\" : \"" + CommonUtils.getStrValue(imgInfo, "imgUrl") + "\", "); //
								sb.append("	\"media\" : \"maapfile://" + CommonUtils.getStrValue(imgInfo, "fileId") + "\" "); //
							}
						} else {
							sb.append("	,\"mediaUrl\" : \"{}\", "); //
							sb.append("	\"media\" : \"\", "); //
						}
					}
					sb.append("	}]");

					List<Map<String, Object>> buttonInfoList = null;
					if (params.containsKey("rcsShortButtons")) {
						buttonInfoList = (List<Map<String, Object>>) params.get("rcsShortButtons");
					}

					if (buttonInfoList != null && buttonInfoList.size() > 0) {
						sb.append(newButtonAddStr(buttonInfoList));
					} else {
						sb.append(",\"buttons\": [{}]");
					}

				} else if ((int) params.get("rcsTemplateTable") == 6) {
					// RCS TALL TYPE
					// ====================================================================
					List<Map<String, Object>> rcsTallImgInfoList = null;

					sb.append("\"rcsPrdType\" : \"TALL\","); // RCS상품타입(세로형[TALL]템플릿) rcsTemplateTable => 6
					sb.append("\"messagebaseId\": \"SMwThT00\","); // 세로형(TALL) 템플릿의 RCS MESSAGEBASE_ID 를 설정

					sb.append("\"mergeData\": [{ ");
					sb.append("	\"title\" : \"" + JSONObject.escape((String) params.get("rcs0Title")) + "\", "); // 제목
					sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcs0Content")) + "\" "); // 메시지
					if (params.containsKey("rcsTallImgInfoList")) {
						rcsTallImgInfoList = (List<Map<String, Object>>) params.get("rcsTallImgInfoList");
						if (rcsTallImgInfoList.size() > 0) {
							Map<String, Object> imgInfo = rcsTallImgInfoList.get(0);// rcs에서 SHORT, TALL에는 이미지가 1개만 들어온다
							if (imgInfo.containsKey("fileId")) {
								sb.append("	,\"mediaUrl\" : \"" + CommonUtils.getStrValue(imgInfo, "imgUrl") + "\", ");
								sb.append("	\"media\" : \"maapfile://" + CommonUtils.getStrValue(imgInfo, "fileId") + "\" ");
							}
						} else {
							sb.append("	,\"mediaUrl\" : \"{}\", ");
							sb.append("	\"media\" : \"\" ");
						}
					}
					sb.append("	}]");

					List<Map<String, Object>> buttonInfoList = null;
					if (params.containsKey("rcsTallButtons")) {
						buttonInfoList = (List<Map<String, Object>>) params.get("rcsTallButtons");
					}

					if (buttonInfoList != null && buttonInfoList.size() > 0) {
						sb.append(newButtonAddStr(buttonInfoList));
					} else {
						sb.append(",\"buttons\": [{}]");
					}

				} else if ((int) params.get("rcsTemplateTable") == 9) {
					// RCS CSHORT TYPE
					// ====================================================================
					sb.append("\"rcsPrdType\" : \"CSHORT\","); // RCS상품타입(캐러셀[SHORT]템플릿) rcsTemplateTable => 9

					int rcs9CardCount;
					if (params.get("rcs9CardCount").getClass().getName().equalsIgnoreCase("java.lang.Integer")) {
						rcs9CardCount = (int) params.get("rcs9CardCount");
					} else {
						rcs9CardCount = Integer.parseInt((String) params.get("rcs9CardCount"));
					}

					String messagebaseId = "";
					if (rcs9CardCount == 3) {
						messagebaseId = "CMwShS0300"; // 슬라이드형(Small,3장)
					} else if (rcs9CardCount == 4) {
						messagebaseId = "CMwShS0400"; // 슬라이드형(Small,4장)
					} else if (rcs9CardCount == 5) {
						messagebaseId = "CMwShS0500"; // 슬라이드형(Small,5장)
					} else if (rcs9CardCount == 6) {
						messagebaseId = "CMwShS0600"; // 슬라이드형(Small,6장)
					}
					sb.append("\"messagebaseId\": \"" + messagebaseId + "\","); /// 캐러셀형(SHORT) 템플릿의 RCS MESSAGEBASE_ID 를 설정
					sb.append("\"cardCount\": \"" + rcs9CardCount + "\","); /// 캐러셀형(SHORT) 템플릿의 페이지 카운트

					sb.append("\"mergeData\": [ ");
					List<Map<String, Object>> rcsCShortImgInfoList = (List<Map<String, Object>>) params.get("rcsCShortImgInfoList");
					for (int k = 0; k < rcsCShortImgInfoList.size(); k++) {
						sb.append(" { ");
						Map<String, Object> rcsCShortImgInfo = rcsCShortImgInfoList.get(k);
						sb.append("	\"title\" : \"" + CommonUtils.getStrValue(rcsCShortImgInfo, "rcsTitle") + "\", "); // 제목
						sb.append("	\"description\" : \"" + CommonUtils.getStrValue(rcsCShortImgInfo, "rcsContent") + "\" "); // 메시지

						// image List
						if (!"".equals(CommonUtils.getStrValue(rcsCShortImgInfo, "imgUrl")) && !"".equals(CommonUtils.getStrValue(rcsCShortImgInfo, "fileId"))) {
							sb.append("	,\"mediaUrl\" : \"" + CommonUtils.getStrValue(rcsCShortImgInfo, "imgUrl") + "\", ");
							sb.append("	\"media\" : \"maapfile://" + CommonUtils.getStrValue(rcsCShortImgInfo, "fileId") + "\" ");
						} else {
							sb.append("	,\"mediaUrl\" : \"{}\", ");
							sb.append("	\"media\" : \"\" ");
						}
						sb.append("	} ");
						if (k < rcs9CardCount - 1)
							sb.append(", ");
					}
					sb.append("	] ");

					if (rcsCShortImgInfoList.size() == 0) {
						sb.append(",\"buttons\": [{}]");
					} else {
						sb.append(",\"buttons\": [ ");
						for (int k = 0; k < rcsCShortImgInfoList.size(); k++) {
							Map<String, Object> rcsCShortImgInfo = rcsCShortImgInfoList.get(k);
							List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) rcsCShortImgInfo.get("rcsButtons");

							sb.append(" { ");

							if (buttonInfoList != null && buttonInfoList.size() > 0) {
								sb.append("\"suggestions\": [ ");
								sb.append(newCarouselButtonAddStr(buttonInfoList));
								sb.append("	]");
							}

							sb.append("	} ");

							if (k < rcsCShortImgInfoList.size() - 1) {
								sb.append(", ");
							}
						}
						sb.append("	] ");
					}

				} else if ((int) params.get("rcsTemplateTable") == 10) {
					// RCS CTALL TYPE
					// ====================================================================
					sb.append("\"rcsPrdType\" : \"CTALL\","); // RCS상품타입(캐러셀[TALL]템플릿) rcsTemplateTable => 10

					int rcs10CardCount;
					if (params.get("rcs10CardCount").getClass().getName().equalsIgnoreCase("java.lang.Integer")) {
						rcs10CardCount = (int) params.get("rcs10CardCount");
					} else {
						rcs10CardCount = Integer.parseInt((String) params.get("rcs10CardCount"));
					}

					String messagebaseId = "";
					if (rcs10CardCount == 3) {
						messagebaseId = "CMwMhM0300"; // 슬라이드형(Medium,3장)
					} else if (rcs10CardCount == 4) {
						messagebaseId = "CMwMhM0400"; // 슬라이드형(Medium,4장)
					} else if (rcs10CardCount == 5) {
						messagebaseId = "CMwMhM0500"; // 슬라이드형(Medium,5장)
					} else if (rcs10CardCount == 6) {
						messagebaseId = "CMwMhM0600"; // 슬라이드형(Medium,6장)
					}

					sb.append("\"messagebaseId\": \"" + messagebaseId + "\","); /// 캐러셀형(TALL) 템플릿의 RCS MESSAGEBASE_ID 를 설정
					sb.append("\"cardCount\": \"" + rcs10CardCount + "\","); /// 캐러셀형(SHORT) 템플릿의 페이지 카운트

					sb.append("\"mergeData\": [ ");
					List<Map<String, Object>> rcsCTallImgInfoList = (List<Map<String, Object>>) params.get("rcsCTallImgInfoList");
					for (int k = 0; k < rcsCTallImgInfoList.size(); k++) {
						sb.append(" { ");
						Map<String, Object> rcsCTallImgInfo = rcsCTallImgInfoList.get(k);
						sb.append("	\"title\" : \"" + CommonUtils.getStrValue(rcsCTallImgInfo, "rcsTitle") + "\", "); // 제목
						sb.append("	\"description\" : \"" + CommonUtils.getStrValue(rcsCTallImgInfo, "rcsContent") + "\" "); // 메시지

						// image List
						if (!"".equals(CommonUtils.getStrValue(rcsCTallImgInfo, "imgUrl")) && !"".equals(CommonUtils.getStrValue(rcsCTallImgInfo, "fileId"))) {
							sb.append("	,\"mediaUrl\" : \"" + CommonUtils.getStrValue(rcsCTallImgInfo, "imgUrl") + "\", ");
							sb.append("	\"media\" : \"maapfile://" + CommonUtils.getStrValue(rcsCTallImgInfo, "fileId") + "\" ");
						} else {
							sb.append("	,\"mediaUrl\" : \"{}\", ");
							sb.append("	\"media\" : \"\" ");
						}
						sb.append("	} ");
						if (k < rcs10CardCount - 1)
							sb.append(", ");
					}
					sb.append("	] ");

					if (rcsCTallImgInfoList.size() == 0) {
						sb.append(",\"buttons\": [{}]");
					} else {
						sb.append(",\"buttons\": [ ");
						for (int k = 0; k < rcsCTallImgInfoList.size(); k++) {
							Map<String, Object> rcsCTallImgInfo = rcsCTallImgInfoList.get(k);
							List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) rcsCTallImgInfo.get("rcsButtons");

							sb.append(" { ");

							if (buttonInfoList != null && buttonInfoList.size() > 0) {
								sb.append("\"suggestions\": [ ");
								sb.append(newCarouselButtonAddStr(buttonInfoList));
								sb.append("	]");
							}

							sb.append("	} ");

							if (k < rcsCTallImgInfoList.size() - 1) {
								sb.append(", ");
							}
						}
						sb.append("	] ");
					}
				}
				sb.append("} ");

			} else if (checkChannelArr[i].equalsIgnoreCase("KAKAO")) {
				// KAKAO ====================================================================
				String linkType = null;
				String name = null;
				String linkPc = null;
				String linkMo = null;
				String linkAnd = null;
				String linkIos = null;
				String linkUrl1 = null;
				String linkUrl2 = null;
				List<Map<String, Object>> buttonInfoList = null;

				if ((int) params.get("kakaoTemplateTable") == 0) { // FRIENDTALK
					if (params.containsKey("friendTalkButtons")) {
						buttonInfoList = (List<Map<String, Object>>) params.get("friendTalkButtons");
					}

					sb.append("\"chTypeList\" : \"" + chTypeList + "\",");
					sb.append("\"ch\" : \"FRIENDTALK\",");

					sb.append("\"data\" : { ");
					sb.append("\"callback\" : \"" + ApiConfig.DEFAULT_CALLBACK + "\",");
					sb.append("\"senderKeyType\" : \"" + params.get("friendTalkSenderKeyType") + "\",");
					sb.append("\"senderKey\" : \"" + params.get("friendTalkSenderKey") + "\",");
					sb.append("\"wideImageYn\" : \"N\",");
					if ("I".equals(params.get("msgKind"))) {
						sb.append("\"adFlag\" : \"N\",");
					} else if ("A".equals(params.get("msgKind"))) {
						sb.append("\"adFlag\" : \"Y\",");
					}
					sb.append("\"msg\" : \"" + JSONObject.escape((String) params.get("friendTalkContent")) + "\",");
					Map<String, Object> imgInfo = null;
					if (params.containsKey("friendTalkImgInfo")) {
						imgInfo = (Map<String, Object>) params.get("friendTalkImgInfo");
						if (imgInfo.containsKey("fileId")) {
							sb.append("\"fileId\" : \"" + CommonUtils.getStrValue(imgInfo, "fileId") + "\",");
						} else {
							sb.append("\"fileId\" : \"\",");
						}
						sb.append("	\"image\" : { "); // 이미지
						if (imgInfo.containsKey("fileId")) {
							sb.append("	\"imgUrl\" : \"" + CommonUtils.getStrValue(imgInfo, "imgUrl") + "\", "); // 이미지
							sb.append("	\"imgLink\" : \"" + params.get("friendTalkImageLink") + "\" "); // 이미지
						}
						sb.append("	}, "); // 이미지
					}
					sb.append("\"buttons\": [ ");

					int fTalkIdx = 1;
					for (Map<String, Object> buttonInfo : buttonInfoList) {
						linkType = CommonUtils.getStrValue(buttonInfo, "type");
						name = CommonUtils.getStrValue(buttonInfo, "name");
						linkUrl1 = CommonUtils.getStrValue(buttonInfo, "linkUrl1");
						linkUrl2 = CommonUtils.getStrValue(buttonInfo, "linkUrl2");

						if (linkType.equalsIgnoreCase("WL")) { // 웹 링크
							sb.append("	{ ");
							sb.append("	\"name\": \"" + name + "\","); // 버튼이름
							sb.append("	\"linkType\": \"" + linkType + "\", "); // 버튼타입
							sb.append("	\"linkPc\": \"" + linkUrl2 + "\", "); // 내용
							sb.append("	\"linkMo\": \"" + linkUrl1 + "\" "); // 내용
							sb.append("	} ");
						}
						if (linkType.equalsIgnoreCase("AL")) { // 앱 랭크
							sb.append("	{ ");
							sb.append("	\"name\": \"" + name + "\","); // 버튼이름
							sb.append("	\"linkType\": \"" + linkType + "\", "); // 버튼타입
							sb.append("	\"linkIos\": \"" + linkUrl2 + "\", "); // 내용
							sb.append("	\"linkAnd\": \"" + linkUrl1 + "\" "); // 내용
							sb.append("	} ");
						}
						if (linkType.equalsIgnoreCase("BK")) { // 봇 키워드
							sb.append("	{ ");
							sb.append("	\"name\": \"" + name + "\","); // 버튼이름
							sb.append("	\"linkType\": \"" + linkType + "\" "); // 버튼타입
							sb.append("	} ");
						}
						if (linkType.equalsIgnoreCase("MD")) { // 메시지 전달
							sb.append("	{ ");
							sb.append("	\"name\": \"" + name + "\","); // 버튼이름
							sb.append("	\"linkType\": \"" + linkType + "\" "); // 버튼타입
							sb.append("	} ");
						}

						if (fTalkIdx++ < buttonInfoList.size()) {
							sb.append(", ");
						}
					}
					sb.append("	]         ");
					sb.append("} ");
				} else if ((int) params.get("kakaoTemplateTable") == 1) { // ALIMTALK
					if (params.containsKey("buttonList")) {
						buttonInfoList = (List<Map<String, Object>>) params.get("buttonList");
					}

					sb.append("\"chTypeList\" : \"" + chTypeList + "\",");
					sb.append("\"ch\" : \"ALIMTALK\",");

					sb.append("\"data\": { ");
					sb.append("\"callback\": \"" + ApiConfig.DEFAULT_CALLBACK + "\",");
					sb.append("\"msg\": \"" + JSONObject.escape((String) params.get("tmpltContent")) + "\",");
					sb.append("\"senderKey\": \"" + params.get("senderKey") + "\",");
					sb.append("\"tmpltKey\": \"" + params.get("tmpltKey") + "\",");
					sb.append("\"subData\": { ");
					sb.append("\"tmpltName\" : \"" + params.get("tmpltName") + "\","); // 템플릿명
					sb.append("\"emphasizeType\" : \"" + params.get("emphasizeType") + "\","); // 템플릿강조여부
					sb.append("\"tmpltEmpsTitle\" : \"" + params.get("tmpltEmpsTitle") + "\","); // 템플릿강조제목
					sb.append("\"tmpltEmpsSubTitle\" : \"" + params.get("tmpltEmpsSubTitle") + "\""); // 템플릿강조부제목
					sb.append("},");
					sb.append("\"buttons\": [");

					int aTalkIdx = 1;
					for (Map<String, Object> buttonInfo : buttonInfoList) {
						linkType = CommonUtils.getStrValue(buttonInfo, "linkType");
						name = CommonUtils.getStrValue(buttonInfo, "name");
						linkPc = CommonUtils.getStrValue(buttonInfo, "linkPc");
						linkMo = CommonUtils.getStrValue(buttonInfo, "linkMo");
						linkAnd = CommonUtils.getStrValue(buttonInfo, "linkAnd");
						linkIos = CommonUtils.getStrValue(buttonInfo, "linkIos");

						if (linkType.equalsIgnoreCase("DS")) { // 배송조회
							sb.append("	{ ");
							sb.append("	\"name\": \"" + name + "\","); // 버튼이름
							sb.append("	\"linkType\": \"" + linkType + "\" "); // 버튼타입
							sb.append("	} ");
						}
						if (linkType.equalsIgnoreCase("WL")) { // 웹 링크
							sb.append("	{ ");
							sb.append("	\"name\": \"" + name + "\","); // 버튼이름
							sb.append("	\"linkType\": \"" + linkType + "\", "); // 버튼타입
							sb.append("	\"linkPc\": \"" + linkPc + "\", "); // 내용
							sb.append("	\"linkMo\": \"" + linkMo + "\" "); // 내용
							sb.append("	} ");
						}
						if (linkType.equalsIgnoreCase("AL")) { // 앱 랭크
							sb.append("	{ ");
							sb.append("	\"name\": \"" + name + "\","); // 버튼이름
							sb.append("	\"linkType\": \"" + linkType + "\", "); // 버튼타입
							sb.append("	\"linkIos\": \"" + linkIos + "\", "); // 내용
							sb.append("	\"linkAnd\": \"" + linkAnd + "\" "); // 내용
							sb.append("	} ");
						}
						if (linkType.equalsIgnoreCase("BK")) { // 봇 키워드
							sb.append("	{ ");
							sb.append("	\"name\": \"" + name + "\","); // 버튼이름
							sb.append("	\"linkType\": \"" + linkType + "\" "); // 버튼타입
							sb.append("	} ");
						}
						if (linkType.equalsIgnoreCase("MD")) { // 메시지 전달
							sb.append("	{ ");
							sb.append("	\"name\": \"" + name + "\","); // 버튼이름
							sb.append("	\"linkType\": \"" + linkType + "\" "); // 버튼타입
							sb.append("	} ");
						}
						if (linkType.equalsIgnoreCase("BC")) { // 상담톡 전환
							sb.append("	{ ");
							sb.append("	\"name\": \"" + name + "\","); // 버튼이름
							sb.append("	\"linkType\": \"" + linkType + "\" "); // 버튼타입
							sb.append("	} ");
						}
						if (linkType.equalsIgnoreCase("BT")) { // 봇 전환
							sb.append("	{ ");
							sb.append("	\"name\": \"" + name + "\","); // 버튼이름
							sb.append("	\"linkType\": \"" + linkType + "\" "); // 버튼타입
							sb.append("	} ");
						}

						if (aTalkIdx++ < buttonInfoList.size()) {
							sb.append(", ");
						}
					}
					sb.append("]");
					sb.append("}");
				}

			} else if (checkChannelArr[i].equalsIgnoreCase("SMSMMS")) {
				// SMSMMS ====================================================================
				if (params.get("smsSendType").equals("S")) {// SMS
					sb.append("\"chTypeList\" : \"" + chTypeList + "\",");
					sb.append("\"ch\" : \"SMS\","); // 발송채널
					sb.append("\"data\" : { ");
					sb.append("\"callback\" : \"" + params.get("callback") + "\",");
					if ("A".equals(params.get("msgKind"))) {
						sb.append("\"msg\" : \"(광고)" + JSONObject.escape((String) params.get("smsContent")) + params.get("smsRcvblcNumber") + "\" "); // 메시지
					} else {
						sb.append("\"msg\" : \"" + JSONObject.escape((String) params.get("smsContent")) + "\" "); // 메시지
					}
					if (params.containsKey("smsRcvblcNumber")) {
						sb.append(",\"rcvblcInput\" : \"" + params.get("smsRcvblcNumber") + "\""); // 수신거부방법
					}
					sb.append("} ");
				} else if (params.get("smsSendType").equals("M")) {// MMS
					sb.append("\"chTypeList\" : \"" + chTypeList + "\",");
					sb.append("\"ch\" : \"MMS\",");// 발송채널
					sb.append("\"data\" : { ");
					sb.append("\"callback\" : \"" + params.get("callback") + "\",");
					if ("A".equals(params.get("msgKind"))) {
						sb.append("\"title\" : \"(광고)" + params.get("smsTitle") + "\",");
						sb.append("\"msg\" : \"" + JSONObject.escape((String) params.get("smsContent")) + JSONObject.escape("\n" + (String) params.get("smsRcvblcNumber")) + "\" ");
					} else {
						sb.append("\"title\" : \"" + params.get("smsTitle") + "\",");
						sb.append("\"msg\" : \"" + JSONObject.escape((String) params.get("smsContent")) + "\" ");
					}
					if (params.containsKey("smsRcvblcNumber")) {
						sb.append(",\"rcvblcInput\" : \"" + params.get("smsRcvblcNumber") + "\" "); // 수신거부방법
					}

					List<Map<String, Object>> imgInfoList = null;
					if (params.containsKey("smsImgInfoList")) {
						imgInfoList = (List<Map<String, Object>>) params.get("smsImgInfoList");
						int maxSize = imgInfoList.size();
						int idx = 0;
						sb.append(",\"fileIdLst\" : [ "); // 이미지
						for (Map<String, Object> imgInfo : imgInfoList) {
							if (imgInfo.containsKey("fileId")) {
								idx = idx + 1;
								if (idx < maxSize) {
									sb.append("	\"" + CommonUtils.getStrValue(imgInfo, "fileId") + "\", "); // 이미지
								} else {
									sb.append("	\"" + CommonUtils.getStrValue(imgInfo, "fileId") + "\" "); // 이미지
								}
							}
						}
						sb.append("	] "); // 이미지
					}
					sb.append("}");
				}
			}
			sb.append("}");
			if (i < checkChannelArr.length - 1) {
				sb.append(",");
			}
		}
		sb.append("]");
		System.out.println("=============================================");
		System.out.println(">>>>service 005 json : " + sb.toString());
		System.out.println("=============================================");

		Map<String, Object> sParams = new HashMap<String, Object>(params);
		sParams.put("tmpltInfo", sb.toString());

		if (sParams.containsKey("multiSendTmpltCode")
				&& StringUtils.isBlank(CommonUtils.getString(sParams.get("multiSendTmpltCode")))) {
			// 템플릿ID 취득
			String tmpltCode = CommonUtils.getCommonId(Const.TMPLT_PREFIX, 5);
			sParams.put("multiSendTmpltCode", tmpltCode);
		}

		if (sParams.get("otherProjectUseYn").equals("Y")) {// 타프로젝트와 같이 사용하면 projecdtId를 ALL로 잡는다.
			sParams.put("projectId", "ALL");
		}

		resultCnt = generalDao.insertGernal(DB.QRY_INSERT_MULTISEND_TMPLT, sParams);

		// redis 테이블 처리
		commonService.updateCmCmdForRedis(CmdTgt.SMART_TMPLT);

		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
			rtn.setData(sParams);
		}

		return rtn;
	}

	/**
	 * 통합 템플릿 삭제 처리
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public RestResult<Object> deleteMultiSendTemplate(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		ArrayList<?> tmpltCodes = (ArrayList<?>) params.get("tmpltCodes");
		for (int i=0; i<tmpltCodes.size(); i++) {
			Map<String, Object> delParams = new HashMap<>();
			delParams.put("tmpltCode", tmpltCodes.get(i));
			// redis 테이블 처리
			commonService.updateCmCmdForRedisAPI("else", "CM_SMART_TMPLT", delParams);
		}

		int resultCnt = generalDao.deleteGernal(DB.QRY_DELETE_MULTISEND_TMPLT, params);
		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
			rtn.setData(params);
		}

		return rtn;
	}

	private String newButtonAddStr(List<Map<String, Object>> buttonInfoList) {
		StringBuffer sb = new StringBuffer();

		sb.append(",\"buttons\": [{ ");
		sb.append("\"suggestions\": [ ");

		int rcsIdx = 1;
		Map<String, Object> btnInfo = null;
		String linkType = "";
		String displayText = "";
		String url = "";
		String text = "";
		String phoneNumber = "";
		String title = "";
		String description = "";
		String startTime = "";
		String endTime = "";
		for (Map<String, Object> buttonInfo : buttonInfoList) {

			btnInfo = (Map<String, Object>) buttonInfo.get("action");
			Map<String, Object> postback = (Map<String, Object>) btnInfo.get("postback");
			linkType = (String) postback.get("data");
			displayText = (String) btnInfo.get("displayText");
			sb.append("{\"action\": { "); // action 열기
			sb.append("	\"displayText\": \"" + displayText + "\","); // 버튼이름
			sb.append("	\"postback\": {\"data\": \""+ linkType + "\"},");

			if (linkType.equalsIgnoreCase("set_by_chatbot_open_url")) { // URL 링크
				Map<String, Object> urlAction = (Map<String, Object>) btnInfo.get("urlAction");
				Map<String, Object> openUrl = (Map<String, Object>) urlAction.get("openUrl");
				url = (String) openUrl.get("url");
				sb.append("	\"urlAction\": { "); // urlAction
				sb.append("	\"openUrl\": { "); // openUrl
				sb.append("	\"url\": \"" + url + "\" "); // 내용
				sb.append("	} ");
				sb.append("	} ");
			}
			if (linkType.equalsIgnoreCase("set_by_chatbot_copy_to_clipboard")) { // 복사하기
				Map<String, Object> clipboardAction = (Map<String, Object>) btnInfo.get("clipboardAction");
				Map<String, Object> copyToClipboard = (Map<String, Object>) clipboardAction.get("copyToClipboard");
				text = (String) copyToClipboard.get("text");
				sb.append("	\"clipboardAction\": { "); // clipboardAction
				sb.append("	\"copyToClipboard\": { "); // copyToClipboard
				sb.append("	\"text\": \"" + text + "\" "); // 내용
				sb.append("	} ");
				sb.append("	} ");
			}
			if (linkType.equalsIgnoreCase("set_by_chatbot_dial_phone_number")) { // 전화걸기
				Map<String, Object> dialerAction = (Map<String, Object>) btnInfo.get("dialerAction");
				Map<String, Object> dialPhoneNumber = (Map<String, Object>) dialerAction.get("dialPhoneNumber");
				phoneNumber = (String) dialPhoneNumber.get("phoneNumber");
				sb.append("	\"dialerAction\": { "); // dialerAction
				sb.append("	\"dialPhoneNumber\": { "); // dialPhoneNumber
				sb.append("	\"phoneNumber\": \"" + phoneNumber + "\" "); // 내용
				sb.append("	} ");
				sb.append("	} ");
			}
			if (linkType.equalsIgnoreCase("set_by_chatbot_create_calendar_event")) { // 일정추가
				Map<String, Object> calendarAction = (Map<String, Object>) btnInfo.get("calendarAction");
				Map<String, Object> createCalendarEvent = (Map<String, Object>) calendarAction
						.get("createCalendarEvent");
				title = (String) createCalendarEvent.get("title");
				description = (String) createCalendarEvent.get("description");
				startTime = (String) createCalendarEvent.get("startTime");
				endTime = (String) createCalendarEvent.get("endTime");
				sb.append("	\"calendarAction\": { "); // calendarAction
				sb.append("	\"createCalendarEvent\": { "); // createCalendarEvent
				sb.append("	\"title\": \"" + title + "\", "); // 제목
				sb.append("	\"description\": \"" + description + "\", "); // 내용
				sb.append("	\"startTime\": \"" + startTime + "\", "); // 시작일
				sb.append("	\"endTime\": \"" + endTime + "\" "); // 종료일
				sb.append("	} ");
				sb.append("	} ");
			}
			if (linkType.equalsIgnoreCase("set_by_chatbot_request_location_push")) { // 지도맵
				sb.append("	\"mapAction\": { "); // mapAction
				sb.append("	\"requestLocationPush\": { "); // requestLocationPush
				sb.append("	} ");
				sb.append("	} ");
			}
			sb.append("	}} "); // action 닫기

			if (rcsIdx++ < buttonInfoList.size()) {
				sb.append(", ");
			}
		}
		sb.append("	]");
		sb.append("}]");

		return sb.toString();
	}

	private String newCarouselButtonAddStr(List<Map<String, Object>> buttonInfoList) {
		StringBuffer sb = new StringBuffer();

		int rcsIdx = 1;
		Map<String, Object> btnInfo = null;
		String linkType = "";
		String displayText = "";
		String url = "";
		String text = "";
		String phoneNumber = "";
		String title = "";
		String description = "";
		String startTime = "";
		String endTime = "";
		for (Map<String, Object> buttonInfo : buttonInfoList) {

			btnInfo = (Map<String, Object>) buttonInfo.get("action");
			Map<String, Object> postback = (Map<String, Object>) btnInfo.get("postback");
			linkType = (String) postback.get("data");
			displayText = (String) btnInfo.get("displayText");
			sb.append("{\"action\": { "); // action 열기
			sb.append("	\"displayText\": \"" + displayText + "\","); // 버튼이름
			sb.append("	\"postback\": {\"data\": \""+ linkType + "\"},");

			if (linkType.equalsIgnoreCase("set_by_chatbot_open_url")) { // URL 링크
				Map<String, Object> urlAction = (Map<String, Object>) btnInfo.get("urlAction");
				Map<String, Object> openUrl = (Map<String, Object>) urlAction.get("openUrl");
				url = (String) openUrl.get("url");
				sb.append("	\"urlAction\": { "); // urlAction
				sb.append("	\"openUrl\": { "); // openUrl
				sb.append("	\"url\": \"" + url + "\" "); // 내용
				sb.append("	} ");
				sb.append("	} ");
			}
			if (linkType.equalsIgnoreCase("set_by_chatbot_copy_to_clipboard")) { // 복사하기
				Map<String, Object> clipboardAction = (Map<String, Object>) btnInfo.get("clipboardAction");
				Map<String, Object> copyToClipboard = (Map<String, Object>) clipboardAction.get("copyToClipboard");
				text = (String) copyToClipboard.get("text");
				sb.append("	\"clipboardAction\": { "); // clipboardAction
				sb.append("	\"copyToClipboard\": { "); // copyToClipboard
				sb.append("	\"text\": \"" + text + "\" "); // 내용
				sb.append("	} ");
				sb.append("	} ");
			}
			if (linkType.equalsIgnoreCase("set_by_chatbot_dial_phone_number")) { // 전화걸기
				Map<String, Object> dialerAction = (Map<String, Object>) btnInfo.get("dialerAction");
				Map<String, Object> dialPhoneNumber = (Map<String, Object>) dialerAction.get("dialPhoneNumber");
				phoneNumber = (String) dialPhoneNumber.get("phoneNumber");
				sb.append("	\"dialerAction\": { "); // dialerAction
				sb.append("	\"dialPhoneNumber\": { "); // dialPhoneNumber
				sb.append("	\"phoneNumber\": \"" + phoneNumber + "\" "); // 내용
				sb.append("	} ");
				sb.append("	} ");
			}
			if (linkType.equalsIgnoreCase("set_by_chatbot_create_calendar_event")) { // 일정추가
				Map<String, Object> calendarAction = (Map<String, Object>) btnInfo.get("calendarAction");
				Map<String, Object> createCalendarEvent = (Map<String, Object>) calendarAction
						.get("createCalendarEvent");
				title = (String) createCalendarEvent.get("title");
				description = (String) createCalendarEvent.get("description");
				startTime = (String) createCalendarEvent.get("startTime");
				endTime = (String) createCalendarEvent.get("endTime");
				sb.append("	\"calendarAction\": { "); // calendarAction
				sb.append("	\"createCalendarEvent\": { "); // createCalendarEvent
				sb.append("	\"title\": \"" + title + "\", "); // 제목
				sb.append("	\"description\": \"" + description + "\", "); // 내용
				sb.append("	\"startTime\": \"" + startTime + "\", "); // 시작일
				sb.append("	\"endTime\": \"" + endTime + "\" "); // 종료일
				sb.append("	} ");
				sb.append("	} ");
			}
			if (linkType.equalsIgnoreCase("set_by_chatbot_request_location_push")) { // 지도맵
				sb.append("	\"mapAction\": { "); // mapAction
				sb.append("	\"requestLocationPush\": { "); // requestLocationPush
				sb.append("	} ");
				sb.append("	} ");
			}
			sb.append("	}} "); // action 닫기

			if (rcsIdx++ < buttonInfoList.size()) {
				sb.append(", ");
			}
		}

		return sb.toString();
	}

	/**
	 * 브랜드 리스트 조회
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public RestResult<Object> selectBrandList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_BRAND_LIST, params);
		rtn.setData(rtnList);

		return rtn;
	}

	/**
	 * 프로젝트 사용가능 채널 조회
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public RestResult<Object> selectProjectUseChannelInfo(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		Map<String, Object> rtnObj = new HashMap<String, Object>();

		Map<String, Object> projectUseChannelInfo = (Map<String, Object>) generalDao
				.selectGernalObject(DB.QRY_SELECT_DASH_PROJECT_INFO, params);

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(CommonUtils.getString(projectUseChannelInfo.get("useChGrpInfo")));
		JSONObject jsonObj = (JSONObject) obj;

		String rcsYn = CommonUtils.getString(jsonObj.get("RCS"));
		String smsmmsYn = CommonUtils.getString(jsonObj.get("SMS/MMS"));
		String pushYn = CommonUtils.getString(jsonObj.get("PUSH"));
		String kakaoYn = CommonUtils.getString(jsonObj.get("KKO"));

		projectUseChannelInfo.put("pushYn", pushYn);
		projectUseChannelInfo.put("rcsYn", rcsYn);
		projectUseChannelInfo.put("kakaoYn", kakaoYn);
		projectUseChannelInfo.put("smsmmsYn", smsmmsYn);

		rtnObj.put("projectUseChannelInfo", projectUseChannelInfo);
		rtn.setData(rtnObj);
		return rtn;
	}

	/**
	 * Message base id 조회
	 * 
	 * @param Param
	 * @return
	 * @throws Exception
	 */
	private String selectMessageBaseId(Map<String, Object> params) throws Exception {
		return (String) generalDao.selectGernalObject("smartTemplate.selectMessageBaseId", params);
	}

}
