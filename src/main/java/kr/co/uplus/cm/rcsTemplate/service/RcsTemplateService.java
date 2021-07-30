package kr.co.uplus.cm.rcsTemplate.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Case;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.ApiInterface;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;

@Service
public class RcsTemplateService {
	
	@Autowired
	private GeneralDao generalDao;
	
	@Autowired
	private ApiInterface apiInterface; 

	public RestResult<Object> selectRcsTemplateList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		String inputTag =  CommonUtils.getString(params.get("inputTag"));
		String searchTag = CommonUtils.getString(params.get("searchTag"));
		
		// input 값이 있으면 해당 searchTag의 값을 구분해서 파라미터를 다시 세팅
		if(!"".equals(inputTag)) {
			switch(searchTag) {
			case "temNm": params.put("temNm", inputTag);
				break;
			case "temId": params.put("temId", inputTag);
				break;
			case "brandNm": params.put("brandNm", inputTag);
				break;
			default: break;
			}
		}
		
		if (params.containsKey("pageNo") && CommonUtils.isNotEmptyObject(params.get("pageNo"))
				&& params.containsKey("listSize") && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
			rtn.setPageProps(params);
			if (rtn.getPageInfo() != null) {
				// 카운트 쿼리 실행
				int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_RCS_TEMPLATE_LIST_CNT, params);
				rtn.getPageInfo().put("totCnt", listCnt);
			}
		}

		List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_RCS_TEMPLATE_LIST, params);
		rtn.setData(rtnList);

		return rtn;
	}

	public RestResult<Object> corpBrandCnt(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		int resultCnt = generalDao.selectGernalCount(DB.QRY_SELECT_CORP_BRAND_CNT, params);
		
		rtn.setData(resultCnt);
		
		return rtn;
	}

	public RestResult<Object> rcsTemplateInit(Map<String, Object> params) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> resultData = new HashMap<String, Object>();
		RestResult<Object> rtn = new RestResult<Object>();
		
		paramMap = params;
		
		// 템플릿코드
		String templateCode = CommonUtils.getCommonId("TPL", 5);
		// 브랜드 리스트
		List<Object> brandList = generalDao.selectGernalList(DB.QRY_SELECT_BRAND_LIST, paramMap);
		// 서술형 유형 리스트
		paramMap.put("cardType" , "description");
		List<Object> desFormList = generalDao.selectGernalList(DB.QRY_SELECT_RCS_MSGBASEFORM_LIST, paramMap);
		// 스타일형 유형 리스트
		paramMap.put("cardType" , "cell");
		List<Object> styleFormList = generalDao.selectGernalList(DB.QRY_SELECT_RCS_MSGBASEFORM_LIST, paramMap);
		
		resultData.put("templateCode", templateCode);
		resultData.put("brandList", brandList);
		resultData.put("desFormList", desFormList);
		resultData.put("styleFormList", styleFormList);
		
		return rtn.setData(resultData);
	}
	
	public RestResult<Object> rcsTemplateUpdateInit(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		Map<String, Object> resultData = new HashMap<String, Object>();
		Map<String, Object> rcsTemMap = (Map<String, Object>) generalDao.selectGernalObject(DB.QRY_SELECT_RCS_TEMPLATE_UPDATE_FORM, params);
		
		// 기초 데이터 세팅
		resultData.put("templateNm", rcsTemMap.get("TMPLT_NAME"));			// 템플릿 명
		resultData.put("templateCode", rcsTemMap.get("MESSAGEBASE_ID"));	// 템플릿 코드
		resultData.put("brandNm", rcsTemMap.get("BRAND_ID"));				// 브랜드 명
		resultData.put("approvalStatus", rcsTemMap.get("APPROVAL_STATUS"));	// 상태
		
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
		String[] contents = new String[jsonArr.size()];			// 내용 (일정추가 제외)
		String[] calenderTitle = new String[jsonArr.size()];	// 일정추가 제목
		String[] calenderDes = new String[jsonArr.size()];		// 일정추가 내용
		String[] startDate = new String[jsonArr.size()];		// 일정추가 시작일
		String[] endDate = new String[jsonArr.size()];			// 일정추가 종료일
		
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
				
				selectBtn[i] = "urlAction";
				btnNm[i] = CommonUtils.getString(jsonArrObj.get("displayText"));
				contents[i] = CommonUtils.getString(urlObj.get("url"));
				calenderTitle[i] = "";
				calenderDes[i] = "";
				startDate[i] = "";
				endDate[i] = "";
			} else if(jsonArrObj.get("clipboardAction") != null) {
				clipboardObj = (JSONObject) jsonArrObj.get("clipboardAction");
				clipboardObj = (JSONObject) clipboardObj.get("copyToClipboard");
				
				selectBtn[i] = "clipboardAction";
				btnNm[i] = CommonUtils.getString(jsonArrObj.get("displayText"));
				contents[i] = CommonUtils.getString(clipboardObj.get("text"));
				calenderTitle[i] = "";
				calenderDes[i] = "";
				startDate[i] = "";
				endDate[i] = "";
			} else if(jsonArrObj.get("dialerAction") != null) {
				dialerObj = (JSONObject) jsonArrObj.get("dialerAction");
				dialerObj = (JSONObject) dialerObj.get("dialPhoneNumber");
				
				selectBtn[i] = "dialerAction";
				btnNm[i] = CommonUtils.getString(jsonArrObj.get("displayText"));
				contents[i] = CommonUtils.getString(dialerObj.get("phoneNumber"));
				calenderTitle[i] = "";
				calenderDes[i] = "";
				startDate[i] = "";
				endDate[i] = "";
			} else if(jsonArrObj.get("calendarAction") != null) {
				calendarObj = (JSONObject) jsonArrObj.get("calendarAction");
				calendarObj = (JSONObject) calendarObj.get("createCalendarEvent");
				
				selectBtn[i] = "calendarAction";
				btnNm[i] = CommonUtils.getString(jsonArrObj.get("displayText"));
				contents[i] = "";
				calenderTitle[i] = CommonUtils.getString(calendarObj.get("title"));
				calenderDes[i] = CommonUtils.getString(calendarObj.get("description"));
				startDate[i] = CommonUtils.getString(calendarObj.get("startTime"));
				endDate[i] = CommonUtils.getString(calendarObj.get("endTime"));
			} else if(jsonArrObj.get("mapAction") != null) {
				mapActionObj = (JSONObject) jsonArrObj.get("mapAction");
				mapActionObj = (JSONObject) mapActionObj.get("dialPhoneNumber");
				
				selectBtn[i] = "mapAction";
				btnNm[i] = CommonUtils.getString(jsonArrObj.get("displayText"));
				contents[i] = "현재위치 공유";
				calenderTitle[i] = "";
				calenderDes[i] = "";
				startDate[i] = "";
				endDate[i] = "";
			}
		}
		
		resultData.put("selectBtn", selectBtn);
		resultData.put("btnNm", btnNm);
		resultData.put("contents", contents);
		resultData.put("calendarTitle", calenderTitle);
		resultData.put("calendarDes", calenderDes);
		resultData.put("startDate", startDate);
		resultData.put("endDate", endDate);
		
		return rtn.setData(resultData);
	}

	public void rcsTemplateDeleteApi(Map<String, Object> params) throws Exception {
		String brandId		= CommonUtils.getString(params.get("brandId"));
		String messagebaseId	= CommonUtils.getString(params.get("messagebaseId"));

		Map<String, Object> apiMap = new HashMap<>();
		apiMap.put("corpId", CommonUtils.getString(params.get("corpId")));
		
		Map<String, Object> headerMap = new HashMap<String, Object>();
		headerMap.put("brandId",	brandId);
		headerMap.put("messagebaseId",	messagebaseId);
		
		// API 통신 처리
		Map<String, Object> result = apiInterface.delete("/console/v1/brand/" + brandId + "/messagebase/" + messagebaseId, null, apiMap, headerMap);
		
		// 성공인지 실패인지 체크
		if(!"10000".equals(result.get("code")) ) {
			throw new Exception("API 통신 에러");
		}
		
	}

	public void rcsTemplateApi(Map<String, Object> params) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> headerMap = new HashMap<String, Object>();
		
		String corpId				= CommonUtils.getString(params.get("corpId"));
		String messagebaseformId	= CommonUtils.getString(params.get("messagebaseformId"));
		String messagebaseId		= CommonUtils.getString(params.get("messagebaseId"));
		String custTmpltId			= CommonUtils.getString(params.get("custTmpltId"));
		String tmpltName			= CommonUtils.getString(params.get("tmpltName"));
		String brandId				= CommonUtils.getString(params.get("brandId"));
		String agencyId				= CommonUtils.getString(params.get("agencyId"));
		String mediaUrl				= this.setMediaUrlFormList(messagebaseformId);
		Map<String, Object> formattedStringMap = new HashMap<String, Object>();
		
		Map<String, Object> RCSMessageMap = new HashMap<String, Object>();
		
		Map<String, Object> openrichcardMessageMap = new HashMap<String, Object>();
		
		Map<String, Object> layoutMap = new HashMap<String, Object>();
		List<Object> layoutChildren = new ArrayList<Object>();
		Map<String, Object> layoutChildrenFirst = new HashMap<String, Object>();
		Map<String, Object> layoutChildrenSecond = new HashMap<String, Object>();
		
		layoutChildrenFirst.put("width", "content");
		layoutChildrenFirst.put("height", "content");
		layoutChildrenFirst.put("widget", "ImageView");
		layoutChildrenFirst.put("mediaUrl", mediaUrl);
		layoutChildrenFirst.put("paddingTop", "16dp");
		layoutChildrenFirst.put("marginBottom", "8dp");
		layoutChildren.add(0, layoutChildrenFirst);
		
		if("des".equals(CommonUtils.getString(params.get("paramCardType")))) {	// 서술형 contents 세팅
			layoutChildrenSecond = this.setDescriptionContents(params);
			layoutChildren.add(1, layoutChildrenSecond);
		} else {																// 스타일형 contents 세팅
			layoutChildrenSecond = this.setStyleContents(params);
			layoutChildren.add(1, layoutChildrenSecond);
		}
		
		layoutMap.put("width", "284dp");
		layoutMap.put("height", "content");
		layoutMap.put("widget", "LinearLayout");
		layoutMap.put("background", "#ffffff");
		layoutMap.put("orientation", "vertical");
		layoutMap.put("paddingLeft", "16dp");
		layoutMap.put("paddingRight", "16dp");
		layoutMap.put("width", "284dp");
		layoutMap.put("children", layoutChildren);
		
		List<Object> suggestionsList = new ArrayList<Object>();
		Map<String, Object> suggestionsListMap = new HashMap<String, Object>();
		
		// 버튼 세팅
		int btnCnt = CommonUtils.getInt(params.get("btnCnt"));
		
		if(btnCnt>0) {
			String paramCardType = CommonUtils.getString(params.get("paramCardType"));
			ArrayList<String> selectBtn = (ArrayList<String>) params.get("selectBtn");
			
			ArrayList<String> btnNm = (ArrayList<String>) params.get("btnNm");
			ArrayList<String> contents = (ArrayList<String>) params.get("contents");
			ArrayList<String> calendarTitle = (ArrayList<String>) params.get("calendarTitle");
			ArrayList<String> calendarDes = (ArrayList<String>) params.get("calendarDes");
			ArrayList<String> desInitStartDate = (ArrayList<String>) params.get("desInitStartDate");
			ArrayList<String> desInitEndDate = (ArrayList<String>) params.get("desInitEndDate");
			ArrayList<String> styleInitStartDate = (ArrayList<String>) params.get("styleInitStartDate");
			ArrayList<String> styleInitEndDate = (ArrayList<String>) params.get("styleInitEndDate");
			for(int i=0; i<btnCnt; i++) {
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
					
					suggestionsListMap.put("action", urlActionMap);
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
					
					suggestionsListMap.put("action", clipboardActionMap);
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
					
					suggestionsListMap.put("action", dialerActionMap);
				} else if ("calendarAction".equals(selectBtnStr)) {
					Map<String, Object> calendarActionMap = new HashMap<String, Object>();
					Map<String, Object> createCalendarEventMap = new HashMap<String, Object>();
					Map<String, Object> createCalendarEventTextMap = new HashMap<String, Object>();
					Map<String, Object> postbackMap = new HashMap<String, Object>();
					
					createCalendarEventTextMap.put("title", calendarTitle.get(i));
					createCalendarEventTextMap.put("description", calendarDes.get(i));
					if("des".equals(paramCardType)) {
						String startTime = desInitStartDate.get(i) + "T00:00:00Z";
						String endTime = desInitEndDate.get(i) + "T23:59:59Z";
						createCalendarEventTextMap.put("startTime", startTime);
						createCalendarEventTextMap.put("endTime", endTime);
					} else {
						String startTime = styleInitStartDate.get(i) + "T00:00:00Z";
						String endTime = styleInitEndDate.get(i) + "T23:59:59Z";
						createCalendarEventTextMap.put("startTime", startTime);
						createCalendarEventTextMap.put("endTime", endTime);
					}
					
					createCalendarEventMap.put("createCalendarEvent", createCalendarEventTextMap);
					postbackMap.put("data", "set_by_chatbot_create_calendar_event");
					
					calendarActionMap.put("calendarAction", createCalendarEventMap);
					calendarActionMap.put("displayText", btnNm.get(i));
					calendarActionMap.put("postback", postbackMap);
					
					suggestionsListMap.put("action", calendarActionMap);
				} else if ("mapAction".equals(selectBtnStr)) {
					Map<String, Object> mapActionMap = new HashMap<String, Object>();
					Map<String, Object> requestLocationPushMap = new HashMap<String, Object>();
					Map<String, Object> postbackMap = new HashMap<String, Object>();
					
					requestLocationPushMap.put("requestLocationPush", new HashMap<String, Object>());
					postbackMap.put("data", "set_by_cahtbot_request_location_push");
					
					mapActionMap.put("mapAction", requestLocationPushMap);
					mapActionMap.put("displayText", btnNm.get(i));
					mapActionMap.put("postback", postbackMap);
					
					suggestionsListMap.put("action", mapActionMap);
				}
				suggestionsList.add(i, suggestionsListMap);
			}
		} else {
			suggestionsList.add(suggestionsListMap);
		}
		openrichcardMessageMap.put("layout", layoutMap);
		openrichcardMessageMap.put("suggestions", suggestionsList);
		openrichcardMessageMap.put("zoomAllowed", "true");
		openrichcardMessageMap.put("card", "open_rich_card");

		RCSMessageMap.put("trafficType", "advertisement");
		RCSMessageMap.put("openrichcardMessage", openrichcardMessageMap);
		
		formattedStringMap.put("RCSMessage", RCSMessageMap);
		paramMap.put("corpId", corpId);
		paramMap.put("messagebaseformId", messagebaseformId);
		paramMap.put("custTmpltId", custTmpltId);
		paramMap.put("tmpltName", tmpltName);
		paramMap.put("brandId", brandId);
		paramMap.put("agencyId", agencyId);
		paramMap.put("formattedString", formattedStringMap);
		
		paramList.add(paramMap);
		
		String flag = CommonUtils.getString(params.get("flag"));
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if("INS".equals(flag)) {
			result = apiInterface.listPost("/console/v1/brand/" + brandId + "/messagebase", paramList, headerMap);
		} else {
			result = apiInterface.listPost("/console/v1/brand/" + brandId + "/messagebase/" + messagebaseId, paramList, headerMap);
		}
		
		// 성공인지 실패인지 체크
		if(!"10000".equals(result.get("code")) ) {
			throw new Exception("API 통신 에러");
		}
	}
	
	public Map<String, Object> setDescriptionContents(Map<String, Object> params) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<Object> childrenArr = new ArrayList<Object>();
		Map<String, Object> childrenArrMap = new HashMap<String, Object>();
		
		childrenArrMap.put("text", CommonUtils.getString(params.get("desContents")));
		childrenArrMap.put("width", "content");
		childrenArrMap.put("height", "content");
		childrenArrMap.put("widget", "TextView");
		childrenArrMap.put("textSize", "17dp");
		childrenArrMap.put("textColor", "#404040");
		childrenArrMap.put("paddingBottom", "8dp");
		childrenArrMap.put("textAlignment", "textStart");
		childrenArr.add(childrenArrMap);
		
		returnMap.put("width", "match");
		returnMap.put("height", "content");
		returnMap.put("widget", "LinearLayout");
		returnMap.put("orientation", "vertical");
		returnMap.put("paddingBottom", "8dp");
		returnMap.put("children", childrenArr);
		
		return returnMap;
	}
	
	public Map<String, Object> setStyleContents(Map<String, Object> params) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<Object> childrenArr = new ArrayList<Object>();
		
		int styleContentCnt = CommonUtils.getInt(params.get("styleContentCnt"));
		ArrayList<String> styleArr = (ArrayList<String>) params.get("styleArr");
		ArrayList<String> styleInput = (ArrayList<String>) params.get("styleInput");
		ArrayList<String> styleInputSec = (ArrayList<String>) params.get("styleInputSec");
		ArrayList<Boolean> styleChk = (ArrayList<Boolean>) params.get("styleChk");
		int arrCnt = 0;
		
		for(int i=0; i<styleContentCnt; i++) {
			// input 세팅
			Map<String, Object> inputMap = new HashMap<String, Object>();
			ArrayList<Object> inputArr = new ArrayList<Object>();
			Map<String, Object> inputArrMap = new HashMap<>();
			Map<String, Object> inputSecArrMap = new HashMap<>();
			
			if(CommonUtils.getInt(styleArr.get(i)) == 1) {
				inputArrMap.put("text", styleInput.get(i));
				inputArrMap.put("width", "match");
				inputArrMap.put("height", "content");
				inputArrMap.put("weight", "1");
				inputArrMap.put("widget", "TextView");
				inputArrMap.put("textSize", "14dp");
				inputArrMap.put("textColor", "#404040");
				inputArrMap.put("textAlignment", "textStart");
				inputArr.add(inputArrMap);
			} else {
				inputArrMap.put("text", styleInput.get(i));
				inputArrMap.put("width", "match");
				inputArrMap.put("height", "content");
				inputArrMap.put("weight", "1");
				inputArrMap.put("widget", "TextView");
				inputArrMap.put("textSize", "14dp");
				inputArrMap.put("textColor", "#404040");
				inputArrMap.put("textAlignment", "textEnd");
				inputArr.add(0, inputArrMap);
				
				inputSecArrMap.put("text", styleInputSec.get(i));
				inputSecArrMap.put("width", "match");
				inputSecArrMap.put("height", "content");
				inputSecArrMap.put("weight", "1");
				inputSecArrMap.put("widget", "TextView");
				inputSecArrMap.put("textSize", "14dp");
				inputSecArrMap.put("textColor", "#404040");
				inputSecArrMap.put("textAlignment", "textStart");
				inputArr.add(1, inputSecArrMap);
			}
			
			inputMap.put("width", "match");
			inputMap.put("height", "content");
			inputMap.put("widget", "LinearLayout");
			inputMap.put("visibility", "visible");
			inputMap.put("orientation", "horizontal");
			inputMap.put("paddingBottom", "8dp");
			inputMap.put("children", inputArr);
			
			childrenArr.add(arrCnt, inputMap);
			arrCnt++;
			// line 세팅
			Map<String, Object> lineMap = new HashMap<String, Object>();
			
			lineMap.put("width", "match");
			lineMap.put("height", "1dp");
			lineMap.put("widget", "View");
			lineMap.put("background", "#c0c0c0");
			if(styleChk.get(i)) {
				lineMap.put("visibility", "visible");
			} else {
				lineMap.put("visibility", "gone");
			}
			lineMap.put("marginBottom", "8dp");
			
			childrenArr.add(arrCnt, lineMap);
			arrCnt++;
		}
		
		
		returnMap.put("width", "match");
		returnMap.put("height", "content");
		returnMap.put("widget", "LinearLayout");
		returnMap.put("orientation", "vertical");
		returnMap.put("paddingBottom", "8dp");
		returnMap.put("children", childrenArr);
		return returnMap;
	}
	
	public String setMediaUrlFormList(String msgFormId) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("msgFormId", msgFormId);
		String formattedStr = (String) generalDao.selectGernalObject(DB.QRY_SELECT_RCS_BASE_FORM, paramMap);
		
		// JSON OBJECT convert
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		obj = (JSONObject) parser.parse(formattedStr);
		obj = (JSONObject) obj.get("RCSMessage");
		obj = (JSONObject) obj.get("openrichcardMessage");
		obj = (JSONObject) obj.get("layout");
		
		JSONArray cellArray = null;
		cellArray = (JSONArray) obj.get("children");
		obj = (JSONObject) cellArray.get(0);
		
		String mediaUrl = CommonUtils.getString(obj.get("mediaUrl"));
		
		return mediaUrl;
	}

}
