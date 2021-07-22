package kr.co.uplus.cm.rcsTemplateSend.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;

@Service
public class RcsTemplateSendService {
	
	@Autowired
	private GeneralDao generalDao;

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
		
		List<Object> result = generalDao.selectGernalList(DB.QRY_SELECT_CALLBACK_LIST, params);
		
		rtn.setData(result);
		
		return rtn;
	}

	public RestResult<Object> rcsMsgSave(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>(true);
		Map<String, Object> paramsData = (Map<String, Object>) params.get("data");
		String templateRadioBtn	= CommonUtils.getString(params.get("templateRadioBtn"));		// RCS 유형
		String saveBoxId		= CommonUtils.getCommonId("BOX", 5);							// MSGBASE KEY
		String corpId			= CommonUtils.getString(params.get("corpId"));					// CORPID
		String brandId			= CommonUtils.getString(paramsData.get("brandId"));				// BRANDID
		String copyPossYn		= CommonUtils.getString(paramsData.get("copy"));				// 복사여부
		String callback			= CommonUtils.getString(paramsData.get("callback"));			// 대체발송 전화번호
		String senderType		= CommonUtils.getString(paramsData.get("senderType"));			// 대체 발송 코드
		
		// 공통 파라미터 세팅
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("saveBoxId", saveBoxId);
		paramMap.put("corpId", corpId);
		paramMap.put("brandId", brandId);
		
		// RCS 유형에 따라 파라미터 세팅
		if("text".equals(templateRadioBtn)) {
			// msgbaseId 세팅 로직 수행
			String msgbaseId = (String) generalDao.selectGernalObject(DB.QRY_SELECT_RCS_MESSAGE_ID, paramMap);
			
			paramMap.put("msgbaseId", msgbaseId);
			paramMap.put("saveBoxName" , CommonUtils.getString(paramsData.get("saveContent")));
			paramMap.put("adYn", null);
			paramMap.put("freeReceiveNum", null);
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
			
			JSONArray jsonArr = new JSONArray();
			JSONObject jsonObj = new JSONObject();
			JSONArray jsonBtnArr = new JSONArray();
			
			jsonObj.put("title", "");
			jsonObj.put("description", CommonUtils.getString(paramsData.get("textContents")));
			jsonObj.put("mediaUrl", "");
			jsonObj.put("media", "");
			jsonObj.put("suggestions", jsonBtnArr);
			
			jsonArr.add(0, jsonObj);
			
			String msgBaseInfo = jsonArr.toString();
			paramMap.put("msgBaseInfo", msgBaseInfo);
			
			// table insert
			generalDao.insertGernal(DB.QRY_INSERT_RCS_TMP_MSGBASE, paramMap);
		}

		return rtn;
	}

	public RestResult<Object> selectRcsMsgList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> pageInfo = (Map<String, Object>) params.get("pageInfo");
		String srcInput= CommonUtils.getString(params.get("srcInput"));
		String srcSelect = CommonUtils.getString(params.get("srcSelect"));		// brand msg null
		String templateRadioBtn= CommonUtils.getString(params.get("templateRadioBtn"));
		String[] msgIdArr = {"SS000000", "SL000000", "SMwThM00", "SMwThT00", "CMwShS0300", "CMwShS0400", "CMwShS0500", "CMwShS0600", "CMwMhM0300", "CMwMhM0400", "CMwMhM0500", "CMwMhM0600"};
		
		paramMap.put("srcInput", srcInput);
		paramMap.put("srcSelect", srcSelect);
		if("text".equals(templateRadioBtn)) {
			// 텍스트 미승인형일 경우 조건 세팅
			paramMap.put("msgIdArr", msgIdArr);
		} else {
			paramMap.put("msgbaseId", templateRadioBtn);
		}
		
		if(pageInfo != null && !pageInfo.isEmpty()) {
			int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_RCS_TMP_MSGBASE_CNT, paramMap);
			pageInfo.put("rowNum", listCnt);
			
			rtn.setPageInfo(pageInfo);
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
		Map<String, Object> msgDatail = (Map<String, Object>) generalDao.selectGernalObject(DB.QRY_SELECT_RCS_TMP_MSGBASE_DETAIL, params);
		String btnType = CommonUtils.getString(params.get("templateRadioBtn"));
		
		// 타입에 따른 json 값 세팅 후 return
		if("text".equals(btnType)) {
			String brandId = CommonUtils.getString(msgDatail.get("BRAND_ID"));
			String saveContent = CommonUtils.getString(msgDatail.get("SAVE_BOX_NAME"));
			
			
			// JSON ARRAY parsing
			String msgInfo = (String) msgDatail.get("MESSAGEBASE_INFO");
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(msgInfo);
			JSONArray jsonArr = (JSONArray) obj;
			JSONObject jsonObj = (JSONObject) jsonArr.get(0);
			
			System.out.println("asdfasdfsdf" + jsonObj);
			
			String textContents = (String) jsonObj.get("description");
			
			rtnMap.put("brandId", brandId);
			rtnMap.put("saveContent", saveContent);
			rtnMap.put("textContents", textContents);
		}
		
		
		rtn.setData(rtnMap);
		
		return rtn;
	}

}
