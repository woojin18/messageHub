package kr.co.uplus.cm.integratedTemplate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
public class IntegratedTemplateService {

	@Autowired
	private GeneralDao generalDao;

	/**
     * 통합 템플릿 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectIntegratedTemplateList(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
            rtn.setPageProps(params);
            if(rtn.getPageInfo() != null) {
                //카운트 쿼리 실행
                int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_PUSH_TMPLT_LIST_CNT, params);
                rtn.getPageInfo().put("totCnt", listCnt);
            }
        }

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_PUSH_TMPLT_LIST, params);
        rtn.setData(rtnList);

        return rtn;
    }

    
	/**
     * 통합 템플릿 정보 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectIntegratedTemplateInfo(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_PUSH_TMPLT_LIST, params);
        
        rtn.setData(rtnList);

        return rtn;
    }
    
    
    /**
     * 통합 템플릿 저장 처리
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public RestResult<Object> insertIntegratedTemplate(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        int resultCnt = 0;
        //java.util.ArrayList cannot be cast to [Ljava.lang.String;
        System.out.println(">>>>service 001 : "+params.get("checkedChannel"));
        ArrayList<String> arrChannelList = (ArrayList<String>) params.get("checkedChannel");
        String[] checkChannelArr = new String[arrChannelList.size()];
        int size = 0;
        for(String channel:arrChannelList) {
        	checkChannelArr[size++] = channel;
        }
        
        System.out.println(">>>>service 002 checkChannelArr.length ["+checkChannelArr.length+"]");
//        JSONArray checkChannel = new JSONArray();
//        
//        for(int i=0; i<checkChannelArr.length;i++) {
//        	JSONObject checkChannelJson = new JSONObject();
//        	checkChannelJson.put("chType", checkChannelArr[i]);
//        	checkChannel.add(checkChannelJson);
//        }
        
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for(int i=0; i<checkChannelArr.length;i++) {
        	sb.append("{");
        	
        	if(checkChannelArr[i].equalsIgnoreCase("Push")) {
        		sb.append("\"chType\" : \"Push\",");//발송채널
        		sb.append("\"sendType\" : \""	+""+"\","); // 발송타입(ALL, FCM, APNS)
        		sb.append("\"title\" : \""		+""+"\","); // 제목
        		sb.append("\"msg\" : \""		+""+"\","); // 메시지
        		sb.append("\"rcvblcInput\" : \""+""+"\","); // 수신거부방법
        		sb.append("\"fileId\" : \""		+""+"\","); // 이미지 파일ID
        		sb.append("\"appId\" : \""		+""+"\""); // APP ID
        	}else if(checkChannelArr[i].equalsIgnoreCase("RCS")) {
        		sb.append("\"chType\" : \"rcs\"");//발송채널
        		//sb.append("\"rcsPrdType\" : \"free\"");	// RCS상품타입(프리 템플릿) rcsRadioIndex => 0
        		//sb.append("\"rcsPrdType\" : \"description\"");	// RCS상품타입(서술 승인템플릿) rcsRadioIndex => 1
        		//sb.append("\"rcsPrdType\" : \"style\"");	// RCS상품타입(스타일 승인템플릿) rcsRadioIndex => 2
        		//sb.append("\"rcsPrdType\" : \"sms\"");	// RCS상품타입(SMS 템플릿) rcsRadioIndex => 3
        		//sb.append("\"rcsPrdType\" : \"lms\"");	// RCS상품타입(LMS 템플릿) rcsRadioIndex => 4
        		//sb.append("\"rcsPrdType\" : \"hShort\"");	// RCS상품타입(세로형[short]템플릿) 	rcsRadioIndex => 5
        		//sb.append("\"rcsPrdType\" : \"hTall\"");	// RCS상품타입(세로형[tall]템플릿) 	rcsRadioIndex => 6
        		//sb.append("\"rcsPrdType\" : \"wLeft\"");	// RCS상품타입(가로형[left]템플릿) 	rcsRadioIndex => 7
        		//sb.append("\"rcsPrdType\" : \"wRight\"");	// RCS상품타입(가로형[right]템플릿) 	rcsRadioIndex => 8
        		//sb.append("\"rcsPrdType\" : \"???\"");	// RCS상품타입(캐러셀[short]템플릿) 	rcsRadioIndex => 9
        		//sb.append("\"rcsPrdType\" : \"???\"");	// RCS상품타입(캐러셀[tall]템플릿) 	rcsRadioIndex => 10
        	}else if(checkChannelArr[i].equalsIgnoreCase("Kakao")) {
        		
        		sb.append("\"chType\" : \"Kakao\"");//발송채널
        		//sb.append("\"kakaoPrdType\" : \"alimtalk\""); 	//카카오톡 상품타입(알림톡) kakaoRadioIndex => 0
        		//sb.append("\"kakaoPrdType\" : \"friendtalk\",");	//카카오톡 상품타입(친구톡) kakaoRadioIndex => 1
        	}else if(checkChannelArr[i].equalsIgnoreCase("SmsMms")) {
        		sb.append("\"chType\" : \"SmsMms\"");//발송채널
        		//sb.append("\"msPrdType\" : \"sms\""); 	//SmsMms 상품타입(sms) kakaoRadioIndex => 0
        		//sb.append("\"msPrdType\" : \"lms\","); 	//SmsMms 상품타입(lms) kakaoRadioIndex => 1
        		//sb.append("\"msPrdType\" : \"mms\","); 	//SmsMms 상품타입(mms) kakaoRadioIndex => 2
        	}
        	sb.append("}");
        	if(i<checkChannelArr.length-1) { 
        		sb.append(","); 
        	}
        }
        sb.append("]");
        
        //System.out.println(">>>>json : "+sb.toString());
        
        params.put("tmpltInfo", sb.toString());
        
        //if (params.containsKey("tmpltCode") && StringUtils.isNotBlank(CommonUtils.getString(params.get("tmpltCode")))) {
        if (params.containsKey("tmpltCode") && StringUtils.isBlank(CommonUtils.getString(params.get("tmpltCode")))) {
        	// 템플릿ID 취득
            String tmpltCode = CommonUtils.getCommonId(Const.TMPLT_PREFIX, 5);
            params.put("tmpltCode", tmpltCode);
        }
        resultCnt = generalDao.insertGernal("integratedTemplate.insertIntegratedTemplate", params);

        if (resultCnt <= 0) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
        } else {
            rtn.setSuccess(true);
            rtn.setData(params);
        }

        return rtn;
    }

    /**
     * 통합 템플릿 삭제 처리
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public RestResult<Object> deleteIntegratedTemplate(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        int resultCnt = generalDao.deleteGernal(DB.QRY_DELETE_PUSH_TMPLT, params);
        if (resultCnt <= 0) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
        } else {
            rtn.setSuccess(true);
            rtn.setData(params);
        }

        return rtn;
    }


}
