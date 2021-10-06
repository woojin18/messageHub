package kr.co.uplus.cm.rcsTemplateSend.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.text.StringSubstitutor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.config.ApiConfig;
import kr.co.uplus.cm.rcsTemplate.service.RcsTemplateService;
import kr.co.uplus.cm.sendMessage.service.SendMessageService;
import kr.co.uplus.cm.utils.ApiInterface;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.DateUtil;
import kr.co.uplus.cm.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class RcsTemplateSendService {
	
	@Autowired
	private GeneralDao generalDao;
	
	@Autowired
	private ApiInterface apiInterface;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private SendMessageService sendMsgService;
	
	@Autowired
	private RcsTemplateService rcsTemplateService;

	public RestResult<Object> rcsTemplatePopInit(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		List<Object> result = generalDao.selectGernalList(DB.QRY_SELECT_BRAND_LIST, params);
		
		rtn.setData(result);
				
		return rtn;
	}

	public RestResult<Object> rcsTemplateSearch(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		List<Object> result = generalDao.selectGernalList(DB.QRY_SELECT_RCS_BASE_FORM_POP, params);
		
		rtn.setData(result);
				
		return rtn;
	}

	@SuppressWarnings("unchecked")
	public RestResult<Object> rcsTemplateDetail(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		Map<String, Object> resultData = new HashMap<String, Object>();
		Map<String, Object> rcsTemMap = (Map<String, Object>) generalDao.selectGernalObject(DB.QRY_SELECT_RCS_MESSAGE_FORM, params);
		
		// 기초 데이터 세팅
		resultData.put("tmpltName", rcsTemMap.get("TMPLT_NAME"));			// 템플릿 명
		resultData.put("tmpltId", rcsTemMap.get("MESSAGEBASE_ID"));			// 템플릿 ID
		resultData.put("formNm", rcsTemMap.get("FORM_NAME"));				// 브랜드 명
		
		// JSON OBJECT convert
		String msgInfo = (String) rcsTemMap.get("MESSAGEBASE_INFO");
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		obj = (JSONObject) parser.parse(msgInfo);
		
		resultData.put("cardType", obj.get("cardType"));					// 카드 타입 (서술형, 스타일형)
		
		String cardType = CommonUtils.getString(obj.get("cardType"));
		if("cell".equals(cardType)) {
			// 유형 세팅
			resultData.put("styleNm", rcsTemMap.get("MESSAGEBASEFORM_ID"));		// 유형
			
			JSONObject cellObj = null;
			cellObj = (JSONObject) obj.get("formattedString");
			cellObj = (JSONObject) cellObj.get("RCSMessage");
			cellObj = (JSONObject) cellObj.get("openrichcardMessage");
			cellObj = (JSONObject) cellObj.get("layout");
			JSONArray cellArray = null;
			cellArray = (JSONArray) cellObj.get("children");
			cellObj = (JSONObject) cellArray.get(1);
			cellArray = (JSONArray) cellObj.get("children");
			int styleContentCnt = 0;
			
			// 해당 children의 widget이 LinearLayout인 경우에만 값을 증가
			for(int i=0; i<cellArray.size(); i++) {
				JSONObject cntObj = (JSONObject) cellArray.get(i);
				String widget = CommonUtils.getString(cntObj.get("widget"));
				if("LinearLayout".equals(widget)) styleContentCnt++;
			}
			
			// 스타일형 내용에 들어갈 배열 세팅
			String[] styleArr = new String[styleContentCnt];
			String[] styleInput = new String[styleContentCnt];
			String[] styleInputSec = new String[styleContentCnt];
			Boolean[] styleChk = new Boolean[styleContentCnt];
			int styleChkCnt = 0;
			
			// 스타일형 내용 세팅
			for(int i=0; i<cellArray.size(); i++) {
				JSONObject styleObj = (JSONObject) cellArray.get(i);
				String widget = CommonUtils.getString(styleObj.get("widget"));
				
				// input json인 경우 배열에 값을 추가하고 다음 widget의 속성을 체크
				if("LinearLayout".equals(widget)) {
					JSONArray styleInputArr = (JSONArray) styleObj.get("children");
					
					int inputCnt = styleInputArr.size();
					styleArr[styleChkCnt] = CommonUtils.getString(inputCnt);							// 칸 개수 세팅
					// input 컬럼이 한개인경우 styleInputSec 빈칸으로 처리
					if(inputCnt == 1) {
						JSONObject styleInputObj = (JSONObject) styleInputArr.get(0);
						styleInput[styleChkCnt] = CommonUtils.getString(styleInputObj.get("text"));		// 첫 input 세팅
						styleInputSec[styleChkCnt] = "";												// 두번째 input 세팅
					} else {
						JSONObject styleInputObj = (JSONObject) styleInputArr.get(0);
						styleInput[styleChkCnt] = CommonUtils.getString(styleInputObj.get("text"));
						styleInputObj = (JSONObject) styleInputArr.get(1);
						styleInputSec[styleChkCnt] = CommonUtils.getString(styleInputObj.get("text"));
					}
					
					// 다음 children의 widget 속성을 기준으로 LinerLayout인경우 수평선을 강제로 세팅하고 sylteChkCnt를 증가 시킨다.
					// widget 속성이 view인 경우 다음 스타일형 내용 세팅의 for문을 통해 수평선을 세팅하고 styleChkCnt 값을 증가시킨다.
					// 다음 속성이 없는 마지막 children의 경우 수평선을 X로
					if(i != (cellArray.size()-1)) {
						JSONObject nextCheckObj = (JSONObject) cellArray.get(i+1);
						String nextCheckWidget = CommonUtils.getString(nextCheckObj.get("widget"));
						if("LinearLayout".equals(nextCheckWidget)) {
							styleChk[styleChkCnt] = false;
							styleChkCnt++;
						}
					} else {
						styleChk[styleChkCnt] = false;
					}
				} else {
					String styleChkLine = CommonUtils.getString(styleObj.get("visibility"));
					if("visible".equals(styleChkLine)) {
						styleChk[styleChkCnt] = true;
						styleChkCnt++;
					} else {
						styleChk[styleChkCnt] = false;
						styleChkCnt++;
					}
				}
			}
			// style형 input 내용 세팅 
			resultData.put("styleContentCnt", styleContentCnt);
			resultData.put("styleArr", styleArr);
			resultData.put("styleInput", styleInput);
			resultData.put("styleInputSec", styleInputSec);
			resultData.put("styleChk", styleChk);
		} else if ("description".equals(cardType)) {
			resultData.put("desNm", rcsTemMap.get("MESSAGEBASEFORM_ID"));		// 유형
			resultData.put("textContents", obj.get("inputText"));				// 내용
		}
		
		// 버튼 파라미터 세팅
		obj = (JSONObject) obj.get("formattedString");
		obj = (JSONObject) obj.get("RCSMessage");
		obj = (JSONObject) obj.get("openrichcardMessage");
		JSONArray jsonArr = null;
		
		jsonArr = (JSONArray) obj.get("suggestions");
		// 비어있는 버튼 Action 배열을 삭제
		if(jsonArr != null) {
			int btnCnt = jsonArr.size();
			if(btnCnt > 0) {
				for(int i=0; i<jsonArr.size(); i++) {
					Map<String, Object> btnMap = (Map<String, Object>) jsonArr.get(i);
					if(btnMap==null) {
						jsonArr.remove(i);
					}
				}
			}
		}
		
		// 버튼 세팅
		resultData.put("btnCnt", jsonArr.size());
		String[] selectBtn = new String[jsonArr.size()];		// 버튼 종류
		String[] btnNm = new String[jsonArr.size()];			// 버튼 이름
		
		for(int i=0; i<jsonArr.size(); i++) {
			JSONObject jsonArrObj = null;
			jsonArrObj = (JSONObject) jsonArr.get(i);
			jsonArrObj = (JSONObject) jsonArrObj.get("action");
			
			JSONObject urlObj = null;
			JSONObject clipboardObj = null;
			JSONObject dialerObj = null;
			JSONObject calendarObj = null;
			JSONObject mapActionObj = null;
			
			if(jsonArrObj.get("urlAction") != null) {
				urlObj = (JSONObject) jsonArrObj.get("urlAction");
				urlObj = (JSONObject) urlObj.get("openUrl");
				jsonArrObj.put("linkType", "urlAction");
				
				selectBtn[i] = "URL 링크";
				btnNm[i] = CommonUtils.getString(jsonArrObj.get("displayText"));
			} else if(jsonArrObj.get("clipboardAction") != null) {
				clipboardObj = (JSONObject) jsonArrObj.get("clipboardAction");
				clipboardObj = (JSONObject) clipboardObj.get("copyToClipboard");
				jsonArrObj.put("linkType", "clipboardAction");
				
				selectBtn[i] = "복사하기";
				btnNm[i] = CommonUtils.getString(jsonArrObj.get("displayText"));
			} else if(jsonArrObj.get("dialerAction") != null) {
				dialerObj = (JSONObject) jsonArrObj.get("dialerAction");
				dialerObj = (JSONObject) dialerObj.get("dialPhoneNumber");
				jsonArrObj.put("linkType", "dialerAction");
				
				selectBtn[i] = "전화걸기";
				btnNm[i] = CommonUtils.getString(jsonArrObj.get("displayText"));
			} else if(jsonArrObj.get("calendarAction") != null) {
				calendarObj = (JSONObject) jsonArrObj.get("calendarAction");
				calendarObj = (JSONObject) calendarObj.get("createCalendarEvent");
				jsonArrObj.put("linkType", "calendarAction");
				
				selectBtn[i] = "일정추가";
				btnNm[i] = CommonUtils.getString(jsonArrObj.get("displayText"));
			} else if(jsonArrObj.get("mapAction") != null) {
				mapActionObj = (JSONObject) jsonArrObj.get("mapAction");
				mapActionObj = (JSONObject) mapActionObj.get("dialPhoneNumber");
				jsonArrObj.put("linkType", "mapAction");
				
				selectBtn[i] = "지도맵";
				btnNm[i] = CommonUtils.getString(jsonArrObj.get("displayText"));
			}
		}
		
		resultData.put("selectBtn", selectBtn);
		resultData.put("btnNm", btnNm);
		resultData.put("rcsButtons", jsonArr);
		
		return rtn.setData(resultData);
	}

	
	public RestResult<Object> selectCallbackList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		List<Object> result = generalDao.selectGernalList(DB.QRY_SELECT_RCS_CALLBACK_LIST, params);
		
		rtn.setData(result);
		
		return rtn;
	}

	public RestResult<Object> rcsMsgSave(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>(true);
		Map<String, Object> paramsData = (Map<String, Object>) params.get("data");
		String templateRadioBtn	= CommonUtils.getString(params.get("templateRadioBtn"));		// RCS 유형
		boolean carouSelType	= (boolean) params.get("carouSelType");
		String saveBoxId		= CommonUtils.getCommonId("BOX", 5);							// MSGBASE KEY
		String corpId			= CommonUtils.getString(params.get("corpId"));					// CORPID
		String brandId			= CommonUtils.getString(paramsData.get("brandId"));				// BRANDID
		String copyPossYn		= CommonUtils.getString(paramsData.get("copy"));				// 복사여부
		String adYn				= CommonUtils.getString(paramsData.get("adYn"));				// 광고여부
		String callback			= CommonUtils.getString(paramsData.get("callback"));			// 대체발송 전화번호
		String senderType		= CommonUtils.getString(paramsData.get("senderType"));			// 대체 발송 코드
		
		// 공통 파라미터 세팅
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("saveBoxId", saveBoxId);
		paramMap.put("corpId", corpId);
		paramMap.put("brandId", brandId);
		
		// RCS 유형에 따라 파라미터 세팅
		if(!carouSelType) {
			// msgbaseId 세팅 로직 수행
			String msgbaseId = (String) generalDao.selectGernalObject(DB.QRY_SELECT_RCS_MESSAGE_ID, paramMap);
			
			// 텍스트 미승인형의 경우 msgBaseId를 DB에서 호출, 나머지경우는 radiobtn이 msgBaseId
			if("text".equals(templateRadioBtn)) {
				paramMap.put("msgbaseId", msgbaseId);
			} else {
				paramMap.put("msgbaseId", templateRadioBtn);
			}
			
			paramMap.put("saveBoxName" , CommonUtils.getString(paramsData.get("saveContent")));
			if("text".equals(templateRadioBtn)) {
				paramMap.put("adYn", null);
				paramMap.put("freeReceiveNum", null);
			} else {
				if("no".equals(adYn)) {
					paramMap.put("adYn" , "N");
				} else {
					paramMap.put("adYn", "Y");
				}
				paramMap.put("freeReceiveNum", CommonUtils.getString(paramsData.get("freeReceiveNum")));
			}
			if("no".equals(copyPossYn)) {
				paramMap.put("copyPossYn" , "N");
			} else {
				paramMap.put("copyPossYn", "Y");
			}
			paramMap.put("callback", callback);
			if("UNUSED".equals(senderType)) {
				paramMap.put("replcSenderCode", null);
				paramMap.put("callbackTitle", null);
				paramMap.put("callbackContents", null);
				paramMap.put("callbackImgUrl", null);
				paramMap.put("callbackFileId", null);
			} else if("SMS".equals(senderType)) {
				paramMap.put("replcSenderCode", "SMS");
				paramMap.put("callbackTitle", null);
				paramMap.put("callbackContents", CommonUtils.getString(paramsData.get("callbackContents")));
				paramMap.put("callbackImgUrl", null);
				paramMap.put("callbackFileId", null);
			} else if("LMS".equals(senderType)) {
				paramMap.put("replcSenderCode", "LMS");
				paramMap.put("callbackTitle", CommonUtils.getString(paramsData.get("callbackTitle")));
				paramMap.put("callbackContents", CommonUtils.getString(paramsData.get("callbackContents")));
				paramMap.put("callbackImgUrl", null);
				paramMap.put("callbackFileId", null);
			} else if("MMS".equals(senderType)) {
				paramMap.put("replcSenderCode", "MMS");
				paramMap.put("callbackTitle", CommonUtils.getString(paramsData.get("callbackTitle")));
				paramMap.put("callbackContents", CommonUtils.getString(paramsData.get("callbackContents")));
				paramMap.put("callbackImgUrl", CommonUtils.getString(paramsData.get("callbackImgUrl")));
				paramMap.put("callbackFileId", CommonUtils.getString(paramsData.get("callbackFileId")));
			}
			paramMap.put("userId", CommonUtils.getString(params.get("userId")));
			
			JSONArray jsonArr = new JSONArray();
			JSONObject jsonObj = new JSONObject();
			JSONArray jsonBtnArr = new JSONArray();
			
			int btnCnt = CommonUtils.getInt(paramsData.get("btnCnt"));
			if(btnCnt>0) {
				ArrayList<String> selectBtn		= (ArrayList<String>) paramsData.get("selectBtn");
				ArrayList<String> btnNm			= (ArrayList<String>) paramsData.get("btnNm");
				ArrayList<String> contents		= (ArrayList<String>) paramsData.get("contents");
				ArrayList<String> calendarTitle	= (ArrayList<String>) paramsData.get("calendarTitle");
				ArrayList<String> calendarDes	= (ArrayList<String>) paramsData.get("calendarDes");
				ArrayList<String> initStartDate	= (ArrayList<String>) paramsData.get("initStartDate");
				ArrayList<String> initEndDate	= (ArrayList<String>) paramsData.get("initEndDate");
				
				for(int i=0; i<btnCnt; i++) {
					JSONObject btnObj = new JSONObject();
					String selectBtnStr = selectBtn.get(i);
					if("urlAction".equals(selectBtnStr)) {
						Map<String, Object> urlActionMap = new HashMap<String, Object>();
						Map<String, Object> openUrlMap = new HashMap<String, Object>();
						Map<String, Object> openUrlTextMap = new HashMap<String, Object>();
						Map<String, Object> postbackMap = new HashMap<String, Object>();
						
						openUrlTextMap.put("url", contents.get(i));
						openUrlMap.put("openUrl", openUrlTextMap);
						postbackMap.put("data", "set_by_chatbot_open_url");
						
						urlActionMap.put("urlAction", openUrlMap);
						urlActionMap.put("displayText", btnNm.get(i));
						urlActionMap.put("postback", postbackMap);
						
						btnObj.put("action", urlActionMap);
					} else if ("clipboardAction".equals(selectBtnStr)) {
						Map<String, Object> clipboardActionMap = new HashMap<String, Object>();
						Map<String, Object> copyToClipboardMap = new HashMap<String, Object>();
						Map<String, Object> copyToClipboardTextMap = new HashMap<String, Object>();
						Map<String, Object> postbackMap = new HashMap<String, Object>();
						
						copyToClipboardTextMap.put("text", contents.get(i));
						copyToClipboardMap.put("copyToClipboard", copyToClipboardTextMap);
						postbackMap.put("data", "set_by_chatbot_copy_to_clipboard");
						
						clipboardActionMap.put("clipboardAction", copyToClipboardMap);
						clipboardActionMap.put("displayText", btnNm.get(i));
						clipboardActionMap.put("postback", postbackMap);
						
						btnObj.put("action", clipboardActionMap);
					} else if ("dialerAction".equals(selectBtnStr)) {
						Map<String, Object> dialerActionMap = new HashMap<String, Object>();
						Map<String, Object> dialPhoneNumberMap = new HashMap<String, Object>();
						Map<String, Object> dialPhoneNumberTextMap = new HashMap<String, Object>();
						Map<String, Object> postbackMap = new HashMap<String, Object>();
						
						dialPhoneNumberTextMap.put("phoneNumber", contents.get(i));
						dialPhoneNumberMap.put("dialPhoneNumber", dialPhoneNumberTextMap);
						postbackMap.put("data", "set_by_chatbot_dial_phone_number");
						
						dialerActionMap.put("dialerAction", dialPhoneNumberMap);
						dialerActionMap.put("displayText", btnNm.get(i));
						dialerActionMap.put("postback", postbackMap);
						
						btnObj.put("action", dialerActionMap);
					} else if ("calendarAction".equals(selectBtnStr)) {
						Map<String, Object> calendarActionMap = new HashMap<String, Object>();
						Map<String, Object> createCalendarEventMap = new HashMap<String, Object>();
						Map<String, Object> createCalendarEventTextMap = new HashMap<String, Object>();
						Map<String, Object> postbackMap = new HashMap<String, Object>();
						
						createCalendarEventTextMap.put("title", calendarTitle.get(i));
						createCalendarEventTextMap.put("description", calendarDes.get(i));
						String startTime = initStartDate.get(i) + "T00:00:00Z";
						String endTime = initEndDate.get(i) + "T23:59:59Z";
						createCalendarEventTextMap.put("startTime", startTime);
						createCalendarEventTextMap.put("endTime", endTime);
						
						createCalendarEventMap.put("createCalendarEvent", createCalendarEventTextMap);
						postbackMap.put("data", "set_by_chatbot_create_calendar_event");
						
						calendarActionMap.put("calendarAction", createCalendarEventMap);
						calendarActionMap.put("displayText", btnNm.get(i));
						calendarActionMap.put("postback", postbackMap);
						
						btnObj.put("action", calendarActionMap);
					} else if ("mapAction".equals(selectBtnStr)) {
						Map<String, Object> mapActionMap = new HashMap<String, Object>();
						Map<String, Object> requestLocationPushMap = new HashMap<String, Object>();
						Map<String, Object> postbackMap = new HashMap<String, Object>();
						
						requestLocationPushMap.put("requestLocationPush", new HashMap<String, Object>());
						postbackMap.put("data", "set_by_cahtbot_request_location_push");
						
						mapActionMap.put("mapAction", requestLocationPushMap);
						mapActionMap.put("displayText", btnNm.get(i));
						mapActionMap.put("postback", postbackMap);
						
						btnObj.put("action", mapActionMap);
					}
					
					jsonBtnArr.add(i, btnObj);
				}
			}
			jsonObj.put("title", CommonUtils.getString(paramsData.get("textTitle")));
			jsonObj.put("description", CommonUtils.getString(paramsData.get("textContents")));
			jsonObj.put("mediaUrl", CommonUtils.getString(paramsData.get("imgUrl")));
			jsonObj.put("media", CommonUtils.getString(paramsData.get("fileId")));
			jsonObj.put("suggestions", jsonBtnArr);
			
			jsonArr.add(0, jsonObj);
			
			String msgBaseInfo = jsonArr.toString();
			paramMap.put("msgBaseInfo", msgBaseInfo);
			
			// table insert
			generalDao.insertGernal(DB.QRY_INSERT_RCS_TMP_MSGBASE, paramMap);
		} else {
			// 기초 insert 세팅
			paramMap.put("msgbaseId", templateRadioBtn);
			
			paramMap.put("saveBoxName" , CommonUtils.getString(paramsData.get("saveContent")));
			if("no".equals(copyPossYn)) {
				paramMap.put("adYn" , "N");
			} else {
				paramMap.put("adYn", "Y");
			}
			paramMap.put("freeReceiveNum", CommonUtils.getString(paramsData.get("freeReceiveNum")));
			
			if("no".equals(copyPossYn)) {
				paramMap.put("copyPossYn" , "N");
			} else {
				paramMap.put("copyPossYn", "Y");
			}
			paramMap.put("callback", callback);
			if("UNUSED".equals(senderType)) {
				paramMap.put("replcSenderCode", null);
				paramMap.put("callbackTitle", null);
				paramMap.put("callbackContents", null);
			} else if("SMS".equals(senderType)) {
				paramMap.put("replcSenderCode", "SMS");
				paramMap.put("callbackTitle", null);
				paramMap.put("callbackContents", CommonUtils.getString(paramsData.get("callbackContents")));
			} else if("LMS".equals(senderType)) {
				paramMap.put("replcSenderCode", "LMS");
				paramMap.put("callbackTitle", CommonUtils.getString(paramsData.get("callbackTitle")));
				paramMap.put("callbackContents", CommonUtils.getString(paramsData.get("callbackContents")));
			}
			paramMap.put("userId", CommonUtils.getString(params.get("userId")));
			
			Map<String, Object> carouselMap		= (Map<String, Object>) paramsData.get("carouselObj");
			ArrayList<String> textTitleArr		= (ArrayList<String>) carouselMap.get("textTitle");
			ArrayList<String> textContentsArr	= (ArrayList<String>) carouselMap.get("textContents");
			ArrayList<String> imgUrlArr			= (ArrayList<String>) carouselMap.get("imgUrl");
			ArrayList<String> fileIdArr			= (ArrayList<String>) carouselMap.get("fileId");
			ArrayList<Object> btnArr			= (ArrayList<Object>) carouselMap.get("btnArr");
			
			JSONArray jsonArr = new JSONArray();
			
			// msgBaseInfo 세팅
			for(int i=0; i<textTitleArr.size(); i++) {
				// btn세팅
				JSONObject jsonObj = new JSONObject();
				JSONArray jsonBtnArr = new JSONArray();
				Map<String, Object> btnMap = (Map<String, Object>) btnArr.get(i);
				Map<String, Object> btnObj = new HashMap<String, Object>();
				
				int btnCnt = CommonUtils.getInt(btnMap.get("btnCnt"));
				
				if(btnCnt>0) {
					ArrayList<String> selectBtn		= (ArrayList<String>) btnMap.get("selectBtn");
					ArrayList<String> btnNm			= (ArrayList<String>) btnMap.get("btnNm");
					ArrayList<String> contents		= (ArrayList<String>) btnMap.get("contents");
					ArrayList<String> calendarTitle	= (ArrayList<String>) btnMap.get("calendarTitle");
					ArrayList<String> calendarDes	= (ArrayList<String>) btnMap.get("calendarDes");
					ArrayList<String> initDate		= (ArrayList<String>) btnMap.get("initDate");
					ArrayList<String> initEndDate	= (ArrayList<String>) btnMap.get("initEndDate");
					
					for(int j=0; j<btnCnt; j++) {
						String selectBtnStr = selectBtn.get(j);
						if("urlAction".equals(selectBtnStr)) {
							Map<String, Object> urlActionMap = new HashMap<String, Object>();
							Map<String, Object> openUrlMap = new HashMap<String, Object>();
							Map<String, Object> openUrlTextMap = new HashMap<String, Object>();
							Map<String, Object> postbackMap = new HashMap<String, Object>();
							
							openUrlTextMap.put("url", contents.get(j));
							openUrlMap.put("openUrl", openUrlTextMap);
							postbackMap.put("data", "set_by_chatbot_open_url");
							
							urlActionMap.put("urlAction", openUrlMap);
							urlActionMap.put("displayText", btnNm.get(j));
							urlActionMap.put("postback", postbackMap);
							
							btnObj.put("action", urlActionMap);
						} else if ("clipboardAction".equals(selectBtnStr)) {
							Map<String, Object> clipboardActionMap = new HashMap<String, Object>();
							Map<String, Object> copyToClipboardMap = new HashMap<String, Object>();
							Map<String, Object> copyToClipboardTextMap = new HashMap<String, Object>();
							Map<String, Object> postbackMap = new HashMap<String, Object>();
							
							copyToClipboardTextMap.put("text", contents.get(j));
							copyToClipboardMap.put("copyToClipboard", copyToClipboardTextMap);
							postbackMap.put("data", "set_by_chatbot_copy_to_clipboard");
							
							clipboardActionMap.put("clipboardAction", copyToClipboardMap);
							clipboardActionMap.put("displayText", btnNm.get(j));
							clipboardActionMap.put("postback", postbackMap);
							
							btnObj.put("action", clipboardActionMap);
						} else if ("dialerAction".equals(selectBtnStr)) {
							Map<String, Object> dialerActionMap = new HashMap<String, Object>();
							Map<String, Object> dialPhoneNumberMap = new HashMap<String, Object>();
							Map<String, Object> dialPhoneNumberTextMap = new HashMap<String, Object>();
							Map<String, Object> postbackMap = new HashMap<String, Object>();
							
							dialPhoneNumberTextMap.put("phoneNumber", contents.get(j));
							dialPhoneNumberMap.put("dialPhoneNumber", dialPhoneNumberTextMap);
							postbackMap.put("data", "set_by_chatbot_dial_phone_number");
							
							dialerActionMap.put("dialerAction", dialPhoneNumberMap);
							dialerActionMap.put("displayText", btnNm.get(j));
							dialerActionMap.put("postback", postbackMap);
							
							btnObj.put("action", dialerActionMap);
						} else if ("calendarAction".equals(selectBtnStr)) {
							Map<String, Object> calendarActionMap = new HashMap<String, Object>();
							Map<String, Object> createCalendarEventMap = new HashMap<String, Object>();
							Map<String, Object> createCalendarEventTextMap = new HashMap<String, Object>();
							Map<String, Object> postbackMap = new HashMap<String, Object>();
							
							createCalendarEventTextMap.put("title", calendarTitle.get(j));
							createCalendarEventTextMap.put("description", calendarDes.get(j));
							String startTime = initDate.get(i) + "T00:00:00Z";
							String endTime = initEndDate.get(i) + "T23:59:59Z";
							createCalendarEventTextMap.put("startTime", startTime);
							createCalendarEventTextMap.put("endTime", endTime);
							
							createCalendarEventMap.put("createCalendarEvent", createCalendarEventTextMap);
							postbackMap.put("data", "set_by_chatbot_create_calendar_event");
							
							calendarActionMap.put("calendarAction", createCalendarEventMap);
							calendarActionMap.put("displayText", btnNm.get(j));
							calendarActionMap.put("postback", postbackMap);
							
							btnObj.put("action", calendarActionMap);
						} else if ("mapAction".equals(selectBtnStr)) {
							Map<String, Object> mapActionMap = new HashMap<String, Object>();
							Map<String, Object> requestLocationPushMap = new HashMap<String, Object>();
							Map<String, Object> postbackMap = new HashMap<String, Object>();
							
							requestLocationPushMap.put("requestLocationPush", new HashMap<String, Object>());
							postbackMap.put("data", "set_by_cahtbot_request_location_push");
							
							mapActionMap.put("mapAction", requestLocationPushMap);
							mapActionMap.put("displayText", btnNm.get(j));
							mapActionMap.put("postback", postbackMap);
							
							btnObj.put("action", mapActionMap);
						}
						jsonBtnArr.add(j, btnObj);
					}
					
				}
				
				
				jsonObj.put("title", textTitleArr.get(i));
				jsonObj.put("description", textContentsArr.get(i));
				jsonObj.put("mediaUrl", imgUrlArr.get(i));
				jsonObj.put("media", fileIdArr.get(i));
				jsonObj.put("suggestions", jsonBtnArr);
				
				jsonArr.add(i, jsonObj);
			}
			
			String msgBaseInfo = jsonArr.toString();
			paramMap.put("msgBaseInfo", msgBaseInfo);
			
			// table insert
			generalDao.insertGernal(DB.QRY_INSERT_RCS_TMP_MSGBASE, paramMap);
		}

		return rtn;
	}

	public RestResult<Object> selectRcsMsgList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		Map<String, Object> paramMap = params;
		Map<String, Object> pageInfo = (Map<String, Object>) params.get("pageInfo");
		String srcInput= CommonUtils.getString(params.get("srcInput"));
		String srcSelect = CommonUtils.getString(params.get("srcSelect"));		// brand msg null
		String templateRadioBtn= CommonUtils.getString(params.get("templateRadioBtn"));
		String[] msgIdArr = {"SS000000", "SL000000", "SMwThM00", "SMwThT00", "CMwShS0300", "CMwShS0400", "CMwShS0500", "CMwShS0600", "CMwMhM0300", "CMwMhM0400", "CMwMhM0500", "CMwMhM0600"};
		
		paramMap.put("srcInput", srcInput);
		paramMap.put("srcSelect", srcSelect);
		paramMap.put("corpId", params.get("corpId"));
		if("text".equals(templateRadioBtn)) {
			// 텍스트 미승인형일 경우 조건 세팅
			paramMap.put("msgIdArr", msgIdArr);
		} else {
			paramMap.put("msgbaseId", templateRadioBtn);
		}
		
		if (paramMap.containsKey("pageNo") && CommonUtils.isNotEmptyObject(paramMap.get("pageNo"))
				&& paramMap.containsKey("listSize") && CommonUtils.isNotEmptyObject(paramMap.get("listSize"))) {
			rtn.setPageProps(paramMap);
			if (rtn.getPageInfo() != null) {
				// 카운트 쿼리 실행
				int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_RCS_TMP_MSGBASE_CNT, paramMap);
				rtn.getPageInfo().put("totCnt", listCnt);
			}
		}

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_RCS_TMP_MSGBASE, paramMap);
		rtn.setData(rtnList);
		
		return rtn;
	}

	public void deleteRcsTmpMsgbase(Map<String, Object> params) throws Exception {
		List<String> saveBoxList = (List<String>) params.get("saveBoxIdArr");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		for(String data : saveBoxList) {
			paramMap.put("saveBoxId", data);
			generalDao.deleteGernal(DB.QRY_DELETE_RCS_TMP_BSGBASE, paramMap);
		}
	}

	public RestResult<Object> selectRcsMsgDetail(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> msgDetail = (Map<String, Object>) generalDao.selectGernalObject(DB.QRY_SELECT_RCS_TMP_MSGBASE_DETAIL, params);
		String btnType = CommonUtils.getString(params.get("templateRadioBtn"));
		boolean carouselType = (boolean) params.get("carouSelType");
		
		// 타입에 따른 json 값 세팅 후 return
		if(!carouselType) {
			String brandId = CommonUtils.getString(msgDetail.get("BRAND_ID"));
			String saveContent = CommonUtils.getString(msgDetail.get("SAVE_BOX_NAME"));
			String adYn = CommonUtils.getString(msgDetail.get("AD_YN"));
			String freeReceiveNum = CommonUtils.getString(msgDetail.get("FREE_RECEIVE_NUM"));
			String copyPossYn = CommonUtils.getString(msgDetail.get("COPY_POSS_YN"));
			String callback = CommonUtils.getString(msgDetail.get("CALLBACK"));
			String replcSenderCode = CommonUtils.getString(msgDetail.get("REPLC_SENDER_CODE"));
			String callbackTitle = CommonUtils.getString(msgDetail.get("CALLBACK_TITLE"));
			String callbackContents = CommonUtils.getString(msgDetail.get("CALLBACK_CONTENTS"));
			
			// JSON ARRAY parsing
			String msgInfo = (String) msgDetail.get("MESSAGEBASE_INFO");
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(msgInfo);
			JSONArray jsonArr = (JSONArray) obj;
			JSONObject jsonObj = (JSONObject) jsonArr.get(0);
			
			String textTitle = CommonUtils.getString(jsonObj.get("title"));
			String textContents = CommonUtils.getString(jsonObj.get("description"));
			String mediaUrl = CommonUtils.getString(jsonObj.get("mediaUrl"));
			String media = CommonUtils.getString(jsonObj.get("media"));
			
			// btn 정보가 있다면 세팅
			JSONArray btnArr = (JSONArray) jsonObj.get("suggestions");
			if(btnArr.size()>0) {
				int btnCnt = btnArr.size();
				String[] selectBtnArr		= new String[btnCnt];
				String[] btnNmArr			= new String[btnCnt];
				String[] contentsArr		= new String[btnCnt];
				String[] calendarTitleArr	= new String[btnCnt];
				String[] calendarDesArr		= new String[btnCnt];
				String[] initDateArr		= new String[btnCnt];
				String[] initEndDateArr		= new String[btnCnt];
				
				for(int i=0; i<btnArr.size(); i++) {
					JSONObject btnObj = (JSONObject) btnArr.get(i);
					btnObj = (JSONObject) btnObj.get("action");
					
					JSONObject btnDetailObj = new JSONObject();
					String selectBtn = "";
					String btnNm = "";
					String contents = "";
					String calendarTitle = "";
					String calendarDes = "";
					String initDate = "";
					String initEndDate = "";
					
					if(btnObj.get("urlAction") != null) {
						// 버튼 이름 세팅
						selectBtn = "urlAction";
						btnNm = CommonUtils.getString(btnObj.get("displayText"));
						
						btnDetailObj = (JSONObject) btnObj.get("urlAction");
						btnDetailObj = (JSONObject) btnDetailObj.get("openUrl");
						// 내용 세팅
						contents = CommonUtils.getString(btnDetailObj.get("url"));
						
					} else if(btnObj.get("clipboardAction") != null) {
						// 버튼 이름 세팅
						selectBtn = "clipboardAction";
						btnNm = CommonUtils.getString(btnObj.get("displayText"));
						
						btnDetailObj = (JSONObject) btnObj.get("clipboardAction");
						btnDetailObj = (JSONObject) btnDetailObj.get("copyToClipboard");
						// 내용 세팅
						contents = CommonUtils.getString(btnDetailObj.get("text"));
					
					} else if(btnObj.get("dialerAction") != null) {
						// 버튼 이름 세팅
						selectBtn = "dialerAction";
						btnNm = CommonUtils.getString(btnObj.get("displayText"));
						
						btnDetailObj = (JSONObject) btnObj.get("dialerAction");
						btnDetailObj = (JSONObject) btnDetailObj.get("dialPhoneNumber");
						// 내용 세팅
						contents = CommonUtils.getString(btnDetailObj.get("phoneNumber"));
						
					} else if(btnObj.get("calendarAction") != null) {
						// 버튼 이름 세팅
						selectBtn = "calendarAction";
						btnNm = CommonUtils.getString(btnObj.get("displayText"));
						
						btnDetailObj = (JSONObject) btnObj.get("calendarAction");
						btnDetailObj = (JSONObject) btnDetailObj.get("createCalendarEvent");
						// 내용 세팅
						calendarTitle = CommonUtils.getString(btnDetailObj.get("title"));
						calendarDes = CommonUtils.getString(btnDetailObj.get("description"));
						initDate = CommonUtils.getString(btnDetailObj.get("startTime"));
						initEndDate = CommonUtils.getString(btnDetailObj.get("endTime"));
						
					} else if(btnObj.get("mapAction") != null) {
						// 버튼 이름 세팅
						selectBtn = "mapAction";
						btnNm = CommonUtils.getString(btnObj.get("displayText"));
					}
					selectBtnArr[i] = selectBtn;
					btnNmArr[i] = btnNm;
					contentsArr[i] = contents;
					calendarTitleArr[i] = calendarTitle;
					calendarDesArr[i] = calendarDes;
					initDateArr[i] = initDate;
					initEndDateArr[i] = initEndDate;
				}
				rtnMap.put("btnCnt", btnCnt);
				rtnMap.put("selectBtn", selectBtnArr);
				rtnMap.put("btnNm", btnNmArr);
				rtnMap.put("contents", contentsArr);
				rtnMap.put("calendarTitle", calendarTitleArr);
				rtnMap.put("calendarDes", calendarDesArr);
				rtnMap.put("initDate", initDateArr);
				rtnMap.put("initEndDate", initEndDateArr);
			}
			rtnMap.put("brandId", brandId);
			rtnMap.put("saveContent", saveContent);
			if("Y".equals(adYn)) {
				rtnMap.put("adYn", "yes");
			} else {
				rtnMap.put("adYn", "no");
			}
			rtnMap.put("freeReceiveNum", freeReceiveNum);
			if("Y".equals(copyPossYn)) {
				rtnMap.put("copy", "yes");
			} else {
				rtnMap.put("copy", "no");
			}
			rtnMap.put("callback", callback);
			if("SMS".equals(replcSenderCode)) {
				rtnMap.put("senderType", "SMS");
			} else if("LMS".equals(replcSenderCode)) {
				rtnMap.put("senderType", "LMS");
			} else if("MMS".equals(replcSenderCode)) {
				rtnMap.put("senderType", "MMS");
			} else {
				rtnMap.put("senderType", "UNUSED");
			}
			rtnMap.put("callbackTitle", callbackTitle);
			rtnMap.put("callbackContents", callbackContents);
			rtnMap.put("textTitle", textTitle);
			rtnMap.put("textContents", textContents);
			rtnMap.put("imgUrl", mediaUrl);
			rtnMap.put("fileId", media);
		} else {
			String brandId = CommonUtils.getString(msgDetail.get("BRAND_ID"));
			String saveContent = CommonUtils.getString(msgDetail.get("SAVE_BOX_NAME"));
			String adYn = CommonUtils.getString(msgDetail.get("AD_YN"));
			String freeReceiveNum = CommonUtils.getString(msgDetail.get("FREE_RECEIVE_NUM"));
			String copyPossYn = CommonUtils.getString(msgDetail.get("COPY_POSS_YN"));
			String callback = CommonUtils.getString(msgDetail.get("CALLBACK"));
			String replcSenderCode = CommonUtils.getString(msgDetail.get("REPLC_SENDER_CODE"));
			String callbackTitle = CommonUtils.getString(msgDetail.get("CALLBACK_TITLE"));
			String callbackContents = CommonUtils.getString(msgDetail.get("CALLBACK_CONTENTS"));
			
			// JSON ARRAY parsing
			String msgInfo = (String) msgDetail.get("MESSAGEBASE_INFO");
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(msgInfo);
			JSONArray jsonArr = (JSONArray) obj;
			
			// 제목 url description media 세팅
			ArrayList<Object> textTitleArr = new ArrayList<>();
			ArrayList<Object> textContentsArr = new ArrayList<>();
			ArrayList<Object> mediaUrlArr = new ArrayList<>();
			ArrayList<Object> mediaArr = new ArrayList<>();
			ArrayList<Object> btnArrLst = new ArrayList<>();
			
			for(int i=0; i<jsonArr.size(); i++) {
				JSONObject jsonObj = (JSONObject) jsonArr.get(i);
				textTitleArr.add(i, jsonObj.get("title"));
				textContentsArr.add(i, jsonObj.get("description"));
				mediaUrlArr.add(i, jsonObj.get("mediaUrl"));
				mediaArr.add(i, jsonObj.get("media"));
			}
			
			// 버튼 정보 세팅
			for(int i=0; i<jsonArr.size(); i++) {
				JSONObject jsonObj = (JSONObject) jsonArr.get(i);
				JSONArray btnArr = (JSONArray) jsonObj.get("suggestions");
				Map<String, Object> btnMap = new HashMap<String, Object>();
				if(btnArr.size()>0) {
					int btnCnt = btnArr.size();
					String[] selectBtnArr		= new String[btnCnt];
					String[] btnNmArr			= new String[btnCnt];
					String[] contentsArr		= new String[btnCnt];
					String[] calendarTitleArr	= new String[btnCnt];
					String[] calendarDesArr		= new String[btnCnt];
					String[] initDateArr		= new String[btnCnt];
					String[] initEndDateArr		= new String[btnCnt];
					
					for(int j=0; j<btnArr.size(); j++) {
						JSONObject btnObj = (JSONObject) btnArr.get(j);
						btnObj = (JSONObject) btnObj.get("action");
						
						JSONObject btnDetailObj = new JSONObject();
						String selectBtn = "";
						String btnNm = "";
						String contents = "";
						String calendarTitle = "";
						String calendarDes = "";
						String initDate = "";
						String initEndDate = "";
						
						if(btnObj.get("urlAction") != null) {
							// 버튼 이름 세팅
							selectBtn = "urlAction";
							btnNm = CommonUtils.getString(btnObj.get("displayText"));
							
							btnDetailObj = (JSONObject) btnObj.get("urlAction");
							btnDetailObj = (JSONObject) btnDetailObj.get("openUrl");
							// 내용 세팅
							contents = CommonUtils.getString(btnDetailObj.get("url"));
							
						} else if(btnObj.get("clipboardAction") != null) {
							// 버튼 이름 세팅
							selectBtn = "clipboardAction";
							btnNm = CommonUtils.getString(btnObj.get("displayText"));
							
							btnDetailObj = (JSONObject) btnObj.get("clipboardAction");
							btnDetailObj = (JSONObject) btnDetailObj.get("copyToClipboard");
							// 내용 세팅
							contents = CommonUtils.getString(btnDetailObj.get("text"));
						
						} else if(btnObj.get("dialerAction") != null) {
							// 버튼 이름 세팅
							selectBtn = "dialerAction";
							btnNm = CommonUtils.getString(btnObj.get("displayText"));
							
							btnDetailObj = (JSONObject) btnObj.get("dialerAction");
							btnDetailObj = (JSONObject) btnDetailObj.get("dialPhoneNumber");
							// 내용 세팅
							contents = CommonUtils.getString(btnDetailObj.get("phoneNumber"));
							
						} else if(btnObj.get("calendarAction") != null) {
							// 버튼 이름 세팅
							selectBtn = "calendarAction";
							btnNm = CommonUtils.getString(btnObj.get("displayText"));
							
							btnDetailObj = (JSONObject) btnObj.get("calendarAction");
							btnDetailObj = (JSONObject) btnDetailObj.get("createCalendarEvent");
							// 내용 세팅
							calendarTitle = CommonUtils.getString(btnDetailObj.get("title"));
							calendarDes = CommonUtils.getString(btnDetailObj.get("description"));
							initDate = CommonUtils.getString(btnDetailObj.get("startTime"));
							initEndDate = CommonUtils.getString(btnDetailObj.get("endTime"));
							
						} else if(btnObj.get("mapAction") != null) {
							// 버튼 이름 세팅
							selectBtn = "mapAction";
							btnNm = CommonUtils.getString(btnObj.get("displayText"));
						}
						selectBtnArr[j] = selectBtn;
						btnNmArr[j] = btnNm;
						contentsArr[j] = contents;
						calendarTitleArr[j] = calendarTitle;
						calendarDesArr[j] = calendarDes;
						initDateArr[j] = initDate;
						initEndDateArr[j] = initEndDate;
					}
					btnMap.put("btnCnt" , btnCnt);
					btnMap.put("selectBtn", selectBtnArr);
					btnMap.put("btnNm", btnNmArr);
					btnMap.put("contents", contentsArr);
					btnMap.put("calendarTitle", calendarTitleArr);
					btnMap.put("calendarDes", calendarDesArr);
					btnMap.put("initDate", initDateArr);
					btnMap.put("initEndDate", initEndDateArr);
				}
				btnArrLst.add(i, btnMap);
			}
			
			rtnMap.put("brandId", brandId);
			rtnMap.put("saveContent", saveContent);
			if("Y".equals(adYn)) {
				rtnMap.put("adYn", "yes");
			} else {
				rtnMap.put("adYn", "no");
			}
			rtnMap.put("freeReceiveNum", freeReceiveNum);
			if("Y".equals(copyPossYn)) {
				rtnMap.put("copy", "yes");
			} else {
				rtnMap.put("copy", "no");
			}
			rtnMap.put("callback", callback);
			if("SMS".equals(replcSenderCode)) {
				rtnMap.put("senderType", "SMS");
			} else if("LMS".equals(replcSenderCode)) {
				rtnMap.put("senderType", "LMS");
			} else {
				rtnMap.put("senderType", "UNUSED");
			}
			
			
			rtnMap.put("callbackTitle", callbackTitle);
			rtnMap.put("callbackContents", callbackContents);
			rtnMap.put("textTitle", textTitleArr);
			rtnMap.put("textContents", textContentsArr);
			rtnMap.put("imgUrl", mediaUrlArr);
			rtnMap.put("fileId", mediaArr);
			rtnMap.put("btnArr", btnArrLst);
		}
		
		rtn.setData(rtnMap);
		
		return rtn;
	}
	
	// RCS 상품 템플릿 승인(서술형, 스타일형) 발송
	public RestResult<Object> sendRcsDataTemplate(Map<String, Object> params, MultipartFile excelFile) throws Exception {
		RestResult<Object> resultObj = new RestResult<>(true);
		Map<String, Object> data = (Map<String, Object>) params.get("data");
		
		// header 세팅
		Map<String, Object> headerMap = this.setHeader(params);
		
		// body 기본 데이터 세팅
		Map<String, Object> apiMap = this.setRcsData(params);
		
		// button 세팅
		Map<String, Object> templateMap = (Map<String, Object>) generalDao.selectGernalObject(DB.QRY_SELECT_RCS_MESSAGE_FORM, apiMap);
		// 버튼 파라미터 세팅
		String msgInfo = (String) templateMap.get("MESSAGEBASE_INFO");
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		obj = (JSONObject) parser.parse(msgInfo);
		obj = (JSONObject) obj.get("formattedString");
		obj = (JSONObject) obj.get("RCSMessage");
		obj = (JSONObject) obj.get("openrichcardMessage");
		JSONArray jsonArr = null;
		jsonArr = (JSONArray) obj.get("suggestions");
		
		// 버튼 json데이터 List 변환 후 apiMap에 추가
		List<Map<String, Object>> btnList = this.convertJsonArrToList(jsonArr);
		Map<String, Object> btnMap = new HashMap<>();
		
		// button이 있는경우 버튼 세팅
		if(btnList.size() > 0) {
			btnMap.put("suggestions", btnList);
		}
		
		ArrayList<Map<String, Object>> btnArr = new ArrayList<>();
		btnArr.add(0, btnMap);
		apiMap.put("buttons", btnArr);
		
		// 대체발송 세팅
		ArrayList<Map<String, Object>> fbInfoLst = this.setFbInfoLst(data);
		apiMap.put("fbInfoLst", fbInfoLst);
		
		// recvInfoLst 세팅
		data.put("webReqId", apiMap.get("webReqId"));
		String cuInputType = CommonUtils.getString(data.get("cuInputType"));
		
		// 엑셀인경우 recvInfoLst 세팅
		if("EXCEL".equals(cuInputType)) {
			List<Map<String, Object>> excelRecvInfoLst = this.setExcelRecvInfoLst(data, excelFile);
			data.put("recvInfoLst", excelRecvInfoLst);
		}
		
		// 서술형의경우 description으로 묶어서 보내는 방식으로 변경 -> 미승인형 모듈로 처리
		String templateRadioBtn = CommonUtils.getString(params.get("templateRadioBtn"));
		
		ArrayList<Map<String, Object>> recvInfoLst;
		if("des".equals(templateRadioBtn)) {
			recvInfoLst = this.setRecvInfoListNonTemplate(data);
		} else {
			recvInfoLst = this.setRecvInfoListTemplate(data);
		}
		
		// 예약발송일경우 웹 발송 내역을 등록하고 통신은 하지 않도록 처리
		
		String rsrvSendYn = CommonUtils.getString(data.get("rsrvSendYn"));
		Map<String, Object> msgMap = new HashMap<String, Object>();
		msgMap = apiMap;
		msgMap.put("recvInfoLst", recvInfoLst);
		if("Y".equals(rsrvSendYn)) {
			this.insertPushCmWebMsg(headerMap, msgMap, params, "SEND");
		} else {
			boolean real = (boolean) params.get("real");
			if(real) {
				List<Object> reSendCdList = sendMsgService.reSendCdList(null);
				this.sendRcs(params, 0, apiMap, headerMap, recvInfoLst, fbInfoLst, reSendCdList);
			} else {
				this.sendTestRcs(apiMap, headerMap);
			}
		}
		
		return resultObj;
	}
	
	// RCS 텍스트 미승인형 발송
	public RestResult<Object> sendRcsDataNonTemplate(Map<String, Object> params, MultipartFile excelFile) throws Exception {
		RestResult<Object> resultObj = new RestResult<>(true);
		Map<String, Object> data = (Map<String, Object>) params.get("data");
		
		// header 세팅
		Map<String, Object> headerMap = this.setHeader(params);
		
		// body 기본 데이터 세팅
		Map<String, Object> apiMap = this.setRcsData(params);
		
		// button 세팅 (텍스트 미승인형의 경우 버튼이 없음)
		ArrayList<Map<String, Object>> btnArr = new ArrayList<>();
		Map<String, Object> btnMap = new HashMap<>();
		btnArr.add(btnMap);
		
		apiMap.put("buttons", btnArr);
		
		// 대체발송 세팅
		ArrayList<Map<String, Object>> fbInfoLst = this.setFbInfoLst(data);
		apiMap.put("fbInfoLst", fbInfoLst);
		
		// recvInfoLst 세팅
		data.put("webReqId", apiMap.get("webReqId"));
		String cuInputType = CommonUtils.getString(data.get("cuInputType"));
		
		// 엑셀인경우 recvInfoLst 세팅
		if("EXCEL".equals(cuInputType)) {
			List<Map<String, Object>> excelRecvInfoLst = this.setExcelRecvInfoLst(data, excelFile);
			data.put("recvInfoLst", excelRecvInfoLst);
		}
		ArrayList<Map<String, Object>> recvInfoLst = this.setRecvInfoListNonTemplate(data);
		
		// 예약발송일경우 웹 발송 내역을 등록하고 통신은 하지 않도록 처리
		
		String rsrvSendYn = CommonUtils.getString(data.get("rsrvSendYn"));
		Map<String, Object> msgMap = new HashMap<String, Object>();
		msgMap = apiMap;
		msgMap.put("recvInfoLst", recvInfoLst);
		if("Y".equals(rsrvSendYn)) {
			this.insertPushCmWebMsg(headerMap, msgMap, params, "SEND");
		} else {
			boolean real = (boolean) params.get("real");
			if(real) {
				List<Object> reSendCdList = sendMsgService.reSendCdList(null);
				this.sendRcs(params, 0, apiMap, headerMap, recvInfoLst, fbInfoLst, reSendCdList);
			} else {
				this.sendTestRcs(apiMap, headerMap);
			}
		}
		
		return resultObj;
		
	}
	
	// RCS 포멧형 발송
	public RestResult<Object> sendRcsDataFormat(Map<String, Object> params, MultipartFile excelFile) throws Exception {
		RestResult<Object> resultObj = new RestResult<>(true);
		Map<String, Object> data = (Map<String, Object>) params.get("data");
		
		// header 세팅
		Map<String, Object> headerMap = this.setHeader(params);
		
		// body 기본 데이터 세팅
		// 포멧형 mesbaseId는 templateRadioBtn 으로 세팅
		data.put("messagebaseId", params.get("templateRadioBtn"));
		Map<String, Object> apiMap = this.setRcsData(params);
		
		// button 세팅
		ArrayList<Map<String, Object>> btnArr = new ArrayList<>();
		btnArr.add(0, this.setRcsButton(data));
		apiMap.put("buttons", btnArr);
		
		// 대체발송 세팅
		ArrayList<Map<String, Object>> fbInfoLst = this.setFbInfoLst(data);
		apiMap.put("fbInfoLst", fbInfoLst);
		
		// recvInfoLst 세팅
		data.put("webReqId", apiMap.get("webReqId"));
		String radioBtn = CommonUtils.getString(params.get("templateRadioBtn"));
		String cuInputType = CommonUtils.getString(data.get("cuInputType"));
		
		// 엑셀인경우 recvInfoLst 세팅
		if("EXCEL".equals(cuInputType)) {
			List<Map<String, Object>> excelRecvInfoLst = this.setExcelRecvInfoLst(data, excelFile);
			data.put("recvInfoLst", excelRecvInfoLst);
		}
		ArrayList<Map<String, Object>> recvInfoLst = this.setRecvInfoListFormat(data, radioBtn);
		
		String rsrvSendYn = CommonUtils.getString(data.get("rsrvSendYn"));
		Map<String, Object> msgMap = new HashMap<String, Object>();
		msgMap = apiMap;
		msgMap.put("recvInfoLst", recvInfoLst);
		if("Y".equals(rsrvSendYn)) {
			this.insertPushCmWebMsg(headerMap, msgMap, params, "SEND_WAIT");
		} else {
			boolean real = (boolean) params.get("real");
			if(real) {
				List<Object> reSendCdList = sendMsgService.reSendCdList(null);
				this.sendRcs(params, 0, apiMap, headerMap, recvInfoLst, fbInfoLst, reSendCdList);
			} else {
				this.sendTestRcs(apiMap, headerMap);
			}
		}
		
		return resultObj;
	}
	
	// RCS 케러셀형 발송
	public RestResult<Object> sendRcsDataCarousel(Map<String, Object> params, MultipartFile excelFile) throws Exception {
		RestResult<Object> resultObj = new RestResult<>(true);
		Map<String, Object> data = (Map<String, Object>) params.get("data");
		
		// header 세팅
		Map<String, Object> headerMap = this.setHeader(params);
		
		// body 기본 데이터 세팅
		data.put("messagebaseId", params.get("templateRadioBtn"));
		Map<String, Object> apiMap = this.setRcsData(params);
		
		// button 세팅
		ArrayList<Map<String, Object>> btnArr = new ArrayList<>();
		Map<String, Object> carouselObj = (Map<String, Object>) data.get("carouselObj");
		ArrayList<Map<String, Object>> carouselBtnArr = (ArrayList<Map<String, Object>>) carouselObj.get("btnArr");
		int carouselCnt = CommonUtils.getInt(params.get("carouselSelect"));
		
		for(int i=0; i<carouselCnt; i++) {
			Map<String, Object> paramBtn = null;
			if(carouselBtnArr.size() > i) {
				paramBtn = carouselBtnArr.get(i);
			}
			btnArr.add(i, this.setRcsButton(paramBtn));
		}
		apiMap.put("buttons", btnArr);
		
		// 대체발송 세팅
		ArrayList<Map<String, Object>> fbInfoLst = this.setFbInfoLst(data);
		apiMap.put("fbInfoLst", fbInfoLst);
		
		// recvInfoLst 세팅
		data.put("webReqId", apiMap.get("webReqId"));
		String cuInputType = CommonUtils.getString(data.get("cuInputType"));
		
		// 엑셀인경우 recvInfoLst 세팅
		if("EXCEL".equals(cuInputType)) {
			List<Map<String, Object>> excelRecvInfoLst = this.setExcelRecvInfoLst(data, excelFile);
			data.put("recvInfoLst", excelRecvInfoLst);
		}
		ArrayList<Map<String, Object>> recvInfoLst = this.setRecvInfoListCarousel(data, carouselCnt);
		
		String rsrvSendYn = CommonUtils.getString(data.get("rsrvSendYn"));
		Map<String, Object> msgMap = new HashMap<String, Object>();
		msgMap = apiMap;
		msgMap.put("recvInfoLst", recvInfoLst);
		if("Y".equals(rsrvSendYn)) {
			this.insertPushCmWebMsg(headerMap, msgMap, params, "SEND");
		} else {
			boolean real = (boolean) params.get("real");
			if(real) {
				List<Object> reSendCdList = sendMsgService.reSendCdList(null);
				this.sendRcs(params, 0, apiMap, headerMap, recvInfoLst, fbInfoLst, reSendCdList);
			} else {
				this.sendTestRcs(apiMap, headerMap);
			}
		}
		
		return resultObj;
	}
	
	// RCS 발송 Header 세팅
	public Map<String, Object> setHeader(Map<String, Object> params) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String corpId = CommonUtils.getString(params.get("corpId"));
		String projectId = CommonUtils.getString(params.get("projectId"));
		String apiKey = commonService.getApiKey(corpId, projectId);
		resultMap.put("apiKey", apiKey);
		return resultMap;
	}

	public Map<String, Object> setRcsData(Map<String, Object> params) throws Exception {
		// 기본 세팅
		Map<String, Object> data = (Map<String, Object>) params.get("data");
		String templateRadioBtn = CommonUtils.getString(params.get("templateRadioBtn"));
		String messagebaseId	= CommonUtils.getString(data.get("messagebaseId"));
		if("text".equals(templateRadioBtn)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("brandId", data.get("brandId"));
			messagebaseId = (String) generalDao.selectGernalObject(DB.QRY_SELECT_RCS_MESSAGE_ID, paramMap);
		}
		String callback	= CommonUtils.getString(data.get("callback"));					// 발신번호
		String adYn = CommonUtils.getString(data.get("adYn"));							// 정보성 no, 광고성 yes
		String header = "";																// 정보성 메시지 0 , 광고성 메시지 1
		if("yes".equals(adYn)) {
			header = "1";
		} else {
			header = "0";
		}
		String footer	= CommonUtils.getString(data.get("freeReceiveNum"));			// 무료수신거부 번호
		boolean copyAllowed;
		String copy = CommonUtils.getString(data.get("copy"));							// 복사 가능 yes, 불가능 no
		if("yes".equals(copy)) {
			copyAllowed	= true;															// 복사/공유 허용 여부
		} else {
			copyAllowed	= false;														// 복사/공유 허용 여부
		}
		String expiryOption = "2";														// expire 옵션 (2 고정)
		String agencyId = rcsTemplateService.setAgencyId();								// agencyId
		String campaignId	= CommonUtils.getString(data.get("campaignId"));			// 캠페인 ID
		String deptCode	= "";															// 부서 코드
		String webReqId = CommonUtils.getCommonId("RCS", 10);							// webReqId
		
		Map<String, Object> apiMap = new HashMap<>();
		apiMap.put("messagebaseId", messagebaseId);
		apiMap.put("callback", callback);
		apiMap.put("header", header);
		apiMap.put("footer", footer);
		apiMap.put("copyAllowed", copyAllowed);
		apiMap.put("expiryOption", expiryOption);
		apiMap.put("agencyId", agencyId);
		apiMap.put("campaignId", campaignId);
		apiMap.put("deptCode", deptCode);
		apiMap.put("webReqId", webReqId);
		return apiMap;
	}
	
	// 버튼 세팅
	public Map<String, Object> setRcsButton(Map<String, Object> data) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		ArrayList<Map<String, Object>> suggestionsArr = new ArrayList<Map<String, Object>>();
		int btnCnt;
		
		if(data == null) {
			btnCnt = 0;
		} else {
			btnCnt = CommonUtils.getInt(data.get("btnCnt"));
		}
		for(int i=0; i<btnCnt; i++) {
			ArrayList<Object> selectBtn = (ArrayList<Object>) data.get("selectBtn");
			ArrayList<Object> btnNm = (ArrayList<Object>) data.get("btnNm");
			ArrayList<Object> contents = (ArrayList<Object>) data.get("contents");
			ArrayList<Object> initStartDate = (ArrayList<Object>) data.get("initStartDate");
			ArrayList<Object> initEndDate = (ArrayList<Object>) data.get("initEndDate");
			ArrayList<Object> calendarTitle = (ArrayList<Object>) data.get("calendarTitle");
			ArrayList<Object> calendarDes = (ArrayList<Object>) data.get("calendarDes");
			
			if("urlAction".equals(selectBtn.get(i))) {
				Map<String, Object> actionMap = new HashMap<String, Object>();
				Map<String, Object> btnMap = new HashMap<String, Object>();
				
				Map<String, Object> openUrlMap = new HashMap<String, Object>();
				Map<String, Object> urlMap = new HashMap<String, Object>();
				Map<String, Object> postbackMap = new HashMap<String, Object>();
				
				urlMap.put("url", contents.get(i));
				openUrlMap.put("openUrl", urlMap);
				postbackMap.put("data", "set_by_chatbot_open_url");
				btnMap.put("urlAction", openUrlMap);
				btnMap.put("displayText", btnNm.get(i));
				btnMap.put("postback", postbackMap);
				
				actionMap.put("action", btnMap);
				suggestionsArr.add(i, actionMap);
				
			} else if("clipboardAction".equals(selectBtn.get(i))) {
				Map<String, Object> actionMap = new HashMap<String, Object>();
				Map<String, Object> btnMap = new HashMap<String, Object>();
				
				Map<String, Object> clipboardActionMap = new HashMap<String, Object>();
				Map<String, Object> copyToClipboardMap = new HashMap<String, Object>();
				Map<String, Object> postbackMap = new HashMap<String, Object>();
				
				copyToClipboardMap.put("text", contents.get(i));
				clipboardActionMap.put("copyToClipboard", copyToClipboardMap);
				postbackMap.put("data", "set_by_chatbot_copy_to_clipboard");
				btnMap.put("clipboardAction", clipboardActionMap);
				btnMap.put("displayText", btnNm.get(i));
				btnMap.put("postback", postbackMap);
				
				actionMap.put("action", btnMap);
				suggestionsArr.add(i, actionMap);
				
			} else if("dialerAction".equals(selectBtn.get(i))) {
				Map<String, Object> actionMap = new HashMap<String, Object>();
				Map<String, Object> btnMap = new HashMap<String, Object>();
				
				Map<String, Object> dialarActionMap = new HashMap<String, Object>();
				Map<String, Object> dialPhoneNumberMap = new HashMap<String, Object>();
				Map<String, Object> postbackMap = new HashMap<String, Object>();
				
				dialPhoneNumberMap.put("phoneNumber", contents.get(i));
				dialarActionMap.put("dialPhoneNumber", dialPhoneNumberMap);
				postbackMap.put("data", "set_by_chatbot_dial_phone_number");
				btnMap.put("dialarAction", dialarActionMap);
				btnMap.put("displayText", btnNm.get(i));
				btnMap.put("postback", postbackMap);
				
				actionMap.put("action", btnMap);
				suggestionsArr.add(i, actionMap);
				
			} else if("calendarAction".equals(selectBtn.get(i))) {
				Map<String, Object> actionMap = new HashMap<String, Object>();
				Map<String, Object> btnMap = new HashMap<String, Object>();
				
				Map<String, Object> calendarActionMap = new HashMap<String, Object>();
				Map<String, Object> createCalendarEventMap = new HashMap<String, Object>();
				Map<String, Object> postbackMap = new HashMap<String, Object>();
				
				createCalendarEventMap.put("startTime", initStartDate.get(i)+"T00:00:00Z");
				createCalendarEventMap.put("endTime", initEndDate.get(i)+"T23:59:59Z");
				createCalendarEventMap.put("title", calendarTitle.get(i));
				createCalendarEventMap.put("description", calendarDes.get(i));
				calendarActionMap.put("createCalendarEvent", createCalendarEventMap);
				postbackMap.put("data", "set_by_chatbot_create_calendar_event");
				btnMap.put("calendarAction", calendarActionMap);
				btnMap.put("displayText", btnNm.get(i));
				btnMap.put("postback", postbackMap);
				
				actionMap.put("action", btnMap);
				suggestionsArr.add(i, actionMap);
				
			} else {
				Map<String, Object> actionMap = new HashMap<String, Object>();
				Map<String, Object> btnMap = new HashMap<String, Object>();
				
				Map<String, Object> mapActionMap = new HashMap<String, Object>();
				Map<String, Object> postbackMap = new HashMap<String, Object>();
				
				mapActionMap.put("requestLocationPush", new HashMap<String, Object>());
				postbackMap.put("data", "set_by_chatbot_request_location_push");
				btnMap.put("mapAction", mapActionMap);
				btnMap.put("displayText", btnNm.get(i));
				btnMap.put("postback", postbackMap);
				
				actionMap.put("action", btnMap);
				suggestionsArr.add(i, actionMap);
				
			}
		}
		
		if(suggestionsArr.size() > 0) {
			returnMap.put("suggestions", suggestionsArr);
		}
		
		return returnMap;
	}
	
	public ArrayList<Map<String, Object>> setFbInfoLst(Map<String, Object> data) {
		ArrayList<Map<String, Object>> resultLst = new ArrayList<Map<String, Object>>();
		String senderType = CommonUtils.getString(data.get("senderType"));
		String adYn = CommonUtils.getString(data.get("adYn"));
		String freeReceiveNum = CommonUtils.getString(data.get("freeReceiveNum"));
		ArrayList<Map<String, Object>> dataList = (ArrayList<Map<String, Object>>) data.get("recvInfoLst");
		
		int resultListCnt = 0;
		
		if("SMS".equals(senderType)) {
			for(Map<String, Object> dataMap : dataList) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				Map<String, Object> mergeMap = (Map<String, Object>) dataMap.get("mergeData");
				String callbackContents = CommonUtils.getString(data.get("callbackContents"));
				if("yes".equals(adYn)) {
					callbackContents = "(광고)"+callbackContents;
				}
				if(!"".equals(freeReceiveNum)) {
					callbackContents = callbackContents + "무료수신거부 : " + freeReceiveNum;
				}
				
				StringSubstitutor sub = new StringSubstitutor(mergeMap, ApiConfig.RCS_REPLACE_VAR_START, ApiConfig.RCS_REPLACE_VAR_END);
				String replaceStr = sub.replace(callbackContents);
				
				resultMap.put("ch", "SMS");
				resultMap.put("title", "");
				resultMap.put("msg", replaceStr);
				resultMap.put("fileId", "");
				
				resultLst.add(resultListCnt, resultMap);
				resultListCnt++;
			}
		} else if("LMS".equals(senderType)) {
			for(Map<String, Object> dataMap : dataList) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				Map<String, Object> mergeMap = (Map<String, Object>) dataMap.get("mergeData");
				String callbackTitle = CommonUtils.getString(data.get("callbackTitle"));
				String callbackContents = CommonUtils.getString(data.get("callbackContents"));
				if("yes".equals(adYn)) {
					callbackTitle = "(광고)"+callbackTitle;
				}
				if(!"".equals(freeReceiveNum)) {
					callbackContents = callbackContents + "무료수신거부 : " + freeReceiveNum;
				}
				
				StringSubstitutor sub = new StringSubstitutor(mergeMap, ApiConfig.RCS_REPLACE_VAR_START, ApiConfig.RCS_REPLACE_VAR_END);
				String replaceStr = sub.replace(callbackContents);
				
				resultMap.put("ch", "MMS");
				resultMap.put("title", callbackTitle);
				resultMap.put("msg", replaceStr);
				resultMap.put("fileId", "");
				
				resultLst.add(resultListCnt, resultMap);
				resultListCnt++;
			}
		} else if("MMS".equals(senderType)) {
			for(Map<String, Object> dataMap : dataList) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				Map<String, Object> mergeMap = (Map<String, Object>) dataMap.get("mergeData");
				String callbackTitle = CommonUtils.getString(data.get("callbackTitle"));
				String callbackContents = CommonUtils.getString(data.get("callbackContents"));
				String fileId = CommonUtils.getString(data.get("callbackFileId"));
				if("yes".equals(adYn)) {
					callbackTitle = "(광고)"+callbackTitle;
				}
				if(!"".equals(freeReceiveNum)) {
					callbackContents = callbackContents + "무료수신거부 : " + freeReceiveNum;
				}
				
				StringSubstitutor sub = new StringSubstitutor(mergeMap, ApiConfig.RCS_REPLACE_VAR_START, ApiConfig.RCS_REPLACE_VAR_END);
				String replaceStr = sub.replace(callbackContents);
				
				resultMap.put("ch", "MMS");
				resultMap.put("title", callbackTitle);
				resultMap.put("msg", replaceStr);
				resultMap.put("fileId", fileId);
				
				resultLst.add(resultListCnt, resultMap);
				resultListCnt++;
			}
		}
		
		return resultLst;
	}
	
	public ArrayList<Map<String ,Object>> setRecvInfoListTemplate(Map<String, Object> data) {
		ArrayList<Map<String, Object>> dataList = (ArrayList<Map<String, Object>>) data.get("recvInfoLst");
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		String webReqId = CommonUtils.getString(data.get("webReqId"));
		long cliKey = NumberUtils.LONG_ONE;
		
		for(Map<String, Object>dataMap : dataList) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
				String clikeyStr = webReqId + "_" + cliKey;
				resultMap.put("cliKey", clikeyStr);
				resultMap.put("phone", dataMap.get("phone"));
				resultMap.put("mergeData", dataMap.get("mergeData"));
				resultList.add(resultMap);
				cliKey++;
		}
		
		return resultList;
	}
	
	public ArrayList<Map<String ,Object>> setRecvInfoListNonTemplate(Map<String, Object> data) {
		String textContents = CommonUtils.getString(data.get("textContents"));			// 미승인형 내용
		ArrayList<Map<String, Object>> dataList = (ArrayList<Map<String, Object>>) data.get("recvInfoLst");
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		String webReqId = CommonUtils.getString(data.get("webReqId"));
		long cliKey = NumberUtils.LONG_ONE;
		
		for(Map<String, Object> dataMap : dataList) {
			Map<String, Object> mergeMap = (Map<String, Object>) dataMap.get("mergeData");
			Map<String, Object> returnMap = new HashMap<String, Object>();
			Map<String, Object> returnMergeMap = new HashMap<String, Object>();
			StringSubstitutor sub = new StringSubstitutor(mergeMap, ApiConfig.RCS_VAR_START, ApiConfig.RCS_VAR_END);
			String replaceStr = sub.replace(textContents);
			returnMergeMap.put("description", replaceStr);

			String clikeyStr = webReqId + "_" + cliKey;
			returnMap.put("cliKey", clikeyStr);
			returnMap.put("phone", dataMap.get("phone"));
			returnMap.put("mergeData", returnMergeMap);
			
			resultList.add(returnMap);
			cliKey++;
		}
		
		return resultList;
	}

	public ArrayList<Map<String ,Object>> setRecvInfoListFormat(Map<String, Object> data, String radioBtn) {
		String textTitle = CommonUtils.getString(data.get("textTitle"));				// 제목
		String textContents = CommonUtils.getString(data.get("textContents"));			// 내용
		String fileId = "maapfile://" + CommonUtils.getString(data.get("fileId"));		// 이미지 fileId
		
		ArrayList<Map<String, Object>> dataList = (ArrayList<Map<String, Object>>) data.get("recvInfoLst");
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		String webReqId = CommonUtils.getString(data.get("webReqId"));
		long cliKey = NumberUtils.LONG_ONE;
		
		for(Map<String, Object> dataMap : dataList) {
			Map<String, Object> mergeMap = (Map<String, Object>) dataMap.get("mergeData");
			Map<String, Object> returnMap = new HashMap<String, Object>();
			Map<String, Object> returnMergeMap = new HashMap<String, Object>();
			StringSubstitutor sub = new StringSubstitutor(mergeMap, ApiConfig.RCS_VAR_START, ApiConfig.RCS_VAR_END);
			String replaceTextTitle = sub.replace(textTitle);
			String replaceTextContents = sub.replace(textContents);
			
			// mergeData 세팅
			if("SL000000".equals(radioBtn)) {
				returnMergeMap.put("title", replaceTextTitle);
			}
			
			if("SMwThM00".equals(radioBtn) || "SMwThT00".equals(radioBtn)) {
				returnMergeMap.put("title", replaceTextTitle);
				returnMergeMap.put("media", fileId);
			}
			
			returnMergeMap.put("description", replaceTextContents);
			
			String clikeyStr = webReqId + "_" + cliKey;
			returnMap.put("cliKey", clikeyStr);
			returnMap.put("phone", dataMap.get("phone"));
			returnMap.put("mergeData", returnMergeMap);
			
			resultList.add(returnMap);
			cliKey++;
		}
		
		return resultList;
	}
	
	public ArrayList<Map<String ,Object>> setRecvInfoListCarousel(Map<String, Object> data, int carouselCnt) {
		Map<String, Object> carouselMap = (Map<String, Object>) data.get("carouselObj");
		ArrayList<Map<String, Object>> dataList = (ArrayList<Map<String, Object>>) data.get("recvInfoLst");
		ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		ArrayList<Object> textTitleArr = (ArrayList<Object>) carouselMap.get("textTitle");
		ArrayList<Object> textContentsArr = (ArrayList<Object>) carouselMap.get("textContents");
		ArrayList<Object> fileIdArr = (ArrayList<Object>) carouselMap.get("fileId");
		String webReqId = CommonUtils.getString(data.get("webReqId"));
		long cliKey = NumberUtils.LONG_ONE;
		
		for(Map<String, Object> dataMap : dataList) {
			Map<String, Object> mergeMap = (Map<String, Object>) dataMap.get("mergeData");
			Map<String, Object> returnMap = new HashMap<String, Object>();
			Map<String, Object> returnMergeMap = new HashMap<String, Object>();
			StringSubstitutor sub = new StringSubstitutor(mergeMap, ApiConfig.RCS_VAR_START, ApiConfig.RCS_VAR_END);
			
			for(int i=0; i<carouselCnt; i++) {
				returnMergeMap.put("title"+(i+1), sub.replace(textTitleArr.get(i)));
				returnMergeMap.put("description"+(i+1), sub.replace(textContentsArr.get(i)));
				String fileId = CommonUtils.getString(fileIdArr.get(i));
				if("".equals(fileId)) {
					fileId = "";
				} else {
					fileId = "maapfile://" + fileId;
				}
				
				returnMergeMap.put("media"+(i+1), fileId);
			}

			String clikeyStr = webReqId + "_" + cliKey;
			returnMap.put("cliKey", clikeyStr);
			returnMap.put("phone", dataMap.get("phone"));
			returnMap.put("mergeData", returnMergeMap);
			
			resultList.add(returnMap);
			cliKey++;
		}
		
		return resultList;
	}
	
	public List<Map<String, Object>> convertJsonArrToList(JSONArray jsonArray) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if( jsonArray != null ) {
			int jsonSize = jsonArray.size();
			for( int i = 0; i < jsonSize; i++ ) {
				Map<String, Object> map = this.convertJsonToMap((JSONObject) jsonArray.get(i));
				list.add( map );
			}
		}
		
		return list;
	}
	
	public Map<String, Object> convertJsonToMap(JSONObject jsonObj) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = new ObjectMapper().readValue(jsonObj.toString(), Map.class) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return map;
	}
	
	public void insertPushCmWebMsg(Map<String, Object> headerMap, Map<String, Object> msgMap, Map<String, Object> params, String Chstr) throws Exception {
		Map<String, Object> data = (Map<String, Object>) params.get("data");
		
		String webReqId = CommonUtils.getString(msgMap.get("webReqId"));
		String ch = "RCS";
		String corpId = CommonUtils.getString(params.get("corpId"));
		String projectId = CommonUtils.getString(params.get("projectId"));
		String userId = CommonUtils.getString(params.get("userId"));
		String rsrvSendYn = CommonUtils.getString(data.get("rsrvSendYn"));
		String rsrvDateStr = "";
		String status = Chstr;
		String callback = CommonUtils.getString(msgMap.get("callback"));
		String campaignId = CommonUtils.getString(msgMap.get("campaignId"));
		
		// 발송 리스트, 대체 발송 리스트 세팅
		if(!StringUtils.equals(rsrvSendYn, Const.COMM_YES)) {
			msgMap.put("recvInfoLst", msgMap.get("msgRecvInfoLst"));
			msgMap.remove("msgRecvInfoLst");
			msgMap.put("fbInfoLst", msgMap.get("msgFbInfoLst"));
			msgMap.remove("msgFbInfoLst");
		}
		
		if(StringUtils.equals(rsrvSendYn, Const.COMM_YES)) {
			String rsrvYmd = CommonUtils.getString(data.get("rsrvDate"));
			String rsrvHH = CommonUtils.getString(data.get("rsrvHH"));
			String rsrvMM = CommonUtils.getString(data.get("rsrvMM"));
			rsrvDateStr = rsrvYmd+" "+rsrvHH+":"+rsrvMM;

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date rsrvDate = dateFormat.parse(rsrvDateStr);
			Date currentDate = new Date();

			currentDate = DateUtils.addMinutes(currentDate, 10);
			if(currentDate.compareTo(rsrvDate) > 0) {
				throw new Exception("잘못된 예약시간입니다. 현재시간 10분 이후로 설정해주세요.");
			}
			if(DateUtil.diffDays(rsrvDate) > Const.SEND_RSRV_LIMIT_DAY) {
				throw new Exception("잘못된 예약일자입니다. 현재일로 부터 "+Const.SEND_RSRV_LIMIT_DAY+"일 이내로 설정해주세요");
			}
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		String json = gson.toJson(msgMap);
		
		paramMap.put("webReqId", webReqId);
		paramMap.put("corpId", corpId);
		paramMap.put("projectId", projectId);
		paramMap.put("userId", userId);
		paramMap.put("apiKey", commonService.getApiKey(corpId, projectId));
		paramMap.put("chString", ch);
		paramMap.put("msgInfo", json);
		if(StringUtils.equals(rsrvSendYn, Const.COMM_YES)) {
			ArrayList<Map<String, Object>> recvInfoLst = (ArrayList<Map<String, Object>>) msgMap.get("recvInfoLst");
			int senderCnt = recvInfoLst.size();
			paramMap.put("senderCnt", senderCnt);
		} else {
			paramMap.put("senderCnt", CommonUtils.getInt(params.get("recvInfoLstCnt")));
		}
		paramMap.put("callback", callback);
		paramMap.put("campaignId", campaignId);
		paramMap.put("senderType", Const.SenderType.CHANNEL);
		paramMap.put("status", status);
		paramMap.put("resvSenderYn", rsrvSendYn);
		paramMap.put("reqDt", rsrvDateStr);
		
		generalDao.insertGernal(DB.QRY_INSERT_CM_WEB_MSG, paramMap);
	}
	
	// 금액 계산
	public String setAccountSendMessage(Map<String, Object> params) throws Exception {
		// 계산 타입
		String payType = sendMsgService.selectPayType(params);

		//선불일경우
		if(StringUtils.equals(payType, Const.PayType.PRE_FEE)) {
			//남은 금액 조회
			BigDecimal rmAmount = sendMsgService.getRmAmount(params);
			//개당 가격 조회
			List<String> productCodes = new ArrayList<String>();
			productCodes.add(Const.MsgProductCode.getCodeByName(Const.Ch.PUSH));
			if(params.containsKey("rplcSendType")
					&& !CommonUtils.isEmptyValue(params, "rplcSendType")
					&& !StringUtils.equals((CharSequence) params.get("rplcSendType"), "NONE")) {
				productCodes.add(Const.MsgProductCode.getCodeByName(CommonUtils.getStrValue(params, "rplcSendType")));
			}

			Map<String, Object> sParam = new HashMap<>();
			sParam.put("corpId", CommonUtils.getStrValue(params, "corpId"));
			sParam.put("productCodes", productCodes);
			BigDecimal feePerOne = sendMsgService.selectMsgFeePerOne(sParam);
			if(rmAmount.compareTo(feePerOne) < 0) {
				throw new Exception("잔액 부족으로 메시지를 발송할 수 없습니다.");
			}
			
			Map<String, Object> data = (Map<String, Object>) params.get("data");
			ArrayList<Object> recvInfoLst = (ArrayList<Object>) data.get("recvInfoLst");
			BigDecimal feePerAll = feePerOne.multiply(new BigDecimal(recvInfoLst.size()));
			if(rmAmount.compareTo(feePerAll) < 0) {
				String testSendYn = "N";
				if(StringUtils.equals(testSendYn, Const.COMM_YES)) {
					throw new Exception("잔액 부족으로 메시지를 발송할 수 없습니다.");
				} else {
					return "잔액 부족으로 메시지가 발송되지 않을 수도 있습니다.";
				}
			}
		}
		return "";
	}
	
	@Async
	public void sendRcs(Map<String, Object> params, int fromIndex, Map<String, Object> apiMap, Map<String, Object> headerMap,
			ArrayList<Map<String, Object>> recvInfoLst, ArrayList<Map<String, Object>> fbInfoLst, List<Object> reSendCdList) throws Exception {
		Map<String, Object> responseBody = null;
		String jsonString = "";
		String failMsg = "";
		boolean isDone = false;
		boolean isServerError = false;
		boolean isAllFail = true;
		List<Map<String, Object>> errorRecvInfoLst = new ArrayList<Map<String, Object>>();
		Map<String, Object> data = (Map<String, Object>) params.get("data");
		Map<String, Object> sParams = new HashMap<String, Object>(data);

		Gson gson = new Gson();

		int retryCnt = NumberUtils.INTEGER_ZERO;
		int cutSize = ApiConfig.DEFAULT_RECV_LIMIT_SIZE;
		int listSize = recvInfoLst.size();
		int toIndex = fromIndex;
		
        String corpId = CommonUtils.getStrValue(params, "corpId");
        String projectId = CommonUtils.getStrValue(params, "projectId");
        Map apiData = commonService.getApiKey2(corpId, projectId);
        String apiKey = CommonUtils.getString(apiData.get("apiKey"));
        String strCps = CommonUtils.getString(apiData.get("cps"),"30");
        int cps = NumberUtils.toInt(strCps, 30);
        if (cps <= 0) cps = 30;
        int sendCnt = 0;
        long start = System.currentTimeMillis();
        long end = 0;
        long second = 1000;
		
		params.put("recvInfoLstCnt", listSize);
		apiMap.put("msgRecvInfoLst", recvInfoLst);
		apiMap.put("msgFbInfoLst", fbInfoLst);
		
		while (toIndex < listSize) {
			isDone = false;
			isServerError = false;
			toIndex = fromIndex + cutSize;
			try {
				if(toIndex > listSize) toIndex = listSize;
				apiMap.put("recvInfoLst", recvInfoLst.subList(fromIndex, toIndex));
				if(fbInfoLst.size() > 0) {
					apiMap.put("fbInfoLst", fbInfoLst.subList(fromIndex, toIndex));
				}
				jsonString = gson.toJson(apiMap);
				responseBody = apiInterface.sendMsg(ApiConfig.SEND_RCS_API_URI, headerMap, jsonString);
				isDone = isApiRequestAgain(responseBody, reSendCdList);
				isAllFail = !isSendSuccess(responseBody);
				if(isAllFail) failMsg = CommonUtils.getString(responseBody.get("message"));
//                isDone = true;
//                isAllFail = false;
			} catch (Exception e) {
				isServerError = true;
				if(retryCnt == ApiConfig.GW_RETRY_CNT) sendMsgService.sendMsgErrorNoti(Const.ApiWatchNotiMsg.API_CONNECTION_FAIL);
			}
			
			if(isDone) {
            	sendCnt++;
				retryCnt = NumberUtils.INTEGER_ZERO;
				fromIndex = toIndex;
			} else if(retryCnt == ApiConfig.GW_RETRY_CNT) {
				errorRecvInfoLst.addAll(recvInfoLst.subList(fromIndex, toIndex));
				retryCnt = NumberUtils.INTEGER_ZERO;
				fromIndex = toIndex;
			} else {
            	sendCnt++;
				retryCnt++;
				toIndex = fromIndex;
				if(!isServerError) TimeUnit.MILLISECONDS.sleep(ApiConfig.GW_RETRY_DELAY_MILLISECONDS);
			}
	        
	        if (sendCnt >= cps || toIndex >= listSize) {
	        	end = System.currentTimeMillis();
	        	long diff  = end - start;
	            log.info("API sendMsg apiKey : {}, cps : {}, sendCnt : {}", apiKey, cps, sendCnt);
	        	if (second > diff && sendCnt >= cps) {
	        		TimeUnit.MILLISECONDS.sleep(second-diff);
	        	}
	        	sendCnt = 0;
	        	start = System.currentTimeMillis();
	        }
		}
		
		if(CollectionUtils.isNotEmpty(errorRecvInfoLst)) {
			try {
				//CM_MSG Insert
				sParams.put("corpId", params.get("corpId"));
				sParams.put("projectId", params.get("projectId"));
				sParams.put("reqCh", Const.Ch.RCS);
				sParams.put("productCode", Const.Ch.RCS.toLowerCase());
				sParams.put("finalCh", Const.Ch.RCS);
				sParams.put("callback", data.get("callback"));
				sParams.put("webReqId", data.get("webReqId"));
				insertCmMsg(sParams, errorRecvInfoLst);
			} catch (Exception e) {
				log.error("{}.sendRCSMsgAsync insertCmMsg Error ==> {}", this.getClass(), e);
			}
		}
		
		//웹 발송 내역 등록
		if(isAllFail) {
			this.insertPushCmWebMsg(headerMap, apiMap, params, "FAIL");
		} else {
			this.insertPushCmWebMsg(headerMap, apiMap, params, "COMPLETED");
		}
		
	}
	
	public void sendTestRcs(Map<String, Object> apiMap, Map<String, Object> headerMap) throws Exception {
		Map<String, Object> result = apiInterface.post("/console/v1/rcs", apiMap, headerMap);
		
		if(!"10000".equals(result.get("code")) ) {
			String resultMsg = CommonUtils.getString(result.get("message"));
			throw new Exception(resultMsg);
		}
	}
	
    /**
     * API 재요청 여부
     * 재요청 코드에 등록되지 않은 모든 상황은 재요청 하지 않는다.
     * @param responseBody
     * @param reSendCdList
     * @return
     */
    private boolean isApiRequestAgain(Map<String, Object> responseBody, List<Object> reSendCdList) {
        boolean isDone = true;
        if(responseBody != null) {
            if(!CommonUtils.isEmptyValue(responseBody, ApiConfig.GW_RESULT_CODE_FIELD_NM)){
                String resultCode = CommonUtils.getString(responseBody.get(ApiConfig.GW_RESULT_CODE_FIELD_NM));
                for(Object reSendCd : reSendCdList) {
                    if(StringUtils.equals(resultCode, CommonUtils.getString(reSendCd))) {
                        isDone = false;
                        break;
                    }
                }
            }
        }
        return isDone;
    }

    /**
     * 발송 성공여부
     * @param responseBody
     * @return
     */
    private boolean isSendSuccess(Map<String, Object> responseBody) {
        boolean isSuccess = false;
        if(responseBody != null) {
            if(!CommonUtils.isEmptyValue(responseBody, ApiConfig.GW_RESULT_CODE_FIELD_NM)
                    && StringUtils.equals(ApiConfig.GW_API_SUCCESS, CommonUtils.getString(responseBody.get(ApiConfig.GW_RESULT_CODE_FIELD_NM)))) {
                isSuccess = true;
            }
        }
        return isSuccess;
    }

	
	public List<Map<String, Object>> setExcelRecvInfoLst(Map<String, Object> data, MultipartFile excelFile) throws Exception {
		List<Map<String, Object>> excelList = null;
		List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
		List<String> colKeys = new ArrayList<String>();
		List<String> contsVarNms = null;

		colKeys.add("phone");
		if(data.containsKey("contsVarNms")) {
			contsVarNms = (ArrayList<String>)data.get("contsVarNms");
			for(String varNm : contsVarNms) {
				colKeys.add(varNm);
			}
		}
		
		excelList = commonService.getExcelDataList(excelFile, 2, colKeys);
		
		for(Map<String, Object> excelInfo : excelList) {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			if(excelInfo.containsKey("phone")) {
				returnMap.put("phone", excelInfo.get("phone"));
			}

			Map<String, Object> mergeData = new HashMap<String, Object>();
			for(String key : excelInfo.keySet()) {
				if(!StringUtils.equals(key, "phone")) {
					mergeData.put(key, (String) excelInfo.get(key));
				}
			}
			returnMap.put("mergeData", mergeData);
			returnList.add(returnMap);
		}
		
		return returnList;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void insertCmMsg(Map<String, Object> data, List<Map<String, Object>> recvInfoLst) throws Exception {
		Map<String, Object> params = null;
		String corpId = CommonUtils.getStrValue(data, "corpId");
		String projectId = CommonUtils.getStrValue(data, "projectId");
		String apiKey = commonService.getApiKey(corpId, projectId);
		String productCode = CommonUtils.getStrValue(data, "productCode");
		String callback = CommonUtils.getStrValue(data, "callback");
		String webReqId = CommonUtils.getStrValue(data, "webReqId");
		String senderType = Const.SenderType.CHANNEL;

		for(Map recvInfo : recvInfoLst) {
			params = new HashMap<String, Object>();
			params.put("msgKey", CommonUtils.getCommonId(Const.SendMsgErrorSet.ERROR_PREFIX, 5));
			params.put("corpId", corpId);
			params.put("projectId", projectId);
			params.put("apiKey", apiKey);
			params.put("cliKey", recvInfo.get("cliKey"));
			params.put("senderType", senderType);
			params.put("reqCh", "RCS");
			params.put("productCode", productCode);
			params.put("finalCh", "RCS");
			params.put("phone", recvInfo.get("phone"));
			params.put("callback", callback);
			params.put("webReqId", webReqId);
			params.put("gwResultCode", Const.SendMsgErrorSet.GW_RESULT_CODE);
			params.put("gwResultDesc", Const.SendMsgErrorSet.GW_RESULT_DESC);
			generalDao.insertGernal(DB.QRY_INSERT_CM_MSG, params);
		}
	}
}
