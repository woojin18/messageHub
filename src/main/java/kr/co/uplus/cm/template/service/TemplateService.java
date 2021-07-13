package kr.co.uplus.cm.template.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

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
import kr.co.uplus.cm.sendMessage.dto.AlimTalkTmpltEtcRequestData;
import kr.co.uplus.cm.sendMessage.dto.AlimTalkTmpltRequestData;
import kr.co.uplus.cm.sendMessage.dto.AlimTalkTmpltRequestData.AlimTalkTmpltModRequest;
import kr.co.uplus.cm.sendMessage.dto.AlimTalkTmpltRequestData.AlimTalkTmpltRegRequest;
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
     * 알림톡 템플릿 데이터 유효성 체크
     * @param rtn
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public AlimTalkTmpltRequestData setAlimTalkTmpltRequestData(RestResult<Object> rtn, Map<String, Object> params) throws Exception {
        AlimTalkTmpltRequestData requestData = new AlimTalkTmpltRequestData();

        //templateCode
        String templateKey = CommonUtils.getStrValue(params, "tmpltKey");
        if(StringUtils.isNotBlank(templateKey)) {
            List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_ALIM_TALK_TMPLT_LIST, params);
            if(rtnList == null || rtnList.size() == 0) {
                rtn.setFail("잘못된 요청 정보입니다.");
                log.warn("{}.setAlimTalkTmpltRequestData Invalid request => templateKey : {}", this.getClass(), templateKey);
                return requestData;
            }
            requestData.setTemplateKey(templateKey);
        }

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

        //유효성 체크
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<AlimTalkTmpltRequestData>> violations = null;
        if(StringUtils.isNotBlank(requestData.getTemplateKey())) {
            violations = validator.validate(requestData, Default.class, AlimTalkTmpltModRequest.class);
        } else {
            violations = validator.validate(requestData, Default.class, AlimTalkTmpltRegRequest.class);
        }
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
     * 알림톡 템플릿 등록요청 처리
     * @param requestData
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked" })
    public RestResult<Object> procApprvRequestKkoTmplt(AlimTalkTmpltRequestData requestData, Map<String, Object> params) throws Exception {
        log.info("{}.procApprvRequestKkoTmplt requestData: {}", this.getClass(), requestData.toString());

        RestResult<Object> rtn = new RestResult<Object>();
        String corpId = CommonUtils.getStrValue(params, "corpId");
        String projectId = CommonUtils.getStrValue(params, "projectId");
        String apiKey = commonService.getApiKey(corpId, projectId);

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("apiKey", apiKey);

        Gson gson = new Gson();
        String jsonString = gson.toJson(requestData);
        Map<String, Object> responseBody = apiInterface.sendMsg(ApiConfig.CREATE_KKO_TMPLT_REQ_API_URI, headerMap, jsonString);

        String rsltCode = "";
        if(responseBody != null) {
            rsltCode = CommonUtils.getStrValue(responseBody, ApiConfig.GW_RESULT_CODE_FIELD_NM);
        }

        if(!StringUtils.equals(ApiConfig.GW_API_SUCCESS, rsltCode)) {
            rtn.setFail("알림톡 템플릿 등록요청에 실패하였습니다.");
            log.warn("{}.procApprvRequestKkoTmplt Fail -request: {}, response: {}", this.getClass(), jsonString, responseBody);
        }

        return rtn;
    }

    /**
     * 알림톡 템플릿 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectAlimTalkTmpltList(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
            rtn.setPageProps(params);
            if(rtn.getPageInfo() != null) {
                //카운트 쿼리 실행
                int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_ALIM_TALK_TMPLT_LIST_CNT, params);
                rtn.getPageInfo().put("totCnt", listCnt);
            }
        }

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_ALIM_TALK_TMPLT_LIST, params);
        rtn.setData(rtnList);

        return rtn;
    }

    /**
     * 알림톡 템플릿 삭제 요청
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public RestResult<Object> procDeleteRequestKkoTmplt(Map<String, Object> params) throws Exception {
        log.info("{}.procDeleteRequestKkoTmplt Start ====> params : {}", this.getClass(), params);

        RestResult<Object> rtn = new RestResult<Object>();
        AlimTalkTmpltEtcRequestData requestData = null;
        List<String> tmpltKeys = null;

        if(params.containsKey("tmpltKeys")) {
            tmpltKeys = (List<String>) params.get("tmpltKeys");
        }

        if(tmpltKeys == null || tmpltKeys.size() == 0) {
            rtn.setFail("잘못된 템플릿 코드 정보입니다.");
            return rtn;
        }

        String corpId = CommonUtils.getStrValue(params, "corpId");
        String projectId = CommonUtils.getStrValue(params, "projectId");
        String apiKey = commonService.getApiKey(corpId, projectId);

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("apiKey", apiKey);

        Map<String, Object> sParams = null;
        Map<String, Object> tmpltInfo = null;
        Map<String, Object> responseBody = null;
        Gson gson = null;

        String tmpltStatCode = "";
        String senderKey = "";
        String jsonString = "";
        String rtltCode = "";
        int success = 0;

        for(String tmpltKey : tmpltKeys) {
            sParams = new HashMap<String, Object>();
            sParams.put("tmpltKey", tmpltKey);
            List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_ALIM_TALK_TMPLT_LIST, sParams);
            if(rtnList == null || rtnList.size() == 0) {
                continue;
            }

            tmpltInfo = (Map<String, Object>) rtnList.get(0);
            tmpltStatCode = CommonUtils.getStrValue(tmpltInfo, "tmpltStatCode");
            senderKey = CommonUtils.getStrValue(tmpltInfo, "senderKey");
            if(!StringUtils.equals(tmpltStatCode, Const.TmpltStatCode.REQ_DONE)) {
                continue;
            }

            requestData = new AlimTalkTmpltEtcRequestData();
            requestData.setSenderKey(senderKey);
            requestData.setTemplateKey(tmpltKey);

            gson = new Gson();
            jsonString = gson.toJson(requestData);
            responseBody = apiInterface.sendMsg(ApiConfig.DELETE_KKO_TMPLT_REQ_API_URI, headerMap, jsonString);

            rtltCode = "";
            if(responseBody != null) {
                rtltCode = CommonUtils.getStrValue(responseBody, ApiConfig.GW_RESULT_CODE_FIELD_NM);
            }

            if(StringUtils.equals(ApiConfig.GW_API_SUCCESS, rtltCode)) success++;
        }

        String reflectionMsg = "반영까지 최대 5분의 시간이 소요될 수 있습니다.";
        rtn.setMessage("총 "+tmpltKeys.size()+"건 중 "+success+"건 삭제 요청 되었습니다."+(success != 0 ? "\n"+reflectionMsg : ""));
        return rtn;

    }

    /**
     * 알림톡 템플릿 검수 요청
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public RestResult<Object> procInspectRequestKkoTmplt(Map<String, Object> params) throws Exception {
        log.info("{}.procDeleteRequestKkoTmplt Start ====> params : {}", this.getClass(), params);

        RestResult<Object> rtn = new RestResult<Object>();

        AlimTalkTmpltEtcRequestData requestData = null;
        String tmpltKey = CommonUtils.getStrValue(params, "tmpltKey");
        String senderKey = CommonUtils.getStrValue(params, "senderKey");

        if(StringUtils.isBlank(tmpltKey) || StringUtils.isBlank(senderKey)) {
            rtn.setFail("잘못된 템플릿 정보입니다.");
            log.warn("{}.procDeleteRequestKkoTmplt => tmpltKey : {}, senderKey : {}"
                    , this.getClass(), tmpltKey, senderKey);
            return rtn;
        }

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_ALIM_TALK_TMPLT_LIST, params);
        if(rtnList == null || rtnList.size() == 0) {
            rtn.setFail("잘못된 템플릿 정보입니다.");
            log.warn("{}.procDeleteRequestKkoTmplt => tmpltKey : {}, senderKey : {}"
                    , this.getClass(), tmpltKey, senderKey);
            return rtn;
        }

        requestData = new AlimTalkTmpltEtcRequestData();
        requestData.setSenderKey(senderKey);
        requestData.setTemplateKey(tmpltKey);

        String corpId = CommonUtils.getStrValue(params, "corpId");
        String projectId = CommonUtils.getStrValue(params, "projectId");
        String apiKey = commonService.getApiKey(corpId, projectId);

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("apiKey", apiKey);

        Gson gson = new Gson();
        String jsonString = gson.toJson(requestData);
        Map<String, Object> responseBody = apiInterface.sendMsg(ApiConfig.INSPECT_KKO_TMPLT_REQ_API_URI, headerMap, jsonString);

        String rtltCode = "";
        if(responseBody != null) {
            rtltCode = CommonUtils.getStrValue(responseBody, ApiConfig.GW_RESULT_CODE_FIELD_NM);
        }

        if(!StringUtils.equals(ApiConfig.GW_API_SUCCESS, rtltCode)) {
            rtn.setFail("알림톡 템플릿 검수요청에 실패하였습니다.");
            log.warn("{}.procInspectRequestKkoTmplt Fail -request: {}, response: {}", this.getClass(), jsonString, responseBody);
        }

        return rtn;
    }

    /**
     * 알림톡 템플릿 수정 요청 처리
     * @param requestData
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked" })
    public RestResult<Object> procUpdateRequestKkoTmplt(AlimTalkTmpltRequestData requestData, Map<String, Object> params) throws Exception {
        log.info("{}.procUpdateRequestKkoTmplt requestData: {}", this.getClass(), requestData.toString());

        RestResult<Object> rtn = new RestResult<Object>();
        String corpId = CommonUtils.getStrValue(params, "corpId");
        String projectId = CommonUtils.getStrValue(params, "projectId");
        String apiKey = commonService.getApiKey(corpId, projectId);

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("apiKey", apiKey);

        Gson gson = new Gson();
        String jsonString = gson.toJson(requestData);
        Map<String, Object> responseBody = apiInterface.sendMsg(ApiConfig.CREATE_KKO_TMPLT_UPDATE_API_URI, headerMap, jsonString);

        String rtltCode = "";
        if(responseBody != null) {
            rtltCode = CommonUtils.getStrValue(responseBody, ApiConfig.GW_RESULT_CODE_FIELD_NM);
        }

        if(!StringUtils.equals(ApiConfig.GW_API_SUCCESS, rtltCode)) {
            rtn.setFail("알림톡 템플릿 수정요청에 실패하였습니다.");
            log.warn("{}.procUpdateRequestKkoTmplt Fail -request: {}, response: {}", this.getClass(), jsonString, responseBody);
        }

        return rtn;
    }

}
