package kr.co.uplus.cm.integratedTemplate.service;

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
public class IntegratedTemplateService {

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
	public RestResult<Object> selectIntegratedTemplateList(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		if (params.containsKey("pageNo") && CommonUtils.isNotEmptyObject(params.get("pageNo"))
				&& params.containsKey("listSize") && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
			rtn.setPageProps(params);
			if (rtn.getPageInfo() != null) {
				// 카운트 쿼리 실행
				int listCnt = generalDao.selectGernalCount("integratedTemplate.selectIntegratedTemplateListCnt",
						params);
				rtn.getPageInfo().put("totCnt", listCnt);
			}
		}

		List<Object> rtnList = generalDao.selectGernalList("integratedTemplate.selectIntegratedTemplateList", params);
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
	public RestResult<Object> selectIntegratedTemplateInfo(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> rtnList = generalDao.selectGernalList("integratedTemplate.selectIntegratedTemplateDetail", params);

		for (int i = 0; i < rtnList.size(); i++) {
			Map<String, Object> rtnMap = (Map<String, Object>) rtnList.get(i);
			String rcsButtonData = (String) rtnMap.get("rcsButton0Data");
			if (!"".equals(rcsButtonData) && rcsButtonData != null) {
				JSONParser parser = new JSONParser();
				JSONObject obj = (JSONObject) parser.parse(rcsButtonData);
				JSONArray jsonArr = null;
				jsonArr = (JSONArray) obj.get("suggestions");
				rtnMap.put("rcsButton0Data", jsonArr);
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

				sb.append("\"title\" : \"" + params.get("pushTitle") + "\","); // 제목
				sb.append("\"msg\" : \"" + JSONObject.escape((String) params.get("pushContent")) + "\","); // 메시지
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
				sb.append("\"brandNm\" : \"" + params.get("brandNm") + "\", "); //

				if ((int) params.get("rcsTemplateTable") == 0) {//
					// RCS FREE TYPE
					// ====================================================================
					sb.append("\"rcsPrdType\" : \"FREE\","); // RCS상품타입(프리 템플릿) rcsTemplateTable => 0
					String messageBaseId = selectMessageBaseId(params);
					sb.append("\"messagebaseId\": \"" + messageBaseId + "\","); // cm.CM_RCS_MSGBASE의 MESSAGEBASE_ID값을
																				// 설정

					sb.append("\"mergeData\": [{ ");
					sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcs0Content")) + "\""); // 메시지
					sb.append("	}]");
				} else if ((int) params.get("rcsTemplateTable") == 1) {
					// RCS DESCRIPTION TYPE
					// ====================================================================
					sb.append("\"rcsPrdType\" : \"DESCRIPTION\","); // RCS상품타입(서술 승인템플릿) rcsTemplateTable => 1
					sb.append("\"messagebaseId\": \"" + params.get("rcsDesMessagebaseId") + "\","); // 서술형 템플릿의 RCS MESSAGEBASE_ID 를 설정
					sb.append("\"messagebaseformId\": \"" + params.get("rcsDesFormNm") + "\","); // 서술형 템플릿의 RCS MESSAGEBASEFORM_ID 를 설정

					sb.append("\"mergeData\": [{ ");
					sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcs1Content")) + "\" "); // 메시지
					sb.append("	}], ");

					List<Map<String, Object>> buttonInfoList = null;
					if (params.containsKey("rcsDesButtons")) {
						buttonInfoList = (List<Map<String, Object>>) params.get("rcsDesButtons");
					}

					sb.append("\"buttons\": [{ ");
					sb.append("\"suggestions\": [ ");

					int rcsDesIdx = 1;
					Map<String, Object> btnInfo = null;
					Map<String, Object> postback = null;
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
						linkType = (String) btnInfo.get("linkType");
						displayText = (String) btnInfo.get("displayText");
						postback = (Map<String, Object>) btnInfo.get("postback");
						sb.append("{\"action\": { "); // action 열기
						sb.append("	\"displayText\": \"" + displayText + "\","); // 버튼이름
						sb.append("	\"postback\": {\"data\": \"" + postback.get("data") + "\"},"); // 버튼이름

						if (linkType.equalsIgnoreCase("urlAction")) { // URL 링크
							Map<String, Object> urlAction = (Map<String, Object>) btnInfo.get("urlAction");
							Map<String, Object> openUrl = (Map<String, Object>) urlAction.get("openUrl");
							url = (String) openUrl.get("url");
							sb.append("	\"linkType\": \"" + linkType + "\","); // 버튼타입
							sb.append("	\"urlAction\": { "); // urlAction
							sb.append("	\"openUrl\": { "); // openUrl
							sb.append("	\"url\": \"" + url + "\" "); // 내용
							sb.append("	} ");
							sb.append("	} ");
						}
						if (linkType.equalsIgnoreCase("clipboardAction")) { // 복사하기
							Map<String, Object> clipboardAction = (Map<String, Object>) btnInfo.get("clipboardAction");
							Map<String, Object> copyToClipboard = (Map<String, Object>) clipboardAction.get("copyToClipboard");
							text = (String) copyToClipboard.get("text");
							sb.append("	\"linkType\": \"" + linkType + "\","); // 버튼타입
							sb.append("	\"clipboardAction\": { "); // clipboardAction
							sb.append("	\"copyToClipboard\": { "); // copyToClipboard
							sb.append("	\"text\": \"" + text + "\" "); // 내용
							sb.append("	} ");
							sb.append("	} ");
						}
						if (linkType.equalsIgnoreCase("dialerAction")) { // 전화걸기
							Map<String, Object> dialerAction = (Map<String, Object>) btnInfo.get("dialerAction");
							Map<String, Object> dialPhoneNumber = (Map<String, Object>) dialerAction.get("dialPhoneNumber");
							phoneNumber = (String) dialPhoneNumber.get("phoneNumber");
							sb.append("	\"linkType\": \"" + linkType + "\","); // 버튼타입
							sb.append("	\"dialerAction\": { "); // dialerAction
							sb.append("	\"dialPhoneNumber\": { "); // dialPhoneNumber
							sb.append("	\"phoneNumber\": \"" + phoneNumber + "\" "); // 내용
							sb.append("	} ");
							sb.append("	} ");
						}
						if (linkType.equalsIgnoreCase("calendarAction")) { // 일정추가
							Map<String, Object> calendarAction = (Map<String, Object>) btnInfo.get("calendarAction");
							Map<String, Object> createCalendarEvent = (Map<String, Object>) calendarAction.get("createCalendarEvent");
							title = (String) createCalendarEvent.get("title");
							description = (String) createCalendarEvent.get("description");
							startTime = (String) createCalendarEvent.get("startTime");
							endTime = (String) createCalendarEvent.get("endTime");
							sb.append("	\"linkType\": \"" + linkType + "\","); // 버튼타입
							sb.append("	\"calendarAction\": { "); // calendarAction
							sb.append("	\"createCalendarEvent\": { "); // createCalendarEvent
							sb.append("	\"title\": \"" + title + "\", "); // 제목
							sb.append("	\"description\": \"" + description + "\", "); // 내용
							sb.append("	\"startTime\": \"" + startTime + "\", "); // 시작일
							sb.append("	\"endTime\": \"" + endTime + "\" "); // 종료일
							sb.append("	} ");
							sb.append("	} ");
						}
						if (linkType.equalsIgnoreCase("mapAction")) { // 지도맵
							sb.append("	\"linkType\": \"" + linkType + "\","); // 버튼타입
							sb.append("	\"mapAction\": { "); // mapAction
							sb.append("	\"requestLocationPush\": { "); // requestLocationPush
							sb.append("	} ");
							sb.append("	} ");
						}
						sb.append("	}} "); // action 닫기

						if (rcsDesIdx++ < buttonInfoList.size()) {
							sb.append(", ");
						}
					}
					sb.append("	]");
					sb.append("}]");
				} else if ((int) params.get("rcsTemplateTable") == 2) {
					// RCS CELL(STYLE) TYPE
					// ====================================================================
					sb.append("\"rcsPrdType\" : \"CELL\","); // RCS상품타입(스타일 승인템플릿) rcsTemplateTable => 2
					sb.append("\"messagebaseId\": \"" + params.get("rcs2MessageFormId") + "\","); // cm.CM_RCS_MSGBASEFORM,

					sb.append("\"mergeData\": [{ ");
					sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcs2Content1")) + ","
							+ JSONObject.escape((String) params.get("rcs2Content2")) + ","
							+ JSONObject.escape((String) params.get("rcs2Content3")) + "\", "); // 메시지

					List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params
							.get("rcsStyleButtons");
					sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));

					sb.append("	}] ");

				} else if ((int) params.get("rcsTemplateTable") == 3) {
					// RCS SMS TYPE
					// ====================================================================
					sb.append("\"rcsPrdType\" : \"SMS\","); // RCS상품타입(SMS 템플릿) rcsTemplateTable => 3
					sb.append("\"messagebaseId\": \"SS000000\","); // cm.CM_RCS_MSGBASE, cm_console.CM_RCS_TMP_MSGBASE의
																	// MESSAGEBASEFORM_ID값을 설정
					sb.append("\"callback\": \"" + params.get("callback") + "\",");
					sb.append("\"footer\": \"" + params.get("rcsSMSHowToDenyReceipt") + "\","); // 무료수신거부 번호, header의 값이
																								// 광고성일 때 footer 값을 포함하지
																								// 않고 발송하면 실패 처리

					sb.append("\"body\": [{ ");
					sb.append("	\"title\" : \"" + params.get("") + "\", "); //
					// String rcsSMSContent = ((String)
					// params.get("rcsSMSContent")).replaceAll("\n", "CHR(13)CHR(10)");
					sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcsSMSContent"))
							+ "\", "); // 메시지
					sb.append("	\"mediaUrl\" : \"{}\", "); //
					sb.append("	\"media\" : \"\", "); //

					List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params.get("rcsSMSButtons");
					sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));

					sb.append("	}] ");

				} else if ((int) params.get("rcsTemplateTable") == 4) {
//RCS LMS TYPE ====================================================================
					sb.append("\"rcsPrdType\" : \"LMS\","); // RCS상품타입(LMS 템플릿) rcsTemplateTable => 4
					sb.append("\"messagebaseId\": \"SL000000\","); // cm.CM_RCS_MSGBASE, cm_console.CM_RCS_TMP_MSGBASE의
																	// MESSAGEBASEFORM_ID값을 설정
					sb.append("\"callback\": \"" + params.get("callback") + "\",");
					sb.append("\"footer\": \"" + params.get("rcsLMSHowToDenyReceipt") + "\","); // 무료수신거부 번호, header의 값이
																								// 광고성일 때 footer 값을 포함하지
																								// 않고 발송하면 실패 처리

					sb.append("\"body\": [{ ");
					sb.append("	\"title\" : \"" + params.get("rcsLMSTitle") + "\", "); //
					// String rcsLMSContent = ((String)
					// params.get("rcsLMSContent")).replaceAll("\n", "CHR(13)CHR(10)");
					sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcsLMSContent"))
							+ "\", "); // 메시지
					sb.append("	\"mediaUrl\" : \"{}\", "); //
					sb.append("	\"media\" : \"\", "); //

					List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params.get("rcsLMSButtons");
					sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));

					sb.append("	}] ");

				} else if ((int) params.get("rcsTemplateTable") == 5) {
//RCS SHORT TYPE ====================================================================     
					System.out.println(">>>>service 003  RCS rcsTemplateTable = 5 : ");

					sb.append("\"rcsPrdType\" : \"SHORT\","); // RCS상품타입(세로형[SHORT] 템플릿) rcsTemplateTable => 5
					sb.append("\"messagebaseId\": \"SMwThM00\","); // cm.CM_RCS_MSGBASE, cm_console.CM_RCS_TMP_MSGBASE의
																	// MESSAGEBASEFORM_ID값을 설정
					sb.append("\"callback\": \"" + params.get("callback") + "\",");
					sb.append("\"footer\": \"" + params.get("rcsShortHowToDenyReceipt") + "\","); // 무료수신거부 번호, header의
																									// 값이 광고성일 때 footer
																									// 값을 포함하지 않고 발송하면
																									// 실패 처리

					sb.append("\"body\": [{ ");
					sb.append("	\"title\" : \"" + params.get("rcsShortTitle") + "\", "); //
					// String rcsShortContent = ((String)
					// params.get("rcsShortContent")).replaceAll("\n", "CHR(13)CHR(10)");
					sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcsShortContent"))
							+ "\", "); // 메시지

					System.out.println(">>>>service 003  RCS rcsTemplateTable = 5_1 : " + sb.toString());
					// System.out.println(">>>>service 003 RCS 001 SHORT 002 rcsShortImgInfoList :
					// "+params.get("rcsShortImgInfoList"));
					// image List
					List<Map<String, Object>> rcsShortImgInfoList = null;
					if (params.containsKey("rcsShortImgInfoList")) {
						rcsShortImgInfoList = (List<Map<String, Object>>) params.get("rcsShortImgInfoList");
						if (rcsShortImgInfoList.size() > 0) {
							Map<String, Object> imgInfo = rcsShortImgInfoList.get(0);// rcs에서 SHORT, TALL에는 이미지가 1개만
																						// 들어온다
							if (imgInfo.containsKey("fileId")) {
								// sb.append(" \""+CommonUtils.getStrValue(imgInfo, "imgUrl")+"\" "); // 이미지
								sb.append("	\"mediaUrl\" : \"{" + CommonUtils.getStrValue(imgInfo, "imgUrl") + "}\", "); //
								sb.append("	\"media\" : \"" + CommonUtils.getStrValue(imgInfo, "fileId") + "\", "); //

							}
						} else {
							sb.append("	\"mediaUrl\" : \"{}\", "); //
							sb.append("	\"media\" : \"\", "); //
						}

						List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params
								.get("rcsShortButtons");
						sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
					}

					sb.append("	}] ");

					System.out.println(">>>>service 003  RCS rcsTemplateTable = 5_2 : " + sb.toString());

				} else if ((int) params.get("rcsTemplateTable") == 6) {
//RCS TALL TYPE ====================================================================
					sb.append("\"rcsPrdType\" : \"TALL\","); // RCS상품타입(세로형[TALL]템플릿) rcsTemplateTable => 6
					sb.append("\"messagebaseId\": \"SMwThT00\","); // cm.CM_RCS_MSGBASE, cm_console.CM_RCS_TMP_MSGBASE의
																	// MESSAGEBASEFORM_ID값을 설정
					sb.append("\"callback\": \"" + params.get("callback") + "\",");
					sb.append("\"footer\": \"" + params.get("rcsTallHowToDenyReceipt") + "\","); // 무료수신거부 번호, header의
																									// 값이 광고성일 때 footer
																									// 값을 포함하지 않고 발송하면
																									// 실패 처리

					sb.append("\"body\": [{ ");
					sb.append("	\"title\" : \"" + params.get("rcsTallTitle") + "\", "); //
					// String rcsTallContent = ((String)
					// params.get("rcsTallContent")).replaceAll("\n", "CHR(13)CHR(10)");

					sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcsTallContent"))
							+ "\", "); // 메시지
					// image List
					List<Map<String, Object>> rcsTallImgInfoList = null;
					if (params.containsKey("rcsTallImgInfoList")) {
						rcsTallImgInfoList = (List<Map<String, Object>>) params.get("rcsTallImgInfoList");
						if (rcsTallImgInfoList.size() > 0) {
							Map<String, Object> imgInfo = rcsTallImgInfoList.get(0);// rcs에서 SHORT, TALL에는 이미지가 1개만 들어온다
							if (imgInfo.containsKey("fileId")) {
								// sb.append(" \""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
								sb.append("	\"mediaUrl\" : \"{" + CommonUtils.getStrValue(imgInfo, "imgUrl") + "}\", "); //
								sb.append("	\"media\" : \"" + CommonUtils.getStrValue(imgInfo, "fileId") + "\", "); //

							}
						} else {
							sb.append("	\"mediaUrl\" : \"{}\", "); //
							sb.append("	\"media\" : \"\", "); //
						}

						List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params
								.get("rcsTallButtons");
						sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
					}

					sb.append("	}] ");

				} else if ((int) params.get("rcsTemplateTable") == 9) {
//RCS CSHORT TYPE ====================================================================        			
					sb.append("\"rcsPrdType\" : \"CSHORT\","); // RCS상품타입(캐러셀[SHORT]템플릿) rcsTemplateTable => 9

					// System.out.println("rcs9CardCount type :
					// "+params.get("rcs9CardCount").getClass().getName());
					int rcs9CardCount;
					if (params.get("rcs9CardCount").getClass().getName().equalsIgnoreCase("java.lang.Integer")) {
						rcs9CardCount = (int) params.get("rcs9CardCount");
					} else {
						rcs9CardCount = Integer.parseInt((String) params.get("rcs9CardCount"));
					}

					String msgbaseId = "";
					if (rcs9CardCount == 3) {
						msgbaseId = "CMwShS0300"; // 슬라이드형(Small,3장)
					} else if (rcs9CardCount == 4) {
						msgbaseId = "CMwShS0400"; // 슬라이드형(Small,4장)
					} else if (rcs9CardCount == 5) {
						msgbaseId = "CMwShS0500"; // 슬라이드형(Small,5장)
					} else if (rcs9CardCount == 6) {
						msgbaseId = "CMwShS0600"; // 슬라이드형(Small,6장)
					}

					sb.append("\"messagebaseId\": \"" + msgbaseId + "\","); // cm.CM_RCS_MSGBASE,
																			// cm_console.CM_RCS_TMP_MSGBASE의
																			// MESSAGEBASEFORM_ID값을 설정
					sb.append("\"callback\": \"" + params.get("callback") + "\",");
					sb.append("\"footer\": \"" + params.get("rcs9HowToDenyReceipt") + "\","); // 무료수신거부 번호, header의 값이
																								// 광고성일 때 footer 값을 포함하지
																								// 않고 발송하면 실패 처리

					sb.append("\"body\": [ ");
					for (int k = 0; k < rcs9CardCount; k++) {
						if (k == 0) {
							sb.append(" { ");
							sb.append("	\"title\" : \"" + params.get("rcs90Title") + "\", "); //
							// String rcs90Content = ((String) params.get("rcs90Content")).replaceAll("\n",
							// "CHR(13)CHR(10)");
							sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcs90Content"))
									+ "\", "); // 메시지

							List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params
									.get("rcs90Buttons");
							// image List
							List<Map<String, Object>> rcs90ImgInfoList = null;
							if (params.containsKey("rcs90ImgInfoList")) {
								rcs90ImgInfoList = (List<Map<String, Object>>) params.get("rcs90ImgInfoList");
								if (rcs90ImgInfoList.size() > 0) {
									Map<String, Object> imgInfo = rcs90ImgInfoList.get(0);// rcs에서 SHORT, TALL, CSHORT,
																							// CTALL에는 이미지가 1개만 들어온다
									if (imgInfo.containsKey("fileId")) {
										// sb.append(" \""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
										sb.append("	\"mediaUrl\" : \"{" + imgInfo.get("imgUrl") + "}\", "); //
										sb.append("	\"media\" : \"" + imgInfo.get("fileId") + "\", "); //

										// if(buttonInfoList.size() > 0) {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\", "); //
										// }else {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\" "); //
										// }
										// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
										// params.get("rcs90Buttons");
										// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
									}
								} else {
									sb.append("	\"mediaUrl\" : \"{}\", "); //
									sb.append("	\"media\" : \"\", "); //

									// if(buttonInfoList.size() > 0) {
									// sb.append(" \"media\" : \"\", "); //
									// }else {
									// sb.append(" \"media\" : \"\" "); //
									// }
									// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
									// params.get("rcs102Buttons");
									// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
								}
								sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
							}
							sb.append("	} ");
							if (k < rcs9CardCount - 1)
								sb.append(", ");
						}

						if (k == 1) {
							sb.append(" { ");
							sb.append("	\"title\" : \"" + params.get("rcs91Title") + "\", "); //
							// String rcs91Content = ((String) params.get("rcs91Content")).replaceAll("\n",
							// "CHR(13)CHR(10)");
							sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcs91Content"))
									+ "\", "); // 메시지

							List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params
									.get("rcs91Buttons");
							// image List
							List<Map<String, Object>> rcs91ImgInfoList = null;
							if (params.containsKey("rcs91ImgInfoList")) {
								rcs91ImgInfoList = (List<Map<String, Object>>) params.get("rcs91ImgInfoList");
								if (rcs91ImgInfoList.size() > 0) {
									Map<String, Object> imgInfo = rcs91ImgInfoList.get(0);// rcs에서 SHORT, TALL에는 이미지가
																							// 1개만 들어온다
									if (imgInfo.containsKey("fileId")) {
										// sb.append(" \""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
										sb.append("	\"mediaUrl\" : \"{" + imgInfo.get("imgUrl") + "}\", "); //
										sb.append("	\"media\" : \"" + imgInfo.get("fileId") + "\", "); //

										// if(buttonInfoList.size() > 0) {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\", "); //
										// }else {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\" "); //
										// }
										// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
										// params.get("rcs91Buttons");
										// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
									}
								} else {
									sb.append("	\"mediaUrl\" : \"{}\", "); //
									sb.append("	\"media\" : \"\", "); //

									// if(buttonInfoList.size() > 0) {
									// sb.append(" \"media\" : \"\", "); //
									// }else {
									// sb.append(" \"media\" : \"\" "); //
									// }
									// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
									// params.get("rcs91Buttons");
									// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
								}
								sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
							}
							sb.append("	} ");
							if (k < rcs9CardCount - 1)
								sb.append(", ");
						}

						if (k == 2) {
							sb.append(" { ");
							sb.append("	\"title\" : \"" + params.get("rcs92Title") + "\", "); //
							// String rcs92Content = ((String) params.get("rcs92Content")).replaceAll("\n",
							// "CHR(13)CHR(10)");
							sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcs92Content"))
									+ "\", "); // 메시지

							List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params
									.get("rcs92Buttons");
							// image List
							List<Map<String, Object>> rcs92ImgInfoList = null;
							if (params.containsKey("rcs92ImgInfoList")) {
								rcs92ImgInfoList = (List<Map<String, Object>>) params.get("rcs92ImgInfoList");
								if (rcs92ImgInfoList.size() > 0) {
									Map<String, Object> imgInfo = rcs92ImgInfoList.get(0);// rcs에서 SHORT, TALL에는 이미지가
																							// 1개만 들어온다
									if (imgInfo.containsKey("fileId")) {
										// sb.append(" \""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
										sb.append("	\"mediaUrl\" : \"{" + imgInfo.get("imgUrl") + "}\", "); //
										sb.append("	\"media\" : \"" + imgInfo.get("fileId") + "\", "); //

										// if(buttonInfoList.size() > 0) {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\", "); //
										// }else {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\" "); //
										// }

										// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
										// params.get("rcs92Buttons");
										// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
									}
								} else {
									sb.append("	\"mediaUrl\" : \"{}\", "); //
									sb.append("	\"media\" : \"\", "); //

									// if(buttonInfoList.size() > 0) {
									// sb.append(" \"media\" : \"\", "); //
									// }else {
									// sb.append(" \"media\" : \"\" "); //
									// }
									// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
									// params.get("rcs92Buttons");
									// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
								}
								sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
							}
							sb.append("	} ");
							if (k < rcs9CardCount - 1)
								sb.append(", ");
						}

						if (k == 3) {
							sb.append(" { ");
							sb.append("	\"title\" : \"" + params.get("rcs93Title") + "\", "); //
							// String rcs93Content = ((String) params.get("rcs93Content")).replaceAll("\n",
							// "CHR(13)CHR(10)");
							sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcs93Content"))
									+ "\", "); // 메시지

							List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params
									.get("rcs93Buttons");
							// image List
							List<Map<String, Object>> rcs93ImgInfoList = null;
							if (params.containsKey("rcs93ImgInfoList")) {
								rcs93ImgInfoList = (List<Map<String, Object>>) params.get("rcs93ImgInfoList");
								if (rcs93ImgInfoList.size() > 0) {
									Map<String, Object> imgInfo = rcs93ImgInfoList.get(0);// rcs에서 SHORT, TALL에는 이미지가
																							// 1개만 들어온다
									if (imgInfo.containsKey("fileId")) {
										// sb.append(" \""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
										sb.append("	\"mediaUrl\" : \"{" + imgInfo.get("imgUrl") + "}\", "); //
										sb.append("	\"media\" : \"" + imgInfo.get("fileId") + "\", "); //

										// if(buttonInfoList.size() > 0) {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\", "); //
										// }else {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\" "); //
										// }
										// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
										// params.get("rcs93Buttons");
										// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
									}
								} else {
									sb.append("	\"mediaUrl\" : \"{}\", "); //
									sb.append("	\"media\" : \"\", "); //

									// if(buttonInfoList.size() > 0) {
									// sb.append(" \"media\" : \"\", "); //
									// }else {
									// sb.append(" \"media\" : \"\" "); //
									// }
									// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
									// params.get("rcs93Buttons");
									// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
								}
								sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
							}
							sb.append("	} ");
							if (k < rcs9CardCount - 1)
								sb.append(", ");
						}

						if (k == 4) {
							sb.append(" { ");
							sb.append("	\"title\" : \"" + params.get("rcs94Title") + "\", "); //
							// String rcs94Content = ((String) params.get("rcs94Content")).replaceAll("\n",
							// "CHR(13)CHR(10)");
							sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcs94Content"))
									+ "\", "); // 메시지

							List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params
									.get("rcs94Buttons");
							// image List
							List<Map<String, Object>> rcs94ImgInfoList = null;
							if (params.containsKey("rcs94ImgInfoList")) {
								rcs94ImgInfoList = (List<Map<String, Object>>) params.get("rcs94ImgInfoList");
								if (rcs94ImgInfoList.size() > 0) {
									Map<String, Object> imgInfo = rcs94ImgInfoList.get(0);// rcs에서 SHORT, TALL에는 이미지가
																							// 1개만 들어온다
									if (imgInfo.containsKey("fileId")) {
										// sb.append(" \""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
										sb.append("	\"mediaUrl\" : \"{" + imgInfo.get("imgUrl") + "}\", "); //
										sb.append("	\"media\" : \"" + imgInfo.get("fileId") + "\", "); //

										// if(buttonInfoList.size() > 0) {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\", "); //
										// }else {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\" "); //
										// }
										// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
										// params.get("rcs94Buttons");
										// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
									}
								} else {
									sb.append("	\"mediaUrl\" : \"{}\", "); //
									sb.append("	\"media\" : \"\", "); //

									// if(buttonInfoList.size() > 0) {
									// sb.append(" \"media\" : \"\", "); //
									// }else {
									// sb.append(" \"media\" : \"\" "); //
									// }
									// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
									// params.get("rcs94Buttons");
									// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
								}
								sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
							}
							sb.append("	} ");
							if (k < rcs9CardCount - 1)
								sb.append(", ");
						}

						if (k == 5) {
							sb.append(" { ");
							sb.append("	\"title\" : \"" + params.get("rcs95Title") + "\", "); //
							// String rcs95Content = ((String) params.get("rcs95Content")).replaceAll("\n",
							// "CHR(13)CHR(10)");
							sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcs95Content"))
									+ "\", "); // 메시지

							List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params
									.get("rcs95Buttons");
							// image List
							List<Map<String, Object>> rcs95ImgInfoList = null;
							if (params.containsKey("rcs95ImgInfoList")) {
								rcs95ImgInfoList = (List<Map<String, Object>>) params.get("rcs95ImgInfoList");
								if (rcs95ImgInfoList.size() > 0) {
									Map<String, Object> imgInfo = rcs95ImgInfoList.get(0);// rcs에서 SHORT, TALL에는 이미지가
																							// 1개만 들어온다
									if (imgInfo.containsKey("fileId")) {
										// sb.append(" \""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
										sb.append("	\"mediaUrl\" : \"{" + imgInfo.get("imgUrl") + "}\", "); //
										sb.append("	\"media\" : \"" + imgInfo.get("fileId") + "\", "); //

										// if(buttonInfoList.size() > 0) {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\", "); //
										// }else {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\" "); //
										// }
										// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
										// params.get("rcs95Buttons");
										// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
									}
								} else {
									sb.append("	\"mediaUrl\" : \"{}\", "); //
									sb.append("	\"media\" : \"\", "); //

									// if(buttonInfoList.size() > 0) {
									// sb.append(" \"media\" : \"\", "); //
									// }else {
									// sb.append(" \"media\" : \"\" "); //
									// }
									// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
									// params.get("rcs95Buttons");
									// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
								}
								sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
							}
							sb.append("	} ");
							// if(k < rcs9CardCount-1) sb.append(", ");
						}

					}
					sb.append("	] ");

				} else if ((int) params.get("rcsTemplateTable") == 10) {
//RCS CTALL TYPE ====================================================================
					sb.append("\"rcsPrdType\" : \"CTALL\","); // RCS상품타입(캐러셀[TALL]템플릿) rcsTemplateTable => 10

					// System.out.println("rcs10CardCount type :
					// "+params.get("rcs10CardCount").getClass().getName());
					int rcs10CardCount;
					if (params.get("rcs10CardCount").getClass().getName().equalsIgnoreCase("java.lang.Integer")) {
						rcs10CardCount = (int) params.get("rcs10CardCount");
					} else {
						rcs10CardCount = Integer.parseInt((String) params.get("rcs10CardCount"));
					}

					// int rcs10CardCount = (int)params.get("rcs10CardCount");

					String msgbaseId = "";
					if (rcs10CardCount == 3) {
						msgbaseId = "CMwMhM0300"; // 슬라이드형(Medium,3장)
					} else if (rcs10CardCount == 4) {
						msgbaseId = "CMwMhM0400"; // 슬라이드형(Medium,4장)
					} else if (rcs10CardCount == 5) {
						msgbaseId = "CMwMhM0500"; // 슬라이드형(Medium,5장)
					} else if (rcs10CardCount == 6) {
						msgbaseId = "CMwMhM0600"; // 슬라이드형(Medium,6장)
					}

					sb.append("\"messagebaseId\": \"" + msgbaseId + "\","); // cm.CM_RCS_MSGBASE,
																			// cm_console.CM_RCS_TMP_MSGBASE의
																			// MESSAGEBASEFORM_ID값을 설정
					sb.append("\"callback\": \"" + params.get("callback") + "\",");
					sb.append("\"footer\": \"" + params.get("rcs10HowToDenyReceipt") + "\","); // 무료수신거부 번호, header의 값이
																								// 광고성일 때 footer 값을 포함하지
																								// 않고 발송하면 실패 처리

					sb.append("\"body\": [ ");
					for (int j = 0; j < rcs10CardCount; j++) {
						if (j == 0) {
							sb.append(" { ");
							sb.append("	\"title\" : \"" + params.get("rcs100Title") + "\", "); //
							// String rcs100Content = ((String)
							// params.get("rcs100Content")).replaceAll("\n", "CHR(13)CHR(10)");
							sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcs100Content"))
									+ "\", "); // 메시지

							List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params
									.get("rcs100Buttons");
							// image List
							List<Map<String, Object>> rcs100ImgInfoList = null;
							if (params.containsKey("rcs100ImgInfoList")) {
								rcs100ImgInfoList = (List<Map<String, Object>>) params.get("rcs100ImgInfoList");
								if (rcs100ImgInfoList.size() > 0) {
									Map<String, Object> imgInfo = rcs100ImgInfoList.get(0);// rcs에서 SHORT, TALL에는 이미지가
																							// 1개만 들어온다
									if (imgInfo.containsKey("fileId")) {
										// sb.append(" \""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
										sb.append("	\"mediaUrl\" : \"{" + imgInfo.get("imgUrl") + "}\", "); //
										sb.append("	\"media\" : \"" + imgInfo.get("fileId") + "\", "); //

										// if(buttonInfoList.size() > 0) {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\", "); //
										// }else {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\" "); //
										// }
										// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
										// params.get("rcs100Buttons");
										// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
									}
								} else {
									sb.append("	\"mediaUrl\" : \"{}\", "); //
									sb.append("	\"media\" : \"\", "); //

									// if(buttonInfoList.size() > 0) {
									// sb.append(" \"media\" : \"\", "); //
									// }else {
									// sb.append(" \"media\" : \"\" "); //
									// }
									// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
									// params.get("rcs100Buttons");
									// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
								}
								sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
							}
							sb.append("	} ");
							if (j < rcs10CardCount - 1)
								sb.append(", ");
						}

						if (j == 1) {
							sb.append(" { ");
							sb.append("	\"title\" : \"" + params.get("rcs101Title") + "\", "); //
							// String rcs101Content = ((String)
							// params.get("rcs101Content")).replaceAll("\n", "CHR(13)CHR(10)");
							sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcs101Content"))
									+ "\", "); // 메시지

							List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params
									.get("rcs101Buttons");
							// image List
							List<Map<String, Object>> rcs101ImgInfoList = null;
							if (params.containsKey("rcs101ImgInfoList")) {
								rcs101ImgInfoList = (List<Map<String, Object>>) params.get("rcs101ImgInfoList");
								if (rcs101ImgInfoList.size() > 0) {
									Map<String, Object> imgInfo = rcs101ImgInfoList.get(0);// rcs에서 SHORT, TALL에는 이미지가
																							// 1개만 들어온다
									if (imgInfo.containsKey("fileId")) {
										// sb.append(" \""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
										sb.append("	\"mediaUrl\" : \"{" + imgInfo.get("imgUrl") + "}\", "); //
										sb.append("	\"media\" : \"" + imgInfo.get("fileId") + "\", "); //

										// if(buttonInfoList.size() > 0) {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\", "); //
										// }else {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\" "); //
										// }
										// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
										// params.get("rcs101Buttons");
										// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
									}
								} else {
									sb.append("	\"mediaUrl\" : \"{}\", "); //
									sb.append("	\"media\" : \"\", "); //

									// if(buttonInfoList.size() > 0) {
									// sb.append(" \"media\" : \"\", "); //
									// }else {
									// sb.append(" \"media\" : \"\" "); //
									// }
									// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
									// params.get("rcs101Buttons");
									// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
								}
								sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
							}
							sb.append("	} ");
							if (j < rcs10CardCount - 1)
								sb.append(", ");
						}

						if (j == 2) {
							sb.append(" { ");
							sb.append("	\"title\" : \"" + params.get("rcs102Title") + "\", "); //
							// String rcs102Content = ((String)
							// params.get("rcs102Content")).replaceAll("\n", "CHR(13)CHR(10)");
							sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcs102Content"))
									+ "\", "); // 메시지

							List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params
									.get("rcs102Buttons");
							// image List
							List<Map<String, Object>> rcs102ImgInfoList = null;
							if (params.containsKey("rcs102ImgInfoList")) {
								rcs102ImgInfoList = (List<Map<String, Object>>) params.get("rcs102ImgInfoList");
								if (rcs102ImgInfoList.size() > 0) {
									Map<String, Object> imgInfo = rcs102ImgInfoList.get(0);// rcs에서 SHORT, TALL에는 이미지가
																							// 1개만 들어온다
									if (imgInfo.containsKey("fileId")) {
										// sb.append(" \""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
										sb.append("	\"mediaUrl\" : \"{" + imgInfo.get("imgUrl") + "}\", "); //
										sb.append("	\"media\" : \"" + imgInfo.get("fileId") + "\", "); //

										// if(buttonInfoList.size() > 0) {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\", "); //
										// }else {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\" "); //
										// }
										// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
										// params.get("rcs102Buttons");
										// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
									}
								} else {
									sb.append("	\"mediaUrl\" : \"{}\", "); //
									sb.append("	\"media\" : \"\", "); //

									// if(buttonInfoList.size() > 0) {
									// sb.append(" \"media\" : \"\", "); //
									// }else {
									// sb.append(" \"media\" : \"\" "); //
									// }
									// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
									// params.get("rcs102Buttons");
									// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
								}
								sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
							}
							sb.append("	} ");
							if (j < rcs10CardCount - 1)
								sb.append(", ");
						}

						if (j == 3) {
							sb.append(" { ");
							sb.append("	\"title\" : \"" + params.get("rcs103Title") + "\", "); //
							// String rcs103Content = ((String)
							// params.get("rcs103Content")).replaceAll("\n", "CHR(13)CHR(10)");
							sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcs103Content"))
									+ "\", "); // 메시지

							List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params
									.get("rcs103Buttons");
							// image List
							List<Map<String, Object>> rcs103ImgInfoList = null;
							if (params.containsKey("rcs103ImgInfoList")) {
								rcs103ImgInfoList = (List<Map<String, Object>>) params.get("rcs103ImgInfoList");
								if (rcs103ImgInfoList.size() > 0) {
									Map<String, Object> imgInfo = rcs103ImgInfoList.get(0);// rcs에서 SHORT, TALL에는 이미지가
																							// 1개만 들어온다
									if (imgInfo.containsKey("fileId")) {
										// sb.append(" \""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
										sb.append("	\"mediaUrl\" : \"{" + imgInfo.get("imgUrl") + "}\", "); //
										sb.append("	\"media\" : \"" + imgInfo.get("fileId") + "\", "); //

										// if(buttonInfoList.size() > 0) {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\", "); //
										// }else {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\" "); //
										// }
										// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
										// params.get("rcs103Buttons");
										// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
									}
								} else {
									sb.append("	\"mediaUrl\" : \"{}\", "); //
									sb.append("	\"media\" : \"\", "); //

									// if(buttonInfoList.size() > 0) {
									// sb.append(" \"media\" : \"\", "); //
									// }else {
									// sb.append(" \"media\" : \"\" "); //
									// }
									// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
									// params.get("rcs103Buttons");
									// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
								}
								sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
							}
							sb.append("	} ");
							if (j < rcs10CardCount - 1)
								sb.append(", ");
						}

						if (j == 4) {
							sb.append(" { ");
							sb.append("	\"title\" : \"" + params.get("rcs104Title") + "\", "); //
							// String rcs104Content = ((String)
							// params.get("rcs104Content")).replaceAll("\n", "CHR(13)CHR(10)");
							sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcs104Content"))
									+ "\", "); // 메시지

							List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params
									.get("rcs104Buttons");
							// image List
							List<Map<String, Object>> rcs104ImgInfoList = null;
							if (params.containsKey("rcs104ImgInfoList")) {
								rcs104ImgInfoList = (List<Map<String, Object>>) params.get("rcs104ImgInfoList");
								if (rcs104ImgInfoList.size() > 0) {
									Map<String, Object> imgInfo = rcs104ImgInfoList.get(0);// rcs에서 SHORT, TALL에는 이미지가
																							// 1개만 들어온다
									if (imgInfo.containsKey("fileId")) {
										// sb.append(" \""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
										sb.append("	\"mediaUrl\" : \"{" + imgInfo.get("imgUrl") + "}\", "); //
										sb.append("	\"media\" : \"" + imgInfo.get("fileId") + "\", "); //

										// if(buttonInfoList.size() > 0) {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\", "); //
										// }else {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\" "); //
										// }
										// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
										// params.get("rcs104Buttons");
										// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
									}
								} else {
									sb.append("	\"mediaUrl\" : \"{}\", "); //
									sb.append("	\"media\" : \"\", "); //

									// if(buttonInfoList.size() > 0) {
									// sb.append(" \"media\" : \"\", "); //
									// }else {
									// sb.append(" \"media\" : \"\" "); //
									// }
									// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
									// params.get("rcs104Buttons");
									// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
								}
								sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
							}
							sb.append("	} ");
							if (j < rcs10CardCount - 1)
								sb.append(", ");
						}

						if (j == 5) {
							sb.append(" { ");
							sb.append("	\"title\" : \"" + params.get("rcs105Title") + "\", "); //
							// String rcs105Content = ((String)
							// params.get("rcs105Content")).replaceAll("\n", "CHR(13)CHR(10)");
							sb.append("	\"description\" : \"" + JSONObject.escape((String) params.get("rcs105Content"))
									+ "\", "); // 메시지

							List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params
									.get("rcs105Buttons");
							// image List
							List<Map<String, Object>> rcs105ImgInfoList = null;
							if (params.containsKey("rcs105ImgInfoList")) {
								rcs105ImgInfoList = (List<Map<String, Object>>) params.get("rcs105ImgInfoList");
								if (rcs105ImgInfoList.size() > 0) {
									Map<String, Object> imgInfo = rcs105ImgInfoList.get(0);// rcs에서 SHORT, TALL에는 이미지가
																							// 1개만 들어온다
									if (imgInfo.containsKey("fileId")) {
										// sb.append(" \""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
										sb.append("	\"mediaUrl\" : \"{" + imgInfo.get("imgUrl") + "}\", "); //
										sb.append("	\"media\" : \"" + imgInfo.get("fileId") + "\", "); //

										// if(buttonInfoList.size() > 0) {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\", "); //
										// }else {
										// sb.append(" \"media\" : \""+imgInfo.get("fileId")+"\" "); //
										// }
										// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
										// params.get("rcs105Buttons");
										// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
									}
								} else {
									sb.append("	\"mediaUrl\" : \"{}\", "); //
									sb.append("	\"media\" : \"\", "); //

									// if(buttonInfoList.size() > 0) {
									// sb.append(" \"media\" : \"\", "); //
									// }else {
									// sb.append(" \"media\" : \"\" "); //
									// }
									// List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>)
									// params.get("rcs105Buttons");
									// sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
								}
								sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
							}
							sb.append("	} ");
						}
					}
					sb.append("	] ");
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
							sb.append("	\"imageUrl\" : \"" + CommonUtils.getStrValue(imgInfo, "imgUrl") + "\", "); // 이미지
							sb.append("	\"imageLink\" : \"" + params.get("friendTalkImageLink") + "\" "); // 이미지
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
					sb.append("\"msg\" : \"" + JSONObject.escape((String) params.get("smsContent")) + "\" "); // 메시지
					if (params.containsKey("smsRcvblcNumber")) {
						sb.append(",\"rcvblcInput\" : \"" + params.get("smsRcvblcNumber") + "\""); // 수신거부방법
					}
					sb.append("} ");
				} else if (params.get("smsSendType").equals("M")) {// MMS
					sb.append("\"chTypeList\" : \"" + chTypeList + "\",");
					sb.append("\"ch\" : \"MMS\",");// 발송채널
					sb.append("\"data\" : { ");
					sb.append("\"callback\" : \"" + params.get("callback") + "\",");
					sb.append("\"title\" : \"" + params.get("smsTitle") + "\",");
					sb.append("\"msg\" : \"" + JSONObject.escape((String) params.get("smsContent")) + "\" ");
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

		resultCnt = generalDao.insertGernal("integratedTemplate.insertIntegratedTemplate", sParams);

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
	public RestResult<Object> deleteIntegratedTemplate(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		int resultCnt = generalDao.deleteGernal("integratedTemplate.deleteIntegratedTemplate", params);
		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
			rtn.setData(params);
		}

		return rtn;
	}

	private String buttonAddStr(Map<String, Object> params, String checkChannel,
			List<Map<String, Object>> buttonInfoList) {

		StringBuffer sb = new StringBuffer();

		String buttonType = null;
		String buttonName = null;
		String buttonLink = null;
		String buttonLink1 = null;
		String startDate = null;
		String endDate = null;

		sb.append("\", buttons\": [ ");
		// sb.append("\"suggestions\": [ ");

		int rcsIdx = 1;
		if (!buttonInfoList.isEmpty()) {
			for (Map<String, Object> buttonInfo : buttonInfoList) {

				buttonType = CommonUtils.getStrValue(buttonInfo, "buttonType");
				buttonName = CommonUtils.getStrValue(buttonInfo, "buttonName");
				buttonLink = CommonUtils.getStrValue(buttonInfo, "buttonLink");

				buttonLink1 = CommonUtils.getStrValue(buttonInfo, "buttonLink1");
				startDate = CommonUtils.getStrValue(buttonInfo, "startDate");
				endDate = CommonUtils.getStrValue(buttonInfo, "endDate");

				// System.out.println(">>>>service 003 button 003 : buttonType : "+buttonType);
				// System.out.println(">>>>service 003 button 004 : buttonName : "+buttonName);
				// System.out.println(">>>>service 003 button 005 : buttonLink : "+buttonLink);
				// System.out.println(">>>>service 003 button 006 : buttonLink1 :
				// "+buttonLink1);
				// System.out.println(">>>>service 003 button 007 : startDate : "+startDate);
				// System.out.println(">>>>service 003 button 008 : endDate : "+endDate);

				if (buttonType.equalsIgnoreCase("U")) {
					sb.append("	{ ");
					sb.append("	\"action\": { ");
					sb.append("	\"urlAction\": { "); // URL링크 버튼인 경우
					sb.append("	\"openUrl\": { ");
					sb.append("	\"url\": \"" + buttonLink + "\" "); // 내용
					sb.append("	} ");
					sb.append("	}, ");
					sb.append("	\"buttonType\": \"" + buttonType + "\", "); // 버튼타입
					sb.append("	\"displayText\": \"" + buttonName + "\","); // 버튼이름
					sb.append("	\"postback\": { ");
					// sb.append(" \"data\": \""+params.get("")+"\" "); //set_by_chatbot_open_url
					sb.append("	\"data\": \"set_by_chatbot_open_url\" ");
					sb.append("	} ");
					sb.append("	} ");
					sb.append("	} ");
				}
				if (buttonType.equalsIgnoreCase("C")) {
					sb.append("	{ ");
					sb.append("	\"action\": { ");
					sb.append("	\"clipboardAction\": { "); // 복사하기 버튼인 경우
					sb.append("	\"copyToClipboard\": { ");
					sb.append("	\"text\": \"" + buttonLink + "\" "); // 내용
					sb.append("	} ");
					sb.append("	}, ");
					sb.append("	\"buttonType\": \"" + buttonType + "\", "); // 버튼타입
					sb.append("	\"displayText\": \"" + buttonName + "\", "); // 버튼이름
					sb.append("	\"postback\": { ");
					// sb.append(" \"data\": \""+params.get("")+"\" ");
					// //set_by_chatbot_copy_to_clipboard
					sb.append("	\"data\": \"set_by_chatbot_copy_to_clipboard\" ");
					sb.append("	} ");
					sb.append("	} ");
					sb.append("	} ");
				}
				if (buttonType.equalsIgnoreCase("T")) {
					sb.append("	{ ");
					sb.append("	\"action\": { ");
					sb.append("	\"dialerAction\": { "); // 전화걸기 버튼인 경우
					sb.append("	\"dialPhoneNumber\": { ");
					sb.append("	\"phoneNumber\": \"" + buttonLink + "\" "); // 휴대폰번호
					sb.append("	} ");
					sb.append("	}, ");
					sb.append("	\"buttonType\": \"" + buttonType + "\", "); // 버튼타입
					sb.append("	\"displayText\": \"" + buttonName + "\", "); // 버튼이름
					sb.append("	\"postback\": { ");
					// sb.append(" \"data\": \""+params.get("")+"\" ");
					// //set_by_chatbot_dial_phone_number
					sb.append("	\"data\": \"set_by_chatbot_dial_phone_number\" ");
					sb.append("	} ");
					sb.append("	} ");
					sb.append("	} ");
				}
				if (buttonType.equalsIgnoreCase("S")) {
					sb.append("	{ ");
					sb.append("	\"action\": { ");
					sb.append("	\"calendarAction\": { "); // 일정추가 버튼인 경우
					sb.append("	\"createCalendarEvent\": { ");
					sb.append("	\"startTime\": \"" + startDate + "\", "); // 시작일 2017-03-14T00:00:00Z
					sb.append("	\"endTime\": \"" + endDate + "\", "); // 종료일
					sb.append("	\"title\": \"" + buttonLink + "\", "); // 제목
					sb.append("	\"description\": \"" + buttonLink1 + "\" "); // 내용
					sb.append("	} ");
					sb.append("	}, ");
					sb.append("	\"buttonType\": \"" + buttonType + "\", "); // 버튼타입
					sb.append("	\"displayText\": \"" + buttonName + "\", "); // 버튼이름
					sb.append("	\"postback\": { ");
					// sb.append(" \"data\": \""+params.get("")+"\"
					// ");//set_by_chatbot_create_calendar_event
					sb.append("	\"data\": \"set_by_chatbot_create_calendar_event\" ");
					sb.append("	} ");
					sb.append("	} ");
					sb.append("	} ");
				}
				if (buttonType.equalsIgnoreCase("M")) {
					sb.append("	{ ");
					sb.append("	\"action\": { ");
					sb.append("	\"mapAction\": { "); // 지도맵 버튼인 경우
					sb.append("	\"requestLocationPush\": {} ");
					sb.append("	}, ");
					sb.append("	\"buttonType\": \"" + buttonType + "\", "); // 버튼타입
					sb.append("	\"displayText\": \"" + buttonName + "\", "); // 버튼이름
					sb.append("	\"postback\": { ");
					// sb.append(" \"data\": \""+buttonLink+"\"
					// ");//set_by_chatbot_request_location_push
					sb.append("	\"data\": \"set_by_chatbot_request_location_push\" ");
					sb.append("	} ");
					sb.append("	} ");
					sb.append("	} ");
				}

				if (rcsIdx++ < buttonInfoList.size()) {
					sb.append(", ");
				}
			} // end for(Map<String, Object> buttonInfo : buttonInfoList) {
		}
		sb.append("	] ");
		// sb.append(" }] ");

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
	 * Message base id 조회
	 * 
	 * @param Param
	 * @return
	 * @throws Exception
	 */
	public String selectMessageBaseId(Map<String, Object> params) throws Exception {
		return (String) generalDao.selectGernalObject("smartTemplate.selectMessageBaseId", params);
	}

}
