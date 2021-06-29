package kr.co.uplus.cm.rcsTemplateSend.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
				
				selectBtn[i] = "URL 링크";
				btnNm[i] = CommonUtils.getString(jsonArrObj.get("displayText"));
			} else if(jsonArrObj.get("clipboardAction") != null) {
				clipboardObj = (JSONObject) jsonArrObj.get("clipboardAction");
				clipboardObj = (JSONObject) clipboardObj.get("copyToClipboard");
				
				selectBtn[i] = "복사하기";
				btnNm[i] = CommonUtils.getString(jsonArrObj.get("displayText"));
			} else if(jsonArrObj.get("dialerAction") != null) {
				dialerObj = (JSONObject) jsonArrObj.get("dialerAction");
				dialerObj = (JSONObject) dialerObj.get("dialPhoneNumber");
				
				selectBtn[i] = "전화걸기";
				btnNm[i] = CommonUtils.getString(jsonArrObj.get("displayText"));
			} else if(jsonArrObj.get("calendarAction") != null) {
				calendarObj = (JSONObject) jsonArrObj.get("calendarAction");
				calendarObj = (JSONObject) calendarObj.get("createCalendarEvent");
				
				selectBtn[i] = "일정추가";
				btnNm[i] = CommonUtils.getString(jsonArrObj.get("displayText"));
			} else if(jsonArrObj.get("mapAction") != null) {
				mapActionObj = (JSONObject) jsonArrObj.get("mapAction");
				mapActionObj = (JSONObject) mapActionObj.get("dialPhoneNumber");
				
				selectBtn[i] = "지도맵";
				btnNm[i] = CommonUtils.getString(jsonArrObj.get("displayText"));
			}
		}
		
		resultData.put("selectBtn", selectBtn);
		resultData.put("btnNm", btnNm);
		
		return rtn.setData(resultData);
	}

	
	public RestResult<Object> selectCallbackList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		List<Object> result = generalDao.selectGernalList(DB.QRY_SELECT_CALLBACK_LIST, params);
		
		rtn.setData(result);
		
		return rtn;
	}

}
