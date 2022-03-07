package kr.co.uplus.cm.messageStatus.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.type.MongoConf;
import kr.co.uplus.cm.common.utils.CmKeyMaker;
import kr.co.uplus.cm.gw.model.mongo.CmMoMsgInfoDto;
import kr.co.uplus.cm.gw.model.mongo.CmMsgInfoDto;
import kr.co.uplus.cm.gw.model.mongo.msgInfo.AlimtalkMsg;
import kr.co.uplus.cm.gw.model.mongo.msgInfo.FriendtalkMsg;
import kr.co.uplus.cm.gw.model.mongo.msgInfo.MmsMsg;
import kr.co.uplus.cm.gw.model.mongo.msgInfo.PushMsg;
import kr.co.uplus.cm.gw.model.mongo.msgInfo.RcsMsg;
import kr.co.uplus.cm.gw.model.mongo.msgInfo.SmsMsg;
import kr.co.uplus.cm.messageStatus.controller.MessageStatusController;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;
import kr.co.uplus.config.mongo.cmd.MongoCmd;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MessageStatusService {

	@Autowired
	private GeneralDao generalDao;
	
	@Autowired
	private MongoCmd mongoCmd;
	
	// 메시지 현황 리스트 조회
	public RestResult<Object> selectMessageStatusList(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		// 날짜 검색을 IN 조건으로 변경처리하기 위해 배열로 변환
		String searchStartDate = CommonUtils.getString(params.get("searchStartDate"));
		String searchEndDate = CommonUtils.getString(params.get("searchEndDate"));
		String[] dateArr = CommonUtils.dateArrReturn(searchStartDate, searchEndDate);
		params.put("dateArr", dateArr);
		
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
	@SuppressWarnings("unchecked")
	public RestResult<Object> selectMessageStatusDetail(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		Map<String, Object> rtnMap = (Map<String, Object>) generalDao.selectGernalObject("messageStatus.selectMessageStatusDetail", params);
		
		String			reqCh		= CommonUtils.getString(rtnMap.get("reqCh"));
		String			msgKey		= params.get("msgKey").toString();
		Query			query		= new Query(Criteria.where("msgKey").is(msgKey));
		CmMsgInfoDto	msgInfo		= mongoCmd.findOne(query, CmMsgInfoDto.class, MongoConf.CM_MSG_INFO.key + "_"+CmKeyMaker.getTime14(msgKey).substring(0,10));
		String			title		= "";//메시지 제목
		String			msg			= "";//메시지 내용
		
		log.info("{} MessageStatusService Mongo Buttons : {}", this.getClass(), msgInfo);
		
		if(msgInfo != null) {
			switch (reqCh) {
			case "SMS":
				SmsMsg sms = msgInfo.getSmsMsg();
				msg = sms.getMsg();
				
				rtnMap.put("msg", msg);
				
				break;
			case "MMS":
				MmsMsg mms = msgInfo.getMmsMsg();
				msg = mms.getMsg();
				
				rtnMap.put("msg", msg);
				
				break;
			case "PUSH":
				PushMsg push = msgInfo.getPushMsg();
				msg = CommonUtils.getString(push.getMsg());
				
				rtnMap.put("msg", msg);
				
				break;
			case "RCS":
				RcsMsg rcs = msgInfo.getRcsMsg();
				Map<String, String> rcsBody = rcs.getBody();
				
				title = CommonUtils.getString(rcsBody.get("title"));
				msg = CommonUtils.getString(rcsBody.get("description"));
				
				
				List<Object> rcsButtonList = rcs.getButtons();
				List<String> returnRcsBtnNmList = new ArrayList<String>();
				
				rtnMap.put("title", title);
				rtnMap.put("msg", msg);
				
				// 버튼이 있는 경우 버튼 세팅
				if(rcsButtonList.size() > 0) {
					Map<String, Object> buttonMap = (Map<String, Object>) rcsButtonList.get(0);
					List<Map<String, Object>> subButtonList = (List<Map<String, Object>>) buttonMap.get("suggestions");
					for(Map<String, Object> subButtonMap : subButtonList) {
						Map<String, Object> action = (Map<String, Object>) subButtonMap.get("action");
						String displayText = CommonUtils.getString(action.get("displayText"));
						returnRcsBtnNmList.add(displayText);
					}
					
					rtnMap.put("btnCnt", returnRcsBtnNmList.size());
					rtnMap.put("btnNmArr", returnRcsBtnNmList);
				}
				
				log.info("{} MessageStatusService Mongo Buttons : {}", this.getClass(), rtnMap);
				
				//rcs는 body에서 가져오는데 메세지 풀 내용을 가지고 있지않기 때문에 body의 내용을 뿌리기로 하였습니다.(손왕구 차장)
				break;
			case "ALIMTALK":
				AlimtalkMsg alim = msgInfo.getAlimtalkMsg();
				title = alim.getTitle();
				msg = alim.getMsg();
				
				rtnMap.put("title", title);
				rtnMap.put("msg", msg);
				// 버튼 세팅
				
				// 버튼 세팅alim
				List<Object> alimButtonList = alim.getButtons();
				List<String> returnAlimBtnNmList = new ArrayList<String>();
				if(alimButtonList.size() >0) {
					for(Object obj : alimButtonList) {
						Map<String ,Object> btnMap = (Map<String, Object>) obj;
						returnAlimBtnNmList.add(CommonUtils.getString(btnMap.get("name")));
					}
					
					rtnMap.put("btnCnt", returnAlimBtnNmList.size());
					rtnMap.put("btnNmArr", returnAlimBtnNmList);
				}
				
				log.info("{} MessageStatusService Mongo Buttons : {}", this.getClass(), alim.getButtons());
				break;
			case "FRIENDTALK":
				FriendtalkMsg fri = msgInfo.getFriendtalkMsg();
				msg = fri.getMsg();
				
				rtnMap.put("msg", msg);
				
				// 버튼 세팅
				List<Object> friButtonList = fri.getButtons();
				List<String> returnFriBtnNmList = new ArrayList<String>();
				if(friButtonList.size() >0) {
					for(Object obj : friButtonList) {
						Map<String ,Object> btnMap = (Map<String, Object>) obj;
						returnFriBtnNmList.add(CommonUtils.getString(btnMap.get("name")));
					}
					
					rtnMap.put("btnCnt", returnFriBtnNmList.size());
					rtnMap.put("btnNmArr", returnFriBtnNmList);
				}
				
				log.info("{} MessageStatusService Mongo Buttons rtnMap : {}", this.getClass(), rtnMap);
				break;
			case "SMART":
				break;
			default:
				break;
			}
		}
		
		log.info("{} MessageStatusService Mongo rtmMap : {}", this.getClass(), rtnMap);
		
		rtn.setData(rtnMap);
		
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
	@SuppressWarnings("unchecked")
	public RestResult<Object> selectWebSendFailList(Map<String, Object> params) throws Exception {
		RestResult<Object>	rtn		= new RestResult<Object>();
		List<Object>		rtnList	= null;
		
		if(params.containsKey("pageNo")
			&& CommonUtils.isNotEmptyObject(params.get("pageNo"))
			&& params.containsKey("listSize")
			&& CommonUtils.isNotEmptyObject(params.get("listSize"))) {
		
			rtn.setPageProps(params);
			
			Map<String, Object> listCnt = (Map<String, Object>) generalDao.selectGernalObject("webSend.selectWebSendFailListCnt", params);
			
			rtn.getPageInfo().put("totCnt", listCnt.get("failCnt"));
			
			String chStr = (String) listCnt.get("chString");
			String [] stringArray= chStr.split(",");
			String chString = "";
			for(int j=0; j < stringArray.length; j++) {
				//발송채널구분 : PUSH, RCS, SMS, MMS, ALIMTALK, FRIENDTALK, SMARTMSG
				if(stringArray[j].equals("PUSH") || stringArray[j].equals("RCS") || stringArray[j].equals("SMS") || stringArray[j].equals("MMS") || stringArray[j].equals("LMS")) {
					chString = chString + "<h5 style=\"width:12%\" class=\"float-left ml color000\">"+stringArray[j];
				} else {
					chString = chString + "<h5 style=\"width:20%\" class=\"float-left ml color000\">"+stringArray[j];
				}
				if(j < stringArray.length -1) chString = chString+"<i class=\"far fa-chevron-right ml40\" style=\"font-size: 10px; color: #a3a3a3; font-weight: 700;\"></i>";
				chString = chString + "</h5>";
			}
			
			rtnList = generalDao.selectGernalList("webSend.selectWebSendFailList", params);
			
			Map<String, Object> rtnMap = new HashMap<>();
			rtnMap.put("list"			, rtnList);
			rtnMap.put("totCnt"			, listCnt.get("totCnt"));
			rtnMap.put("failCnt"		, listCnt.get("failCnt"));
			rtnMap.put("senderTypeNm"	, listCnt.get("senderTypeNm"));
			rtnMap.put("senderType"		, listCnt.get("senderType"));
			rtnMap.put("chString"		, chString);
			
			rtn.setData(rtnMap);
		} else {
			rtnList = generalDao.selectGernalList("webSend.selectWebSendFailList", params);
			rtn.setData(rtnList);
		}
		
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
//        HashMap<String,Object> hMap = (HashMap<String, Object>) rtnList.get(0);
//    	hMap.put("receptionNumber", receptionNumberStr);
//    	rtnList.set(0, hMap);
    	
        rtn.setData(rtnList);

        return rtn;
	}
	
	// 메시지 현황 상세 조회
	@SuppressWarnings("unchecked")
	public RestResult<Object> selectMoMessageDetail(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		
		String			moKey		= params.get("moKey").toString();
		Query			query		= new Query(Criteria.where("moKey").is(moKey));
		CmMoMsgInfoDto	msgInfo		= mongoCmd.findOne(query, CmMoMsgInfoDto.class, MongoConf.CM_MO_MSG_INFO.key + "_"+CmKeyMaker.getTime14(moKey).substring(0,10));

		log.info("{} MessageStatusService Mongo Buttons : {}", this.getClass(), msgInfo);
		
		if(msgInfo != null) {
			rtnMap.put("msg", msgInfo.getMoMsg());
			rtnMap.put("allMsg", msgInfo.toString());
		}
		
		log.info("{} MessageStatusService Mongo rtmMap : {}", this.getClass(), rtnMap);
		
		rtn.setData(rtnMap);
		
		return rtn;
	}	
}
