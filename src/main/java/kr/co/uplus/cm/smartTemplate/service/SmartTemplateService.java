package kr.co.uplus.cm.smartTemplate.service;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
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
public class SmartTemplateService {

	@Autowired
	private GeneralDao generalDao;

	/**
     * 스마트 상품 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectSmartProductList(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        List<Object> rtnList = generalDao.selectGernalList("smartTemplate.selectSmartProductList", params);
        rtn.setData(rtnList);

        return rtn;
    }
    
	/**
     * 스마트 상품 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectSmartProductInfo(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        List<Object> rtnList = generalDao.selectGernalList("smartTemplate.selectSmartProductInfo", params);
        
        rtn.setData(rtnList);

        return rtn;
    }
    
	/**
     * 스마트 템플릿 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectSmartTemplateList(Map<String, Object> params) throws Exception {

    	Map<String, Object> sParams = new HashMap<String, Object>(params);
        RestResult<Object> rtn = new RestResult<Object>();
//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//System.out.println("sParams.get('productCode') : "+sParams.get("productCode"));
//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        List<Object> productList = generalDao.selectGernalList("smartTemplate.selectSmartProductList", sParams);
        if(productList.size() > 0) {
	        HashMap<String,String> product = (HashMap<String,String>) productList.get(0);
	        //RCS, PUSH, SMS, MMS, FRIENDTALK, ALIMTALK
	        String notIncludedRCS = "[\"RCS\"]";
	        String notIncludedPUSH = "[\"PUSH\"]";
	        String notIncludedSMS = "[\"SMS\"]";
	        String notIncludedMMS = "[\"MMS\"]";
	        String notIncludedFRIENDTALK = "[\"FRIENDTALK\"]";
	        String notIncludedALIMTALK = "[\"ALIMTALK\"]";
	        
	        String msgKind = product.get("msgKind");
	        String chType0 = product.get("chType0");
	        String chType1 = product.get("chType1");
	        String chType2 = product.get("chType2");
	        String chType3 = product.get("chType3");
	        
	        StringBuffer sb = new StringBuffer();
	        //List<String> chTypeList = new ArrayList<String>();
	        sb.append("[");
	        if(chType0 != null && chType0 != "") {
	        	sb.append("\""+chType0+"\"");
	        }else {
	        	chType0 = "";
	        }
	        if(chType1 != null && chType1 != "") {
	        	sb.append(",\""+chType1+"\"");
	        }else {
	        	chType1 = "";
	        }
	        if(chType2 != null && chType2 != "") {
	        	sb.append(",\""+chType2+"\"");
	        }else {
	        	chType2 = "";
	        }
	        if(chType3 != null && chType3 != "") {
	        	sb.append(",\""+chType3+"\"");
	        }else {
	        	chType3 = "";
	        }
	        sb.append("]");
	        
	        if(chType0.equalsIgnoreCase("RCS") || chType1.equalsIgnoreCase("RCS") || chType2.equalsIgnoreCase("RCS") || chType3.equalsIgnoreCase("RCS")) notIncludedRCS = "";
	        if(chType0.equalsIgnoreCase("PUSH") || chType1.equalsIgnoreCase("PUSH") || chType2.equalsIgnoreCase("PUSH") || chType3.equalsIgnoreCase("PUSH")) notIncludedPUSH = "";
	        if(chType0.equalsIgnoreCase("SMS") || chType1.equalsIgnoreCase("SMS") || chType2.equalsIgnoreCase("SMS") || chType3.equalsIgnoreCase("SMS")) notIncludedSMS = "";
	        if(chType0.equalsIgnoreCase("MMS") || chType1.equalsIgnoreCase("MMS") || chType2.equalsIgnoreCase("MMS") || chType3.equalsIgnoreCase("MMS")) notIncludedMMS = "";
	        if(chType0.equalsIgnoreCase("FRIENDTALK") || chType1.equalsIgnoreCase("FRIENDTALK") || chType2.equalsIgnoreCase("FRIENDTALK") || chType3.equalsIgnoreCase("FRIENDTALK")) notIncludedFRIENDTALK = "";
	        if(chType0.equalsIgnoreCase("ALIMTALK") || chType1.equalsIgnoreCase("ALIMTALK") || chType2.equalsIgnoreCase("ALIMTALK") || chType3.equalsIgnoreCase("ALIMTALK")) notIncludedALIMTALK = "";

	        
	        sParams.put("msgKind", product.get("msgKind"));
	        sParams.put("chTypeList", sb.toString());

	        if(notIncludedRCS != "") sParams.put("notIncludedRCS", notIncludedRCS);
	        if(notIncludedPUSH != "") sParams.put("notIncludedPUSH", notIncludedPUSH);
	        if(notIncludedSMS != "") sParams.put("notIncludedSMS", notIncludedSMS);
	        if(notIncludedMMS != "") sParams.put("notIncludedMMS", notIncludedMMS);
	        if(notIncludedFRIENDTALK != "") sParams.put("notIncludedFRIENDTALK", notIncludedFRIENDTALK);
	        if(notIncludedALIMTALK != "") sParams.put("notIncludedALIMTALK", notIncludedALIMTALK);
        }else {
	        sParams.put("msgKind", "");
	        sParams.put("chTypeList", "");
        }
        if(sParams.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(sParams.get("pageNo"))
                && sParams.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(sParams.get("listSize"))) {
            rtn.setPageProps(sParams);
            if(rtn.getPageInfo() != null) {
                //카운트 쿼리 실행
                int listCnt = generalDao.selectGernalCount("smartTemplate.selectSmartTemplateListCnt", sParams);
                rtn.getPageInfo().put("totCnt", listCnt);
            }
        }

        List<Object> rtnList = generalDao.selectGernalList("smartTemplate.selectSmartTemplateList", sParams);
        rtn.setData(rtnList);

        return rtn;
    }

    
	/**
     * 스마트 템플릿 정보 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectSmartTemplateInfo(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        List<Object> rtnList = generalDao.selectGernalList("smartTemplate.selectSmartTemplateDetail", params);
        
        rtn.setData(rtnList);

        return rtn;
    }
    
    
    /**
     * 스마트 템플릿 저장 처리
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public RestResult<Object> insertSmartTemplate(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        int resultCnt = 0;
        ArrayList<String> arrChannelList = (ArrayList<String>) params.get("checkedChannel");
        String[] checkChannelArr = new String[arrChannelList.size()];
        int size = 0;
        for(String channel:arrChannelList) {
        	checkChannelArr[size++] = channel;
        }
        
        //System.out.println(">>>>service 002 checkChannelArr.length ["+checkChannelArr.length+"]");
        
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        StringBuffer sbChannel = new StringBuffer();
        //sbChannel.append("\"chList\" : \"");//채널순서
        String chTypeList = "";
        for(int i=0; i<checkChannelArr.length;i++) {
        	chTypeList = chTypeList + (checkChannelArr[i]);
        	if(i<checkChannelArr.length-1) chTypeList = chTypeList + ",";
        }
        //System.out.println(">>>>service 002 chTypeList ["+chTypeList+"]");
        //sbChannel.append("\",");
        
        for(int i=0; i<checkChannelArr.length;i++) {
        	//System.out.println(">>>>service 002-1 no : "+ checkChannelArr[i]);
        	sb.append("{");
        	sb.append(sbChannel.toString());//채널순서를 기억하기 위해 추가
        	if(checkChannelArr[i].equalsIgnoreCase("PUSH")) {  
//PUSH ====================================================================  
        		//System.out.println(">>>>service 003  PUSH 001 : "+params.get("pushImgInfo").getClass().getName());
        		LinkedHashMap<String, String> pushImgInfoMap = (LinkedHashMap<String, String>) params.get("pushImgInfo");
        		////System.out.println(">>>>service 004  PUSH 002 : "+pushImgInfoMap.get("imgUrl"));
        		sb.append("\"chTypeList\" : \""	+chTypeList+"\",");
        		sb.append("\"chType\" : \"PUSH\",");//발송채널
        		sb.append("\"sendType\" : \""	+params.get("pushSend")+"\","); // 발송타입(ALL, FCM, APNS)
        		sb.append("\"title\" : \""		+params.get("pushTitle")+"\","); // 제목
        		//String pushContent = ((String) params.get("pushContent")).replaceAll("\n", "CHR(13)CHR(10)");
        		sb.append("\"msg\" : \""		+JSONObject.escape((String)params.get("pushContent"))+"\","); // 메시지
        		sb.append("\"rcvblcInput\" : \""+params.get("pushHowToDenyReceipt")+"\","); // 수신거부방법
        		if(pushImgInfoMap.containsKey("fileId")) {
	        		sb.append("\"fileId\" : \""		+pushImgInfoMap.get("fileId")+"\","); // 이미지 파일ID
	        		//sb.append("\"imgUrl\" : \""		+pushImgInfoMap.get("imgUrl")+"\","); // 이미지 파일url
        		}
        		sb.append("\"appId\" : \""		+params.get("pushAppId")+"\""); // APP ID
        	}else if(checkChannelArr[i].equalsIgnoreCase("RCS")) {
        		//System.out.println(">>>>service 003  RCS 001");
//RCS ====================================================================
        		sb.append("\"chTypeList\" : \""	+chTypeList+"\",");
        		sb.append("\"chType\" : \"RCS\",");//발송채널

        		if((int)params.get("rcsTemplateTable") == 0) {//
//RCS FREE TYPE ====================================================================
        			//String brand = (String) params.get("brandNm");
        			String freeMessageBaseId = selectMessageBaseId(params);
        			sb.append("\"rcsPrdType\" : \"FREE\",");	// RCS상품타입(프리 템플릿) rcsTemplateTable => 0
        			sb.append("\"messagebaseId\": \""+freeMessageBaseId+"\","); // cm.CM_RCS_MSGBASE,           c cm_console.CM_RCS_TMP_MSGBASE의 MESSAGEBASEFORM_ID값을 설정
        			sb.append("\"callback\": \""+params.get("callback")+"\",");
        			sb.append("\"footer\": \"\","); // 무료수신거부 번호, header의 값이 광고성일 때 footer 값을 포함하지 않고 발송하면 실패 처리
        			
        			sb.append("\"body\": [{ ");   
        			sb.append("	\"title\" : \"\", "); //
        			//String rcs0Content = ((String) params.get("rcs0Content")).replaceAll("\n", "CHR(13)CHR(10)");
        			sb.append("	\"description\" : \""+JSONObject.escape((String)params.get("rcs0Content"))+"\", "); // 메시지
        			sb.append("	\"mediaUrl\" : \"{}\", "); //
        			sb.append("	\"media\" : \"\", "); //
        			sb.append("	\"suggestions\" : \"\" "); //
        			sb.append("	}] ");
        			
        		}else if((int)params.get("rcsTemplateTable") == 1){
//RCS DESCRIPTION TYPE ====================================================================        			
        			sb.append("\"rcsPrdType\" : \"DESCRIPTION\",");	// RCS상품타입(서술 승인템플릿) rcsTemplateTable => 1
        			sb.append("\"messagebaseId\": \""+params.get("rcs1MessageFormId")+"\","); // cm.CM_RCS_MSGBASEFORM,           cm_console.CM_RCS_TMP_MSGBASE의 MESSAGEBASEFORM_ID값을 설정
        			sb.append("\"callback\": \""+params.get("callback")+"\",");
        			sb.append("\"footer\": \"\","); // 무료수신거부 번호, header의 값이 광고성일 때 footer 값을 포함하지 않고 발송하면 실패 처리
        			
        			sb.append("\"body\": [{ ");   
        			sb.append("	\"title\" : \""+params.get("rcs1Title")+"\", "); //
        			//String rcs1Content = ((String) params.get("rcs1Content")).replaceAll("\n", "CHR(13)CHR(10)");
        			sb.append("	\"description\" : \""+JSONObject.escape((String)params.get("rcs1Content"))+"\", "); // 메시지
        			sb.append("	\"mediaUrl\" : \"{}\", "); //
        			sb.append("	\"media\" : \"\" "); //

        			List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params.get("rcsDescriptionButtons");
        			sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
        			
        			sb.append("	}] ");
        			
        		}else if((int)params.get("rcsTemplateTable") == 2){
//RCS STYLE TYPE ====================================================================   
        			sb.append("\"rcsPrdType\" : \"STYLE\",");	// RCS상품타입(스타일 승인템플릿) rcsTemplateTable => 2
        			sb.append("\"messagebaseId\": \""+params.get("rcs2MessageFormId")+"\","); // cm.CM_RCS_MSGBASEFORM,  cm_console.CM_RCS_TMP_MSGBASE의 MESSAGEBASEFORM_ID값을 설정
        			sb.append("\"callback\": \""+params.get("callback")+"\",");
        			sb.append("\"footer\": \"\","); // 무료수신거부 번호, header의 값이 광고성일 때 footer 값을 포함하지 않고 발송하면 실패 처리
        			
        			sb.append("\"body\": [{ ");   
        			sb.append("	\"title\" : \""+params.get("rcs2Title")+"\", "); //
        			//arrChannelList. rcs2Content1 = ((String) params.get("rcs2Content1")).replaceAll("\n", "CHR(13)CHR(10)");
        			//String rcs2Content2 = ((String) params.get("rcs2Content2")).replaceAll("\n", "CHR(13)CHR(10)");
        			//String rcs2Content3 = ((String) params.get("rcs2Content3")).replaceAll("\n", "CHR(13)CHR(10)");
        			sb.append("	\"description\" : \""+JSONObject.escape((String)params.get("rcs2Content1"))+","+JSONObject.escape((String)params.get("rcs2Content2"))+","+JSONObject.escape((String)params.get("rcs2Content3"))+"\", "); // 메시지
        			sb.append("	\"mediaUrl\" : \"{}\", "); //
        			sb.append("	\"media\" : \"\" "); //

        			List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params.get("rcsStyleButtons");
        			sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
        			
        			sb.append("	}] ");
        			
        		}else if((int)params.get("rcsTemplateTable") == 3){
//RCS SMS TYPE ====================================================================
        			sb.append("\"rcsPrdType\" : \"SMS\",");	// RCS상품타입(SMS 템플릿) rcsTemplateTable => 3
        			sb.append("\"messagebaseId\": \"SS000000\","); // cm.CM_RCS_MSGBASE,            cm_console.CM_RCS_TMP_MSGBASE의 MESSAGEBASEFORM_ID값을 설정
        			sb.append("\"callback\": \""+params.get("callback")+"\",");
        			sb.append("\"footer\": \""+params.get("rcsSMSHowToDenyReceipt")+"\","); // 무료수신거부 번호, header의 값이 광고성일 때 footer 값을 포함하지 않고 발송하면 실패 처리
        			
        			sb.append("\"body\": [{ ");   
        			sb.append("	\"title\" : \""+params.get("")+"\", "); //
        			//String rcsSMSContent = ((String) params.get("rcsSMSContent")).replaceAll("\n", "CHR(13)CHR(10)");
        			sb.append("	\"description\" : \""+JSONObject.escape((String)params.get("rcsSMSContent"))+"\", "); // 메시지
        			sb.append("	\"mediaUrl\" : \"{}\", "); //
        			sb.append("	\"media\" : \"\", "); //

        			List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params.get("rcsSMSButtons");
        			sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
        			
        			sb.append("	}] ");
        			
        		}else if((int)params.get("rcsTemplateTable") == 4){
//RCS LMS TYPE ====================================================================
        			sb.append("\"rcsPrdType\" : \"LMS\",");	// RCS상품타입(LMS 템플릿) rcsTemplateTable => 4
        			sb.append("\"messagebaseId\": \"SL000000\","); // cm.CM_RCS_MSGBASE,            cm_console.CM_RCS_TMP_MSGBASE의 MESSAGEBASEFORM_ID값을 설정
        			sb.append("\"callback\": \""+params.get("callback")+"\",");
        			sb.append("\"footer\": \""+params.get("rcsLMSHowToDenyReceipt")+"\","); // 무료수신거부 번호, header의 값이 광고성일 때 footer 값을 포함하지 않고 발송하면 실패 처리
        			
        			sb.append("\"body\": [{ ");   
        			sb.append("	\"title\" : \""+params.get("rcsLMSTitle")+"\", "); //
        			//String rcsLMSContent = ((String) params.get("rcsLMSContent")).replaceAll("\n", "CHR(13)CHR(10)");
        			sb.append("	\"description\" : \""+JSONObject.escape((String)params.get("rcsLMSContent"))+"\", "); // 메시지
        			sb.append("	\"mediaUrl\" : \"{}\", "); //
        			sb.append("	\"media\" : \"\", "); //

        			List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params.get("rcsLMSButtons");
        			sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
        			
        			sb.append("	}] ");
        			
        		}else if((int)params.get("rcsTemplateTable") == 5){
//RCS SHORT TYPE ====================================================================     
        			sb.append("\"rcsPrdType\" : \"SHORT\",");	// RCS상품타입(세로형[SHORT] 템플릿) rcsTemplateTable => 5
        			sb.append("\"messagebaseId\": \"SMwThM00\","); // cm.CM_RCS_MSGBASE,            cm_console.CM_RCS_TMP_MSGBASE의 MESSAGEBASEFORM_ID값을 설정
        			sb.append("\"callback\": \""+params.get("callback")+"\",");
        			sb.append("\"footer\": \""+params.get("rcsShortHowToDenyReceipt")+"\","); // 무료수신거부 번호, header의 값이 광고성일 때 footer 값을 포함하지 않고 발송하면 실패 처리
        			
        			sb.append("\"body\": [{ ");   
        			sb.append("	\"title\" : \""+params.get("rcsShortTitle")+"\", "); //
        			//String rcsShortContent = ((String) params.get("rcsShortContent")).replaceAll("\n", "CHR(13)CHR(10)");
        			sb.append("	\"description\" : \""+JSONObject.escape((String)params.get("rcsShortContent"))+"\", "); // 메시지
        			
        			//System.out.println(">>>>service 003  RCS 001 SHORT 002  rcsShortImgInfoList : "+params.get("rcsShortImgInfoList"));
        			//image List
        	        List<Map<String, Object>> rcsShortImgInfoList = null;
        	        if(params.containsKey("rcsShortImgInfoList")) {
        	        	rcsShortImgInfoList = (List<Map<String, Object>>) params.get("rcsShortImgInfoList");
        	            Map<String, Object> imgInfo = rcsShortImgInfoList.get(0);//rcs에서 SHORT, TALL에는 이미지가 1개만 들어온다
        	            if(imgInfo.containsKey("fileId")) {
        	            	//sb.append("	\""+CommonUtils.getStrValue(imgInfo, "imgUrl")+"\" "); // 이미지
                			sb.append("	\"mediaUrl\" : \"{"+CommonUtils.getStrValue(imgInfo, "imgUrl")+"}\", "); //
                			sb.append("	\"media\" : \""+CommonUtils.getStrValue(imgInfo, "fileId")+"\", "); //

                			List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params.get("rcsShortButtons");
                			sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
        	            }
        	        }
        	        
        			sb.append("	}] ");
        			
        			

        		}else if((int)params.get("rcsTemplateTable") == 6){
//RCS TALL TYPE ====================================================================
        			sb.append("\"rcsPrdType\" : \"TALL\",");	// RCS상품타입(세로형[TALL]템플릿) 	rcsTemplateTable => 6
        			sb.append("\"messagebaseId\": \"SMwThT00\","); // cm.CM_RCS_MSGBASE,            cm_console.CM_RCS_TMP_MSGBASE의 MESSAGEBASEFORM_ID값을 설정
        			sb.append("\"callback\": \""+params.get("callback")+"\",");
        			sb.append("\"footer\": \""+params.get("rcsTallHowToDenyReceipt")+"\","); // 무료수신거부 번호, header의 값이 광고성일 때 footer 값을 포함하지 않고 발송하면 실패 처리
        			
        			sb.append("\"body\": [{ ");   
        			sb.append("	\"title\" : \""+params.get("rcsTallTitle")+"\", "); //
        			//String rcsTallContent = ((String) params.get("rcsTallContent")).replaceAll("\n", "CHR(13)CHR(10)");
        			sb.append("	\"description\" : \""+JSONObject.escape((String)params.get("rcsTallContent"))+"\", "); // 메시지
        			//image List
        	        List<Map<String, Object>> rcsTallImgInfoList = null;
        	        if(params.containsKey("rcsTallImgInfoList")) {
        	        	rcsTallImgInfoList = (List<Map<String, Object>>) params.get("rcsTallImgInfoList");
        	            Map<String, Object> imgInfo = rcsTallImgInfoList.get(0);//rcs에서 SHORT, TALL에는 이미지가 1개만 들어온다
        	            if(imgInfo.containsKey("fileId")) {
        	            	//sb.append("	\""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
                			sb.append("	\"mediaUrl\" : \"{"+CommonUtils.getStrValue(imgInfo, "imgUrl")+"}\", "); //
                			sb.append("	\"media\" : \""+CommonUtils.getStrValue(imgInfo, "fileId")+"\", "); //
                			
                			List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params.get("rcsTallButtons");
                			sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
        	            }
        	        }
        	        
        			sb.append("	}] ");
        			
        		}else if((int)params.get("rcsTemplateTable") == 9){
//RCS CSHORT TYPE ====================================================================        			
        			sb.append("\"rcsPrdType\" : \"CSHORT\",");	// RCS상품타입(캐러셀[SHORT]템플릿) 	rcsTemplateTable => 9
        			sb.append("\"messagebaseId\": \"msgbaseId0\","); // cm.CM_RCS_MSGBASE,            cm_console.CM_RCS_TMP_MSGBASE의 MESSAGEBASEFORM_ID값을 설정
        			sb.append("\"callback\": \""+params.get("callback")+"\",");
        			sb.append("\"footer\": \""+params.get("rcs9HowToDenyReceipt")+"\","); // 무료수신거부 번호, header의 값이 광고성일 때 footer 값을 포함하지 않고 발송하면 실패 처리
        			
        			//System.out.println("rcs9CardCount type : "+params.get("rcs9CardCount").getClass().getName());
        			//int rcs9CardCount = Integer.parseInt((String)params.get("rcs9CardCount"));
        			int rcs9CardCount = (int)params.get("rcs9CardCount");
        			
        			sb.append("\"body\": [ ");  
        			for(int k=0;k<rcs9CardCount;k++) {
        				if(k == 0) {
		        			sb.append(" { "); 
		        			sb.append("	\"title\" : \""+params.get("rcs90Title")+"\", "); //
		        			//String rcs90Content = ((String) params.get("rcs90Content")).replaceAll("\n", "CHR(13)CHR(10)");
		        			sb.append("	\"description\" : \""+JSONObject.escape((String)params.get("rcs90Content"))+"\", "); // 메시지
		        			//image List
		        	        List<Map<String, Object>> rcs90ImgInfoList = null;
		        	        if(params.containsKey("rcs90ImgInfoList")) {
		        	        	rcs90ImgInfoList = (List<Map<String, Object>>) params.get("rcs90ImgInfoList");
		        	            Map<String, Object> imgInfo = rcs90ImgInfoList.get(0);//rcs에서 SHORT, TALL, CSHORT, CTALL에는 이미지가 1개만 들어온다
		        	            if(imgInfo.containsKey("fileId")) {
		        	            	//sb.append("	\""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
		                			sb.append("	\"mediaUrl\" : \"{"+imgInfo.get("imgUrl")+"}\", "); //
		                			sb.append("	\"media\" : \""+imgInfo.get("fileId")+"\", "); //

		                			List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params.get("rcs90Buttons");
		                			sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
		        	            }
		        	        }
		        			sb.append("	} ");
		        			if(k < rcs9CardCount-1) sb.append(", ");
        				}
        				
        				if(k == 1) {
		        			sb.append(" { "); 
		        			sb.append("	\"title\" : \""+params.get("rcs91Title")+"\", "); //
		        			//String rcs91Content = ((String) params.get("rcs91Content")).replaceAll("\n", "CHR(13)CHR(10)");
		        			sb.append("	\"description\" : \""+JSONObject.escape((String)params.get("rcs91Content"))+"\", "); // 메시지
		        			//image List
		        	        List<Map<String, Object>> rcs91ImgInfoList = null;
		        	        if(params.containsKey("rcs91ImgInfoList")) {
		        	        	rcs91ImgInfoList = (List<Map<String, Object>>) params.get("rcs91ImgInfoList");
		        	            Map<String, Object> imgInfo = rcs91ImgInfoList.get(0);//rcs에서 SHORT, TALL에는 이미지가 1개만 들어온다
		        	            if(imgInfo.containsKey("fileId")) {
		        	            	//sb.append("	\""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
		                			sb.append("	\"mediaUrl\" : \"{"+imgInfo.get("imgUrl")+"}\", "); //
		                			sb.append("	\"media\" : \""+imgInfo.get("fileId")+"\", "); //
		                			
		                			List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params.get("rcs91Buttons");
		                			sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
		        	            }
		        	        }
		        			sb.append("	} ");
		        			if(k < rcs9CardCount-1) sb.append(", ");
        				}
        				
        				if(k == 2) {
		        			sb.append(" { "); 
		        			sb.append("	\"title\" : \""+params.get("rcs92Title")+"\", "); //
		        			//String rcs92Content = ((String) params.get("rcs92Content")).replaceAll("\n", "CHR(13)CHR(10)");
		        			sb.append("	\"description\" : \""+JSONObject.escape((String)params.get("rcs92Content"))+"\", "); // 메시지
		        			//image List
		        	        List<Map<String, Object>> rcs92ImgInfoList = null;
		        	        if(params.containsKey("rcs92ImgInfoList")) {
		        	        	rcs92ImgInfoList = (List<Map<String, Object>>) params.get("rcs92ImgInfoList");
		        	            Map<String, Object> imgInfo = rcs92ImgInfoList.get(0);//rcs에서 SHORT, TALL에는 이미지가 1개만 들어온다
		        	            if(imgInfo.containsKey("fileId")) {
		        	            	//sb.append("	\""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
		                			sb.append("	\"mediaUrl\" : \"{"+imgInfo.get("imgUrl")+"}\", "); //
		                			sb.append("	\"media\" : \""+imgInfo.get("fileId")+"\", "); //

		                			List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params.get("rcs92Buttons");
		                			sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
		        	            }
		        	        }
		        			sb.append("	} ");
		        			if(k < rcs9CardCount-1) sb.append(", ");
        				}
        				
        				if(k == 3) {
		        			sb.append(" { "); 
		        			sb.append("	\"title\" : \""+params.get("rcs93Title")+"\", "); //
		        			//String rcs93Content = ((String) params.get("rcs93Content")).replaceAll("\n", "CHR(13)CHR(10)");
		        			sb.append("	\"description\" : \""+JSONObject.escape((String)params.get("rcs93Content"))+"\", "); // 메시지
		        			//image List
		        	        List<Map<String, Object>> rcs93ImgInfoList = null;
		        	        if(params.containsKey("rcs93ImgInfoList")) {
		        	        	rcs93ImgInfoList = (List<Map<String, Object>>) params.get("rcs93ImgInfoList");
		        	            Map<String, Object> imgInfo = rcs93ImgInfoList.get(0);//rcs에서 SHORT, TALL에는 이미지가 1개만 들어온다
		        	            if(imgInfo.containsKey("fileId")) {
		        	            	//sb.append("	\""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
		                			sb.append("	\"mediaUrl\" : \"{"+imgInfo.get("imgUrl")+"}\", "); //
		                			sb.append("	\"media\" : \""+imgInfo.get("fileId")+"\", "); //

		                			List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params.get("rcs93Buttons");
		                			sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
		        	            }
		        	        }
		        			sb.append("	} ");
		        			if(k < rcs9CardCount-1) sb.append(", ");
        				}
        				
        				if(k == 4) {
		        			sb.append(" { "); 
		        			sb.append("	\"title\" : \""+params.get("rcs94Title")+"\", "); //
		        			//String rcs94Content = ((String) params.get("rcs94Content")).replaceAll("\n", "CHR(13)CHR(10)");
		        			sb.append("	\"description\" : \""+JSONObject.escape((String)params.get("rcs94Content"))+"\", "); // 메시지
		        			//image List
		        	        List<Map<String, Object>> rcs94ImgInfoList = null;
		        	        if(params.containsKey("rcs94ImgInfoList")) {
		        	        	rcs94ImgInfoList = (List<Map<String, Object>>) params.get("rcs94ImgInfoList");
		        	            Map<String, Object> imgInfo = rcs94ImgInfoList.get(0);//rcs에서 SHORT, TALL에는 이미지가 1개만 들어온다
		        	            if(imgInfo.containsKey("fileId")) {
		        	            	//sb.append("	\""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
		                			sb.append("	\"mediaUrl\" : \"{"+imgInfo.get("imgUrl")+"}\", "); //
		                			sb.append("	\"media\" : \""+imgInfo.get("fileId")+"\", "); //

		                			List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params.get("rcs94Buttons");
		                			sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
		        	            }
		        	        }
		        			sb.append("	} ");
		        			if(k < rcs9CardCount-1) sb.append(", ");
        				}
        				
        				if(k == 5) {
		        			sb.append(" { "); 
		        			sb.append("	\"title\" : \""+params.get("rcs95Title")+"\", "); //
		        			//String rcs95Content = ((String) params.get("rcs95Content")).replaceAll("\n", "CHR(13)CHR(10)");
		        			sb.append("	\"description\" : \""+JSONObject.escape((String)params.get("rcs95Content"))+"\", "); // 메시지
		        			//image List
		        	        List<Map<String, Object>> rcs95ImgInfoList = null;
		        	        if(params.containsKey("rcs95ImgInfoList")) {
		        	        	rcs95ImgInfoList = (List<Map<String, Object>>) params.get("rcs95ImgInfoList");
		        	            Map<String, Object> imgInfo = rcs95ImgInfoList.get(0);//rcs에서 SHORT, TALL에는 이미지가 1개만 들어온다
		        	            if(imgInfo.containsKey("fileId")) {
		        	            	//sb.append("	\""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
		                			sb.append("	\"mediaUrl\" : \"{"+imgInfo.get("imgUrl")+"}\", "); //
		                			sb.append("	\"media\" : \""+imgInfo.get("fileId")+"\", "); //

		                			List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params.get("rcs95Buttons");
		                			sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
		        	            }
		        	        }
		        			sb.append("	} ");
		        			//if(k < rcs9CardCount-1) sb.append(", ");
        				}
        				
        				
        			}
        			sb.append("	] ");
        			
        		}else if((int)params.get("rcsTemplateTable") == 10){
//RCS CTALL TYPE ====================================================================
        			sb.append("\"rcsPrdType\" : \"CTALL\",");	// RCS상품타입(캐러셀[TALL]템플릿) 	rcsTemplateTable => 10
        			
        			//System.out.println("rcs10CardCount type : "+params.get("rcs10CardCount").getClass().getName());
        			//int rcs10CardCount = Integer.parseInt((String)params.get("rcs10CardCount"));
        			int rcs10CardCount = (int)params.get("rcs10CardCount");
        			
        			String msgbaseId = "";
        			if(rcs10CardCount == 3) {
        				msgbaseId = "CMwMhM0300";	//슬라이드형(Medium,3장)
        			}else if(rcs10CardCount == 4) {
        				msgbaseId = "CMwMhM0400";	//슬라이드형(Medium,4장)
        			}else if(rcs10CardCount == 5) {
        				msgbaseId = "CMwMhM0500";	//슬라이드형(Medium,5장)
        			}else if(rcs10CardCount == 6) {
        				msgbaseId = "CMwMhM0600";	//슬라이드형(Medium,6장)
        			}
        			
        		    sb.append("\"messagebaseId\": \""+msgbaseId+"\","); // cm.CM_RCS_MSGBASE,            cm_console.CM_RCS_TMP_MSGBASE의 MESSAGEBASEFORM_ID값을 설정
        		    
        		    sb.append("\"callback\": \""+params.get("callback")+"\",");
        			sb.append("\"footer\": \""+params.get("rcs10HowToDenyReceipt")+"\","); // 무료수신거부 번호, header의 값이 광고성일 때 footer 값을 포함하지 않고 발송하면 실패 처리
        			
        			sb.append("\"body\": [ ");  
        			for(int j=0; j<rcs10CardCount; j++) {
        				if(j == 0) {
        					sb.append(" { ");  
		        			sb.append("	\"title\" : \""+params.get("rcs100Title")+"\", "); //
		        			//String rcs100Content = ((String) params.get("rcs100Content")).replaceAll("\n", "CHR(13)CHR(10)");
		        			sb.append("	\"description\" : \""+JSONObject.escape((String)params.get("rcs100Content"))+"\", "); // 메시지
		        			//image List
		        	        List<Map<String, Object>> rcs100ImgInfoList = null;
		        	        if(params.containsKey("rcs100ImgInfoList")) {
		        	        	rcs100ImgInfoList = (List<Map<String, Object>>) params.get("rcs100ImgInfoList");
		        	            Map<String, Object> imgInfo = rcs100ImgInfoList.get(0);//rcs에서 SHORT, TALL에는 이미지가 1개만 들어온다
		        	            if(imgInfo.containsKey("fileId")) {
		        	            	//sb.append("	\""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
		                			sb.append("	\"mediaUrl\" : \"{"+ imgInfo.get("imgUrl")+"}\", "); //
		                			sb.append("	\"media\" : \""+imgInfo.get("fileId")+"\", "); //

		                			List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params.get("rcs100Buttons");
		                			sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
		        	            }
		        	        }
		        			sb.append("	} ");
		        			if(j < rcs10CardCount-1) sb.append(", ");
        				}
        				
        				if(j == 1) {
        					sb.append(" { ");  
		        			sb.append("	\"title\" : \""+params.get("rcs101Title")+"\", "); //
		        			//String rcs101Content = ((String) params.get("rcs101Content")).replaceAll("\n", "CHR(13)CHR(10)");
		        			sb.append("	\"description\" : \""+JSONObject.escape((String)params.get("rcs101Content"))+"\", "); // 메시지
		        			//image List
		        	        List<Map<String, Object>> rcs101ImgInfoList = null;
		        	        if(params.containsKey("rcs101ImgInfoList")) {
		        	        	rcs101ImgInfoList = (List<Map<String, Object>>) params.get("rcs101ImgInfoList");
		        	            Map<String, Object> imgInfo = rcs101ImgInfoList.get(0);//rcs에서 SHORT, TALL에는 이미지가 1개만 들어온다
		        	            if(imgInfo.containsKey("fileId")) {
		        	            	//sb.append("	\""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
		                			sb.append("	\"mediaUrl\" : \"{"+imgInfo.get("imgUrl")+"}\", "); //
		                			sb.append("	\"media\" : \""+imgInfo.get("fileId")+"\", "); //
		                			
		                			List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params.get("rcs101Buttons");
		                			sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
		        	            }
		        	        }
		        			sb.append("	} ");
		        			if(j < rcs10CardCount-1) sb.append(", ");
        				}
        				
        				if(j == 2) {
        					sb.append(" { ");  
		        			sb.append("	\"title\" : \""+params.get("rcs102Title")+"\", "); //
		        			//String rcs102Content = ((String) params.get("rcs102Content")).replaceAll("\n", "CHR(13)CHR(10)");
		        			sb.append("	\"description\" : \""+JSONObject.escape((String)params.get("rcs102Content"))+"\", "); // 메시지
		        			//image List
		        	        List<Map<String, Object>> rcs102ImgInfoList = null;
		        	        if(params.containsKey("rcs102ImgInfoList")) {
		        	        	rcs102ImgInfoList = (List<Map<String, Object>>) params.get("rcs102ImgInfoList");
		        	            Map<String, Object> imgInfo = rcs102ImgInfoList.get(0);//rcs에서 SHORT, TALL에는 이미지가 1개만 들어온다
		        	            if(imgInfo.containsKey("fileId")) {
		        	            	//sb.append("	\""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
		                			sb.append("	\"mediaUrl\" : \"{"+imgInfo.get("imgUrl")+"}\", "); //
		                			sb.append("	\"media\" : \""+imgInfo.get("fileId")+"\", "); //

		                			List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params.get("rcs102Buttons");
		                			sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
		        	            }
		        	        }
		        			sb.append("	} ");
		        			if(j < rcs10CardCount-1) sb.append(", ");
        				}
        				
        				if(j == 3) {
        					sb.append(" { ");  
		        			sb.append("	\"title\" : \""+params.get("rcs103Title")+"\", "); //
		        			//String rcs103Content = ((String) params.get("rcs103Content")).replaceAll("\n", "CHR(13)CHR(10)");
		        			sb.append("	\"description\" : \""+JSONObject.escape((String)params.get("rcs103Content"))+"\", "); // 메시지
		        			//image List
		        	        List<Map<String, Object>> rcs103ImgInfoList = null;
		        	        if(params.containsKey("rcs103ImgInfoList")) {
		        	        	rcs103ImgInfoList = (List<Map<String, Object>>) params.get("rcs103ImgInfoList");
		        	            Map<String, Object> imgInfo = rcs103ImgInfoList.get(0);//rcs에서 SHORT, TALL에는 이미지가 1개만 들어온다
		        	            if(imgInfo.containsKey("fileId")) {
		        	            	//sb.append("	\""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
		                			sb.append("	\"mediaUrl\" : \"{"+imgInfo.get("imgUrl")+"}\", "); //
		                			sb.append("	\"media\" : \""+imgInfo.get("fileId")+"\", "); //

		                			List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params.get("rcs103Buttons");
		                			sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
		        	            }
		        	        }
		        			sb.append("	} ");
		        			if(j < rcs10CardCount-1) sb.append(", ");
        				}
        				
        				if(j == 4) {
        					sb.append(" { ");  
		        			sb.append("	\"title\" : \""+params.get("rcs104Title")+"\", "); //
		        			//String rcs104Content = ((String) params.get("rcs104Content")).replaceAll("\n", "CHR(13)CHR(10)");
		        			sb.append("	\"description\" : \""+JSONObject.escape((String)params.get("rcs104Content"))+"\", "); // 메시지
		        			//image List
		        	        List<Map<String, Object>> rcs104ImgInfoList = null;
		        	        if(params.containsKey("rcs104ImgInfoList")) {
		        	        	rcs104ImgInfoList = (List<Map<String, Object>>) params.get("rcs104ImgInfoList");
		        	            Map<String, Object> imgInfo = rcs104ImgInfoList.get(0);//rcs에서 SHORT, TALL에는 이미지가 1개만 들어온다
		        	            if(imgInfo.containsKey("fileId")) {
		        	            	//sb.append("	\""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
		                			sb.append("	\"mediaUrl\" : \"{"+imgInfo.get("imgUrl")+"}\", "); //
		                			sb.append("	\"media\" : \""+imgInfo.get("fileId")+"\", "); //

		                			List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params.get("rcs104Buttons");
		                			sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
		        	            }
		        	        }
		        			sb.append("	} ");
		        			if(j < rcs10CardCount-1) sb.append(", ");
        				}
        				
        				if(j == 5) {
        					sb.append(" { ");  
		        			sb.append("	\"title\" : \""+params.get("rcs105Title")+"\", "); //
		        			//String rcs105Content = ((String) params.get("rcs105Content")).replaceAll("\n", "CHR(13)CHR(10)");
		        			sb.append("	\"description\" : \""+JSONObject.escape((String)params.get("rcs105Content"))+"\", "); // 메시지
		        			//image List
		        	        List<Map<String, Object>> rcs105ImgInfoList = null;
		        	        if(params.containsKey("rcs105ImgInfoList")) {
		        	        	rcs105ImgInfoList = (List<Map<String, Object>>) params.get("rcs105ImgInfoList");
		        	            Map<String, Object> imgInfo = rcs105ImgInfoList.get(0);//rcs에서 SHORT, TALL에는 이미지가 1개만 들어온다
		        	            if(imgInfo.containsKey("fileId")) {
		        	            	//sb.append("	\""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
		                			sb.append("	\"mediaUrl\" : \"{"+imgInfo.get("imgUrl")+"}\", "); //
		                			sb.append("	\"media\" : \""+imgInfo.get("fileId")+"\", "); //

		                			List<Map<String, Object>> buttonInfoList = (List<Map<String, Object>>) params.get("rcs105Buttons");
		                			sb.append(buttonAddStr(params, checkChannelArr[i], buttonInfoList));
		        	            }
		        	        }
		        			sb.append("	} ");
        				}
        			}
        			sb.append("	] ");
        		}        			
        		//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        		//System.out.println(sb.toString());
        		//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

    		    
        	}else if(checkChannelArr[i].equalsIgnoreCase("FRIENDTALK")) {
        		//System.out.println(">>>>service 003  kakao 001 : "+params.get("kakaoTemplateTable"));
//KAKAO ====================================================================  
        		String buttonType = null;
    			String buttonName = null;
    			String buttonLink = null;
    			String buttonLink1 = null;
    			String startDate = null;
    			String endDate = null;
        		List<Map<String, Object>> buttonInfoList = null;
        		

        			//System.out.println(">>>>service 003  RCS 002 FRIENDTALK");
        			if(params.containsKey("friendTalkButtons")) {
        	            buttonInfoList = (List<Map<String, Object>>) params.get("friendTalkButtons");
        			}
        			
        			sb.append("\"chTypeList\" : \""	+chTypeList+"\",");
        			sb.append("\"chType\" : \"FRIENDTALK\",");
        			
        			sb.append("\"friendtalk\" : { ");
        			sb.append("\"senderKeyType\" : \""+params.get("friendTalkSenderKeyType")+"\",");
        			sb.append("\"senderKey\" : \""+params.get("friendTalkSenderKey")+"\",");
        			sb.append("\"wideImageYn\" : \"N\",");
        			//String friendTalkContent = ((String) params.get("friendTalkContent")).replaceAll("\n", "CHR(13)CHR(10)");
        			sb.append("\"msg\" : \""+JSONObject.escape((String)params.get("friendTalkContent"))+"\",");
        			//System.out.println(">>>>service 003  RCS 003 friendtalk : "+sb.toString());
        	        Map<String, Object> imgInfo = null;
        	        if(params.containsKey("friendTalkImgInfo")) {
        	        	imgInfo = (Map<String, Object>) params.get("friendTalkImgInfo");
        	        	if(imgInfo.containsKey("fileId")) {
        	        		sb.append("\"fileId\" : \""+CommonUtils.getStrValue(imgInfo, "fileId")+"\",");
        	        	}else {
        	        		sb.append("\"fileId\" : \"\",");
        	        	}
        	            sb.append("	\"image\" : { "); // 이미지
    	                if(imgInfo.containsKey("fileId")) {
    	                    	sb.append("	\"imageUrl\" : \""+CommonUtils.getStrValue(imgInfo, "imgUrl")+"\", "); // 이미지
    	                    	sb.append("	\"imageLink\" : \""+params.get("friendTalkImageLink")+"\" "); // 이미지
    	                }
        	            sb.append("	}, "); // 이미지
        	        }
        	        //System.out.println(">>>>service 003  RCS 004 friendtalk : "+sb.toString());
        			sb.append("\"buttons\": [{ ");
        			sb.append("\"suggestions\": [ ");
        			
        			int fTalkIdx = 1;
        			for(Map<String, Object> buttonInfo : buttonInfoList) {
        				
    	    				buttonType = CommonUtils.getStrValue(buttonInfo, "buttonType");
    	                    buttonName = CommonUtils.getStrValue(buttonInfo, "buttonName");
    	                    buttonLink = CommonUtils.getStrValue(buttonInfo, "buttonLink");
    	                    
    	                    buttonLink1 = CommonUtils.getStrValue(buttonInfo, "buttonLink1");
    	                    startDate = CommonUtils.getStrValue(buttonInfo, "startDate");
    	                    endDate = CommonUtils.getStrValue(buttonInfo, "endDate");
    	                    
    	                    //System.out.println(">>>>service 003  button 003 : buttonType : "+buttonType);
    	        			//System.out.println(">>>>service 003  button 004 : buttonName : "+buttonName);
    	        			//System.out.println(">>>>service 003  button 005 : buttonLink : "+buttonLink);
    	        			//System.out.println(">>>>service 003  button 006 : buttonLink1 : "+buttonLink1);
    	        			//System.out.println(">>>>service 003  button 007 : startDate : "+startDate);
    	        			//System.out.println(">>>>service 003  button 008 : endDate : "+endDate);
        			
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
    		    		    
    		    		    if(fTalkIdx++ < buttonInfoList.size()) {
    		    		    	sb.append(", ");
    		    		    }
    		    		    //fTalkIdx = fTalkIdx + 1;
    		    		         			
    		        }//end for(Map<String, Object> buttonInfo : buttonInfoList) {
        			//System.out.println(">>>>service 005  RCS 002 friendtalk : "+sb.toString());
        		    sb.append("	]         ");	
        		    sb.append("	}]         ");	
        		    sb.append("} ");

        		

        	}else if(checkChannelArr[i].equalsIgnoreCase("ALIMTALK")) {
        		//System.out.println(">>>>service 003  kakao 001 : "+params.get("kakaoTemplateTable"));
//KAKAO ====================================================================  
        		String buttonType = null;
    			String buttonName = null;
    			String buttonLink = null;
    			String buttonLink1 = null;
    			String startDate = null;
    			String endDate = null;
        		List<Map<String, Object>> buttonInfoList = null;

        			//System.out.println(">>>>service 003  RCS 002 ALIMTALK");
        			sb.append("\"chTypeList\" : \""	+chTypeList+"\",");
        			sb.append("\"chType\" : \"ALIMTALK\",");
        			
        			sb.append("\"alimtalkInfo\" : { ");
        			sb.append("\"senderKey\" : \""+params.get("alimTalkSendKey")+"\",");
        			sb.append("\"tmpltCode\" : \""+params.get("alimTalkTmpltCode")+"\",");
        			sb.append("} ");
  		    
    		    
        	}else if(checkChannelArr[i].equalsIgnoreCase("SMSMMS")) {
//SMSMMS ====================================================================    
        		//System.out.println("=============================================");
        		//System.out.println("smsSendType : "+params.get("smsSendType"));
        		//System.out.println("=============================================");
        		if(params.get("smsSendType").equals("S")){//SMS
        			sb.append("\"chTypeList\" : \""	+chTypeList+"\",");
        			sb.append("\"chType\" : \"SMS\",");//발송채널
        			
        			sb.append("\"smsInfo\" : {");
        			sb.append("\"callback\" : \""+params.get("callback")+"\",");
        			//String smsContent = ((String) params.get("smsContent")).replaceAll("\n", "CHR(13)CHR(10)");
        			sb.append("\"msg\" : \""+JSONObject.escape((String)params.get("smsContent"))+"\" ");
        			sb.append("}");
        			
        		}else if(params.get("smsSendType").equals("M")){//MMS
        			sb.append("\"chTypeList\" : \""	+chTypeList+"\",");
        			sb.append("\"chType\" : \"MMS\",");//발송채널
        			
        			sb.append("\"mmsInfo\" : {");
        			sb.append("\"callback\" : \""+params.get("callback")+"\",");
        			sb.append("\"title\" : \""+params.get("smsTitle")+"\",");
        			//String smsContent = ((String) params.get("smsContent")).replaceAll("\n", "CHR(13)CHR(10)");
        			sb.append("\"msg\" : \""+JSONObject.escape((String)params.get("smsContent"))+"\",");
        			
        			List<Map<String, Object>> imgInfoList = null;
        	        if(params.containsKey("smsImgInfoList")) {
        	            imgInfoList = (List<Map<String, Object>>) params.get("smsImgInfoList");
        	            int maxSize = imgInfoList.size();
        	            int idx = 0;
        	            sb.append("	\"fileIdLst\" : [ "); // 이미지
        	            for(Map<String, Object> imgInfo : imgInfoList) {
        	            	
        	                if(imgInfo.containsKey("fileId")) {
        	                    idx = idx +1;
        	                    if( idx < maxSize) {
        	                    	sb.append("	\""+CommonUtils.getStrValue(imgInfo, "fileId")+"\", "); // 이미지
        	                    }else {
        	                    	sb.append("	\""+CommonUtils.getStrValue(imgInfo, "fileId")+"\" "); // 이미지
        	                    }
        	                }
        	            } 
        	            sb.append("	] "); // 이미지
        	        }
        	        
        			sb.append("}");

        		}
        	}
        	sb.append("}");
        	if(i<checkChannelArr.length-1) { 
        		sb.append(","); 
        	}
        }
        sb.append("]");
        //System.out.println("=============================================");
        //System.out.println(">>>>service 005 json : "+sb.toString());
        //System.out.println("=============================================");
        
        Map<String, Object> sParams = new HashMap<String, Object>(params);
        
        //JSONParser parser = new JSONParser();
        //Object obj = parser.parse( sb.toString() );
        //JSONObject jsonObj = (JSONObject) obj;
        
        sParams.put("tmpltInfo", sb.toString());
        //sParams.put("tmpltInfo", jsonObj);
        
         
        //if (params.containsKey("tmpltCode") && StringUtils.isNotBlank(CommonUtils.getString(params.get("tmpltCode")))) {
        if (sParams.containsKey("tmpltCode") && StringUtils.isBlank(CommonUtils.getString(sParams.get("tmpltCode")))) {
        	// 템플릿ID 취득
            String tmpltCode = CommonUtils.getCommonId(Const.TMPLT_PREFIX, 5);
            sParams.put("tmpltCode", tmpltCode);
        }
        
        if (sParams.get("otherProjectUseYn").equals("Y")) {//타프로젝트와 같이 사용하면 projecdtId를 ALL로 잡는다.
        	sParams.put("projectId", "ALL");
        }
        resultCnt = generalDao.insertGernal("smartTemplate.insertSmartTemplate", sParams);

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
     * 스마트 템플릿 삭제 처리
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public RestResult<Object> deleteSmartTemplate(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        int resultCnt = generalDao.deleteGernal("smartTemplate.deleteSmartTemplate", params);
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
                
                //System.out.println(">>>>service 003  button 003 : buttonType : "+buttonType);
    			//System.out.println(">>>>service 003  button 004 : buttonName : "+buttonName);
    			//System.out.println(">>>>service 003  button 005 : buttonLink : "+buttonLink);
    			//System.out.println(">>>>service 003  button 006 : buttonLink1 : "+buttonLink1);
    			//System.out.println(">>>>service 003  button 007 : startDate : "+startDate);
    			//System.out.println(">>>>service 003  button 008 : endDate : "+endDate);
		
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

    /**
     * 브랜드 리스트 조회
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
     * @param Param
     * @return
     * @throws Exception
     */
    public String selectMessageBaseId(Map<String, Object> params) throws Exception {
    	 return (String) generalDao.selectGernalObject("smartTemplate.selectMessageBaseId", params);
    }
}
