package kr.co.uplus.cm.template.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.config.ApiConfig;
import kr.co.uplus.cm.sendMessage.dto.AlimTalkButtonsInfo;
import kr.co.uplus.cm.sendMessage.dto.AlimTalkTmpltRequestData;
import kr.co.uplus.cm.sendMessage.dto.ServiceInfo;
import kr.co.uplus.cm.utils.ApiInterface;
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
public class TemplateService {

    @Autowired
    private GeneralDao generalDao;

    @Autowired
    ApiInterface apiInterface;

    @Autowired
    private CommonService commonService;

    /**
     * 푸시 템플릿 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectPushTemplateList(Map<String, Object> params) throws Exception {

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
     * 푸시 템플릿 저장 처리
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public RestResult<Object> savePushTemplate(Map<String, Object> params) throws Exception {
        Map<String, Object> sParams = new HashMap<String, Object>(params);
        RestResult<Object> rtn = new RestResult<Object>();
        int resultCnt = 0;

        String otherProjectUseYn = CommonUtils.getStrValue(sParams, "otherProjectUseYn");

        if(!StringUtils.equalsIgnoreCase(otherProjectUseYn, Const.COMM_NO)) {
            sParams.put("projectId", Const.OTHER_PROJECT_USE_ID);
        }

        // update
        if (sParams.containsKey("tmpltId") && StringUtils.isNotBlank(CommonUtils.getString(sParams.get("tmpltId")))) {
            resultCnt = generalDao.updateGernal(DB.QRY_UPDATE_PUSH_TMPLT, sParams);
        // insert
        } else {
        	// 템플릿ID 취득
            String tmpltId = CommonUtils.getCommonId(Const.TMPLT_PREFIX, 5);
            sParams.put("tmpltId", tmpltId);
            resultCnt = generalDao.insertGernal(DB.QRY_INSERT_PUSH_TMPLT, sParams);
        }

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
     * 푸시 템플릿 삭제 처리
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public RestResult<Object> deletePushTemplate(Map<String, Object> params) throws Exception {
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

    /**
     * SMS 템플릿 저장 처리
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public RestResult<Object> saveSmsTemplate(Map<String, Object> params) throws Exception {
        Map<String, Object> sParams = new HashMap<String, Object>(params);
        RestResult<Object> rtn = new RestResult<Object>();
        int resultCnt = 0;
        String otherProjectUseYn = CommonUtils.getStrValue(sParams, "otherProjectUseYn");

        if(!StringUtils.equalsIgnoreCase(otherProjectUseYn, Const.COMM_NO)) {
            sParams.put("projectId", Const.OTHER_PROJECT_USE_ID);
        }

        // update
        if (sParams.containsKey("tmpltId") && StringUtils.isNotBlank(CommonUtils.getString(sParams.get("tmpltId")))) {
            resultCnt = generalDao.updateGernal(DB.QRY_UPDATE_SMS_TMPLT, sParams);
        // insert
        } else {
            // 템플릿ID 취득
            String tmpltId = CommonUtils.getCommonId(Const.TMPLT_PREFIX, 5);
            sParams.put("tmpltId", tmpltId);
            resultCnt = generalDao.insertGernal(DB.QRY_INSERT_SMS_TMPLT, sParams);
        }

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
     * SMS/MMS 템플릿 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectSmsTemplateList(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
            rtn.setPageProps(params);
            if(rtn.getPageInfo() != null) {
                //카운트 쿼리 실행
                int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_SMS_TMPLT_LIST_CNT, params);
                rtn.getPageInfo().put("totCnt", listCnt);
            }
        }

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_SMS_TMPLT_LIST, params);
        rtn.setData(rtnList);

        return rtn;
    }

    /**
     * SMS 템플릿 삭제처리
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public RestResult<Object> deleteSmsTmplt(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        int resultCnt = generalDao.deleteGernal(DB.QRY_DELETE_SMS_TMPLT, params);
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
     * 친구톡 템플릿 저장
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public RestResult<Object> saveFrndTalkTmplt(Map<String, Object> params) throws Exception {
        Map<String, Object> sParams = new HashMap<String, Object>(params);
        RestResult<Object> rtn = new RestResult<Object>();
        int resultCnt = 0;
        String otherProjectUseYn = CommonUtils.getStrValue(sParams, "otherProjectUseYn");

        if(!StringUtils.equalsIgnoreCase(otherProjectUseYn, Const.COMM_NO)) {
            sParams.put("projectId", Const.OTHER_PROJECT_USE_ID);
        }

        // update
        if (sParams.containsKey("tmpltId") && StringUtils.isNotBlank(CommonUtils.getString(sParams.get("tmpltId")))) {
            resultCnt = generalDao.updateGernal(DB.QRY_UPDATE_FRND_TALK_TMPLT, sParams);
        // insert
        } else {
            // 템플릿ID 취득
            String tmpltId = CommonUtils.getCommonId(Const.TMPLT_PREFIX, 5);
            sParams.put("tmpltId", tmpltId);
            resultCnt = generalDao.insertGernal(DB.QRY_INSERT_FRND_TALK_TEMPLATE, sParams);
        }

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
     * 친구톡 템플릿 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectFrndTalkList(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
            rtn.setPageProps(params);
            if(rtn.getPageInfo() != null) {
                //카운트 쿼리 실행
                int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_FRND_TALK_TMPLT_LIST_CNT, params);
                rtn.getPageInfo().put("totCnt", listCnt);
            }
        }

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_FRND_TALK_TMPLT_LIST, params);
        rtn.setData(rtnList);

        return rtn;
    }

    /**
     * 친구톡 템플릿 삭제 처리
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public RestResult<Object> deleteFrndTalkTmplt(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        int resultCnt = generalDao.deleteGernal(DB.QRY_DELETE_FRND_TALK_TMPLT, params);
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
     * 카카오 발신 프로필키 목록 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectSenderKeyList(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        List<Object> rtnList = null;
        String senderKeyType = CommonUtils.getStrValue(params, "senderKeyType");
        String kkoSvc = CommonUtils.getStrValue(params, "kkoSvc");

        if(StringUtils.equals(senderKeyType, Const.KkoSenderKeyType.GROUP)) {
            rtnList = generalDao.selectGernalList(DB.QRY_SELECT_KKO_SENDER_GRP_KEY_LIST, params);
        } else {
            params.put("kkoSvc", Const.KkoSvcUseCode.getType(kkoSvc));
            rtnList = generalDao.selectGernalList(DB.QRY_SELECT_KKO_SENDER_KEY_LIST, params);
        }

        rtn.setData(rtnList);
        return rtn;
    }

    /**
     * 카카오 템플릿 카테고리 그룹 목록 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectKkoTmpltCatGrpList(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_KKO_TMPLT_CAT_GRP_LIST, params);
        rtn.setData(rtnList);
        return rtn;
    }

    /**
     * 카카오 템플릿 카테고리 목록 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectKkoTmpltCatList(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_KKO_TMPLT_CAT_LIST, params);
        rtn.setData(rtnList);
        return rtn;
    }

    /**
     * 알림톡 템플릿 승인요청 데이터 유효성 체크
     * @param rtn
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public AlimTalkTmpltRequestData setApprvRequestKkoTmpltData(RestResult<Object> rtn, Map<String, Object> params) throws Exception {
        AlimTalkTmpltRequestData requestData = new AlimTalkTmpltRequestData();

        //senderKey
        requestData.setSenderKey(CommonUtils.getStrValue(params, "senderKey"));
        //senderType
        requestData.setSenderType(CommonUtils.getStrValue(params, "senderKeyType"));
        //templateName
        requestData.setTemplateName(CommonUtils.getStrValue(params, "tmpltName"));
        //templateContent
        requestData.setTemplateContent(CommonUtils.getStrValue(params, "tmpltContent"));
        //categoryCode
        requestData.setCategoryCode(CommonUtils.getStrValue(params, "categoryCode"));
        //templateMessageType
        //BA: 기본형 고정
        //templateEmphasizeType
        requestData.setTemplateEmphasizeType(CommonUtils.getStrValue(params, "emphasizeType"));
        //templateTitle
        requestData.setTemplateTitle(CommonUtils.getStrValue(params, "tmpltEmpsTitle"));
        //templateSubtitle
        requestData.setTemplateSubtitle(CommonUtils.getStrValue(params, "tmpltEmpsSubTitle"));

        //버튼정보
        if(!CommonUtils.isEmptyValue(params, "buttonList")) {
            List<AlimTalkButtonsInfo> buttonList = (List<AlimTalkButtonsInfo>) params.get("buttonList");
            if(CollectionUtils.isNotEmpty(buttonList)) {
                requestData.setButtons(buttonList);
            }
        }

        //서비스정보
        ServiceInfo svcInfo = new ServiceInfo();
        svcInfo.setCorpId(CommonUtils.getStrValue(params, "corpId"));
        svcInfo.setProjectId(CommonUtils.getStrValue(params, "projectId"));

        Map<String, Object> sParam = new HashMap<String, Object>();
        sParam.put("relayChType", "KKO");
        sParam.put("relay", "LOTTE");
        String relaySvcId = CommonUtils.getString(generalDao.selectGernalObject(DB.QRY_SELECT_RELAY_SVC_ID, sParam));
        svcInfo.setRelay(relaySvcId);
        requestData.setSvcInfo(svcInfo);

        //유효성 체크
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<AlimTalkTmpltRequestData>> violations = validator.validate(requestData);
        String errorMsg = "";

        for (ConstraintViolation violation : violations) {
            errorMsg += (StringUtils.isNotBlank(errorMsg) ? "\n" : "") + violation.getMessage();
            //log.info("path : [{}], message : [{}]", violation.getPropertyPath(), violation.getMessage());
        }

        //연관유효성 체크
        if(StringUtils.equals(Const.KkoTmpltEmphasizeType.TEXT, requestData.getTemplateEmphasizeType())) {
            if(StringUtils.isBlank(requestData.getTemplateTitle())) {
                errorMsg += (StringUtils.isNotBlank(errorMsg) ? "\n" : "") + "템플릿강조제목은 강조표기형 일때 필수입니다.";
            }
            if(StringUtils.isBlank(requestData.getTemplateSubtitle())) {
                errorMsg += (StringUtils.isNotBlank(errorMsg) ? "\n" : "") + "템플릿강조부제목은 강조표기형 일때 필수입니다.";
            }
        }

        if(StringUtils.isNotBlank(errorMsg)) {
            rtn.setSuccess(false);
            rtn.setMessage(errorMsg);
        }

        return requestData;
    }

    /**
     * 알림톡 템플릿 승인요청 처리
     * @param requestData
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked" })
    public RestResult<Object> procApprvRequestKkoTmplt(AlimTalkTmpltRequestData requestData) throws Exception {
        log.info("{}.procApprvRequestKkoTmplt requestData: {}", this.getClass(), requestData.toString());

        RestResult<Object> rtn = new RestResult<Object>();
        String corpId = requestData.getSvcInfo().getCorpId();
        String projectId = requestData.getSvcInfo().getProjectId();
        String apiKey = commonService.getApiKey(corpId, projectId);

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("apiKey", apiKey);

        Gson gson = new Gson();
        String jsonString = gson.toJson(requestData);
        Map<String, Object> responseBody = apiInterface.sendMsg(ApiConfig.CREATE_KKO_TMPLT_REQ_API_URI, headerMap, jsonString);

        String rslt = "";
        if(responseBody != null) {
            rslt = CommonUtils.getStrValue(responseBody, "rslt");
        }

        if(!StringUtils.equals(ApiConfig.GW_API_SUCCESS, rslt)) {
            rtn.setFail("알림톡 템플릿 승인요청에 실패하였습니다.");
            log.warn("{}.procApprvRequestKkoTmplt Fail -request: {}, response: {}", this.getClass(), jsonString, responseBody);
        }

        return rtn;
    }



}
