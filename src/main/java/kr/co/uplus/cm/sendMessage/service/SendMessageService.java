package kr.co.uplus.cm.sendMessage.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.sendMessage.dto.FbInfo;
import kr.co.uplus.cm.sendMessage.dto.PushMsg;
import kr.co.uplus.cm.sendMessage.dto.RecvInfo;
import kr.co.uplus.cm.sendMessage.dto.SmsRequestData;
import kr.co.uplus.cm.sendMessage.dto.PushRequestData;
import kr.co.uplus.cm.utils.ApiInterface;
import kr.co.uplus.cm.utils.CommonUtils;
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

        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_CALLBACK_LIST, params);
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
     * Get 푸시 수신자 리스트
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<RecvInfo> getRecvInfoLst(Map<String, Object> params, MultipartFile excelFile) throws Exception{
        List<RecvInfo> recvInfoLst = new ArrayList<RecvInfo>();
        long cliKey = NumberUtils.LONG_ONE;

        if(params.containsKey("cuInputType")) {

            //직접입력 && 주소록
            if(StringUtils.equals("DICT", (String)params.get("cuInputType"))
                    || StringUtils.equals("ADDR", (String)params.get("cuInputType"))) {
                List<Map<String, Object>> objList = (List<Map<String, Object>>) params.get("recvInfoLst");
                RecvInfo recvInfo = null;

                ObjectMapper mapper = new ObjectMapper();
                for(Map<String, Object> recvObj : objList) {
                    recvInfo = mapper.convertValue(recvObj, RecvInfo.class);
                    recvInfo.setCliKey(String.valueOf(cliKey++));
                    recvInfoLst.add(recvInfo);
                }

            //엑셀
            } else if(StringUtils.equals("EXCEL", (String)params.get("cuInputType"))) {

                //read excelFile
                List<Map<String, Object>> excelList = null;
                List<String> colKeys = new ArrayList<String>();
                List<String> contsVarNms = null;

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
                excelList = commonService.getExcelDataList(excelFile, 2, colKeys);

                RecvInfo recvInfo = null;
                Map<String, String> mergeData = null;
                for(Map<String, Object> excelInfo : excelList) {
                    recvInfo = new RecvInfo();
                    recvInfo.setCliKey(String.valueOf(cliKey++));
                    if(excelInfo.containsKey("cuid")) recvInfo.setCuid((String) excelInfo.get("cuid"));
                    if(excelInfo.containsKey("phone")) recvInfo.setPhone((String) excelInfo.get("phone"));

                    mergeData = new HashMap<String, String>();
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
                List<Object> sltObjList = generalDao.selectGernalList(DB.QRY_SELECT_ALL_ADDR_CU_LIST, params);
                recvInfoLst = (List<RecvInfo>)(Object)sltObjList;
            }
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
     * TODO : G/W 에서 API 만들면 API 호출로 교체
     * @return
     */
    public BigDecimal getRmAmount() {
        return new BigDecimal("100000");
    }

    /**
     * 발송 가격 조회
     * @param sendCnt
     * @return
     * @throws Exception
     */
    public BigDecimal selectMsgFeePerOne(Map<String, Object> params) throws Exception {
        Object selectObject = generalDao.selectGernalObject(DB.QRY_SELECT_MSG_FEE_PER_ONE, params);
        return (BigDecimal) selectObject;
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

        //appId
        pushRequestData.setAppId(CommonUtils.getStrValue(params, "appId"));

        //캠페인 ID
        pushRequestData.setCampaignId(CommonUtils.getStrValue(params, "campaignId"));

        //발송정책
        pushRequestData.setServiceCode(CommonUtils.getStrValue(params, "serviceCode"));

        //푸시 메시지
        String pushContent = (CommonUtils.getStrValue(params, "pushContent"));
        String rcvblcNumber = (CommonUtils.getStrValue(params, "rcvblcNumber"));
        String msgKind = (CommonUtils.getStrValue(params, "msgKind"));
        String pushBody = pushContent;

        if(StringUtils.equals(msgKind, Const.MsgKind.AD)
                && StringUtils.isNotBlank(rcvblcNumber)) {
            pushBody += "\n" +  rcvblcNumber;
        }

        PushMsg pushMsg = new PushMsg();
        pushMsg.setTitle(CommonUtils.getStrValue(params, "pushTitle"));
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
            String fbMsg = CommonUtils.getStrValue(fbInfo, "msg");
            String fbRcvblcNumber = CommonUtils.getStrValue(fbInfo, "rcvblcNumber");
            String fbMsgBody = fbMsg;

            if(StringUtils.equals(msgKind, Const.MsgKind.AD)
                    && StringUtils.isNotBlank(fbRcvblcNumber)) {
                fbMsgBody += "\n" +  fbRcvblcNumber;
            }

            FbInfo pushFbInfo = new FbInfo();
            pushFbInfo.setCh(rplcSendType);
            pushFbInfo.setMsg(fbMsgBody);

            if(StringUtils.equals(rplcSendType, Const.RplcSendType.LMS)) {
                pushFbInfo.setTitle(CommonUtils.getStrValue(fbInfo, "title"));
            } else if(StringUtils.equals(rplcSendType, Const.RplcSendType.MMS)) {
                pushFbInfo.setTitle(CommonUtils.getStrValue(fbInfo, "title"));
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
        String rsrvSendYn = (CommonUtils.getStrValue(data, "rsrvSendYn"));
        String rsrvDateStr = "";
        String status = Const.MsgSendStatus.COMPLETED;

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

        int resultCnt = insertCmWebMsg(params);

        if (resultCnt <= 0) {
            log.info("{}.insertPushCmWebMsg Fail =>  webReqId : {}", this.getClass(), pushRequestData.getWebReqId());
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

        if(!CommonUtils.isEmptyValue(resultMap, "rslt")
                && StringUtils.equals(Const.API_SUCCESS, CommonUtils.getString(resultMap.get("rslt")))) {

            try {
                int successCnt = 0;
                List<Map<String, Object>> dataList = (List<Map<String, Object>>) resultMap.get("data");
                for(Map<String, Object> dataInfo : dataList) {
                    if(!CommonUtils.isEmptyValue(dataInfo, "rsltCode")
                            && StringUtils.equals(Const.API_SUCCESS, CommonUtils.getString(dataInfo.get("rsltCode")))) {
                        successCnt++;
                    }
                }
                rtn.setMessage(dataList.size() + "건 중 " + successCnt + "건 발송 성공하였습니다.");
            } catch (Exception e) {
                log.error("{}.testSendPushMsg success message Error ==> {}", this.getClass(), e);
            }

        } else {
            log.warn("{}.testSendPushMsg Fail ==> response : {}", this.getClass(), resultMap);
            rtn.setFail("푸시 테스트 발송이 실패하였습니다.");
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

        return apiInterface.sendMsg(Const.SEND_PUSH_API_URI, headerMap, jsonString);
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
    @Async
    public void sendPushMsgAsync(RestResult<Object> rtn
            , int fromIndex
            , Map<String, Object> data
            , PushRequestData pushRequestData
            , List<RecvInfo> recvInfoLst) throws Exception {

        String corpId = CommonUtils.getStrValue(data, "corpId");
        String projectId = CommonUtils.getStrValue(data, "projectId");
        String apiKey = commonService.getApiKey(corpId, projectId);
        String jsonString = "";

        Gson gson = new Gson();
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("apiKey", apiKey);

        int cutSize = Const.DEFAULT_RECV_LIMIT_SIZE;
        int listSize = recvInfoLst.size();
        int toIndex = fromIndex;

        while (toIndex < listSize) {
            toIndex = fromIndex + cutSize;
            if(toIndex > listSize) toIndex = listSize;

            pushRequestData.setRecvInfoLst(recvInfoLst.subList(fromIndex, toIndex));
            jsonString = gson.toJson(pushRequestData);
            apiInterface.sendMsg(Const.SEND_PUSH_API_URI, headerMap, jsonString);
            fromIndex = toIndex;
        }

        //웹 발송 내역 등록
        insertPushCmWebMsg(rtn, data, pushRequestData, recvInfoLst);
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

        //callback(TODO : 수정필요할 수도 있다.)
        requestData.setCallback(CommonUtils.getStrValue(params, "callback"));

        //캠페인 ID
        requestData.setCampaignId(CommonUtils.getStrValue(params, "campaignId"));

        //SMS 메시지
        String smsContent = CommonUtils.getStrValue(params, "smsContent");
        String rcvblcNumber = CommonUtils.getStrValue(params, "rcvblcNumber");
        String msgKind = CommonUtils.getStrValue(params, "msgKind");
        String msg = smsContent;

        if(StringUtils.equals(msgKind, Const.MsgKind.AD)
                && StringUtils.isNotBlank(rcvblcNumber)) {
            msg += "\n" +  rcvblcNumber;
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
            rtn.setSuccess(false);
            rtn.setMessage(errorMsg);
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
        String rsrvSendYn = (CommonUtils.getStrValue(data, "rsrvSendYn"));
        String rsrvDateStr = "";
        String status = Const.MsgSendStatus.COMPLETED;

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

        int resultCnt = insertCmWebMsg(params);

        if (resultCnt <= 0) {
            log.info("{}.insertPushCmWebMsg Fail =>  webReqId : {}", this.getClass(), requestData.getWebReqId());
        }

        return rtn;
    }

    /**
     * SMS 메시지 발송 비동기 처리
     * @param rtn
     * @param fromIndex
     * @param data
     * @param pushRequestData
     * @param recvInfoLst
     * @throws Exception
     */
    @Async
    public void sendSmsMsgAsync(RestResult<Object> rtn
            , int fromIndex
            , Map<String, Object> data
            , SmsRequestData requestData
            , List<RecvInfo> recvInfoLst) throws Exception {

        String corpId = CommonUtils.getStrValue(data, "corpId");
        String projectId = CommonUtils.getStrValue(data, "projectId");
        String apiKey = commonService.getApiKey(corpId, projectId);
        String jsonString = "";

        Gson gson = new Gson();
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("apiKey", apiKey);

        int cutSize = Const.DEFAULT_RECV_LIMIT_SIZE;
        int listSize = recvInfoLst.size();
        int toIndex = fromIndex;

        while (toIndex < listSize) {
            toIndex = fromIndex + cutSize;
            if(toIndex > listSize) toIndex = listSize;

            requestData.setRecvInfoLst(recvInfoLst.subList(fromIndex, toIndex));
            jsonString = gson.toJson(requestData);
            apiInterface.sendMsg(Const.SEND_SMS_API_URI, headerMap, jsonString);
            fromIndex = toIndex;
        }

        //웹 발송 내역 등록
        insertSmsCmWebMsg(rtn, data, requestData, recvInfoLst);
    }


}
