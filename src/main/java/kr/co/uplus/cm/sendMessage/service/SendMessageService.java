package kr.co.uplus.cm.sendMessage.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.consts.Const.ApiWatchNotiMsg;
import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.config.ApiConfig;
import kr.co.uplus.cm.exception.CMException;
import kr.co.uplus.cm.rcsTemplateSend.Service.RcsTemplateSendService;
import kr.co.uplus.cm.sendMessage.dto.AlimTalkRequestData;
import kr.co.uplus.cm.sendMessage.dto.FbInfo;
import kr.co.uplus.cm.sendMessage.dto.FrndTalkRequestData;
import kr.co.uplus.cm.sendMessage.dto.KkoButtonInfo;
import kr.co.uplus.cm.sendMessage.dto.KkoButtonInfo.AlimTalkSendRequest;
import kr.co.uplus.cm.sendMessage.dto.KkoButtonInfo.FrndTalkSendRequest;
import kr.co.uplus.cm.sendMessage.dto.MmsRequestData;
import kr.co.uplus.cm.sendMessage.dto.PushMsg;
import kr.co.uplus.cm.sendMessage.dto.PushRequestData;
import kr.co.uplus.cm.sendMessage.dto.RecvInfo;
import kr.co.uplus.cm.sendMessage.dto.SmartRequestData;
import kr.co.uplus.cm.sendMessage.dto.SmsRequestData;
import kr.co.uplus.cm.utils.ApiInterface;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.DateUtil;
import kr.co.uplus.cm.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

/**
 * <pre>
 * SendMessageService
 * 각 채널별 메시지 발송처리 Service
 * </pre>
 *
 * @author liam (Lee Sung Min)
 * @Date : 2021.03.29.
 * @Version : 1.0 Copyright 2020 LG Uplus Corp. All Rights Reserved.
 */
@Log4j2
@Service
public class SendMessageService {

    @Autowired
    private GeneralDao generalDao;

    @Autowired
    private CommonService commonService;

    @Autowired
    ApiInterface apiInterface;
    
    @Autowired
    private RcsTemplateSendService rcsTemplateSendSvc;
    
    private long second = 1000;

    /**
     * APP ID 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectAppIdList(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_APP_ID_LIST, params);
        rtn.setData(rtnList);

        return rtn;
    }

    /**
     * 발신번호 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectCallbackList(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        Map<String, Object> sParams = new HashMap<String, Object>(params);

        sParams.put("approvalStatus", Const.ApprovalStatus.APPROVE);
        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_CALLBACK_LIST, sParams);
        rtn.setData(rtnList);

        return rtn;
    }

    /**
     * 주소록 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectAddressList(Map<String, Object> params) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();
        Map<String, Object> rtnObj = new HashMap<String, Object>();

        //주소록 그룹 목록 조회
        List<Object> addrGrpList = generalDao.selectGernalList(DB.QRY_SELECT_ADDR_GRP_LIST, params);
        List<Object> addrCtgyList = generalDao.selectGernalList(DB.QRY_SELECT_ADDR_CTGY_LIST, params);

        rtnObj.put("addrGrpList", addrGrpList);
        rtnObj.put("addrCtgyList", addrCtgyList);
        rtn.setData(rtnObj);

        return rtn;
    }

    /**
     * 주소록 카테고리 구성원 목록 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectCmCuList(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
            rtn.setPageProps(params);
            if(rtn.getPageInfo() != null) {
                //카운트 쿼리 실행
                int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_CM_CU_LIST_CNT, params);
                rtn.getPageInfo().put("totCnt", listCnt);
            }
        }
        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_CM_CU_LIST, params);
        rtn.setData(rtnList);

        return rtn;
    }

    /**
     * Get 수신자 리스트
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<RecvInfo> getRecvInfoLst(Map<String, Object> params, MultipartFile excelFile) throws Exception{
        List<RecvInfo> recvInfoLst = new ArrayList<RecvInfo>();
        long cliKey = NumberUtils.LONG_ONE;
        String separator = "_";
        String webReqId = CommonUtils.getStrValue(params, "webReqId");

        if(params.containsKey("cuInputType")) {

            //직접입력 && 주소록
            if(StringUtils.equals("DICT", (String)params.get("cuInputType"))
                    || StringUtils.equals("ADDR", (String)params.get("cuInputType"))) {
                List<Map<String, Object>> objList = (List<Map<String, Object>>) params.get("recvInfoLst");
                RecvInfo recvInfo = null;

                ObjectMapper mapper = new ObjectMapper();
                for(Map<String, Object> recvObj : objList) {
                    recvInfo = mapper.convertValue(recvObj, RecvInfo.class);
                    recvInfo.setCliKey(webReqId+separator+String.valueOf(cliKey++));

                    if(params.containsKey("requiredCuPhone") && (Boolean) params.get("requiredCuPhone")) {
                        if(CommonUtils.isHpNumber(recvInfo.getPhone()) == false) {
                            throw new CMException("유효하지 않은 핸드폰번호가 존재합니다.("+recvInfo.getPhone()+")");
                        }
                    }

                    recvInfoLst.add(recvInfo);
                }

            //엑셀
            } else if(StringUtils.equals("EXCEL", (String)params.get("cuInputType"))) {

                //read excelFile
                List<Map<String, Object>> excelList = null;
                List<String> colKeys = new ArrayList<String>();
                List<String> contsVarNms = null;
                int excelHeader = 2;
                int limitRow = 0;

                if(params.containsKey("requiredCuid") && (Boolean) params.get("requiredCuid")) {
                    colKeys.add("cuid");
                }
                if(params.containsKey("requiredCuPhone") && (Boolean) params.get("requiredCuPhone")) {
                    colKeys.add("phone");
                }
                if(params.containsKey("contsVarNms")) {
                    contsVarNms = (ArrayList<String>)params.get("contsVarNms");
                    for(String varNm : contsVarNms) {
                        colKeys.add(varNm);
                    }
                }
                if(params.containsKey("excelLimitRow")) {
                    try {
                        limitRow = (Integer) params.get("excelLimitRow");
                    } catch (Exception e) {
                        limitRow = 0;
                        log.error("{}.getRecvInfoLst get excelLimitRow Exception : {}", this.getClass(), e);
                    }
                }

                if(limitRow > 0) {
                    excelList = commonService.getExcelDataList(excelFile, excelHeader, limitRow+excelHeader, colKeys);
                } else {
                    excelList = commonService.getExcelDataList(excelFile, excelHeader, colKeys);
                }

                RecvInfo recvInfo = null;
                Map<String, Object> mergeData = null;
                String hpNumber = "";
                for(Map<String, Object> excelInfo : excelList) {
                    recvInfo = new RecvInfo();
                    recvInfo.setCliKey(webReqId+separator+String.valueOf(cliKey++));
                    if(excelInfo.containsKey("cuid")) recvInfo.setCuid((String) excelInfo.get("cuid"));
                    if(excelInfo.containsKey("phone")) {
                        hpNumber = (String) excelInfo.get("phone");
                        if(CommonUtils.isHpNumber(hpNumber) == false) {
                            throw new CMException("유효하지 않은 핸드폰번호가 존재합니다.("+hpNumber+")");
                        }
                        recvInfo.setPhone(hpNumber);
                    }

                    mergeData = new HashMap<String, Object>();
                    for(String key : excelInfo.keySet()) {
                        if(!StringUtils.equals(key, "cuid") && !StringUtils.equals(key, "phone")) {
                            mergeData.put(key, (String) excelInfo.get(key));
                        }
                    }
                    recvInfo.setMergeData(mergeData);
                    recvInfoLst.add(recvInfo);
                }

            //전체발송
            } else if(StringUtils.equals("ALL", (String)params.get("cuInputType"))) {
                List<Object> sltObjList = generalDao.selectGernalList(DB.QRY_SELECT_ALL_APP_USER_LIST, params);
                recvInfoLst = (List<RecvInfo>)(Object)sltObjList;
            }
        }

        String senderType = CommonUtils.getStrValue(params, "senderType");
        if(StringUtils.equals(senderType, Const.SenderType.MERGER) || StringUtils.equals(senderType, Const.SenderType.SMART)) {
            setMergeDataByChannel((List<Map<String, Object>>) params.get("chMappingVarList"), recvInfoLst);
        }

        return recvInfoLst;
    }

    /**
     * 결제방식 조회
     * @param Param
     * @return
     * @throws Exception
     */
    public String selectPayType(Map<String, Object> params) throws Exception {
        return (String) generalDao.selectGernalObject(DB.QRY_SELECT_PAY_TYPE, params);
    }

    /**
     * 잔여금액 조회
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "unused", "rawtypes" })
    public BigDecimal getRmAmount(Map<String, Object> params) throws Exception {
        BigDecimal rmAmount = new BigDecimal(0);
        ObjectMapper mapper = null;

        String cashBalance = "";
        String corpId = CommonUtils.getStrValue(params, "corpId");
        String projectId = CommonUtils.getStrValue(params, "projectId");
        String apiKey = commonService.getApiKey(corpId, projectId);
        String apiUri = ApiConfig.GET_CASH_INFO_API_URI + corpId;

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("apiKey", apiKey);

        log.info("{}.getRmAmount API START=======>", this.getClass());
        log.info("{}.getRmAmount API URL : {}, Header : {}", this.getClass(), ApiConfig.CASH_SERVER_DOMAIN + apiUri, headerMap);
        Map resultMap = apiInterface.get(ApiConfig.CASH_SERVER_DOMAIN, apiUri, headerMap);
        log.info("{}.getRmAmount API Result : {}", this.getClass(), resultMap);
        log.info("{}.getRmAmount API END=======>", this.getClass());

        if (!CommonUtils.isEmptyValue(resultMap, ApiConfig.GW_RESULT_CODE_FIELD_NM)
                && StringUtils.equals(ApiConfig.GW_API_SUCCESS, CommonUtils.getString(resultMap.get(ApiConfig.GW_RESULT_CODE_FIELD_NM)))) {
            Map<String, Object> dataMap = (Map<String, Object>) resultMap.get(ApiConfig.COMMON_DATA_FIELD_NM);
            List<Map<String, Object>> cashInfoList = (List<Map<String, Object>>) dataMap.get(ApiConfig.CASH_INFO_FIELD_NM);

            for(Map<String, Object> cashInfo : cashInfoList) {
                cashBalance = CommonUtils.getStrValue(cashInfo, ApiConfig.CASH_BALANCE_FIELD_NM);
                rmAmount = rmAmount.add(new BigDecimal(cashBalance));
            }
        } else {
            log.error("{}.getRmAmount API Fail => API response Body: {}", this.getClass(), resultMap);
            throw new CMException("캐시 정보 조회에 실패하였습니다.");
        }

        return rmAmount;
    }

    /**
     * 발송 가격 조회
     * @param sendCnt
     * @return
     * @throws Exception
     */
    public BigDecimal selectMsgFeePerOne(Map<String, Object> params) throws Exception {
        Object selectObject = generalDao.selectGernalObject(DB.QRY_SELECT_MSG_FEE_PER_ONE, params);
        if(selectObject == null) {
            throw new Exception("발송 가격 조회값 없음");
        }
        return (BigDecimal) selectObject;
    }

    /**
     * 광고성 멘트 추가
     * @param params
     * @param titleKey
     * @param msgKey
     * @param rcvblcNumKey
     * @return
     */
    public Map<String, Object> SetAdText(String title, String msg, String rcvblcNum) {

        Map<String, Object> rtnMap = new HashMap<String, Object>();

        //광고 글자 추가
        if(StringUtils.isNotBlank(title)) {
            title = ApiConfig.AD_TEXT + title;
        } else {
            msg = ApiConfig.AD_TEXT + msg;
        }
        //수신거부번호 Set
        if(StringUtils.isNotBlank(rcvblcNum)) {
            msg += "\n" +  rcvblcNum;
        }

        rtnMap.put("title", title);
        rtnMap.put("msg", msg);

        return rtnMap;
    }

