package kr.co.uplus.cm.messageStatus.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;

@Service
public class MessageStatusService {

	@Autowired
	private GeneralDao generalDao;

	// 메시지 현황 리스트 조회
	public RestResult<Object> selectMessageStatusList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		//List<Object> rtnList = generalDao.selectGernalList("messageStatus.selectMessageStatusList", params);
        //rtn.setData(rtnList);
        //return rtn;
		
        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
        	rtn.setPageProps(params);
        	if(rtn.getPageInfo() != null) {
	            //카운트 쿼리 실행
	            int listCnt = generalDao.selectGernalCount("messageStatus.selectMessageStatusListCnt", params);
	            rtn.getPageInfo().put("totCnt", listCnt);
        	}
        }
        
        List<Object> rtnList = generalDao.selectGernalList("messageStatus.selectMessageStatusList", params);
        rtn.setData(rtnList);

        return rtn;
	}
	
	// 메시지 현황 상세 조회
	public RestResult<Object> selectMessageStatusDetail(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

        List<Object> rtnList = generalDao.selectGernalList("messageStatus.selectMessageStatusDetail", params);
        rtn.setData(rtnList);

        return rtn;
	}	


	// 웹 현황 리스트 조회
	public RestResult<Object> selectWebSendList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
        	rtn.setPageProps(params);
            if(rtn.getPageInfo() != null) {
	            //카운트 쿼리 실행
	            int listCnt = generalDao.selectGernalCount("webSend.selectWebSendListCnt", params);
	            rtn.getPageInfo().put("totCnt", listCnt);
            }
        }

        List<Object> rtnList = generalDao.selectGernalList("webSend.selectWebSendList", params);
        
        for(int i=0; i < rtnList.size(); i++){
        	HashMap<String,Object> hMap = (HashMap<String, Object>) rtnList.get(i);
        	
        	hMap.put("push", "X"); 
        	hMap.put("rcs", "X"); 
        	hMap.put("alimTalk", "X"); 
        	hMap.put("friendTalk", "X");
        	hMap.put("sms", "X"); 
        	
        	String chStr = (String) hMap.get("chString");
        	String [] stringArray= chStr.split(",");
        	for(int j=0; j < stringArray.length; j++) {
        		//발송채널구분 : PUSH, RCS, SMS, MMS, ALIMTALK, FRIENDTALK, SMARTMSG
        		if("PUSH".equalsIgnoreCase(stringArray[j])) hMap.put("push", "O"); 
        		if("RCS".equalsIgnoreCase(stringArray[j])) hMap.put("rcs", "O"); 
        		if("ALIMTALK".equalsIgnoreCase(stringArray[j])) hMap.put("alimTalk", "O"); 
        		if("FRIENDTALK".equalsIgnoreCase(stringArray[j])) hMap.put("friendTalk", "O"); 
        		if("SMS".equalsIgnoreCase(stringArray[j]) || "MMS".equalsIgnoreCase(stringArray[j])) hMap.put("sms", "O"); 
        	}
        	
        	rtnList.set(i, hMap);
        }
		
        rtn.setData(rtnList);

        return rtn;
	}
	
	
	
	// 웹 현황 실패 리스트 조회
	public RestResult<Object> selectWebSendFailList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
        	rtn.setPageProps(params);
            if(rtn.getPageInfo() != null) {
	            //카운트 쿼리 실행
	            int listCnt = generalDao.selectGernalCount("webSend.selectWebSendFailListCnt", params);
	            rtn.getPageInfo().put("totCnt", listCnt);
            }
        }

        List<Object> rtnList = generalDao.selectGernalList("webSend.selectWebSendFailList", params);
        
        //for(int i=0; i < rtnList.size(); i++){//o번째에만 발송채널을  html구조로 넘겨서 사용한다
        if(rtnList.size() > 0) {
        	HashMap<String,Object> hMap = (HashMap<String, Object>) rtnList.get(0);
        	
        	String chStr = (String) hMap.get("chString");
        	String [] stringArray= chStr.split(",");
        	String chString = "";
        	for(int j=0; j < stringArray.length; j++) {
        		//발송채널구분 : PUSH, RCS, SMS, MMS, ALIMTALK, FRIENDTALK, SMARTMSG
        		
        		chString = chString + "<h5 style=\"width:18%\" class=\"float-left ml color000\">"+stringArray[j];
        		if(j < stringArray.length -1) chString = chString+"<i class=\"far fa-chevron-right ml40\" style=\"font-size: 10px; color: #a3a3a3; font-weight: 700;\"></i>";
        		chString = chString + "</h5>";
        	}
        	
        	hMap.put("chString", chString);
        	rtnList.set(0, hMap);
        }
        //}
		
        rtn.setData(rtnList);

        return rtn;
	}
	
	
	// 예약발송 리스트 조회
	public RestResult<Object> selectBookingSendList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
        	rtn.setPageProps(params);
            if(rtn.getPageInfo() != null) {
	            //카운트 쿼리 실행
	            int listCnt = generalDao.selectGernalCount("bookingSend.selectBookingSendListCnt", params);
	            rtn.getPageInfo().put("totCnt", listCnt);
            }
        }

        List<Object> rtnList = generalDao.selectGernalList("bookingSend.selectBookingSendList", params);
        
        for(int i=0; i < rtnList.size(); i++){
        	HashMap<String,Object> hMap = (HashMap<String, Object>) rtnList.get(i);
        	
        	hMap.put("push", "X"); 
        	hMap.put("rcs", "X"); 
        	hMap.put("alimTalk", "X"); 
        	hMap.put("friendTalk", "X");
        	hMap.put("sms", "X"); 
        	
        	String chStr = (String) hMap.get("chString");
        	String [] stringArray= chStr.split(",");
        	for(int j=0; j < stringArray.length; j++) {
        		//발송채널구분 : PUSH, RCS, SMS, MMS, ALIMTALK, FRIENDTALK, SMARTMSG
        		if("PUSH".equalsIgnoreCase(stringArray[j])) hMap.put("push", "O"); 
        		if("RCS".equalsIgnoreCase(stringArray[j])) hMap.put("rcs", "O"); 
        		if("ALIMTALK".equalsIgnoreCase(stringArray[j])) hMap.put("alimTalk", "O"); 
        		if("FRIENDTALK".equalsIgnoreCase(stringArray[j])) hMap.put("friendTalk", "O"); 
        		if("SMS".equalsIgnoreCase(stringArray[j]) || "MMS".equalsIgnoreCase(stringArray[j])) hMap.put("sms", "O"); 
        	}
        	
        	rtnList.set(i, hMap);
        }
        
        rtn.setData(rtnList);

        return rtn;
	}
	
	
	// 예약발송 취소
	public RestResult<Object> cancelBookingSend(Map<String, Object> params) throws Exception {

		RestResult<Object> rtn = new RestResult<Object>();

		int resultCnt = generalDao.updateGernal("bookingSend.cancelBookingSend", params);

		if (resultCnt <= 0) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
		} else {
			rtn.setSuccess(true);
		}

		return rtn;
	}
	
	
	// MO 수신 리스트 조회
	public RestResult<Object> selectMoReceptionList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
        	rtn.setPageProps(params);
            if(rtn.getPageInfo() != null) {
	            //카운트 쿼리 실행
	            int listCnt = generalDao.selectGernalCount("moReception.selectMoReceptionListCnt", params);
	            rtn.getPageInfo().put("totCnt", listCnt);
            }
        }

        List<Object> rtnList = generalDao.selectGernalList("moReception.selectMoReceptionList", params);
        
        rtn.setData(rtnList);

        return rtn;
	}
	
	
	// 상태값 리스트 조회 (select box)
	public RestResult<Object> selectConditionList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

        List<Object> rtnList = generalDao.selectGernalList("moReception.selectConditionList", params);
        
        rtn.setData(rtnList);

        return rtn;
	}
	
	// 수신번호 리스트 조회 (check box) //수신번호는 최대 3개.
	public RestResult<Object> selectReceptionNumberList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

        List<Object> rtnList = generalDao.selectGernalList("moReception.selectReceptionNumberList", params);
        
        String receptionNumberStr = "";
        String num = "90";
        if(rtnList.size() > 0) {
        	if(rtnList.size() == 2) num = "45";
        	if(rtnList.size() == 3) num = "30";
        	for(int i=0; i<rtnList.size();i++) {
        		HashMap<String,Object> hMap = (HashMap<String, Object>) rtnList.get(i);
        		String moNumber = (String) hMap.get("moNumber");
        		receptionNumberStr = receptionNumberStr + "<div class=\"consolCheck\" style=\"width:"+num+"%\"><input type=\"checkbox\" id=\"searchReceptionNumber\" name=\"searchReceptionNumber\" class=\"checkStyle2\" value=\""+moNumber+"\" v-model=\"searchData.searchReceptionNumber\"><label for=\"searchReceptionNumber\">"+moNumber+"</label></div>";
        		//receptionNumberStr = receptionNumberStr + "<div class=\"consolCheck\" style=\"width:"+num+"%\"><input type=\"checkbox\" id=\"searchReceptionNumber_"+i+"\" class=\"checkStyle2\" value=\""+moNumber+"\"  v-model=\"searchData.searchReceptionNumber\"><label for=\"searchReceptionNumber_"+i+"\">"+moNumber+"</label></div>";
        	}
        }
        //System.out.println(">>>>receptionNumberStr ["+receptionNumberStr+"]");
        HashMap<String,Object> hMap = (HashMap<String, Object>) rtnList.get(0);
    	hMap.put("receptionNumber", receptionNumberStr);
    	rtnList.set(0, hMap);
    	
        rtn.setData(rtnList);

        return rtn;
	}
	
	
}
