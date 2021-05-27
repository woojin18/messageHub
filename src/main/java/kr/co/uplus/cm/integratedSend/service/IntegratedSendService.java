package kr.co.uplus.cm.integratedSend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

/**
 * <pre>
 * SendService
 * 각 통합발송 관리 Service
 * </pre>
 *
 * @author liam (Lee Sung Min)
 * @Date : 2021.03.25.
 * @Version : 1.0 Copyright 2020 LG Uplus Corp. All Rights Reserved.
 */
@Log4j2
@Service
public class IntegratedSendService {

	@Autowired
	private GeneralDao generalDao;

	/**
     * 통합 템플릿 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectIntegratedSendList(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
            rtn.setPageProps(params);
            if(rtn.getPageInfo() != null) {
                //카운트 쿼리 실행
                int listCnt = generalDao.selectGernalCount("integratedSend.selectIntegratedSendListCnt", params);
                rtn.getPageInfo().put("totCnt", listCnt);
            }
        }

        List<Object> rtnList = generalDao.selectGernalList("integratedSend.selectIntegratedSendList", params);
        rtn.setData(rtnList);

        return rtn;
    }

    
	/**
     * 통합 템플릿 정보 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectIntegratedSendInfo(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        List<Object> rtnList = generalDao.selectGernalList("integratedSend.selectIntegratedSendDetail", params);
        
        rtn.setData(rtnList);

        return rtn;
    }
    
    


    /**
     * 통합 템플릿 삭제 처리
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public RestResult<Object> deleteIntegratedSend(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        int resultCnt = generalDao.deleteGernal("integratedSend.deleteIntegratedSend", params);
        if (resultCnt <= 0) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
        } else {
            rtn.setSuccess(true);
            rtn.setData(params);
        }

        return rtn;
    }
    
    
    private String buttonAddStr(Map<String, Object> params, String checkChannel, List<Map<String, Object>> buttonInfoList) {
    	
    	StringBuffer sb = new StringBuffer();
    	
    		
		String buttonType = null;
		String buttonName = null;
		String buttonLink = null;
		String buttonLink1 = null;
		String startDate = null;
		String endDate = null;
		
		//sb.append("\"buttons\": [{ ");
		sb.append("\"suggestions\": [ ");
		
		int rcsIdx = 1;
		for(Map<String, Object> buttonInfo : buttonInfoList) {
			
				buttonType = CommonUtils.getStrValue(buttonInfo, "buttonType");
                buttonName = CommonUtils.getStrValue(buttonInfo, "buttonName");
                buttonLink = CommonUtils.getStrValue(buttonInfo, "buttonLink");
                
                buttonLink1 = CommonUtils.getStrValue(buttonInfo, "buttonLink1");
                startDate = CommonUtils.getStrValue(buttonInfo, "startDate");
                endDate = CommonUtils.getStrValue(buttonInfo, "endDate");
                
                System.out.println(">>>>service 003  button 003 : buttonType : "+buttonType);
    			System.out.println(">>>>service 003  button 004 : buttonName : "+buttonName);
    			System.out.println(">>>>service 003  button 005 : buttonLink : "+buttonLink);
    			System.out.println(">>>>service 003  button 006 : buttonLink1 : "+buttonLink1);
    			System.out.println(">>>>service 003  button 007 : startDate : "+startDate);
    			System.out.println(">>>>service 003  button 008 : endDate : "+endDate);
		
    			if(buttonType.equalsIgnoreCase("U")) {
	    			sb.append("	{ ");
	    			sb.append("	\"action\": { ");
	    		    sb.append("	\"urlAction\": { "); // URL링크 버튼인 경우
	    		    sb.append("	\"openUrl\": { ");
	    		    sb.append("	\"url\": \""+buttonLink+"\" "); // 내용
	    		    sb.append("	} ");
	    		    sb.append("	}, ");
	    		    sb.append("	\"buttonType\": \""+buttonType+"\", "); // 버튼타입
	    		    sb.append("	\"displayText\": \""+buttonName+"\","); // 버튼이름
	    		    sb.append("	\"postback\": { ");
	    		    //sb.append("	\"data\": \""+params.get("")+"\" "); //set_by_chatbot_open_url
	    		    sb.append("	\"data\": \"set_by_chatbot_open_url\" ");
	    		    sb.append("	} ");
	    		    sb.append("	} ");
	    		    sb.append("	} ");
    			}
    			if(buttonType.equalsIgnoreCase("C")) {
	    			sb.append("	{ ");
	    			sb.append("	\"action\": { ");
	    			sb.append("	\"clipboardAction\": { "); // 복사하기 버튼인 경우
	    			sb.append("	\"copyToClipboard\": { ");
	    			sb.append("	\"text\": \""+buttonLink+"\" "); // 내용
	    			sb.append("	} ");
	    			sb.append("	}, ");
	    			sb.append("	\"buttonType\": \""+buttonType+"\", "); // 버튼타입
	    			sb.append("	\"displayText\": \""+buttonName+"\", "); // 버튼이름
	    			sb.append("	\"postback\": { ");
	    		    //sb.append("	\"data\": \""+params.get("")+"\" ");  //set_by_chatbot_copy_to_clipboard
	    		    sb.append("	\"data\": \"set_by_chatbot_copy_to_clipboard\" ");
	    		    sb.append("	} ");
	    		    sb.append("	} ");
	    		    sb.append("	} ");
    			}
    		    if(buttonType.equalsIgnoreCase("T")) {
	    		    sb.append("	{ ");
	    		    sb.append("	\"action\": { ");
	    		    sb.append("	\"dialerAction\": { "); // 전화걸기 버튼인 경우
	    		    sb.append("	\"dialPhoneNumber\": { ");
	    		    sb.append("	\"phoneNumber\": \""+buttonLink+"\" "); // 휴대폰번호
	    		    sb.append("	} ");
	    		    sb.append("	}, ");
	    		    sb.append("	\"buttonType\": \""+buttonType+"\", "); // 버튼타입
	    		    sb.append("	\"displayText\": \""+buttonName+"\", "); // 버튼이름
	    		    sb.append("	\"postback\": { ");
	    		    //sb.append("	\"data\": \""+params.get("")+"\" ");  //set_by_chatbot_dial_phone_number
	    		    sb.append("	\"data\": \"set_by_chatbot_dial_phone_number\" ");
	    		    sb.append("	} ");
	    		    sb.append("	} ");
	    		    sb.append("	} ");
    		    }
    		    if(buttonType.equalsIgnoreCase("S")) {
	    		    sb.append("	{ ");
	    		    sb.append("	\"action\": { ");
	    		    sb.append("	\"calendarAction\": { "); // 일정추가 버튼인 경우
	    		    sb.append("	\"createCalendarEvent\": { ");
	    		    sb.append("	\"startTime\": \""+startDate+"\", "); // 시작일 2017-03-14T00:00:00Z
	    		    sb.append("	\"endTime\": \""+endDate+"\", ");   // 종료일
	    		    sb.append("	\"title\": \""+buttonLink+"\", ");                // 제목
	    		    sb.append("	\"description\": \""+buttonLink1+"\" ");  // 내용
	    		    sb.append("	} ");
	    		    sb.append("	}, ");
	    		    sb.append("	\"buttonType\": \""+buttonType+"\", "); // 버튼타입
	    		    sb.append("	\"displayText\": \""+buttonName+"\", "); // 버튼이름
	    		    sb.append("	\"postback\": { ");
	    		    //sb.append("	\"data\": \""+params.get("")+"\" ");//set_by_chatbot_create_calendar_event
	    		    sb.append("	\"data\": \"set_by_chatbot_create_calendar_event\" ");
	    		    sb.append("	} ");
	    		    sb.append("	} ");
	    		    sb.append("	} ");
    		    }
    		    if(buttonType.equalsIgnoreCase("M")) {
	    		    sb.append("	{ ");
	    		    sb.append("	\"action\": { ");
	    		    sb.append("	\"mapAction\": { "); // 지도맵 버튼인 경우
	    		    sb.append("	\"requestLocationPush\": {} ");
	    		    sb.append("	}, ");
	    		    sb.append("	\"buttonType\": \""+buttonType+"\", "); // 버튼타입
	    		    sb.append("	\"displayText\": \""+buttonName+"\", "); // 버튼이름
	    		    sb.append("	\"postback\": { ");
	    		    //sb.append("	\"data\": \""+buttonLink+"\" ");//set_by_chatbot_request_location_push
	    		    sb.append("	\"data\": \"set_by_chatbot_request_location_push\" ");
	    		    sb.append("	} ");
	    		    sb.append("	} ");
	    		    sb.append("	} ");
    		    }
    		    
    		    if(rcsIdx++ < buttonInfoList.size()) {
    		    	sb.append(", ");
    		    }
		}//end for(Map<String, Object> buttonInfo : buttonInfoList) {
		
	    sb.append("	] ");
	    //sb.append("	}]         ");	
    	
    	return sb.toString();
    }


}