    /**
     * 푸시 발송 데이터 유효성 체크
     * @param rtn
     * @param params
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public PushRequestData setPushSendData(RestResult<Object> rtn, Map<String, Object> params) {
        PushRequestData pushRequestData = new PushRequestData();

        String webReqId = CommonUtils.getCommonId(Const.WebReqIdPrefix.PUSH_PREFIX, 5);

        //webReqId
        pushRequestData.setWebReqId(webReqId);
        params.put("webReqId", webReqId);

        //appId
        pushRequestData.setAppId(CommonUtils.getStrValue(params, "appId"));

        //캠페인 ID
        pushRequestData.setCampaignId(CommonUtils.getStrValue(params, "campaignId"));

        //발송정책
        pushRequestData.setServiceCode(CommonUtils.getStrValue(params, "serviceCode"));

        //푸시 메시지
        String msgTitle = CommonUtils.getStrValue(params, "pushTitle");
        String pushContent = (CommonUtils.getStrValue(params, "pushContent"));
        String rcvblcNumber = (CommonUtils.getStrValue(params, "rcvblcNumber"));
        String msgKind = (CommonUtils.getStrValue(params, "msgKind"));
        String pushBody = pushContent;

        //광고성일 경우
        if(StringUtils.equals(msgKind, Const.MsgKind.AD)) {
            Map<String, Object> adMap = SetAdText(msgTitle, pushBody, rcvblcNumber);
            msgTitle = CommonUtils.getStrValue(adMap, "title");
            pushBody = (CommonUtils.getStrValue(adMap, "msg"));
        }

        PushMsg pushMsg = new PushMsg();
        pushMsg.setTitle(msgTitle);
        pushMsg.setBody(pushBody);
        pushRequestData.setMsg(pushMsg);

        //이미지, 부가정보
        String msgType = (CommonUtils.getStrValue(params, "msgType"));
        String fileId = (CommonUtils.getStrValue(params, "fileId"));
        String imgUrl = (CommonUtils.getStrValue(params, "imgUrl"));
        String adtnInfo = (CommonUtils.getStrValue(params, "adtnInfo"));

        if(StringUtils.equals(msgType, Const.MsgType.IMAGE)) {
            pushRequestData.setFileId(fileId);
            pushRequestData.getExt().put("imageUrl", imgUrl);
        }
        if(StringUtils.isNotBlank(adtnInfo)) {
            pushRequestData.getExt().put("data1", adtnInfo);
        }

        //대체발송
        String rplcSendType = (CommonUtils.getStrValue(params, "rplcSendType"));
        if(!StringUtils.equals(rplcSendType, Const.RplcSendType.NONE)) {
            List<FbInfo> fbInfoLst = new ArrayList<FbInfo>();
            Map<String, Object> fbInfo = (Map<String, Object>) params.get("fbInfo");
            String fbTitle = CommonUtils.getStrValue(fbInfo, "title");
            String fbMsg = CommonUtils.getStrValue(fbInfo, "msg");
            String fbRcvblcNumber = CommonUtils.getStrValue(fbInfo, "rcvblcNumber");
            String fbMsgBody = fbMsg;

            //광고성일 경우
            if(StringUtils.equals(msgKind, Const.MsgKind.AD)) {
                Map<String, Object> adMap = SetAdText(fbTitle, fbMsgBody, fbRcvblcNumber);
                fbTitle = CommonUtils.getStrValue(adMap, "title");
                fbMsgBody = (CommonUtils.getStrValue(adMap, "msg"));
            }

            FbInfo pushFbInfo = new FbInfo();
            pushFbInfo.setCh(rplcSendType);
            pushFbInfo.setMsg(fbMsgBody);

            if(StringUtils.equals(rplcSendType, Const.RplcSendType.LMS)) {
                pushFbInfo.setCh(Const.RplcSendType.MMS);  //LMS 는 MMS로 전송
                pushFbInfo.setTitle(fbTitle);
            } else if(StringUtils.equals(rplcSendType, Const.RplcSendType.MMS)) {
                pushFbInfo.setTitle(fbTitle);
                pushFbInfo.setFileId(CommonUtils.getStrValue(fbInfo, "fileId"));
            }

            fbInfoLst.add(pushFbInfo);
            pushRequestData.setFbInfoLst(fbInfoLst);
            pushRequestData.setCallback(CommonUtils.getStrValue(fbInfo, "callback"));
        }

        //유효성 체크
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PushRequestData>> violations = validator.validate(pushRequestData);
        String errorMsg = "";

        for (ConstraintViolation violation : violations) {
            errorMsg += (StringUtils.isNotBlank(errorMsg) ? "\n" : "") + violation.getMessage();
            //log.info("path : [{}], message : [{}]", violation.getPropertyPath(), violation.getMessage());
        }

        if(StringUtils.isNotBlank(errorMsg)) {
            rtn.setSuccess(false);
            rtn.setMessage(errorMsg);
        }

        return pushRequestData;
    }

    /**
     * 웹 발송 내역 등록
     * @param data
     * @param pushRequestData
     * @param recvInfoLst
     * @return
     * @throws Exception
     */
    public RestResult<Object> insertPushCmWebMsg(RestResult<Object> rtn
            , Map<String, Object> data
            , PushRequestData pushRequestData
            , List<RecvInfo> recvInfoLst) throws Exception {
        String ch = CommonUtils.getStrValue(data, "ch");
        String corpId = CommonUtils.getStrValue(data, "corpId");
        String projectId = CommonUtils.getStrValue(data, "projectId");
        String rsrvSendYn = CommonUtils.getStrValue(data, "rsrvSendYn");
        String rsrvDateStr = "";
        String allFailYn = CommonUtils.getStrValue(data, "allFailYn");
        String status = (StringUtils.equals(allFailYn, Const.COMM_YES) ? Const.MsgSendStatus.FAIL : Const.MsgSendStatus.COMPLETED);

        if(StringUtils.equals(rsrvSendYn, Const.COMM_YES)) {
            String rsrvYmd = CommonUtils.getStrValue(data, "rsrvDate");
            String rsrvHH = CommonUtils.getStrValue(data, "rsrvHH");
            String rsrvMM = CommonUtils.getStrValue(data, "rsrvMM");
            rsrvDateStr = rsrvYmd+" "+rsrvHH+":"+rsrvMM;

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date rsrvDate = dateFormat.parse(rsrvDateStr);
            Date currentDate = new Date();

            currentDate = DateUtils.addMinutes(currentDate, 10);
            if(currentDate.compareTo(rsrvDate) > 0) {
                rtn.setSuccess(false);
                rtn.setMessage("잘못된 예약시간입니다. 현재시간 10분 이후로 설정해주세요.");
                return rtn;
            }
            if(DateUtil.diffDays(rsrvDate) > Const.SEND_RSRV_LIMIT_DAY) {
                rtn.setFail("잘못된 예약일자입니다. 현재일로 부터 "+Const.SEND_RSRV_LIMIT_DAY+"일 이내로 설정해주세요");
            }
            status = Const.MsgSendStatus.SEND_WAIT;
        }

        pushRequestData.setRecvInfoLst(recvInfoLst);
        Gson gson = new Gson();
        String json = gson.toJson(pushRequestData);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("webReqId", pushRequestData.getWebReqId());
        params.put("corpId", corpId);
        params.put("projectId", projectId);
        params.put("apiKey", commonService.getApiKey(corpId, projectId));
        params.put("chString", ch);
        params.put("msgInfo", json);
        params.put("senderCnt", recvInfoLst.size());
        params.put("callback", pushRequestData.getCallback());
        params.put("campaignId", pushRequestData.getCampaignId());
        params.put("senderType", Const.SenderType.CHANNEL);
        params.put("status", status);
        params.put("resvSenderYn", rsrvSendYn);
        params.put("reqDt", rsrvDateStr);
        params.put("userId", CommonUtils.getStrValue(data, "userId"));

        int resultCnt = insertCmWebMsg(params);

        if (resultCnt <= 0) {
            log.warn("{}.insertPushCmWebMsg Fail =>  webReqId : {}", this.getClass(), pushRequestData.getWebReqId());
        }

        return rtn;
    }

    /**
     * 웹 발송 내역 등록
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public int insertCmWebMsg(Map<String, Object> params) throws Exception {
        return generalDao.insertGernal(DB.QRY_INSERT_CM_WEB_MSG, params);
    }

    /**
     * 푸시 테스트 발송 처리
     * @param data
     * @param pushRequestData
     * @param sendList
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public RestResult<Object> testSendPushMsg(
            Map<String, Object> data
            , PushRequestData pushRequestData
            , List<RecvInfo> sendList) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        pushRequestData.setWebReqId(StringUtils.EMPTY);  //테스트발송은 웹 요청 아이디를 넣지 않는다.
        Map<String, Object> resultMap = sendPushMsg(data, pushRequestData, sendList);

        if(isSendSuccess(resultMap)) {
            int successCnt = 0;
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) resultMap.get(ApiConfig.COMMON_DATA_FIELD_NM);
            for(Map<String, Object> dataInfo : dataList) {
                if(!CommonUtils.isEmptyValue(dataInfo, ApiConfig.GW_RESULT_CODE_FIELD_NM)
                        && StringUtils.equals(ApiConfig.GW_API_SUCCESS, CommonUtils.getString(dataInfo.get(ApiConfig.GW_RESULT_CODE_FIELD_NM)))) {
                    successCnt++;
                }
            }
            rtn.setMessage(dataList.size() + "건 중 " + successCnt + "건 발송 성공하였습니다.");
        } else {
            log.warn("{}.testSendPushMsg Fail ==> response : {}", this.getClass(), resultMap);
            rtn.setFail(CommonUtils.getGWApiFailMsg(resultMap, "테스트 발송이 실패하였습니다."));
        }

        return rtn;
    }

    /**
     * 푸시 메시지 발송 처리
     * @param data
     * @param pushRequestData
     * @param sendList
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> sendPushMsg(
            Map<String, Object> data
            , PushRequestData pushRequestData
            , List<RecvInfo> sendList) throws Exception {

        String corpId = CommonUtils.getStrValue(data, "corpId");
        String projectId = CommonUtils.getStrValue(data, "projectId");
        String apiKey = commonService.getApiKey(corpId, projectId);

        pushRequestData.setRecvInfoLst(sendList);

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("apiKey", apiKey);

        Gson gson = new Gson();
        String jsonString = gson.toJson(pushRequestData);

        return apiInterface.sendMsg(ApiConfig.SEND_PUSH_API_URI, headerMap, jsonString);
    }

    /**
     * API 재요청 여부
     * 재요청 코드에 등록되지 않은 모든 상황은 재요청 하지 않는다.
     * @param responseBody
     * @param reSendCdList
     * @return
     */
    @SuppressWarnings("unchecked")
    private boolean isApiRequestAgain(Map<String, Object> responseBody, List<Object> reSendCdList) {
        boolean isDone = true;
        if(responseBody != null) {
            if(!CommonUtils.isEmptyValue(responseBody, ApiConfig.GW_RESULT_CODE_FIELD_NM)){
                String resultCode = CommonUtils.getString(responseBody.get(ApiConfig.GW_RESULT_CODE_FIELD_NM));
                for(Object reSendCd : reSendCdList) {
                    if(StringUtils.equals(resultCode, CommonUtils.getString(reSendCd))) {
                        isDone = false;
                    }

                    //재요청(CPS)일 경우 마스터 code를 재요청 코드를 줘야 되는데 성공으로 주고 세부데이터를 재요청 코드로 넘겨준다...
                    if(responseBody.containsKey(ApiConfig.COMMON_DATA_FIELD_NM)) {
                        try {
                            List<Map<String, Object>> data = (List<Map<String, Object>>) responseBody.get(ApiConfig.COMMON_DATA_FIELD_NM);
                            for(Map<String, Object> ind : data) {
                                if(!CommonUtils.isEmptyValue(ind, ApiConfig.GW_RESULT_CODE_FIELD_NM)
                                        && StringUtils.equals(CommonUtils.getString(ind.get(ApiConfig.GW_RESULT_CODE_FIELD_NM)), CommonUtils.getString(reSendCd))) {
                                    isDone = false;
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            log.warn("{}.isApiRequestAgain casting error => responseBody : {}", this.getClass(), responseBody);
                        }
                    }

                    if(isDone == false) break;
                }
            }
        }
        return isDone;
    }

    /**
     * 발송 성공여부
     * @param responseBody
     * @return
     */
    private boolean isSendSuccess(Map<String, Object> responseBody) {
        boolean isSuccess = false;
        if(responseBody != null) {
            if(!CommonUtils.isEmptyValue(responseBody, ApiConfig.GW_RESULT_CODE_FIELD_NM)
                    && StringUtils.equals(ApiConfig.GW_API_SUCCESS, CommonUtils.getString(responseBody.get(ApiConfig.GW_RESULT_CODE_FIELD_NM)))) {
                isSuccess = true;
            }
        }
        return isSuccess;
    }

    /**
     * 채널별 메시지 내역 등록
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public void insertCmMsg(Map<String, Object> data, List<RecvInfo> recvInfoLst) throws Exception {
        Map<String, Object> params = null;
        String corpId = CommonUtils.getStrValue(data, "corpId");
        String projectId = CommonUtils.getStrValue(data, "projectId");
        String apiKey = CommonUtils.getStrValue(data, "apiKey");
        String reqCh = CommonUtils.getStrValue(data, "reqCh");
        String productCode = CommonUtils.getStrValue(data, "productCode");
        String finalCh = CommonUtils.getStrValue(data, "finalCh");
        String pushAppId = CommonUtils.getStrValue(data, "pushAppId");
        String callback = CommonUtils.getStrValue(data, "callback");
        String webReqId = CommonUtils.getStrValue(data, "webReqId");
        String senderType = Const.SenderType.CHANNEL;

        for(RecvInfo recvInfo : recvInfoLst) {
            params = new HashMap<String, Object>();
            params.put("msgKey", CommonUtils.getCommonId(Const.SendMsgErrorSet.ERROR_PREFIX, 5));
            params.put("corpId", corpId);
            params.put("projectId", projectId);
            params.put("apiKey", apiKey);
            params.put("cliKey", recvInfo.getCliKey());
            params.put("senderType", senderType);
            params.put("reqCh", reqCh);
            params.put("productCode", productCode);
            params.put("finalCh", finalCh);
            params.put("phone", recvInfo.getPhone());
            params.put("pushAppId", pushAppId);
            params.put("pushCuid", recvInfo.getCuid());
            params.put("callback", callback);
            params.put("webReqId", webReqId);
            params.put("gwResultCode", Const.SendMsgErrorSet.GW_RESULT_CODE);
            params.put("gwResultDesc", Const.SendMsgErrorSet.GW_RESULT_DESC);
            generalDao.insertGernal(DB.QRY_INSERT_CM_MSG, params);
        }
    }

    /**
     * 재전송 코드 조회
     * @param params
     * @return
     * @throws Exception
     */
    public List<Object> reSendCdList(Map<String, Object> params) throws Exception {
        return generalDao.selectGernalList(DB.QRY_SELECT_RE_SEND_CD_LIST, params);
    }

    /**
     * 푸시 메시지 발송 비동기 처리
     * @param rtn
     * @param fromIndex
     * @param data
     * @param pushRequestData
     * @param recvInfoLst
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Async
    public void sendPushMsgAsync(RestResult<Object> rtn
            , int fromIndex
            , Map<String, Object> data
            , PushRequestData pushRequestData
            , List<RecvInfo> recvInfoLst
            , List<Object> reSendCdList) throws Exception {

        List<RecvInfo> errorRecvInfoLst = new ArrayList<RecvInfo>();
        Map<String, Object> responseBody = null;
        Map<String, Object> sParams = new HashMap<String, Object>(data);

        String corpId = CommonUtils.getStrValue(sParams, "corpId");
        String projectId = CommonUtils.getStrValue(sParams, "projectId");
        Map apiData = commonService.getApiKey2(corpId, projectId);
        String apiKey = CommonUtils.getString(apiData.get("apiKey"));
        String strCps = CommonUtils.getString(apiData.get("cps"),"30");
        String jsonString = "";
        boolean isDone = false;
        boolean isServerError = false;
        boolean isAllFail = true;

        Gson gson = new Gson();
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("apiKey", apiKey);

        int retryCnt = NumberUtils.INTEGER_ZERO;
        int cutSize = ApiConfig.DEFAULT_RECV_LIMIT_SIZE;
        int listSize = recvInfoLst.size();
        int toIndex = fromIndex;
        
        int cps = NumberUtils.toInt(strCps, 30);
        if (cps <= 0) cps = 30;
        int sendCnt = 0;
        long start = System.currentTimeMillis();
        long end = 0;

        while (toIndex < listSize) {
            isDone = false;
            isServerError = false;
            toIndex = fromIndex + cutSize;
            try {
                if(toIndex > listSize) toIndex = listSize;
                pushRequestData.setRecvInfoLst(recvInfoLst.subList(fromIndex, toIndex));
                jsonString = gson.toJson(pushRequestData);
                responseBody = apiInterface.sendMsg(ApiConfig.SEND_PUSH_API_URI, headerMap, jsonString);
                isDone = isApiRequestAgain(responseBody, reSendCdList);
                isAllFail = !isSendSuccess(responseBody);
//                isDone = true;
//                isAllFail = false;
            } catch (Exception e) {
                log.error("{}.sendPushMsgAsync API Request Error ==> {}", this.getClass(), e);
                isServerError = true;
                if(retryCnt == ApiConfig.GW_RETRY_CNT) sendMsgErrorNoti(Const.ApiWatchNotiMsg.API_CONNECTION_FAIL);
            }

            if(isDone) {
            	sendCnt++;
                retryCnt = NumberUtils.INTEGER_ZERO;
                fromIndex = toIndex;
            } else if(retryCnt == ApiConfig.GW_RETRY_CNT) {
                errorRecvInfoLst.addAll(recvInfoLst.subList(fromIndex, toIndex));
                retryCnt = NumberUtils.INTEGER_ZERO;
                fromIndex = toIndex;
            } else {
            	sendCnt++;
                retryCnt++;
                toIndex = fromIndex;
                if(!isServerError) TimeUnit.MILLISECONDS.sleep(ApiConfig.GW_RETRY_DELAY_MILLISECONDS);
            }
            
            if (sendCnt >= cps || toIndex >= listSize) {
            	end = System.currentTimeMillis();
            	long diff  = end - start;
                log.info("API sendMsg apiKey : {}, cps : {}, sendCnt : {}", apiKey, cps, sendCnt);
            	if (second > diff && sendCnt >= cps) {
            		TimeUnit.MILLISECONDS.sleep(second-diff);
            	}
            	sendCnt = 0;
            	start = System.currentTimeMillis();
            }
        }

        if(CollectionUtils.isNotEmpty(errorRecvInfoLst)) {
            try {
                //CM_MSG Insert
                sParams.put("apiKey", apiKey);
                sParams.put("reqCh", Const.Ch.PUSH);
                sParams.put("productCode", Const.Ch.PUSH.toLowerCase());
                sParams.put("finalCh", Const.Ch.PUSH);
                sParams.put("pushAppId", pushRequestData.getAppId());
                sParams.put("callback", pushRequestData.getCallback());
                sParams.put("webReqId", pushRequestData.getWebReqId());
                insertCmMsg(sParams, errorRecvInfoLst);
            } catch (Exception e) {
                log.error("{}.sendPushMsgAsync insertCmMsg Error ==> {}", this.getClass(), e);
            }
        }

        //웹 발송 내역 등록
        if(isAllFail) sParams.put("allFailYn", Const.COMM_YES);
        insertPushCmWebMsg(rtn, sParams, pushRequestData, recvInfoLst);
    }

    /**
     * SMS 발송 데이터 유효성 체크
     * @param rtn
     * @param params
     */
    @SuppressWarnings("rawtypes")
    public SmsRequestData setSmsSendData(RestResult<Object> rtn, Map<String, Object> params) {
        SmsRequestData requestData = new SmsRequestData();

        //webReqId
        String webReqId = CommonUtils.getCommonId(Const.WebReqIdPrefix.SMS_PREFIX, 5);
        requestData.setWebReqId(webReqId);
        params.put("webReqId", webReqId);

        //callback
        requestData.setCallback(CommonUtils.getStrValue(params, "callback"));

        //캠페인 ID
        requestData.setCampaignId(CommonUtils.getStrValue(params, "campaignId"));

        //SMS 메시지
        String smsContent = CommonUtils.getStrValue(params, "smsContent");
        String rcvblcNumber = CommonUtils.getStrValue(params, "rcvblcNumber");
        String msgKind = CommonUtils.getStrValue(params, "msgKind");
        String msg = smsContent;

        if(StringUtils.equals(msgKind, Const.MsgKind.AD)) {
            Map<String, Object> adMap = SetAdText("", msg, rcvblcNumber);
            msg = (CommonUtils.getStrValue(adMap, "msg"));
        }
        requestData.setMsg(msg);

        //유효성 체크
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<SmsRequestData>> violations = validator.validate(requestData);
        String errorMsg = "";

        for (ConstraintViolation violation : violations) {
            errorMsg += (StringUtils.isNotBlank(errorMsg) ? "\n" : "") + violation.getMessage();
            //log.info("path : [{}], message : [{}]", violation.getPropertyPath(), violation.getMessage());
        }

        if(StringUtils.isNotBlank(errorMsg)) {
            rtn.setFail(errorMsg);
        }

        return requestData;
    }

    /**
     * SMS 웹 발송 내역 등록
     * @param rtn
     * @param data
     * @param requestData
     * @param recvInfoLst
     * @return
     * @throws Exception
     */
    public RestResult<Object> insertSmsCmWebMsg(RestResult<Object> rtn
            , Map<String, Object> data
            , SmsRequestData requestData
            , List<RecvInfo> recvInfoLst) throws Exception {
        String ch = CommonUtils.getStrValue(data, "senderType");
        String corpId = CommonUtils.getStrValue(data, "corpId");
        String projectId = CommonUtils.getStrValue(data, "projectId");
        String rsrvSendYn = CommonUtils.getStrValue(data, "rsrvSendYn");
        String rsrvDateStr = "";
        String allFailYn = CommonUtils.getStrValue(data, "allFailYn");
        String status = (StringUtils.equals(allFailYn, Const.COMM_YES) ? Const.MsgSendStatus.FAIL : Const.MsgSendStatus.COMPLETED);

        if(StringUtils.equals(rsrvSendYn, Const.COMM_YES)) {
            String rsrvYmd = CommonUtils.getStrValue(data, "rsrvDate");
            String rsrvHH = CommonUtils.getStrValue(data, "rsrvHH");
            String rsrvMM = CommonUtils.getStrValue(data, "rsrvMM");
            rsrvDateStr = rsrvYmd+" "+rsrvHH+":"+rsrvMM;

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date rsrvDate = dateFormat.parse(rsrvDateStr);
            Date currentDate = new Date();

            currentDate = DateUtils.addMinutes(currentDate, 10);
            if(currentDate.compareTo(rsrvDate) > 0) {
                rtn.setSuccess(false);
                rtn.setMessage("잘못된 예약시간입니다. 현재시간 10분 이후로 설정해주세요.");
                return rtn;
            }
            if(DateUtil.diffDays(rsrvDate) > Const.SEND_RSRV_LIMIT_DAY) {
                rtn.setFail("잘못된 예약일자입니다. 현재일로 부터 "+Const.SEND_RSRV_LIMIT_DAY+"일 이내로 설정해주세요");
            }
            status = Const.MsgSendStatus.SEND_WAIT;
        }

        requestData.setRecvInfoLst(recvInfoLst);
        Gson gson = new Gson();
        String json = gson.toJson(requestData);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("webReqId", requestData.getWebReqId());
        params.put("corpId", corpId);
        params.put("projectId", projectId);
        params.put("apiKey", commonService.getApiKey(corpId, projectId));
        params.put("chString", ch);
        params.put("msgInfo", json);
        params.put("senderCnt", recvInfoLst.size());
        params.put("callback", requestData.getCallback());
        params.put("campaignId", requestData.getCampaignId());
        params.put("senderType", Const.SenderType.CHANNEL);
        params.put("status", status);
        params.put("resvSenderYn", rsrvSendYn);
        params.put("reqDt", rsrvDateStr);
        params.put("userId", CommonUtils.getStrValue(data, "userId"));

        int resultCnt = insertCmWebMsg(params);

        if (resultCnt <= 0) {
            log.warn("{}.insertSmsCmWebMsg Fail =>  webReqId : {}", this.getClass(), requestData.getWebReqId());
        }

        return rtn;
    }
    
    private Vector sms = new Vector();

    /**
     * SMS 메시지 발송 비동기 처리
     * @param rtn
     * @param fromIndex
     * @param data
     * @param pushRequestData
     * @param recvInfoLst
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Async
    public void sendSmsMsgAsync(RestResult<Object> rtn
            , int fromIndex
            , Map<String, Object> data
            , SmsRequestData requestData
            , List<RecvInfo> recvInfoLst
            , List<Object> reSendCdList) throws Exception {
        List<RecvInfo> errorRecvInfoLst = new ArrayList<RecvInfo>();
        Map<String, Object> responseBody = null;
        Map<String, Object> sParams = new HashMap<String, Object>(data);

        String corpId = CommonUtils.getStrValue(sParams, "corpId");
        String projectId = CommonUtils.getStrValue(sParams, "projectId");
        Map apiData = commonService.getApiKey2(corpId, projectId);
        String apiKey = CommonUtils.getString(apiData.get("apiKey"));
        String strCps = CommonUtils.getString(apiData.get("cps"),"30");
        String jsonString = "";
        boolean isDone = false;
        boolean isServerError = false;
        boolean isAllFail = true;

        Gson gson = new Gson();
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("apiKey", apiKey);

        int retryCnt = NumberUtils.INTEGER_ZERO;
        int cutSize = ApiConfig.DEFAULT_RECV_LIMIT_SIZE;
        int listSize = recvInfoLst.size();
        int toIndex = fromIndex;
        
        int cps = NumberUtils.toInt(strCps, 30);
        if (cps <= 0) cps = 30;
        int sendCnt = 0;
        long start = System.currentTimeMillis();
        long end = 0;

        while (toIndex < listSize) {
            isDone = false;
            isServerError = false;
            toIndex = fromIndex + cutSize;
            try {
                if(toIndex > listSize) toIndex = listSize;
                requestData.setRecvInfoLst(recvInfoLst.subList(fromIndex, toIndex));
                jsonString = gson.toJson(requestData);
                responseBody = apiInterface.sendMsg(ApiConfig.SEND_SMS_API_URI, headerMap, jsonString);
                isDone = isApiRequestAgain(responseBody, reSendCdList);
                isAllFail = !isSendSuccess(responseBody);
//                isDone = true;
//                isAllFail = false;
            } catch (Exception e) {
                log.error("{}.sendSmsMsgAsync API Request Error ==> {}", this.getClass(), e);
                isServerError = true;
                if(retryCnt == ApiConfig.GW_RETRY_CNT) sendMsgErrorNoti(Const.ApiWatchNotiMsg.API_CONNECTION_FAIL);
            }

            if(isDone) {
            	sendCnt++;
                retryCnt = NumberUtils.INTEGER_ZERO;
                fromIndex = toIndex;
            } else if(retryCnt == ApiConfig.GW_RETRY_CNT) {
                errorRecvInfoLst.addAll(recvInfoLst.subList(fromIndex, toIndex));
                retryCnt = NumberUtils.INTEGER_ZERO;
                fromIndex = toIndex;
            } else {
            	sendCnt++;
                retryCnt++;
                toIndex = fromIndex;
                if(!isServerError) TimeUnit.MILLISECONDS.sleep(ApiConfig.GW_RETRY_DELAY_MILLISECONDS);
            }
            
            if (sendCnt >= cps || toIndex >= listSize) {
            	end = System.currentTimeMillis();
            	long diff  = end - start;
                log.info("API sendMsg apiKey : {}, cps : {}, sendCnt : {}", apiKey, cps, sendCnt);
            	if (second > diff && sendCnt >= cps) {
            		TimeUnit.MILLISECONDS.sleep(second-diff);
            	}
            	sendCnt = 0;
            	start = System.currentTimeMillis();
            }
        }

        if(CollectionUtils.isNotEmpty(errorRecvInfoLst)) {
            try {
                //CM_MSG Insert
                sParams.put("apiKey", apiKey);
                sParams.put("reqCh", Const.Ch.SMS);
                sParams.put("productCode", Const.Ch.SMS.toLowerCase());
                sParams.put("finalCh", Const.Ch.SMS);
                sParams.put("callback", requestData.getCallback());
                sParams.put("webReqId", requestData.getWebReqId());
                insertCmMsg(sParams, errorRecvInfoLst);
            } catch (Exception e) {
                log.error("{}.sendSmsMsgAsync insertCmMsg Error ==> {}", this.getClass(), e);
            }
        }

        //웹 발송 내역 등록
        if(isAllFail) sParams.put("allFailYn", Const.COMM_YES);
        insertSmsCmWebMsg(rtn, sParams, requestData, recvInfoLst);
    }

    /**
     * MMS 발송 데이터 유효성 체크
     * @param rtn
     * @param params
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public MmsRequestData setMmsSendData(RestResult<Object> rtn, Map<String, Object> params) {
        MmsRequestData requestData = new MmsRequestData();

        //webReqId
        String webReqId = CommonUtils.getCommonId(Const.WebReqIdPrefix.MMS_PREFIX, 5);
        requestData.setWebReqId(webReqId);
        params.put("webReqId", webReqId);

        //callback
        requestData.setCallback(CommonUtils.getStrValue(params, "callback"));

        //캠페인 ID
        requestData.setCampaignId(CommonUtils.getStrValue(params, "campaignId"));

        //MMS 내용
        String title = CommonUtils.getStrValue(params, "smsTitle");
        String smsContent = CommonUtils.getStrValue(params, "smsContent");
        String rcvblcNumber = CommonUtils.getStrValue(params, "rcvblcNumber");
        String msgKind = CommonUtils.getStrValue(params, "msgKind");
        String msg = smsContent;

        //광고성일 경우
        if(StringUtils.equals(msgKind, Const.MsgKind.AD)) {
            Map<String, Object> adMap = SetAdText(title, msg, rcvblcNumber);
            title = CommonUtils.getStrValue(adMap, "title");
            msg = (CommonUtils.getStrValue(adMap, "msg"));
        }
        requestData.setTitle(title);
        requestData.setMsg(msg);

        //File List
        List<Map<String, Object>> imgInfoList = null;
        if(params.containsKey("imgInfoList")) {
            imgInfoList = (List<Map<String, Object>>) params.get("imgInfoList");
            for(Map<String, Object> imgInfo : imgInfoList) {
                if(imgInfo.containsKey("fileId")) {
                    requestData.getFileIdLst().add(CommonUtils.getStrValue(imgInfo, "fileId"));
                }
            }
        }

        //유효성 체크
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MmsRequestData>> violations = validator.validate(requestData);
        String errorMsg = "";

        for (ConstraintViolation violation : violations) {
            errorMsg += (StringUtils.isNotBlank(errorMsg) ? "\n" : "") + violation.getMessage();
            //log.info("path : [{}], message : [{}]", violation.getPropertyPath(), violation.getMessage());
        }

        if(StringUtils.isNotBlank(errorMsg)) {
            rtn.setSuccess(false);
            rtn.setMessage(errorMsg);
        }

        return requestData;
    }

    /**
     * MMS 웹 발송 내역 등록
     * @param rtn
     * @param data
     * @param requestData
     * @param recvInfoLst
     * @return
     * @throws Exception
     */
    public RestResult<Object> insertMmsCmWebMsg(RestResult<Object> rtn
            , Map<String, Object> data
            , MmsRequestData requestData
            , List<RecvInfo> recvInfoLst) throws Exception {
        String ch = CommonUtils.getStrValue(data, "senderType");
        String corpId = CommonUtils.getStrValue(data, "corpId");
        String projectId = CommonUtils.getStrValue(data, "projectId");
        String rsrvSendYn = CommonUtils.getStrValue(data, "rsrvSendYn");
        String rsrvDateStr = "";
        String allFailYn = CommonUtils.getStrValue(data, "allFailYn");
        String status = (StringUtils.equals(allFailYn, Const.COMM_YES) ? Const.MsgSendStatus.FAIL : Const.MsgSendStatus.COMPLETED);

        if(StringUtils.equals(rsrvSendYn, Const.COMM_YES)) {
            String rsrvYmd = CommonUtils.getStrValue(data, "rsrvDate");
            String rsrvHH = CommonUtils.getStrValue(data, "rsrvHH");
            String rsrvMM = CommonUtils.getStrValue(data, "rsrvMM");
            rsrvDateStr = rsrvYmd+" "+rsrvHH+":"+rsrvMM;

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date rsrvDate = dateFormat.parse(rsrvDateStr);
            Date currentDate = new Date();

            currentDate = DateUtils.addMinutes(currentDate, 10);
            if(currentDate.compareTo(rsrvDate) > 0) {
                rtn.setSuccess(false);
                rtn.setMessage("잘못된 예약시간입니다. 현재시간 10분 이후로 설정해주세요.");
                return rtn;
            }
            if(DateUtil.diffDays(rsrvDate) > Const.SEND_RSRV_LIMIT_DAY) {
                rtn.setFail("잘못된 예약일자입니다. 현재일로 부터 "+Const.SEND_RSRV_LIMIT_DAY+"일 이내로 설정해주세요");
            }
            status = Const.MsgSendStatus.SEND_WAIT;
        }

        requestData.setRecvInfoLst(recvInfoLst);
        Gson gson = new Gson();
        String json = gson.toJson(requestData);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("webReqId", requestData.getWebReqId());
        params.put("corpId", corpId);
        params.put("projectId", projectId);
        params.put("apiKey", commonService.getApiKey(corpId, projectId));
        params.put("chString", ch);
        params.put("msgInfo", json);
        params.put("senderCnt", recvInfoLst.size());
        params.put("callback", requestData.getCallback());
        params.put("campaignId", requestData.getCampaignId());
        params.put("senderType", Const.SenderType.CHANNEL);
        params.put("status", status);
        params.put("resvSenderYn", rsrvSendYn);
        params.put("reqDt", rsrvDateStr);
        params.put("userId", CommonUtils.getStrValue(data, "userId"));

        int resultCnt = insertCmWebMsg(params);

        if (resultCnt <= 0) {
            log.warn("{}.insertMmsCmWebMsg Fail =>  webReqId : {}", this.getClass(), requestData.getWebReqId());
        }

        return rtn;
    }

    /**
     * SMS 메시지 발송 비동기 처리
     * @param rtn
     * @param fromIndex
     * @param data
     * @param requestData
     * @param recvInfoLst
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Async
    public void sendMmsMsgAsync(RestResult<Object> rtn
            , int fromIndex
            , Map<String, Object> data
            , MmsRequestData requestData
            , List<RecvInfo> recvInfoLst
            , List<Object> reSendCdList) throws Exception {
        List<RecvInfo> errorRecvInfoLst = new ArrayList<RecvInfo>();
        Map<String, Object> responseBody = null;
        Map<String, Object> sParams = new HashMap<String, Object>(data);

        String corpId = CommonUtils.getStrValue(sParams, "corpId");
        String projectId = CommonUtils.getStrValue(sParams, "projectId");
        Map apiData = commonService.getApiKey2(corpId, projectId);
        String apiKey = CommonUtils.getString(apiData.get("apiKey"));
        String strCps = CommonUtils.getString(apiData.get("cps"),"30");
        String jsonString = "";
        boolean isDone = false;
        boolean isServerError = false;
        boolean isAllFail = true;

        Gson gson = new Gson();
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("apiKey", apiKey);

        int retryCnt = NumberUtils.INTEGER_ZERO;
        int cutSize = ApiConfig.DEFAULT_RECV_LIMIT_SIZE;
        int listSize = recvInfoLst.size();
        int toIndex = fromIndex;

        int cps = NumberUtils.toInt(strCps, 30);
        if (cps <= 0) cps = 30;
        int sendCnt = 0;
        long start = System.currentTimeMillis();
        long end = 0;

        while (toIndex < listSize) {
            isDone = false;
            isServerError = false;
            toIndex = fromIndex + cutSize;
            try {
                if(toIndex > listSize) toIndex = listSize;
                requestData.setRecvInfoLst(recvInfoLst.subList(fromIndex, toIndex));
                jsonString = gson.toJson(requestData);
                responseBody = apiInterface.sendMsg(ApiConfig.SEND_MMS_API_URI, headerMap, jsonString);
                isDone = isApiRequestAgain(responseBody, reSendCdList);
                isAllFail = !isSendSuccess(responseBody);
//                isDone = true;
//                isAllFail = false;
            } catch (Exception e) {
                log.error("{}.sendMmsMsgAsync API Request Error ==> {}", this.getClass(), e);
                isServerError = true;
                if(retryCnt == ApiConfig.GW_RETRY_CNT) sendMsgErrorNoti(Const.ApiWatchNotiMsg.API_CONNECTION_FAIL);
            }

            if(isDone) {
            	sendCnt++;
                retryCnt = NumberUtils.INTEGER_ZERO;
                fromIndex = toIndex;
            } else if(retryCnt == ApiConfig.GW_RETRY_CNT) {
                errorRecvInfoLst.addAll(recvInfoLst.subList(fromIndex, toIndex));
                retryCnt = NumberUtils.INTEGER_ZERO;
                fromIndex = toIndex;
            } else {
            	sendCnt++;
                retryCnt++;
                toIndex = fromIndex;
                if(!isServerError) TimeUnit.MILLISECONDS.sleep(ApiConfig.GW_RETRY_DELAY_MILLISECONDS);
            }
            
            if (sendCnt >= cps || toIndex >= listSize) {
            	end = System.currentTimeMillis();
            	long diff  = end - start;
                log.info("API sendMsg apiKey : {}, cps : {}, sendCnt : {}", apiKey, cps, sendCnt);
            	if (second > diff && sendCnt >= cps) {
            		TimeUnit.MILLISECONDS.sleep(second-diff);
            	}
            	sendCnt = 0;
            	start = System.currentTimeMillis();
            }
        }

        if(CollectionUtils.isNotEmpty(errorRecvInfoLst)) {
            try {
                //CM_MSG Insert
                sParams.put("apiKey", apiKey);
                sParams.put("reqCh", Const.Ch.MMS);
                sParams.put("productCode", Const.Ch.MMS.toLowerCase());
                sParams.put("finalCh", Const.Ch.MMS);
                sParams.put("callback", requestData.getCallback());
                sParams.put("webReqId", requestData.getWebReqId());
                insertCmMsg(sParams, errorRecvInfoLst);
            } catch (Exception e) {
                log.error("{}.sendMmsMsgAsync insertCmMsg Error ==> {}", this.getClass(), e);
            }
        }

        //웹 발송 내역 등록
        if(isAllFail) sParams.put("allFailYn", Const.COMM_YES);
        insertMmsCmWebMsg(rtn, sParams, requestData, recvInfoLst);
    }

    /**
     * MMS 메시지 발송 처리
     * @param data
     * @param pushRequestData
     * @param sendList
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> sendMmsMsg(
            Map<String, Object> data
            , MmsRequestData requestData
            , List<RecvInfo> sendList) throws Exception {

        String corpId = CommonUtils.getStrValue(data, "corpId");
        String projectId = CommonUtils.getStrValue(data, "projectId");
        String apiKey = commonService.getApiKey(corpId, projectId);

        requestData.setRecvInfoLst(sendList);

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("apiKey", apiKey);

        Gson gson = new Gson();
        String jsonString = gson.toJson(requestData);

        return apiInterface.sendMsg(ApiConfig.SEND_MMS_API_URI, headerMap, jsonString);
    }

    /**
     * MMS 테스트 발송
     * @param data
     * @param requestData
     * @param sendList
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public RestResult<Object> testSendMmsMsg(
            Map<String, Object> data
            , MmsRequestData requestData
            , List<RecvInfo> sendList) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        requestData.setWebReqId(StringUtils.EMPTY);  //테스트발송은 웹 요청 아이디를 넣지 않는다.
        Map<String, Object> resultMap = sendMmsMsg(data, requestData, sendList);

        if(isSendSuccess(resultMap)) {
            int successCnt = 0;
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) resultMap.get(ApiConfig.COMMON_DATA_FIELD_NM);
            for(Map<String, Object> dataInfo : dataList) {
                if(!CommonUtils.isEmptyValue(dataInfo, ApiConfig.GW_RESULT_CODE_FIELD_NM)
                        && StringUtils.equals(ApiConfig.GW_API_SUCCESS, CommonUtils.getString(dataInfo.get(ApiConfig.GW_RESULT_CODE_FIELD_NM)))) {
                    successCnt++;
                }
            }
            rtn.setMessage(dataList.size() + "건 중 " + successCnt + "건 발송 성공하였습니다.");
        } else {
            log.warn("{}.testSendMmsMsg Fail ==> response : {}", this.getClass(), resultMap);
            rtn.setFail(CommonUtils.getGWApiFailMsg(resultMap, "테스트 발송이 실패하였습니다."));
        }

        return rtn;
    }

    /**
     * SMS 메시지 발송 처리
     * @param data
     * @param pushRequestData
     * @param sendList
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> sendSmsMsg(
            Map<String, Object> data
            , SmsRequestData requestData
            , List<RecvInfo> sendList) throws Exception {

        String corpId = CommonUtils.getStrValue(data, "corpId");
        String projectId = CommonUtils.getStrValue(data, "projectId");
        String apiKey = commonService.getApiKey(corpId, projectId);

        requestData.setRecvInfoLst(sendList);

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("apiKey", apiKey);

        Gson gson = new Gson();
        String jsonString = gson.toJson(requestData);

        return apiInterface.sendMsg(ApiConfig.SEND_SMS_API_URI, headerMap, jsonString);
    }

    /**
     * SMS 테스트 발송
     * @param data
     * @param requestData
     * @param sendList
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public RestResult<Object> testSendSmsMsg(
            Map<String, Object> data
            , SmsRequestData requestData
            , List<RecvInfo> sendList) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        requestData.setWebReqId(StringUtils.EMPTY);  //테스트발송은 웹 요청 아이디를 넣지 않는다.
        Map<String, Object> resultMap = sendSmsMsg(data, requestData, sendList);

        if(isSendSuccess(resultMap)) {
            int successCnt = 0;
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) resultMap.get(ApiConfig.COMMON_DATA_FIELD_NM);
            for(Map<String, Object> dataInfo : dataList) {
                if(!CommonUtils.isEmptyValue(dataInfo, ApiConfig.GW_RESULT_CODE_FIELD_NM)
                        && StringUtils.equals(ApiConfig.GW_API_SUCCESS, CommonUtils.getString(dataInfo.get(ApiConfig.GW_RESULT_CODE_FIELD_NM)))) {
                    successCnt++;
                }
            }
            rtn.setMessage(dataList.size() + "건 중 " + successCnt + "건 발송 성공하였습니다.");
        } else {
            log.warn("{}.testSendSmsMsg Fail ==> response : {}", this.getClass(), resultMap);
            rtn.setFail(CommonUtils.getGWApiFailMsg(resultMap, "테스트 발송이 실패하였습니다."));
        }

        return rtn;
    }

    /**
     * 친구톡 발송 데이터 유효성 체크
     * @param rtn
     * @param params
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public FrndTalkRequestData setFrndTalkSendData(RestResult<Object> rtn, Map<String, Object> params) {
        FrndTalkRequestData requestData = new FrndTalkRequestData();

        //webReqId
        String webReqId = CommonUtils.getCommonId(Const.WebReqIdPrefix.FRND_TALK_PREFIX, 5);
        requestData.setWebReqId(webReqId);
        params.put("webReqId", webReqId);

        //callback
        //대채발송이 아닐 경우 callback 이 없는게 맞으나 G/W 에서 필수로 보내달라고 해서
        //1. 챗봇 발신번호 조회 해서 setting 한다.
        //2. 1의 방법에서 발신번호가 없거나 오류시 기본 callback 번호로 setting한다.
        String callback = "";
        String rplcSendType = CommonUtils.getStrValue(params, "rplcSendType");
        if(StringUtils.equals(rplcSendType, Const.RplcSendType.NONE)) {
            try {
                Map<String, Object> sParams = new HashMap<String, Object>(params);
                sParams.put("approvalStatus", Const.ApprovalStatus.APPROVE);
                List<Object> callbackList = generalDao.selectGernalList(DB.QRY_SELECT_CALLBACK_LIST, sParams);
                if(!CollectionUtils.isEmpty(callbackList)) {
                    Map<String, Object> callbackInfo = (Map<String, Object>) callbackList.get(0);
                    callback = CommonUtils.getStrValue(callbackInfo, "callback");
                }
            } catch (Exception e) {
                log.error("{}.setFrndTalkSendData selectGernalList ERROR : {}", this.getClass(), e);
            }
            if(StringUtils.isBlank(callback)) {
                callback = ApiConfig.DEFAULT_CALLBACK;
            }
        }
        requestData.setCallback(callback);

        //캠페인 ID
        requestData.setCampaignId(CommonUtils.getStrValue(params, "campaignId"));

        //광고 표기 여부
        String adFlag = Const.COMM_NO;
        String msgKind = CommonUtils.getStrValue(params, "msgKind");
        if(StringUtils.equals(msgKind, Const.MsgKind.AD)) {
            adFlag = Const.COMM_YES;
        }
        requestData.setAdFlag(adFlag);

        //메시지
        requestData.setMsg(CommonUtils.getStrValue(params, "frndTalkContent"));

        //카카오톡 발신 프로필키
        requestData.setSenderKey(CommonUtils.getStrValue(params, "senderKey"));

        //이미지파일관련
        requestData.setFileId(CommonUtils.getStrValue(params, "fileId"));
        requestData.setWideImageYn(CommonUtils.getStrValue(params, "wideImgYn"));
        if(StringUtils.isNotBlank(requestData.getFileId())) {
            requestData.getImage().setImageUrl(CommonUtils.getStrValue(params, "imgUrl"));
            requestData.getImage().setImageLink(CommonUtils.getStrValue(params, "imgLink"));
        }

        //버튼정보
        if(!CommonUtils.isEmptyValue(params, "buttonList")) {
            List<Map<String, Object>> tempList = (List<Map<String, Object>>) params.get("buttonList");

            if(CollectionUtils.isNotEmpty(tempList)) {
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                String json = gson.toJson(tempList);
                List<KkoButtonInfo> buttons = gson.fromJson(json, new TypeToken<List<KkoButtonInfo>>(){}.getType());
                requestData.setButtons(buttons);
            }
        }

        //대체발송
        if(!StringUtils.equals(rplcSendType, Const.RplcSendType.NONE)) {
            List<FbInfo> fbInfoLst = new ArrayList<FbInfo>();
            Map<String, Object> fbInfo = (Map<String, Object>) params.get("fbInfo");
            String fbTitle = CommonUtils.getStrValue(fbInfo, "title");
            String fbMsg = CommonUtils.getStrValue(fbInfo, "msg");
            String fbRcvblcNumber = CommonUtils.getStrValue(fbInfo, "rcvblcNumber");
            String fbMsgBody = fbMsg;

            //광고성일 경우
            if(StringUtils.equals(msgKind, Const.MsgKind.AD)) {
                Map<String, Object> adMap = SetAdText(fbTitle, fbMsgBody, fbRcvblcNumber);
                fbTitle = CommonUtils.getStrValue(adMap, "title");
                fbMsgBody = (CommonUtils.getStrValue(adMap, "msg"));
            }

            FbInfo pushFbInfo = new FbInfo();
            pushFbInfo.setCh(rplcSendType);
            pushFbInfo.setMsg(fbMsgBody);

            if(StringUtils.equals(rplcSendType, Const.RplcSendType.LMS)) {
                pushFbInfo.setCh(Const.RplcSendType.MMS);  //LMS 는 MMS로 전송
                pushFbInfo.setTitle(fbTitle);
            } else if(StringUtils.equals(rplcSendType, Const.RplcSendType.MMS)) {
                pushFbInfo.setTitle(fbTitle);
                pushFbInfo.setFileId(CommonUtils.getStrValue(fbInfo, "fileId"));
            }

            fbInfoLst.add(pushFbInfo);
            requestData.setFbInfoLst(fbInfoLst);
            requestData.setCallback(CommonUtils.getStrValue(fbInfo, "callback"));
        }

        //유효성 체크
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<FrndTalkRequestData>> violations = validator.validate(requestData, Default.class, FrndTalkSendRequest.class);
        String errorMsg = "";

        for (ConstraintViolation violation : violations) {
            errorMsg += (StringUtils.isNotBlank(errorMsg) ? "\n" : "") + violation.getMessage();
            //log.info("path : [{}], message : [{}]", violation.getPropertyPath(), violation.getMessage());
        }

        //연관유효성 체크
        if(StringUtils.isNotBlank(requestData.getFileId())) {
            if(requestData.getImage() == null || StringUtils.isBlank(requestData.getImage().getImageUrl())) {
                errorMsg += (StringUtils.isNotBlank(errorMsg) ? "\n" : "") + "이미지 정보 입력 시 이미지 URL은 필수입니다.";
            }
//            if(requestData.getImage() == null || StringUtils.isBlank(requestData.getImage().getImageLink())) {
//                errorMsg += (StringUtils.isNotBlank(errorMsg) ? "\n" : "") + "이미지 정보 입력 시 이미지 링크는 필수입니다.";
//            }
        }

        if(StringUtils.isNotBlank(errorMsg)) {
            rtn.setSuccess(false);
            rtn.setMessage(errorMsg);
        }

        return requestData;
    }

    /**
     * 친구톡 웹 발송 내역 등록
     * @param rtn
     * @param data
     * @param requestData
     * @param recvInfoLst
     * @return
     * @throws Exception
     */
    public RestResult<Object> insertFrndTalkCmWebMsg(RestResult<Object> rtn
            , Map<String, Object> data
            , FrndTalkRequestData requestData
            , List<RecvInfo> recvInfoLst) throws Exception {
        String ch = Const.Ch.FRIENDTALK;
        String corpId = CommonUtils.getStrValue(data, "corpId");
        String projectId = CommonUtils.getStrValue(data, "projectId");
        String rsrvSendYn = CommonUtils.getStrValue(data, "rsrvSendYn");
        String rsrvDateStr = "";
        String allFailYn = CommonUtils.getStrValue(data, "allFailYn");
        String status = (StringUtils.equals(allFailYn, Const.COMM_YES) ? Const.MsgSendStatus.FAIL : Const.MsgSendStatus.COMPLETED);

        if(StringUtils.equals(rsrvSendYn, Const.COMM_YES)) {
            String rsrvYmd = CommonUtils.getStrValue(data, "rsrvDate");
            String rsrvHH = CommonUtils.getStrValue(data, "rsrvHH");
            String rsrvMM = CommonUtils.getStrValue(data, "rsrvMM");
            rsrvDateStr = rsrvYmd+" "+rsrvHH+":"+rsrvMM;

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date rsrvDate = dateFormat.parse(rsrvDateStr);
            Date currentDate = new Date();

            currentDate = DateUtils.addMinutes(currentDate, 10);
            if(currentDate.compareTo(rsrvDate) > 0) {
                rtn.setSuccess(false);
                rtn.setMessage("잘못된 예약시간입니다. 현재시간 10분 이후로 설정해주세요.");
                return rtn;
            }
            if(DateUtil.diffDays(rsrvDate) > Const.SEND_RSRV_LIMIT_DAY) {
                rtn.setFail("잘못된 예약일자입니다. 현재일로 부터 "+Const.SEND_RSRV_LIMIT_DAY+"일 이내로 설정해주세요");
            }
            status = Const.MsgSendStatus.SEND_WAIT;
        }

        requestData.setRecvInfoLst(recvInfoLst);
        Gson gson = new Gson();
        String json = gson.toJson(requestData);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("webReqId", requestData.getWebReqId());
        params.put("corpId", corpId);
        params.put("projectId", projectId);
        params.put("apiKey", commonService.getApiKey(corpId, projectId));
        params.put("chString", ch);
        params.put("msgInfo", json);
        params.put("senderCnt", recvInfoLst.size());
        params.put("callback", requestData.getCallback());
        params.put("campaignId", requestData.getCampaignId());
        params.put("senderType", Const.SenderType.CHANNEL);
        params.put("status", status);
        params.put("resvSenderYn", rsrvSendYn);
        params.put("reqDt", rsrvDateStr);
        params.put("userId", CommonUtils.getStrValue(data, "userId"));

        int resultCnt = insertCmWebMsg(params);

        if (resultCnt <= 0) {
            log.warn("{}.insertFrndTalkCmWebMsg Fail =>  webReqId : {}", this.getClass(), requestData.getWebReqId());
        }

        return rtn;
    }

    /**
     * 친구톡 발송 처리
     * @param rtn
     * @param fromIndex
     * @param data
     * @param pushRequestData
     * @param recvInfoLst
     * @param reSendCdList
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Async
    public void sendFrndTalkMsgAsync(RestResult<Object> rtn
            , int fromIndex
            , Map<String, Object> data
            , FrndTalkRequestData requestData
            , List<RecvInfo> recvInfoLst
            , List<Object> reSendCdList) throws Exception {

        List<RecvInfo> errorRecvInfoLst = new ArrayList<RecvInfo>();
        Map<String, Object> responseBody = null;
        Map<String, Object> sParams = new HashMap<String, Object>(data);

        String corpId = CommonUtils.getStrValue(sParams, "corpId");
        String projectId = CommonUtils.getStrValue(sParams, "projectId");
        Map apiData = commonService.getApiKey2(corpId, projectId);
        String apiKey = CommonUtils.getString(apiData.get("apiKey"));
        String strCps = CommonUtils.getString(apiData.get("cps"),"30");
        String jsonString = "";
        boolean isDone = false;
        boolean isServerError = false;
        boolean isAllFail = true;

        Gson gson = new Gson();
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("apiKey", apiKey);

        int retryCnt = NumberUtils.INTEGER_ZERO;
        int cutSize = ApiConfig.DEFAULT_RECV_LIMIT_SIZE;
        int listSize = recvInfoLst.size();
        int toIndex = fromIndex;
        
        int cps = NumberUtils.toInt(strCps, 30);
        if (cps <= 0) cps = 30;
        int sendCnt = 0;
        long start = System.currentTimeMillis();
        long end = 0;

        while (toIndex < listSize) {
            isDone = false;
            isServerError = false;
            toIndex = fromIndex + cutSize;
            try {
                if(toIndex > listSize) toIndex = listSize;
                requestData.setRecvInfoLst(recvInfoLst.subList(fromIndex, toIndex));
                jsonString = gson.toJson(requestData);
                responseBody = apiInterface.sendMsg(ApiConfig.SEND_FRND_TALK_API_URI, headerMap, jsonString);
                isDone = isApiRequestAgain(responseBody, reSendCdList);
                isAllFail = !isSendSuccess(responseBody);
//                isDone = true;
//                isAllFail = false;
            } catch (Exception e) {
                log.error("{}.sendFrndTalkMsgAsync API Request Error ==> {}", this.getClass(), e);
                isServerError = true;
                if(retryCnt == ApiConfig.GW_RETRY_CNT) sendMsgErrorNoti(Const.ApiWatchNotiMsg.API_CONNECTION_FAIL);
            }

            if(isDone) {
            	sendCnt++;
                retryCnt = NumberUtils.INTEGER_ZERO;
                fromIndex = toIndex;
            } else if(retryCnt == ApiConfig.GW_RETRY_CNT) {
                errorRecvInfoLst.addAll(recvInfoLst.subList(fromIndex, toIndex));
                retryCnt = NumberUtils.INTEGER_ZERO;
                fromIndex = toIndex;
            } else {
            	sendCnt++;
                retryCnt++;
                toIndex = fromIndex;
                if(!isServerError) TimeUnit.MILLISECONDS.sleep(ApiConfig.GW_RETRY_DELAY_MILLISECONDS);
            }
            
            if (sendCnt >= cps || toIndex >= listSize) {
            	end = System.currentTimeMillis();
            	long diff  = end - start;
                log.info("API sendMsg apiKey : {}, cps : {}, sendCnt : {}", apiKey, cps, sendCnt);
            	if (second > diff && sendCnt >= cps) {
            		TimeUnit.MILLISECONDS.sleep(second-diff);
            	}
            	sendCnt = 0;
            	start = System.currentTimeMillis();
            }
        }

        if(CollectionUtils.isNotEmpty(errorRecvInfoLst)) {
            try {
                //CM_MSG Insert
                sParams.put("apiKey", apiKey);
                sParams.put("reqCh", Const.Ch.FRIENDTALK);
                sParams.put("productCode", Const.Ch.FRIENDTALK.toLowerCase());
                sParams.put("finalCh", Const.Ch.FRIENDTALK);
                sParams.put("callback", requestData.getCallback());
                sParams.put("webReqId", requestData.getWebReqId());
                insertCmMsg(sParams, errorRecvInfoLst);
            } catch (Exception e) {
                log.error("{}.sendFrndTalkMsgAsync insertCmMsg Error ==> {}", this.getClass(), e);
            }
        }

        //웹 발송 내역 등록
        if(isAllFail) sParams.put("allFailYn", Const.COMM_YES);
        insertFrndTalkCmWebMsg(rtn, sParams, requestData, recvInfoLst);
    }

    /**
     * 친구톡 테스트 발송 처리
     * @param data
     * @param pushRequestData
     * @param sendList
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public RestResult<Object> testSendFrndTalkMsg(
            Map<String, Object> data
            , FrndTalkRequestData requestData
            , List<RecvInfo> sendList) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        requestData.setWebReqId(StringUtils.EMPTY);  //테스트발송은 웹 요청 아이디를 넣지 않는다.
        Map<String, Object> resultMap = sendFrndTalkMsg(data, requestData, sendList);

        if(isSendSuccess(resultMap)) {
            int successCnt = 0;
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) resultMap.get(ApiConfig.COMMON_DATA_FIELD_NM);
            for(Map<String, Object> dataInfo : dataList) {
                if(!CommonUtils.isEmptyValue(dataInfo, ApiConfig.GW_RESULT_CODE_FIELD_NM)
                        && StringUtils.equals(ApiConfig.GW_API_SUCCESS, CommonUtils.getString(dataInfo.get(ApiConfig.GW_RESULT_CODE_FIELD_NM)))) {
                    successCnt++;
                }
            }
            rtn.setMessage(dataList.size() + "건 중 " + successCnt + "건 발송 성공하였습니다.");
        } else {
            log.warn("{}.testSendFrndTalkMsg Fail ==> response : {}", this.getClass(), resultMap);
            rtn.setFail(CommonUtils.getGWApiFailMsg(resultMap, "테스트 발송이 실패하였습니다."));
        }

        return rtn;
    }

    /**
     * 친구톡 메시지 발송 처리
     * @param data
     * @param pushRequestData
     * @param sendList
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> sendFrndTalkMsg(
            Map<String, Object> data
            , FrndTalkRequestData requestData
            , List<RecvInfo> sendList) throws Exception {

        String corpId = CommonUtils.getStrValue(data, "corpId");
        String projectId = CommonUtils.getStrValue(data, "projectId");
        String apiKey = commonService.getApiKey(corpId, projectId);

        requestData.setRecvInfoLst(sendList);

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("apiKey", apiKey);

        Gson gson = new Gson();
        String jsonString = gson.toJson(requestData);

        return apiInterface.sendMsg(ApiConfig.SEND_FRND_TALK_API_URI, headerMap, jsonString);
    }

    /**
     * 알림톡 발송 데이터 유효성 체크
     * @param rtn
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public AlimTalkRequestData setAlimTalkSendData(RestResult<Object> rtn, Map<String, Object> params) throws Exception {
        AlimTalkRequestData requestData = new AlimTalkRequestData();

        //알림톡 정보 Get
        String tmpltCode = CommonUtils.getStrValue(params, "tmpltCode");
        String tmpltKey = CommonUtils.getStrValue(params, "tmpltKey");
        String tmpltStatCode = Const.TmpltStatCode.OK;

        Map<String, Object> sParams = new HashMap<String, Object>();
        sParams.put("tmpltCode", tmpltCode);
        sParams.put("tmpltKey", tmpltKey);
        sParams.put("tmpltStatCode", tmpltStatCode);

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_ALIM_TALK_TMPLT_LIST, params);
        if(rtnList == null || rtnList.size() == 0) {
            rtn.setFail("유효하지 않은 템플릿 정보입니다.");
            log.warn("{}.setAlimTalkSendData => tmpltCode : {}, tmpltKey : {}, tmpltStatCode : {}"
                    , this.getClass(), tmpltCode, tmpltKey, tmpltStatCode);
            return requestData;
        }
        Map<String, Object> tmpltMap = (Map<String, Object>) rtnList.get(0);
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = CommonUtils.getStrValue(tmpltMap, "tmpltInfo");
        Map<String, Object> tmpltInfo = mapper.readValue(jsonStr, Map.class);

        //title
        requestData.setTitle(CommonUtils.getStrValue(tmpltInfo, "templateTitle"));
        //msg
        requestData.setMsg(CommonUtils.getStrValue(tmpltInfo, "templateContent"));
        //senderKey
        requestData.setSenderKey(CommonUtils.getStrValue(tmpltMap, "senderKey"));
        //tmpltKey
        requestData.setTmpltKey(CommonUtils.getStrValue(tmpltMap, "tmpltKey"));
        //버튼정보
        if(tmpltInfo.containsKey("buttons")) {
            List<Map<String, Object>> tempList = (List<Map<String, Object>>) tmpltInfo.get("buttons");

            if(CollectionUtils.isNotEmpty(tempList)) {
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                String json = gson.toJson(tempList);
                List<KkoButtonInfo> buttons = gson.fromJson(json, new TypeToken<List<KkoButtonInfo>>(){}.getType());
                requestData.setButtons(buttons);
            }
        }

        //webReqId
        String webReqId = CommonUtils.getCommonId(Const.WebReqIdPrefix.ALIM_TALK_PREFIX, 5);
        requestData.setWebReqId(webReqId);
        params.put("webReqId", webReqId);

        //callback
        //대채발송이 아닐 경우 callback 이 없는게 맞으나 G/W 에서 필수로 보내달라고 해서
        //1. 챗봇 발신번호 조회 해서 setting 한다.
        //2. 1의 방법에서 발신번호가 없거나 오류시 기본 callback 번호로 setting한다.
        String callback = "";
        String rplcSendType = CommonUtils.getStrValue(params, "rplcSendType");
        if(StringUtils.equals(rplcSendType, Const.RplcSendType.NONE)) {
            try {
                sParams = new HashMap<String, Object>(params);
                sParams.put("approvalStatus", Const.ApprovalStatus.APPROVE);
                List<Object> callbackList = generalDao.selectGernalList(DB.QRY_SELECT_CALLBACK_LIST, sParams);
                if(!CollectionUtils.isEmpty(callbackList)) {
                    Map<String, Object> callbackInfo = (Map<String, Object>) callbackList.get(0);
                    callback = CommonUtils.getStrValue(callbackInfo, "callback");
                }
            } catch (Exception e) {
                log.error("{}.setAlimTalkSendData selectGernalList ERROR : {}", this.getClass(), e);
            }
            if(StringUtils.isBlank(callback)) {
                callback = ApiConfig.DEFAULT_CALLBACK;
            }
        }
        requestData.setCallback(callback);

        //campaignId
        requestData.setCampaignId(CommonUtils.getStrValue(params, "campaignId"));

        //대체발송
        if(!StringUtils.equals(rplcSendType, Const.RplcSendType.NONE)) {
            List<FbInfo> fbInfoLst = new ArrayList<FbInfo>();
            Map<String, Object> fbInfo = (Map<String, Object>) params.get("fbInfo");
            String fbMsg = CommonUtils.getStrValue(fbInfo, "msg");

            FbInfo pushFbInfo = new FbInfo();
            pushFbInfo.setCh(rplcSendType);
            pushFbInfo.setMsg(fbMsg);

            if(StringUtils.equals(rplcSendType, Const.RplcSendType.LMS)) {
                pushFbInfo.setCh(Const.RplcSendType.MMS);  //LMS 는 MMS로 전송
                pushFbInfo.setTitle(CommonUtils.getStrValue(fbInfo, "title"));
            } else if(StringUtils.equals(rplcSendType, Const.RplcSendType.MMS)) {
                pushFbInfo.setTitle(CommonUtils.getStrValue(fbInfo, "title"));
                pushFbInfo.setFileId(CommonUtils.getStrValue(fbInfo, "fileId"));
            }

            fbInfoLst.add(pushFbInfo);
            requestData.setFbInfoLst(fbInfoLst);
            requestData.setCallback(CommonUtils.getStrValue(fbInfo, "callback"));
        }

        //유효성 체크
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<AlimTalkRequestData>> violations = validator.validate(requestData, Default.class, AlimTalkSendRequest.class);
        String errorMsg = "";

        for (ConstraintViolation violation : violations) {
            errorMsg += (StringUtils.isNotBlank(errorMsg) ? "\n" : "") + violation.getMessage();
            //log.info("path : [{}], message : [{}]", violation.getPropertyPath(), violation.getMessage());
        }

        if(StringUtils.isNotBlank(errorMsg)) {
            rtn.setFail(errorMsg);
        }

        return requestData;
    }

    /**
     * 알림톡 웹 발송 내역 등록
     * @param rtn
     * @param data
     * @param requestData
     * @param recvInfoLst
     * @return
     * @throws Exception
     */
    public RestResult<Object> insertAlimTalkCmWebMsg(RestResult<Object> rtn
            , Map<String, Object> data
            , AlimTalkRequestData requestData
            , List<RecvInfo> recvInfoLst) throws Exception {

        String ch = Const.Ch.ALIMTALK;
        String corpId = CommonUtils.getStrValue(data, "corpId");
        String projectId = CommonUtils.getStrValue(data, "projectId");
        String rsrvSendYn = CommonUtils.getStrValue(data, "rsrvSendYn");
        String rsrvDateStr = "";
        String allFailYn = CommonUtils.getStrValue(data, "allFailYn");
        String status = (StringUtils.equals(allFailYn, Const.COMM_YES) ? Const.MsgSendStatus.FAIL : Const.MsgSendStatus.COMPLETED);

        if(StringUtils.equals(rsrvSendYn, Const.COMM_YES)) {
            String rsrvYmd = CommonUtils.getStrValue(data, "rsrvDate");
            String rsrvHH = CommonUtils.getStrValue(data, "rsrvHH");
            String rsrvMM = CommonUtils.getStrValue(data, "rsrvMM");
            rsrvDateStr = rsrvYmd+" "+rsrvHH+":"+rsrvMM;

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date rsrvDate = dateFormat.parse(rsrvDateStr);
            Date currentDate = new Date();

            currentDate = DateUtils.addMinutes(currentDate, 10);
            if(currentDate.compareTo(rsrvDate) > 0) {
                rtn.setSuccess(false);
                rtn.setMessage("잘못된 예약시간입니다. 현재시간 10분 이후로 설정해주세요.");
                return rtn;
            }
            if(DateUtil.diffDays(rsrvDate) > Const.SEND_RSRV_LIMIT_DAY) {
                rtn.setFail("잘못된 예약일자입니다. 현재일로 부터 "+Const.SEND_RSRV_LIMIT_DAY+"일 이내로 설정해주세요");
            }
            status = Const.MsgSendStatus.SEND_WAIT;
        }

        requestData.setRecvInfoLst(recvInfoLst);
        Gson gson = new Gson();
        String json = gson.toJson(requestData);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("webReqId", requestData.getWebReqId());
        params.put("corpId", corpId);
        params.put("projectId", projectId);
        params.put("apiKey", commonService.getApiKey(corpId, projectId));
        params.put("chString", ch);
        params.put("msgInfo", json);
        params.put("senderCnt", recvInfoLst.size());
        params.put("callback", requestData.getCallback());
        params.put("campaignId", requestData.getCampaignId());
        params.put("senderType", Const.SenderType.CHANNEL);
        params.put("status", status);
        params.put("resvSenderYn", rsrvSendYn);
        params.put("reqDt", rsrvDateStr);
        params.put("userId", CommonUtils.getStrValue(data, "userId"));

        int resultCnt = insertCmWebMsg(params);
        if (resultCnt <= 0) {
            log.warn("{}.insertAlimTalkCmWebMsg Fail =>  webReqId : {}", this.getClass(), requestData.getWebReqId());
        }

        return rtn;
    }

    /**
     * 알림톡 발송 처리
     * @param rtn
     * @param fromIndex
     * @param data
     * @param requestData
     * @param recvInfoLst
     * @param reSendCdList
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Async
    public void sendAlimTalkMsgAsync(RestResult<Object> rtn
            , int fromIndex
            , Map<String, Object> data
            , AlimTalkRequestData requestData
            , List<RecvInfo> recvInfoLst
            , List<Object> reSendCdList) throws Exception {

        List<RecvInfo> errorRecvInfoLst = new ArrayList<RecvInfo>();
        Map<String, Object> responseBody = null;
        Map<String, Object> sParams = new HashMap<String, Object>(data);

        String corpId = CommonUtils.getStrValue(sParams, "corpId");
        String projectId = CommonUtils.getStrValue(sParams, "projectId");
        Map apiData = commonService.getApiKey2(corpId, projectId);
        String apiKey = CommonUtils.getString(apiData.get("apiKey"));
        String strCps = CommonUtils.getString(apiData.get("cps"),"30");
        String jsonString = "";
        boolean isDone = false;
        boolean isServerError = false;
        boolean isAllFail = true;

        Gson gson = new Gson();
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("apiKey", apiKey);

        int retryCnt = NumberUtils.INTEGER_ZERO;
        int cutSize = ApiConfig.DEFAULT_RECV_LIMIT_SIZE;
        int listSize = recvInfoLst.size();
        int toIndex = fromIndex;
        
        int cps = NumberUtils.toInt(strCps, 30);
        if (cps <= 0) cps = 30;
        int sendCnt = 0;
        long start = System.currentTimeMillis();
        long end = 0;

        while (toIndex < listSize) {
            isDone = false;
            isServerError = false;
            toIndex = fromIndex + cutSize;
            try {
                if(toIndex > listSize) toIndex = listSize;
                requestData.setRecvInfoLst(recvInfoLst.subList(fromIndex, toIndex));
                jsonString = gson.toJson(requestData);
                responseBody = apiInterface.sendMsg(ApiConfig.SEND_ALIM_TALK_API_URI, headerMap, jsonString);
                isDone = isApiRequestAgain(responseBody, reSendCdList);
                isAllFail = !isSendSuccess(responseBody);
//                isDone = true;
//                isAllFail = false;
            } catch (Exception e) {
                log.error("{}.sendAlimTalkMsgAsync API Request Error ==> {}", this.getClass(), e);
                isServerError = true;
                if(retryCnt == ApiConfig.GW_RETRY_CNT) sendMsgErrorNoti(Const.ApiWatchNotiMsg.API_CONNECTION_FAIL);
            }

            if(isDone) {
            	sendCnt++;
                retryCnt = NumberUtils.INTEGER_ZERO;
                fromIndex = toIndex;
            } else if(retryCnt == ApiConfig.GW_RETRY_CNT) {
                errorRecvInfoLst.addAll(recvInfoLst.subList(fromIndex, toIndex));
                retryCnt = NumberUtils.INTEGER_ZERO;
                fromIndex = toIndex;
            } else {
            	sendCnt++;
                retryCnt++;
                toIndex = fromIndex;
                if(!isServerError) TimeUnit.MILLISECONDS.sleep(ApiConfig.GW_RETRY_DELAY_MILLISECONDS);
            }
            
            if (sendCnt >= cps || toIndex >= listSize) {
            	end = System.currentTimeMillis();
            	long diff  = end - start;
                log.info("API sendMsg apiKey : {}, cps : {}, sendCnt : {}", apiKey, cps, sendCnt);
            	if (second > diff && sendCnt >= cps) {
            		TimeUnit.MILLISECONDS.sleep(second-diff);
            	}
            	sendCnt = 0;
            	start = System.currentTimeMillis();
            }
        }

        if(CollectionUtils.isNotEmpty(errorRecvInfoLst)) {
            try {
                //CM_MSG Insert
                sParams.put("apiKey", apiKey);
                sParams.put("reqCh", Const.Ch.ALIMTALK);
                sParams.put("productCode", Const.Ch.ALIMTALK.toLowerCase());
                sParams.put("finalCh", Const.Ch.ALIMTALK);
                sParams.put("callback", requestData.getCallback());
                sParams.put("webReqId", requestData.getWebReqId());
                insertCmMsg(sParams, errorRecvInfoLst);
            } catch (Exception e) {
                log.error("{}.sendAlimTalkMsgAsync insertCmMsg Error ==> {}", this.getClass(), e);
            }
        }

        //웹 발송 내역 등록
        if(isAllFail) sParams.put("allFailYn", Const.COMM_YES);
        insertAlimTalkCmWebMsg(rtn, sParams, requestData, recvInfoLst);
    }

    /**
     * 알림톡 테스트 발송 처리
     * @param data
     * @param requestData
     * @param sendList
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public RestResult<Object> testSendAlimTalkMsg(
            Map<String, Object> data
            , AlimTalkRequestData requestData
            , List<RecvInfo> sendList) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        requestData.setWebReqId(StringUtils.EMPTY);  //테스트발송은 웹 요청 아이디를 넣지 않는다.
        Map<String, Object> resultMap = sendAlimTalkMsg(data, requestData, sendList);

        if(isSendSuccess(resultMap)) {
            int successCnt = 0;
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) resultMap.get(ApiConfig.COMMON_DATA_FIELD_NM);
            for(Map<String, Object> dataInfo : dataList) {
                if(!CommonUtils.isEmptyValue(dataInfo, ApiConfig.GW_RESULT_CODE_FIELD_NM)
                        && StringUtils.equals(ApiConfig.GW_API_SUCCESS, CommonUtils.getString(dataInfo.get(ApiConfig.GW_RESULT_CODE_FIELD_NM)))) {
                    successCnt++;
                }
            }
            rtn.setMessage(dataList.size() + "건 중 " + successCnt + "건 발송 성공하였습니다.");
        } else {
            log.warn("{}.testSendAlimTalkMsg Fail ==> response : {}", this.getClass(), resultMap);
            rtn.setFail(CommonUtils.getGWApiFailMsg(resultMap, "테스트 발송이 실패하였습니다."));
        }

        return rtn;
    }

    /**
     * 알림톡 메시지 발송 처리
     * @param data
     * @param requestData
     * @param sendList
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> sendAlimTalkMsg(
            Map<String, Object> data
            , AlimTalkRequestData requestData
            , List<RecvInfo> sendList) throws Exception {

        String corpId = CommonUtils.getStrValue(data, "corpId");
        String projectId = CommonUtils.getStrValue(data, "projectId");
        String apiKey = commonService.getApiKey(corpId, projectId);

        requestData.setRecvInfoLst(sendList);

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("apiKey", apiKey);

        Gson gson = new Gson();
        String jsonString = gson.toJson(requestData);

        return apiInterface.sendMsg(ApiConfig.SEND_ALIM_TALK_API_URI, headerMap, jsonString);
    }

    /**
     * 통합/스마트 데이터 유효성 체크
     * @param rtn
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public SmartRequestData setSmartSendData(RestResult<Object> rtn, Map<String, Object> params) throws Exception {
        SmartRequestData requestData = new SmartRequestData();

        //통합/스마트 정보 Get
        Map<String, Object> tmpltInfo = (Map<String, Object>) generalDao.selectGernalObject(DB.QRY_SELECT_SMART_TMPLT_INFO, params);
        if(tmpltInfo == null || StringUtils.isBlank(CommonUtils.getStrValue(tmpltInfo, "tmpltCode"))) {
            rtn.setFail("유효하지 않은 템플릿 정보입니다.");
            return requestData;
        }

        String senderType = CommonUtils.getStrValue(tmpltInfo, "tmpltType");
        String smartPrdFee = CommonUtils.getStrValue(tmpltInfo, "smartPrdFee");

        //tmpltCode
        requestData.setTmpltCode(CommonUtils.getStrValue(params, "tmpltCode"));

        //campaignId
        requestData.setCampaignId(CommonUtils.getStrValue(params, "campaignId"));

        //webReqId
        String prefix = (StringUtils.equals(senderType, Const.SenderType.SMART) ? Const.WebReqIdPrefix.SMT_PREFIX : Const.WebReqIdPrefix.ITG_PREFIX);
        String webReqId = CommonUtils.getCommonId(prefix, 5);
        requestData.setWebReqId(webReqId);
        params.put("webReqId", webReqId);

        //내부용 데이터 Set
        ObjectMapper mapper = new ObjectMapper();
        List<String> chTypeList = mapper.readValue(CommonUtils.getStrValue(tmpltInfo, "chTypeList"), List.class);
        String chString = chTypeList.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(","));
        params.put("chTypeList", chTypeList);
        params.put("chString", chString);
        params.put("senderType", senderType);
        params.put("smartPrdFee", smartPrdFee);

        //유효성 체크
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<SmartRequestData>> violations = validator.validate(requestData);
        String errorMsg = "";

        for (ConstraintViolation violation : violations) {
            errorMsg += (StringUtils.isNotBlank(errorMsg) ? "\n" : "") + violation.getMessage();
            //log.info("path : [{}], message : [{}]", violation.getPropertyPath(), violation.getMessage());
        }

        if(StringUtils.isNotBlank(errorMsg)) {
            rtn.setFail(errorMsg);
        }

        return requestData;
    }

    /**
     * 통합/스마트 발송 내역 등록
     * @param rtn
     * @param data
     * @param requestData
     * @param recvInfoLst
     * @return
     * @throws Exception
     */
    public RestResult<Object> insertSmartCmWebMsg(RestResult<Object> rtn
            , Map<String, Object> data
            , SmartRequestData requestData
            , List<RecvInfo> recvInfoLst) throws Exception {

        String ch = CommonUtils.getStrValue(data, "chString");
        String corpId = CommonUtils.getStrValue(data, "corpId");
        String projectId = CommonUtils.getStrValue(data, "projectId");
        String rsrvSendYn = CommonUtils.getStrValue(data, "rsrvSendYn");
        String rsrvDateStr = "";
        String allFailYn = CommonUtils.getStrValue(data, "allFailYn");
        String status = (StringUtils.equals(allFailYn, Const.COMM_YES) ? Const.MsgSendStatus.FAIL : Const.MsgSendStatus.COMPLETED);

        if(StringUtils.equals(rsrvSendYn, Const.COMM_YES)) {
            String rsrvYmd = CommonUtils.getStrValue(data, "rsrvDate");
            String rsrvHH = CommonUtils.getStrValue(data, "rsrvHH");
            String rsrvMM = CommonUtils.getStrValue(data, "rsrvMM");
            rsrvDateStr = rsrvYmd+" "+rsrvHH+":"+rsrvMM;

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date rsrvDate = dateFormat.parse(rsrvDateStr);
            Date currentDate = new Date();

            currentDate = DateUtils.addMinutes(currentDate, 10);
            if(currentDate.compareTo(rsrvDate) > 0) {
                rtn.setSuccess(false);
                rtn.setMessage("잘못된 예약시간입니다. 현재시간 10분 이후로 설정해주세요.");
                return rtn;
            }
            if(DateUtil.diffDays(rsrvDate) > Const.SEND_RSRV_LIMIT_DAY) {
                rtn.setFail("잘못된 예약일자입니다. 현재일로 부터 "+Const.SEND_RSRV_LIMIT_DAY+"일 이내로 설정해주세요");
            }
            status = Const.MsgSendStatus.SEND_WAIT;
        }

        requestData.setRecvInfoLst(recvInfoLst);
        Gson gson = new Gson();
        String json = gson.toJson(requestData);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("webReqId", requestData.getWebReqId());
        params.put("corpId", corpId);
        params.put("projectId", projectId);
        params.put("apiKey", commonService.getApiKey(corpId, projectId));
        params.put("chString", ch);
        params.put("msgInfo", json);
        params.put("senderCnt", recvInfoLst.size());
        //params.put("callback", requestData.getCallback());
        params.put("campaignId", requestData.getCampaignId());
        params.put("senderType", CommonUtils.getStrValue(data, "senderType"));
        params.put("status", status);
        params.put("resvSenderYn", rsrvSendYn);
        params.put("reqDt", rsrvDateStr);
        params.put("userId", CommonUtils.getStrValue(data, "userId"));

        int resultCnt = insertCmWebMsg(params);
        if (resultCnt <= 0) {
            log.warn("{}.insertSmartCmWebMsg Fail =>  webReqId : {}", this.getClass(), requestData.getWebReqId());
        }

        return rtn;
    }

    /**
     * 통합/스마트 테스트 발송 처리
     * @param data
     * @param requestData
     * @param sendList
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public RestResult<Object> testSendSmartMsg(
            Map<String, Object> data
            , SmartRequestData requestData
            , List<RecvInfo> sendList) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();

        requestData.setWebReqId(StringUtils.EMPTY);  //테스트발송은 웹 요청 아이디를 넣지 않는다.
        Map<String, Object> resultMap = sendSmartMsg(data, requestData, sendList);

        if(isSendSuccess(resultMap)) {
            int successCnt = 0;
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) resultMap.get(ApiConfig.COMMON_DATA_FIELD_NM);
            for(Map<String, Object> dataInfo : dataList) {
                if(!CommonUtils.isEmptyValue(dataInfo, ApiConfig.GW_RESULT_CODE_FIELD_NM)
                        && StringUtils.equals(ApiConfig.GW_API_SUCCESS, CommonUtils.getString(dataInfo.get(ApiConfig.GW_RESULT_CODE_FIELD_NM)))) {
                    successCnt++;
                }
            }
            rtn.setMessage(dataList.size() + "건 중 " + successCnt + "건 발송 성공하였습니다.");
        } else {
            log.warn("{}.testSendSmartMsg Fail ==> response : {}", this.getClass(), resultMap);
            rtn.setFail(CommonUtils.getGWApiFailMsg(resultMap, "테스트 발송이 실패하였습니다."));
        }

        return rtn;
    }

    /**
     * 통합/스마트 메시지 발송 처리
     * @param data
     * @param requestData
     * @param sendList
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> sendSmartMsg(
            Map<String, Object> data
            , SmartRequestData requestData
            , List<RecvInfo> sendList) throws Exception {

        String corpId = CommonUtils.getStrValue(data, "corpId");
        String projectId = CommonUtils.getStrValue(data, "projectId");
        String apiKey = commonService.getApiKey(corpId, projectId);

        requestData.setRecvInfoLst(sendList);

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("apiKey", apiKey);

        Gson gson = new Gson();
        String jsonString = gson.toJson(requestData);

        return apiInterface.sendMsg(ApiConfig.SEND_SMART_API_URI, headerMap, jsonString);
    }

    /**
     * 통합/스마트 메시지 발송 처리(비동기)
     * @param rtn
     * @param fromIndex
     * @param data
     * @param requestData
     * @param recvInfoLst
     * @param reSendCdList
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Async
    public void sendSmartMsgAsync(RestResult<Object> rtn
            , int fromIndex
            , Map<String, Object> data
            , SmartRequestData requestData
            , List<RecvInfo> recvInfoLst
            , List<Object> reSendCdList) throws Exception {

        List<RecvInfo> errorRecvInfoLst = new ArrayList<RecvInfo>();
        Map<String, Object> responseBody = null;
        Map<String, Object> sParams = new HashMap<String, Object>(data);

        String corpId = CommonUtils.getStrValue(sParams, "corpId");
        String projectId = CommonUtils.getStrValue(sParams, "projectId");
        Map apiData = commonService.getApiKey2(corpId, projectId);
        String apiKey = CommonUtils.getString(apiData.get("apiKey"));
        String strCps = CommonUtils.getString(apiData.get("cps"),"30");
        String jsonString = "";
        boolean isDone = false;
        boolean isServerError = false;
        boolean isAllFail = true;

        Gson gson = new Gson();
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("apiKey", apiKey);

        int retryCnt = NumberUtils.INTEGER_ZERO;
        int cutSize = ApiConfig.DEFAULT_RECV_LIMIT_SIZE;
        int listSize = recvInfoLst.size();
        int toIndex = fromIndex;
        
        int cps = NumberUtils.toInt(strCps, 30);
        if (cps <= 0) cps = 30;
        int sendCnt = 0;
        long start = System.currentTimeMillis();
        long end = 0;

        while (toIndex < listSize) {
            isDone = false;
            isServerError = false;
            toIndex = fromIndex + cutSize;
            try {
                if(toIndex > listSize) toIndex = listSize;
                requestData.setRecvInfoLst(recvInfoLst.subList(fromIndex, toIndex));
                jsonString = gson.toJson(requestData);
                responseBody = apiInterface.sendMsg(ApiConfig.SEND_SMART_API_URI, headerMap, jsonString);
                isDone = isApiRequestAgain(responseBody, reSendCdList);
                isAllFail = !isSendSuccess(responseBody);
//                isDone = true;
//                isAllFail = false;
            } catch (Exception e) {
                log.error("{}.sendSmartMsgAsync API Request Error ==> {}", this.getClass(), e);
                isServerError = true;
                if(retryCnt == ApiConfig.GW_RETRY_CNT) sendMsgErrorNoti(Const.ApiWatchNotiMsg.API_CONNECTION_FAIL);
            }

            if(isDone) {
            	sendCnt++;
                retryCnt = NumberUtils.INTEGER_ZERO;
                fromIndex = toIndex;
            } else if(retryCnt == ApiConfig.GW_RETRY_CNT) {
                errorRecvInfoLst.addAll(recvInfoLst.subList(fromIndex, toIndex));
                retryCnt = NumberUtils.INTEGER_ZERO;
                fromIndex = toIndex;
            } else {
            	sendCnt++;
                retryCnt++;
                toIndex = fromIndex;
                if(!isServerError) TimeUnit.MILLISECONDS.sleep(ApiConfig.GW_RETRY_DELAY_MILLISECONDS);
            }
            
            if (sendCnt >= cps || toIndex >= listSize) {
            	end = System.currentTimeMillis();
            	long diff  = end - start;
                log.info("API sendMsg apiKey : {}, cps : {}, sendCnt : {}", apiKey, cps, sendCnt);
            	if (second > diff && sendCnt >= cps) {
            		TimeUnit.MILLISECONDS.sleep(second-diff);
            	}
            	sendCnt = 0;
            	start = System.currentTimeMillis();
            }
        }

        if(CollectionUtils.isNotEmpty(errorRecvInfoLst)) {
            try {
                //CM_MSG Insert
                sParams.put("apiKey", apiKey);
                sParams.put("reqCh", Const.Ch.SMART);
                sParams.put("productCode", CommonUtils.getStrValue(sParams, "chString").toLowerCase());
                //sParams.put("finalCh", Const.Ch.ALIMTALK);
                //sParams.put("callback", requestData.getCallback());
                sParams.put("webReqId", requestData.getWebReqId());
                insertCmMsg(sParams, errorRecvInfoLst);
            } catch (Exception e) {
                log.error("{}.sendSmartMsgAsync insertCmMsg Error ==> {}", this.getClass(), e);
            }
        }

        //웹 발송 내역 등록
        if(isAllFail) sParams.put("allFailYn", Const.COMM_YES);
        insertSmartCmWebMsg(rtn, sParams, requestData, recvInfoLst);
    }

    /**
     * 스마트 템플릿 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public RestResult<Object> selectSmartTmpltList(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        //사용 채널 그룹 정보 조회
        String useChGrpInfoStr = CommonUtils.getString(generalDao.selectGernalObject(DB.QRY_SELECT_USE_CH_GRP_INFO, params));
        if(StringUtils.isEmpty(useChGrpInfoStr)) {
            rtn.setFail("이용 가능한 채널이 존재하지 않습니다.");
            return rtn;
        }

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> useChGrpInfo = mapper.readValue(useChGrpInfoStr, Map.class);
        List<String> useChGrps = new ArrayList<String>();

        if(useChGrpInfo != null) {
            for(String key : useChGrpInfo.keySet()) {
                if(StringUtils.equals(useChGrpInfo.get(key), Const.COMM_YES)
                        && Const.chGrp.containsKey(key)) {
                    useChGrps.addAll(Const.chGrp.get(key));
                }
            }
        }
        params.put("useChGrps", useChGrps);

        if(params.containsKey("pageNo")
                && CommonUtils.isNotEmptyObject(params.get("pageNo"))
                && params.containsKey("listSize")
                && CommonUtils.isNotEmptyObject(params.get("listSize"))) {
            rtn.setPageProps(params);
            if(rtn.getPageInfo() != null) {
                //카운트 쿼리 실행
                int listCnt = generalDao.selectGernalCount(DB.QRY_SELECT_SMART_TMPLT_LIST_CNT, params);
                rtn.getPageInfo().put("totCnt", listCnt);
            }
        }

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_SMART_TMPLT_LIST, params);
        rtn.setData(rtnList);

        return rtn;
    }

    /**
     * 스마트 템플릿 정보 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectSmartTmpltInfo(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        rtn.setData(generalDao.selectGernalObject(DB.QRY_SELECT_SMART_TMPLT_INFO, params));
        return rtn;
    }

    /**
     * 채널별 변수바인딩
     * @param chMappingVarList
     * @param recvInfoLst
     */
    @SuppressWarnings("unchecked")
    public void setMergeDataByChannel(List<Map<String, Object>> chMappingVarList, List<RecvInfo> recvInfoLst) {
        Map<String, Object> mergeData = new HashMap<String, Object>();
        Map<String, Object> tempData = new HashMap<String, Object>();
        String ch = "";
        List<String> varNms = null;

        for(RecvInfo recvInfo : recvInfoLst) {
            mergeData = new HashMap<String, Object>();

            for(Map<String, Object> chMappingVar : chMappingVarList) {
                ch = CommonUtils.getStrValue(chMappingVar, "ch");
                varNms = (List<String>) chMappingVar.get("varNms");
                tempData = new HashMap<String, Object>();

                for(String key : varNms) {
                    tempData.put(key, CommonUtils.getStrValue(recvInfo.getMergeData(), key));
                }
                mergeData.put(ch, tempData);
            }
            recvInfo.setMergeData(mergeData);
        }
    }

    /**
     * 채널 사용 가능 여부
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectValidUseChGrp(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        String chGrp = CommonUtils.getStrValue(params, "chGrp");
        if(StringUtils.isBlank(chGrp)) {
            rtn.setFail();
            log.error("{}.selectValidUseChGrp Invalid parameter information. params ==> {}", this.getClass(), params);
            return rtn;
        }

        //사용 채널 그룹 정보 조회
        Map<String, Object> sParams = new HashMap<String, Object>(params);
        sParams.put("chGrp", "\""+chGrp+"\"");
        rtn.setData(CommonUtils.getString(generalDao.selectGernalObject(DB.QRY_SELECT_USE_CH_GRP_INFO, sParams)));
        return rtn;
    }

    /**
     * 스마트 템플릿 친구톡 상품 정보 조회
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> selectSmartTmpltFrndPrdtInfo(Map<String, Object> params) throws Exception {
        return (Map<String, Object>) generalDao.selectGernalObject(DB.QRY_SELECT_SMART_TMPLT_FRND_PRDT_INFO, params);
    }

    /**
     * RCS 정보 조회
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> selectSmartTmpltRcsInfo(Map<String, Object> params) throws Exception {
        return (Map<String, Object>) generalDao.selectGernalObject(DB.QRY_SELECT_SMART_TMPLT_RCS_INFO, params);
    }

    /**
     * RCS 메시지 변수 바인딩
     * @param recvInfoLst
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<RecvInfo> replaceRcsMsgVar(List<RecvInfo> recvInfoLst, Map<String, Object> params) throws Exception{
        log.info(Const.PRIVACY_MARKER, "{}.replaceRcsMsgVar start. recvInfoLst ==> {}", this.getClass(), recvInfoLst);

        List<RecvInfo> rtnList = new ArrayList<RecvInfo>(recvInfoLst);
        Map<String, Object> rcsltInfo = (Map<String, Object>) generalDao.selectGernalObject(DB.QRY_SELECT_SMART_TMPLT_RCS_INFO, params);
        String rcsPrdType = CommonUtils.getStrValue(rcsltInfo, "rcsPrdType");
        String tmpltMergeDataStr = CommonUtils.getStrValue(rcsltInfo, "mergeData");

        // 미승인형 및 서술형(승인)
        if (StringUtils.equals(Const.RcsPrd.STYEL, rcsPrdType) == false) {
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            List<Map<String, Object>> tmpltMergeDataList = gson.fromJson(tmpltMergeDataStr, new TypeToken<List<Map<String, Object>>>(){}.getType());

            if(CollectionUtils.isNotEmpty(tmpltMergeDataList)) {
                Map<String, Object> rcsVar = null;
                Map<String, Object> rcsMergeData = null;
                Map<String, Object> sendMergeData = null;
                StringSubstitutor ss;
                int carouselCnt = 1;

                for(RecvInfo recvInfo : rtnList) {
                    rcsMergeData = new HashMap<String, Object>();
                    rcsVar = new HashMap<String, Object>();
                    sendMergeData = recvInfo.getMergeData();


                    if(sendMergeData.containsKey(Const.Ch.RCS)) {
                        rcsVar = (Map<String, Object>) sendMergeData.get(Const.Ch.RCS);
                        ss = new StringSubstitutor(rcsVar, ApiConfig.RCS_VAR_START, ApiConfig.RCS_VAR_END);

                        if(StringUtils.equals(Const.RcsPrd.CAROUSEL_SMALL, rcsPrdType) || StringUtils.equals(Const.RcsPrd.CAROUSEL_TALL, rcsPrdType)) {
                            carouselCnt = 1;
                            for(Map<String, Object> tmpltMergeData : tmpltMergeDataList) {
                                rcsMergeData.put("title"+carouselCnt, ss.replace(CommonUtils.getStrValue(tmpltMergeData, "title")));
                                rcsMergeData.put("description"+carouselCnt, ss.replace(CommonUtils.getStrValue(tmpltMergeData, "description")));
                               	rcsMergeData.put("media"+carouselCnt, ss.replace(CommonUtils.getStrValue(tmpltMergeData, "media")));
                                carouselCnt++;
                            }
                            sendMergeData.put(Const.Ch.RCS, rcsMergeData);
                        } else {
                        	if (StringUtils.equals(Const.RcsPrd.NARRATIVE, rcsPrdType)) {
                        		sendMergeData.remove(Const.Ch.RCS);
                        		sendMergeData.put("description", ss.replace(CommonUtils.getStrValue(tmpltMergeDataList.get(0), "description")));
                        	} else if (StringUtils.equals(Const.RcsPrd.NARRATIVE_MEDIUM, rcsPrdType) || StringUtils.equals(Const.RcsPrd.NARRATIVE_TALL, rcsPrdType)) {
                                rcsMergeData.put("title", ss.replace(CommonUtils.getStrValue(tmpltMergeDataList.get(0), "title")));
                                rcsMergeData.put("description", ss.replace(CommonUtils.getStrValue(tmpltMergeDataList.get(0), "description")));
                               	rcsMergeData.put("media", ss.replace(CommonUtils.getStrValue(tmpltMergeDataList.get(0), "media")));
                               	sendMergeData.put(Const.Ch.RCS, rcsMergeData);
                        	} else {
                                rcsMergeData.put("title", ss.replace(CommonUtils.getStrValue(tmpltMergeDataList.get(0), "title")));
                                rcsMergeData.put("description", ss.replace(CommonUtils.getStrValue(tmpltMergeDataList.get(0), "description")));
                                sendMergeData.put(Const.Ch.RCS, rcsMergeData);
                        	}
                        }
                    }
                }
            }
        }

        return rtnList;
    }

    /**
     * 수신거부번호관리 등록
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public RestResult<Object> insertRejectPhone(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        //중복확인
        if(generalDao.selectGernalCount(DB.QRY_SELECT_DUPL_REJECT_PHONE, params) > 0) {
            String rejectNum = CommonUtils.getStrValue(params, "phone");
            rtn.setFail("이미 등록되어 있는 수신거부번호입니다."+(StringUtils.isNoneBlank(rejectNum) ? "("+rejectNum+")" : ""));
            return rtn;
        }
        if (generalDao.insertGernal(DB.QRY_INSERT_REJECT_PHONE, params) > 0) {
            rtn.setSuccess(true);
            rtn.setData(params);
        } else {
            rtn.setFail("실패하였습니다.");
        }

        return rtn;
    }

    /**
     * 수신거부번호관리 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectRejectPhoneList(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_REJECT_PHONE_LIST, params);
        rtn.setData(rtnList);
        return rtn;
    }

    /**
     * 수신거부번호 삭제
     * @param params
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
    public RestResult<Object> deleteRejectPhoneList(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        if (generalDao.deleteGernal(DB.QRY_DELETE_REJECT_PHONE_LIST, params) > 0) {
            rtn.setSuccess(true);
            rtn.setData(params);
        } else {
            rtn.setFail("실패하였습니다.");
        }

        return rtn;
    }

    /**
     * 알림서비스
     */
    @SuppressWarnings("unchecked")
    public void sendNoti(Map<String,Object> params) {

        try {
            /** header */
            Map<String, Object> headerMap = new HashMap<String, Object>();
            headerMap.put("X-API-KEY", ApiConfig.API_WATCH_NOTI_KEY);

            /** body */
            Map<String, Object> bodyMap = new HashMap<String, Object>();
            bodyMap.put("notiCode", ApiConfig.API_WATCH_NOTI_CODE);
            bodyMap.put("recvInfoLst", params.get("recvInfoLst"));
            bodyMap.put("sendChLst", params.get("sendChLst"));

            log.info("{}.sendNoti api request ==>> header : {}, body : {}", this.getClass(), headerMap, bodyMap);
            Map<String, Object> result = apiInterface.etcPost(ApiConfig.NOTI_SERVER_DOMAIN+ApiConfig.SEND_NOTI_URI, bodyMap, headerMap);
            log.info("{}.sendNoti api response : {}", this.getClass(), result);

            if(isSendSuccess(result) == false) {
                String failMsg = CommonUtils.getString(result.get(ApiConfig.GW_RESULT_MESSAGE_FIELD_NM));
                throw new Exception("sendNoti fail ===> " + failMsg);
            }

        } catch (Exception e) {
            log.error("{}.sendNoti : {}, Error : {}", this.getClass(), e);
        }
    }

    /**
     * 발송 오류 감시항목 메시지 전송
     * @param notiMsg
     */
    @SuppressWarnings({ "unchecked" })
    public void sendMsgErrorNoti(ApiWatchNotiMsg notiMsg) {

        log.info("{}.sendMsgErrorNoti Start ====>>>  : {}", this.getClass(), notiMsg);

        try {

            Map<String,Object> params = new HashMap<String, Object>();
            Map<String,Object> sParams = new HashMap<String, Object>();

            String callback = ApiConfig.API_WATCH_CALLBACK;
            String msg = notiMsg.getMsg();

            sParams.put("notiCode", ApiConfig.API_WATCH_NOTI_CODE);
            List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_NOTI_RECIPIENT, sParams);

            /** 수신자 설정 */
            ArrayList<Map<String, Object>> recvInfoLst = new ArrayList<Map<String,Object>>();
            Map<String, Object> rcvMap = new HashMap<String, Object>();

            for(Object o : rtnList) {
                rcvMap = (Map<String, Object>) o;
                recvInfoLst.add(rcvMap);
            }

            /** 발송 내용 설정 */
            ArrayList<Map<String, Object>> sendChLst = new ArrayList<Map<String,Object>>();
            Map<String, Object> sendChMap = new HashMap<String, Object>();
            sendChMap.put("ch", Const.NotiCh.SMS);
            sendChMap.put("contents", msg);
            sendChMap.put("callback", callback);
            sendChLst.add(sendChMap);

            params.put("recvInfoLst", recvInfoLst);
            params.put("sendChLst", sendChLst);
            sendNoti(params);

        } catch (Exception e) {
            log.error("{}.sendMsgErrorNoti : {}, Error : {}", this.getClass(), e);
        }

        log.info("{}.sendMsgErrorNoti End ====>>>  : {}", this.getClass(), notiMsg);
    }

    /**
     * 스마트 채널 상품 리스트 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectSmartChProductList(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_SMART_CH_PRODUCT, params);
        rtn.setData(rtnList);
        return rtn;
    }
    
    /**
     * RCS 비동기 발송
     * @param params
     * @return
     * @throws Exception
     */
    @Async
	public void sendRcs(Map<String, Object> params, int fromIndex, Map<String, Object> apiMap, Map<String, Object> headerMap,
			ArrayList<Map<String, Object>> recvInfoLst, ArrayList<Map<String, Object>> fbInfoLst, List<Object> reSendCdList) throws Exception {
		Map<String, Object> responseBody = null;
		String jsonString = "";
		String failMsg = "";
		boolean isDone = false;
		boolean isServerError = false;
		boolean isAllFail = true;
		List<Map<String, Object>> errorRecvInfoLst = new ArrayList<Map<String, Object>>();
		Map<String, Object> data = (Map<String, Object>) params.get("data");
		Map<String, Object> sParams = new HashMap<String, Object>(data);

		Gson gson = new Gson();

		int retryCnt = NumberUtils.INTEGER_ZERO;
		int cutSize = ApiConfig.DEFAULT_RECV_LIMIT_SIZE;
		int listSize = recvInfoLst.size();
		int toIndex = fromIndex;
		
        String corpId = CommonUtils.getStrValue(params, "corpId");
        String projectId = CommonUtils.getStrValue(params, "projectId");
        Map apiData = commonService.getApiKey2(corpId, projectId);
        String apiKey = CommonUtils.getString(apiData.get("apiKey"));
        String strCps = CommonUtils.getString(apiData.get("cps"),"30");
        int cps = NumberUtils.toInt(strCps, 30);
        if (cps <= 0) cps = 30;
        int sendCnt = 0;
        long start = System.currentTimeMillis();
        long end = 0;
        long second = 1000;
		
		params.put("recvInfoLstCnt", listSize);
		apiMap.put("msgRecvInfoLst", recvInfoLst);
		apiMap.put("msgFbInfoLst", fbInfoLst);
		
		while (toIndex < listSize) {
			isDone = false;
			isServerError = false;
			toIndex = fromIndex + cutSize;
			try {
				if(toIndex > listSize) toIndex = listSize;
				apiMap.put("recvInfoLst", recvInfoLst.subList(fromIndex, toIndex));
				if(fbInfoLst.size() > 0) {
					apiMap.put("fbInfoLst", fbInfoLst.subList(fromIndex, toIndex));
				}
				jsonString = gson.toJson(apiMap);
				responseBody = apiInterface.sendMsg(ApiConfig.SEND_RCS_API_URI, headerMap, jsonString);
				isDone = isApiRequestAgain(responseBody, reSendCdList);
				isAllFail = !isSendSuccess(responseBody);
				if(isAllFail) failMsg = CommonUtils.getString(responseBody.get("message"));
//                isDone = true;
//                isAllFail = false;
			} catch (Exception e) {
				isServerError = true;
				if(retryCnt == ApiConfig.GW_RETRY_CNT) sendMsgErrorNoti(Const.ApiWatchNotiMsg.API_CONNECTION_FAIL);
			}
			
			if(isDone) {
            	sendCnt++;
				retryCnt = NumberUtils.INTEGER_ZERO;
				fromIndex = toIndex;
			} else if(retryCnt == ApiConfig.GW_RETRY_CNT) {
				errorRecvInfoLst.addAll(recvInfoLst.subList(fromIndex, toIndex));
				retryCnt = NumberUtils.INTEGER_ZERO;
				fromIndex = toIndex;
			} else {
            	sendCnt++;
				retryCnt++;
				toIndex = fromIndex;
				if(!isServerError) TimeUnit.MILLISECONDS.sleep(ApiConfig.GW_RETRY_DELAY_MILLISECONDS);
			}
	        
	        if (sendCnt >= cps || toIndex >= listSize) {
	        	end = System.currentTimeMillis();
	        	long diff  = end - start;
	            log.info("API sendMsg apiKey : {}, cps : {}, sendCnt : {}", apiKey, cps, sendCnt);
	        	if (second > diff && sendCnt >= cps) {
	        		TimeUnit.MILLISECONDS.sleep(second-diff);
	        	}
	        	sendCnt = 0;
	        	start = System.currentTimeMillis();
	        }
		}
		
		if(CollectionUtils.isNotEmpty(errorRecvInfoLst)) {
			try {
				//CM_MSG Insert
				sParams.put("corpId", params.get("corpId"));
				sParams.put("projectId", params.get("projectId"));
				sParams.put("reqCh", Const.Ch.RCS);
				sParams.put("productCode", Const.Ch.RCS.toLowerCase());
				sParams.put("finalCh", Const.Ch.RCS);
				sParams.put("callback", data.get("callback"));
				sParams.put("webReqId", data.get("webReqId"));
				rcsTemplateSendSvc.insertCmMsg(sParams, errorRecvInfoLst);
			} catch (Exception e) {
				log.error("{}.sendRCSMsgAsync insertCmMsg Error ==> {}", this.getClass(), e);
			}
		}
		
		//웹 발송 내역 등록
		if(isAllFail) {
			rcsTemplateSendSvc.insertPushCmWebMsg(headerMap, apiMap, params, "FAIL");
		} else {
			rcsTemplateSendSvc.insertPushCmWebMsg(headerMap, apiMap, params, "COMPLETED");
		}
		
	}

}