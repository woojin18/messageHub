package kr.co.uplus.cm.sendMessage.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
     * 문자 발신 번호 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectCallbackList(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        Map<String, Object> sParams = new HashMap<String, Object>(params);

        sParams.put("approvalStatus", Const.ApprovalStatus.APPROVE);
        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_MMS_CALLBACK_LIST, sParams);
        rtn.setData(rtnList);

        return rtn;
    }

    /**
     * RCS 발신 번호 조회
     * @param params
     * @return
     * @throws Exception
     */
    public RestResult<Object> selectRcsCallbackList(Map<String, Object> params) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        Map<String, Object> sParams = new HashMap<String, Object>(params);

        sParams.put("approvalStatus", Const.ApprovalStatus.APPROVE);
        List<Object> rtnList = generalDao.selectGernalList(DB.QRY_SELECT_RCS_CALLBACK_LIST, sParams);
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

                // excel업로드 중복 데이터 제거 (전화번호, 앱ID가 중복인 경우 해당 번호의 중복 데이터를 지움)
                // 전화번호, 앱 아이디가 전부 들어가는경우만 case 처리
                if(params.containsKey("requiredCuid") && (Boolean) params.get("requiredCuid")
                	&& params.containsKey("requiredCuPhone") && (Boolean) params.get("requiredCuPhone")) {

                	// tempList -> 중복 키가 들어가는 list 생성
                	// resultTemplist -> 중복 데이터 제거된 실제 excel 데이터
                	List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
                	List<Map<String, Object>> resultTemplist = new ArrayList<Map<String, Object>>();
                	for(Map<String, Object> map : excelList) {
                		Map<String, Object> tempMap = new HashMap<String, Object>();
                		tempMap.put("cuid", map.get("cuid"));
                		tempMap.put("phone", map.get("phone"));
                		tempList.add(tempMap);
                	}
                	Set<Map<String, Object>> excelSet = new HashSet<>(tempList);
                	List<Map<String, Object>> overlapList = new ArrayList<Map<String, Object>>(excelSet);
            		for(Map<String, Object> overlapMap : overlapList) {
                    	for(Map<String, Object> map : excelList) {
                    		if(CommonUtils.getString(overlapMap.get("cuid")).equals(CommonUtils.getString(map.get("cuid")))
                    			&& CommonUtils.getString(overlapMap.get("phone")).equals(CommonUtils.getString(map.get("phone")))) {
                    			resultTemplist.add(map);
                    			break;
                    		}
                    	}
            		}
                	excelList.clear();
                	excelList = resultTemplist;
                } else {
                	if(params.containsKey("requiredCuid") && (Boolean) params.get("requiredCuid")) {
                        excelList = CommonUtils.distinctArray(excelList, "cuid");
                    }
                    if(params.containsKey("requiredCuPhone") && (Boolean) params.get("requiredCuPhone")) {
                        excelList = CommonUtils.distinctArray(excelList, "phone");
                    }
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

            // 단축URL 여부 체크
    	    if(fbMsgBody.contains("#URL{"))
    	    	pushRequestData.setClickUrlYn("Y");

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

        // 단축URL 여부 체크
	    if(pushContent.contains("#URL{"))
	    	pushRequestData.setClickUrlYn("Y");

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

            currentDate = DateUtils.addMinutes(currentDate, 9);

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
            params.put("finalCh", finalCh);
            params.put("phone", recvInfo.getPhone());
            params.put("pushAppId", pushAppId);
            params.put("pushCuid", recvInfo.getCuid());
            params.put("callback", callback);
            params.put("webReqId", webReqId);
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

        // 단축URL 여부 체크
        if(smsContent.contains("#URL{"))
        	requestData.setClickUrlYn("Y");

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

            currentDate = DateUtils.addMinutes(currentDate, 9);
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
    public MmsRequestData setMmsSendData(RestResult<Object> rtn, Map<String, Object> params) throws Exception {
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
        String rsrvSendYn = (CommonUtils.getStrValue(params, "rsrvSendYn"));
        String rsrvDate = "";
        String rsrvHH = "";
        String rsrvMM = "";
        String rsrvDt = "";
        if(rsrvSendYn.equals("Y")){
            rsrvDate = (CommonUtils.getStrValue(params, "rsrvDate"));
            rsrvHH = (CommonUtils.getStrValue(params, "rsrvHH"));
            rsrvMM = (CommonUtils.getStrValue(params, "rsrvMM"));
            rsrvDt = rsrvDate + " " + rsrvHH + ":" + rsrvMM + ":00";
        }

        if(params.containsKey("imgInfoList")) {
            imgInfoList = (List<Map<String, Object>>) params.get("imgInfoList");
            int imgExpCnt = 0;
            for(Map<String, Object> imgInfo : imgInfoList) {
                if(imgInfo.containsKey("fileId")) {
                	// 이미지가 있는경우 이미지 세팅전 해당 이미지의 사용여부를 확인하고 이미지의 유효기간이 지난경우 예외처리
                	// 즉시 발송인경우 현재시간과 비교하여 사용여부를체크, 예약 발송인경우 해당 예약 발송기간에 따른 이미지 사용여부를 체크
                	Map<String, Object> paramMap = new HashMap<String, Object>();
                	paramMap.put("ch", "mms");
                	paramMap.put("fileId", imgInfo.get("fileId"));
                	paramMap.put("rsrvSendYn", rsrvSendYn);
                	paramMap.put("rsrvDt", rsrvDt);
                	int imgCnt = generalDao.selectGernalCount(DB.QRY_SELECT_EXP_IMG_CNT, paramMap);
                	if(imgCnt>0) imgExpCnt++;
                    requestData.getFileIdLst().add(CommonUtils.getStrValue(imgInfo, "fileId"));
                }
            }
            if(imgExpCnt != imgInfoList.size()) {
            	rtn.setSuccess(false);
            	rtn.setFail("선택하신 이미지를 사용할 수 없습니다. 이미지를 다시 선택해 주세요.");
            }
        }

	    // 단축URL 여부 체크
	    if(smsContent.contains("#URL{"))
	    	requestData.setClickUrlYn("Y");

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

            currentDate = DateUtils.addMinutes(currentDate, 9);
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
        String frndTalkContent = CommonUtils.getStrValue(params, "frndTalkContent");
        requestData.setMsg(frndTalkContent);

        //카카오톡 발신 프로필키
        requestData.setSenderKey(CommonUtils.getStrValue(params, "senderKey"));

        //이미지파일관련
        requestData.setFileId(CommonUtils.getStrValue(params, "fileId"));
        requestData.setWideImageYn(CommonUtils.getStrValue(params, "wideImgYn"));
        if(StringUtils.isNotBlank(requestData.getFileId())) {
            requestData.getImage().setImgUrl(CommonUtils.getStrValue(params, "imgUrl"));
            requestData.getImage().setImgLink(CommonUtils.getStrValue(params, "imgLink"));
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

            // 단축URL 여부 체크
    	    if(fbMsgBody.contains("#URL{"))
    	    	requestData.setClickUrlYn("Y");

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

        // 단축URL 여부 체크
	    if(frndTalkContent.contains("#URL{"))
	    	requestData.setClickUrlYn("Y");

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
            if(requestData.getImage() == null || StringUtils.isBlank(requestData.getImage().getImgUrl())) {
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

            currentDate = DateUtils.addMinutes(currentDate, 9);
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
        String templateContent = CommonUtils.getStrValue(tmpltInfo, "templateContent");
        requestData.setMsg(templateContent);
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

            // 단축URL 여부 체크
    	    if(fbMsg.contains("#URL{"))
    	    	requestData.setClickUrlYn("Y");

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

        // 단축URL 여부 체크
	    if(templateContent.contains("#URL{"))
	    	requestData.setClickUrlYn("Y");

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

            currentDate = DateUtils.addMinutes(currentDate, 9);
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
		}

		if(StringUtils.isNotBlank(errorMsg)) {
			rtn.setFail(errorMsg);
		}


		// #13636 일감 관련 validation 추가
		// 1. RCS, 문자
		// 1-1. 문자만 사용하는경우 (RCS X)
		// 문자 발신번호의 유효성 검사
		List<Map<String, Object>> tmpltInfoList = mapper.readValue(CommonUtils.getStrValue(tmpltInfo, "tmpltInfo"), List.class);
		Map<String, Object> valiMap = new HashMap<String, Object>();
		if(!chTypeList.contains("RCS") && chTypeList.contains("SMS")){
			for(Map<String, Object> map : tmpltInfoList) {
				String ch = CommonUtils.getString(map.get("ch"));
				if("SMS".equals(ch)) {
					Map<String, Object> data = (Map<String, Object>) map.get("data");
					valiMap.put("callback", data.get("callback"));
					break;
				}
			}

			int callbackCnt = generalDao.selectGernalCount(DB.QRY_SELECT_USE_CALLBACK_CNT, valiMap);
			// 해당 유효 문자발신번호가 없는 경우 해당 템플릿을 미사용처리 하고 미발송처리 및 로직 종료
			if(callbackCnt == 0) {
				generalDao.deleteGernal(DB.QRY_UPDATE_SMART_TMPLT_STATUS, params);
				rtn.setData(true);
				rtn.setFail("선택하신 문자발송 발신번호가 사용불가 상태입니다. 해당 템플릿은 삭제 처리됩니다.");
				return requestData;
			}
		}

		if(!chTypeList.contains("RCS") && chTypeList.contains("MMS")){
			for(Map<String, Object> map : tmpltInfoList) {
				String ch = CommonUtils.getString(map.get("ch"));
				if("MMS".equals(ch)) {
					Map<String, Object> data = (Map<String, Object>) map.get("data");
					valiMap.put("callback", data.get("callback"));
					break;
				}
			}

			int callbackCnt = generalDao.selectGernalCount(DB.QRY_SELECT_USE_CALLBACK_CNT, valiMap);
			// 해당 유효 문자발신번호가 없는 경우 해당 템플릿을 삭제상태 처리 하고 로직 종료
			if(callbackCnt == 0) {
				generalDao.deleteGernal(DB.QRY_UPDATE_SMART_TMPLT_STATUS, params);
				rtn.setData(true);
				rtn.setFail("선택하신 문자발송 발신번호가 사용불가 상태입니다. 해당 템플릿은 삭제 처리됩니다.");
				return requestData;
			}
		}

		// 1-2. RCS가 포함되는경우
		// RCS 발신번호의 유효성 검사
		// 선택 브랜드의 사용 유무 검사
		// 템플릿의 사용 유무 검사
		if(chTypeList.contains("RCS")){
			for(Map<String, Object> map : tmpltInfoList) {
				String ch = CommonUtils.getString(map.get("ch"));
				if("RCS".equals(ch)) {
					Map<String, Object> data = (Map<String, Object>) map.get("data");
					valiMap.put("callback", data.get("callback"));
					valiMap.put("brandId", data.get("brandNm"));
					valiMap.put("messagebaseId", data.get("messagebaseId"));
					break;
				}
			}

			// 1. RCS 발신번호 유효성 검사
			int callbackCnt = generalDao.selectGernalCount(DB.QRY_SELECT_USE_RCS_CALLBACK_CNT, valiMap);
			// 해당 유효 RCS 발신번호가 없는 경우 해당 템플릿을 삭제상태 처리 하고 로직 종료
			if(callbackCnt == 0) {
				generalDao.deleteGernal(DB.QRY_UPDATE_SMART_TMPLT_STATUS, params);
				rtn.setData(true);
				rtn.setFail("선택하신 RCS 발신번호가 사용불가 상태입니다. 해당 템플릿은 삭제 처리됩니다.");
				return requestData;
			}

			// 2. 선택 브랜드의 사용 유무 검사
			int brandCnt = generalDao.selectGernalCount(DB.QRY_SELECT_RCS_BRAND_USE_CNT, valiMap);
			if(brandCnt == 0) {
				generalDao.deleteGernal(DB.QRY_UPDATE_SMART_TMPLT_STATUS, params);
				rtn.setData(true);
				rtn.setFail("선택하신 RCS 브랜드가 사용불가 상태입니다. 해당 템플릿은 삭제 처리됩니다.");
				return requestData;
			}

			// 3. 템플릿의 사용 유무 검사
			int tmpltCnt = generalDao.selectGernalCount(DB.QRY_SELECT_RCS_TMPLT_USE_CNT, valiMap);
			if(tmpltCnt == 0) {
				generalDao.deleteGernal(DB.QRY_UPDATE_SMART_TMPLT_STATUS, params);
				rtn.setData(true);
				rtn.setFail("선택하신 RCS 템플릿이 사용불가 상태입니다. 해당 통합 템플릿은 삭제 처리됩니다.");
				return requestData;
			}
		}
		// 2. 카카오(친구톡)
		// 카카오채널 사용 유무 검사
		if(chTypeList.contains("FRIENDTALK")){
			for(Map<String, Object> map : tmpltInfoList) {
				String ch = CommonUtils.getString(map.get("ch"));
				if("FRIENDTALK".equals(ch)) {
					Map<String, Object> data = (Map<String, Object>) map.get("data");
					valiMap.put("senderKey", data.get("senderKey"));
					valiMap.put("projectId", params.get("projectId"));
					break;
				}
			}

			int kkoChCnt = generalDao.selectGernalCount(DB.QRY_SELECT_KKO_CH_USE_CNT, valiMap);
			if(kkoChCnt == 0) {
				generalDao.deleteGernal(DB.QRY_UPDATE_SMART_TMPLT_STATUS, params);
				rtn.setData(true);
				rtn.setFail("선택하신 카카오 채널이 사용불가 상태입니다. 해당 통합 템플릿은 삭제 처리됩니다.");
				return requestData;
			}

		}

		// 3. 카카오(알림톡)
		// 선택한 템플릿 사용 유무 검사

		if(chTypeList.contains("ALIMTALK")){
			for(Map<String, Object> map : tmpltInfoList) {
				String ch = CommonUtils.getString(map.get("ch"));
				if("ALIMTALK".equals(ch)) {
					Map<String, Object> data = (Map<String, Object>) map.get("data");
					valiMap.put("tmpltKey", data.get("tmpltKey"));
					valiMap.put("projectId", params.get("projectId"));
					break;
				}
			}

			int kkoTmpltCnt = generalDao.selectGernalCount(DB.QRY_SELECT_KKO_TMPLT_USE_CNT, valiMap);
			if(kkoTmpltCnt == 0) {
				generalDao.deleteGernal(DB.QRY_UPDATE_SMART_TMPLT_STATUS, params);
				rtn.setData(true);
				rtn.setFail("선택하신 알림톡 템플릿이 사용불가 상태입니다. 해당 통합 템플릿은 삭제 처리됩니다.");
				return requestData;
			}
		}

		// 4. PUSH
		// APP_ID 사용 유무 검사
		if(chTypeList.contains("PUSH")){
			for(Map<String, Object> map : tmpltInfoList) {
				String ch = CommonUtils.getString(map.get("ch"));
				if("PUSH".equals(ch)) {
					Map<String, Object> data = (Map<String, Object>) map.get("data");
					valiMap.put("appId", data.get("appId"));
					valiMap.put("projectId", params.get("projectId"));
					break;
				}
			}

			int pushIdCnt = generalDao.selectGernalCount(DB.QRY_SELECT_PUSH_ID_USE_CNT, valiMap);
			if(pushIdCnt == 0) {
				generalDao.deleteGernal(DB.QRY_UPDATE_SMART_TMPLT_STATUS, params);
				rtn.setData(true);
				rtn.setFail("선택하신 PUSH ID가 사용불가 상태입니다. 해당 통합 템플릿은 삭제 처리됩니다.");
				return requestData;
			}
		}

		// 이미지가 들어간 템플릿의 경우 해당 이미지의 유효성 검사 (이미지 유효기간이 있는 MMS, RCS만 체크한다)
		String msgType = CommonUtils.getStrValue(tmpltInfo, "msgType");
		if("IMAGE".equals(msgType)) {
			if(chTypeList.contains("MMS")) {
				List<Object> imgFileIdLst = new ArrayList<Object>();
				for(Map<String, Object> map : tmpltInfoList) {
					String ch = CommonUtils.getString(map.get("ch"));
					if("MMS".equals(ch)) {
						Map<String, Object> data = (Map<String, Object>) map.get("data");
						imgFileIdLst = (List<Object>) data.get("fileIdLst");
						break;
					}
				}

				String rsrvSendYn = (CommonUtils.getStrValue(params, "rsrvSendYn"));
				String rsrvDate = "";
				String rsrvHH = "";
				String rsrvMM = "";
				String rsrvDt = "";
				if(rsrvSendYn.equals("Y")){
					rsrvDate = (CommonUtils.getStrValue(params, "rsrvDate"));
					rsrvHH = (CommonUtils.getStrValue(params, "rsrvHH"));
					rsrvMM = (CommonUtils.getStrValue(params, "rsrvMM"));
					rsrvDt = rsrvDate + " " + rsrvHH + ":" + rsrvMM + ":00";
				}

				int imgExpCnt = 0;
				for(int i=0; i<imgFileIdLst.size(); i++) {
					// 이미지가 있는경우 이미지 세팅전 해당 이미지의 사용여부를 확인하고 이미지의 유효기간이 지난경우 예외처리
					// 즉시 발송인경우 현재시간과 비교하여 사용여부를체크, 예약 발송인경우 해당 예약 발송기간에 따른 이미지 사용여부를 체크
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("ch", "mms");
					paramMap.put("fileId", imgFileIdLst.get(i));
					paramMap.put("rsrvSendYn", rsrvSendYn);
					paramMap.put("rsrvDt", rsrvDt);
					int imgCnt = generalDao.selectGernalCount(DB.QRY_SELECT_EXP_IMG_CNT, paramMap);
					if(imgCnt>0) imgExpCnt++;
				}

				if(imgExpCnt != imgFileIdLst.size()) {
					rtn.setSuccess(false);
					rtn.setFail("선택하신 통합 템플릿의 MMS 이미지가 사용이 만료되었습니다. 템플릿의 이미지를 변경해주세요.");
					return requestData;
				}
			}

			if(chTypeList.contains("RCS")) {
				List<Object> imgFileIdLst = new ArrayList<Object>();
				for(Map<String, Object> map : tmpltInfoList) {
					String ch = CommonUtils.getString(map.get("ch"));
					if("RCS".equals(ch)) {
						Map<String, Object> data = (Map<String, Object>) map.get("data");
						List<Map<String, Object>> mergeData = (List<Map<String, Object>>) data.get("mergeData");

						for(Map<String, Object> mergeMap : mergeData) {
							String fileId = CommonUtils.getString(mergeMap.get("media"));
							fileId = fileId.replace("maapfile://", "");
							imgFileIdLst.add(fileId);
						}

						break;
					}
				}

				String rsrvSendYn = (CommonUtils.getStrValue(params, "rsrvSendYn"));
				String rsrvDate = "";
				String rsrvHH = "";
				String rsrvMM = "";
				String rsrvDt = "";
				if(rsrvSendYn.equals("Y")){
					rsrvDate = (CommonUtils.getStrValue(params, "rsrvDate"));
					rsrvHH = (CommonUtils.getStrValue(params, "rsrvHH"));
					rsrvMM = (CommonUtils.getStrValue(params, "rsrvMM"));
					rsrvDt = rsrvDate + " " + rsrvHH + ":" + rsrvMM + ":00";
				}

				int imgExpCnt = 0;
				for(int i=0; i<imgFileIdLst.size(); i++) {
					// 이미지가 있는경우 이미지 세팅전 해당 이미지의 사용여부를 확인하고 이미지의 유효기간이 지난경우 예외처리
					// 즉시 발송인경우 현재시간과 비교하여 사용여부를체크, 예약 발송인경우 해당 예약 발송기간에 따른 이미지 사용여부를 체크
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("ch", "rcs");
					paramMap.put("fileId", imgFileIdLst.get(i));
					paramMap.put("rsrvSendYn", rsrvSendYn);
					paramMap.put("rsrvDt", rsrvDt);
					int imgCnt = generalDao.selectGernalCount(DB.QRY_SELECT_EXP_IMG_CNT, paramMap);
					if(imgCnt>0) imgExpCnt++;
				}

				if(imgExpCnt != imgFileIdLst.size()) {
					rtn.setSuccess(false);
					rtn.setFail("선택하신 통합 템플릿의 RCS 이미지가 사용이 만료되었습니다. 템플릿의 이미지를 변경해주세요.");
					return requestData;
				}
			}
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

            currentDate = DateUtils.addMinutes(currentDate, 9);
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
            	ArrayList<Object> chTypeList = (ArrayList<Object>) data.get("chTypeList");
                sParams.put("apiKey", apiKey);
                sParams.put("reqCh", chTypeList.get(0));
                sParams.put("finalCh", chTypeList.get(0));
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
                        		rcsMergeData.put("description", ss.replace(CommonUtils.getStrValue(tmpltMergeDataList.get(0), "description")));
                        		sendMergeData.put(Const.Ch.RCS, rcsMergeData);
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
				sParams.put("finalCh", Const.Ch.RCS);
				sParams.put("callback", data.get("callback"));
				sParams.put("webReqId", data.get("webReqId"));
				rcsTemplateSendSvc.insertCmMsg(sParams, errorRecvInfoLst);
			} catch (Exception e) {
				log.error("{}.sendRCSMsgAsync insertCmMsg Error ==> {}", this.getClass(), e);
			}
		}
		
		// web insert시 전체 목록을 insert하기 위해서 apiMap에 insert용 object 세팅
		apiMap.put("msgRecvInfoLst", recvInfoLst);
		apiMap.put("msgFbInfoLst", fbInfoLst);
		//웹 발송 내역 등록
		if(isAllFail) {
			rcsTemplateSendSvc.insertPushCmWebMsg(headerMap, apiMap, params, "FAIL");
		} else {
			rcsTemplateSendSvc.insertPushCmWebMsg(headerMap, apiMap, params, "COMPLETED");
		}

	}

    /**
     * 야간 메시지 발송 제한 유효성 체크
     * @param rtn
     * @param params
     */
    @SuppressWarnings("rawtypes")
    public boolean checkNightSendMsgValid(Map<String, Object> params) throws Exception {
    	// sms : {imgInfoList=[], smsTitle=, cuInfo=[{"phone":"01054113739","mergeData":{}}], recvInfoLst=[{phone=01054113739, mergeData={}}], requiredCuPhone=true, rsrvDate=2022-03-14, testSendYn=N, webReqId=SMSPHek03q, rsrvHH=00, smsContent=test, rsrvSendYn=N, callbackList=[{callback=01050959083}, {callback=01080806316}, {callback=01088259173}, {callback=021112222}, {callback=0215421144}, {callback=022223333}, {callback=023334444}, {callback=0243113369}, {callback=0269496227}, {callback=0269496229}, {callback=027204650}, {callback=027204651}, {callback=0274125555}, {callback=0295957878}, {callback=0297974511}, {callback=03147848548}, {callback=0317849152}, {callback=0318513285}, {callback=06143113369}, {callback=07052227696}, {callback=32145215321}], cuInputType=DICT, rcvblcNumber=, chGrp=SMS/MMS, corpId=COM2104142281316, msgKind=I, campaignId=, requiredCuid=false, userId=MBR2104261075129, excelLimitRow=0, testRecvInfoLst=[], rsrvMM=00, callback=027204650, senderType=SMS, contsVarNms=[], projectId=PJTDojV8sw}
        // lms : {imgInfoList=[], smsTitle=test, cuInfo=[{"phone":"01054113739","mergeData":{}}], recvInfoLst=[{phone=01054113739, mergeData={}}], requiredCuPhone=true, rsrvDate=2022-03-14, testSendYn=N, webReqId=MMSYMa2Bx7, rsrvHH=00, smsContent=test, rsrvSendYn=N, callbackList=[{callback=01050959083}, {callback=01080806316}, {callback=01088259173}, {callback=021112222}, {callback=0215421144}, {callback=022223333}, {callback=023334444}, {callback=0243113369}, {callback=0269496227}, {callback=0269496229}, {callback=027204650}, {callback=027204651}, {callback=0274125555}, {callback=0295957878}, {callback=0297974511}, {callback=03147848548}, {callback=0317849152}, {callback=0318513285}, {callback=06143113369}, {callback=07052227696}, {callback=32145215321}], cuInputType=DICT, rcvblcNumber=, chGrp=SMS/MMS, corpId=COM2104142281316, msgKind=I, campaignId=, requiredCuid=false, userId=MBR2104261075129, excelLimitRow=0, testRecvInfoLst=[], rsrvMM=00, callback=027204650, senderType=LMS, contsVarNms=[], projectId=PJTDojV8sw}
    	// mms : {imgInfoList=[], smsTitle=test, cuInfo=[{"phone":"01054113739","mergeData":{}}], recvInfoLst=[{phone=01054113739, mergeData={}}], requiredCuPhone=true, rsrvDate=2022-03-14, testSendYn=N, webReqId=MMSqd1MhTZ, rsrvHH=00, smsContent=test, rsrvSendYn=N, callbackList=[{callback=01050959083}, {callback=01080806316}, {callback=01088259173}, {callback=021112222}, {callback=0215421144}, {callback=022223333}, {callback=023334444}, {callback=0243113369}, {callback=0269496227}, {callback=0269496229}, {callback=027204650}, {callback=027204651}, {callback=0274125555}, {callback=0295957878}, {callback=0297974511}, {callback=03147848548}, {callback=0317849152}, {callback=0318513285}, {callback=06143113369}, {callback=07052227696}, {callback=32145215321}], cuInputType=DICT, rcvblcNumber=, chGrp=SMS/MMS, corpId=COM2104142281316, msgKind=I, campaignId=, requiredCuid=false, userId=MBR2104261075129, excelLimitRow=0, testRecvInfoLst=[], rsrvMM=00, callback=027204650, senderType=MMS, contsVarNms=[], projectId=PJTDojV8sw}
    	// rcs : {templateRadioBtn=text, data={messagebaseId=, brandId=BR.LsrxlRxNny, brandArr=[{BRAND_ID=BR.p8IrdTOxu3, BRAND_NAME=이커머스검수D브랜드}, {BRAND_ID=BR.2aduLMOx1Y, BRAND_NAME=제임스 브랜드2_검수기}, {BRAND_ID=BR.D9YV4wegIy, BRAND_NAME=0528 test 11111}, {BRAND_ID=BR.FYFqO2v1fT, BRAND_NAME=비트큐브}, {BRAND_ID=BR.q7U3H8JeN5, BRAND_NAME=0609 공용 브랜드}, {BRAND_ID=BR.v7ZBN27VMZ, BRAND_NAME=0601 test 1}, {BRAND_ID=BR.vC475jIdAh, BRAND_NAME=이커머스테크1 검수기}, {BRAND_ID=BR.W0ewiPMBSD, BRAND_NAME=이커머스테크2 검수기}, {BRAND_ID=BR.Z21Ir8v6He, BRAND_NAME=비트큐브 브랜드_검수기}, {BRAND_ID=BR.LsrxlRxNny, BRAND_NAME=MRO 비즈니스}], textTitle=, textContents=test, btnCnt=0, selectBtn=[], btnNm=[], contents=[], calendarTitle=[], calendarDes=[], initStartDate=[], initEndDate=[], imgUrl=, fileId=, wideImgYn=N, SMwThM00Img=data:image/png;base64,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, SMwThT00Img=data:image/png;base64,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, callback=03143113369, callbackArr=[{callback=03143113369}], copy=no, adYn=no, freeReceiveNum=, senderType=SMS, callbackTitle=, callbackContents=test, callbackImgUrl=, callbackFileId=, saveContent=, carouselObj={textTitle=[, , , , , ], textContents=[, , , , , ], imgUrl=[, , , , , ], fileId=[], wideImgYn=[], btnArr=[]}, requiredCuid=false, requiredCuPhone=true, cuInputType=DICT, cuInfo=[{"phone":"01054113739","mergeData":{}}], rsrvSendYn=N, rsrvDate=2022-03-14, rsrvHH=00, rsrvMM=00, campaignId=, imgInfoList=[], recvInfoLst=[{phone=01054113739, mergeData={}}], contsVarNms=[], testRecvInfoLst=[], excelLimitRow=0}, corpId=COM2104142281316, carouselSelect=3, brandId=BR.LsrxlRxNny, real=true, userId=MBR2104261075129, projectId=PJTDojV8sw}
    	// alimtalk : {senderKey=da17d231bbf13f83174a36a4bb0353476ae012b3, reason=, kkoChId=@이커머스테크, senderKeyTypeName=일반, existsRejResnYn=N, tmpltCode=LMSG_20210923180747000579, webReqId=ALMr1NCzYF, tmpltStatCode=A, senderKeyType=S, tmpltEmpsTitle=, rsrvHH=00, tmpltReqDt=null, fbInfo={ch=SMS, callback=01080806316, msg=tes}, corpId=COM2104142281316, tmpltCategoryCode=001001, msgKind=I, campaignId=, tmpltStatCodeName=승인, requiredCuid=false, tmpltEmpsSubTitle=, buttonList=[], rownum=4, regDt=2021-09-23T09:07:47.000+0000, rsrvMM=00, contsVarNms=[], updDt=2021-09-27 09:00:00, projectId=313431323336706A74, emphasizeType=NONE, tmpltInfo=, cuInfo=[{"phone":"01054113739","mergeData":{}}], tmpltMsgType=BA, recvInfoLst=[{phone=01054113739, mergeData={}}], requiredCuPhone=true, rsrvDate=2022-03-14, testSendYn=N, tmpltContent=testtest, tmpltKey=TPnbGM0R4B, approvalDt=null, useYn=Y, rsrvSendYn=N, cuInputType=DICT, chGrp=KKO, ch=ALIMTALK, tmpltName=test-3, rplcSendType=SMS, userId=MBR2104261075129, excelLimitRow=0, testRecvInfoLst=[], categoryGrpName=회원}
    	// fndTalk : {senderKey=da17d231bbf13f83174a36a4bb0353476ae012b3, cuInfo=[{"phone":"01054113739","mergeData":{}}], recvInfoLst=[{phone=01054113739, mergeData={}}], requiredCuPhone=true, rsrvDate=2022-03-14, testSendYn=N, webReqId=FRDzBX7Mqp, wideImgYn=N, rsrvHH=00, fbInfo={}, rsrvSendYn=N, cuInputType=DICT, chGrp=KKO, imgLink=, corpId=COM2104142281316, ch=FRIENDTALK, msgKind=I, rplcSendType=NONE, campaignId=, frndTalkContent=tesetttt, requiredCuid=false, buttonList=[{name=aaa, linkMo=https://m.naver.com, linkPc=https://www.naver.com, linkType=WL}], userId=MBR2104261075129, imgUrl=, excelLimitRow=0, testRecvInfoLst=[], rsrvMM=00, contsVarNms=[], projectId=313431323336706A74, fileId=}
    	// push : {senderKey=da17d231bbf13f83174a36a4bb0353476ae012b3, cuInfo=[{"phone":"01054113739","mergeData":{}}], recvInfoLst=[{phone=01054113739, mergeData={}}], requiredCuPhone=true, rsrvDate=2022-03-14, testSendYn=N, webReqId=FRDzBX7Mqp, wideImgYn=N, rsrvHH=00, fbInfo={}, rsrvSendYn=N, cuInputType=DICT, chGrp=KKO, imgLink=, corpId=COM2104142281316, ch=FRIENDTALK, msgKind=I, rplcSendType=NONE, campaignId=, frndTalkContent=tesetttt, requiredCuid=false, buttonList=[{name=aaa, linkMo=https://m.naver.com, linkPc=https://www.naver.com, linkType=WL}], userId=MBR2104261075129, imgUrl=, excelLimitRow=0, testRecvInfoLst=[], rsrvMM=00, contsVarNms=[], projectId=313431323336706A74, fileId=}
    	// all : {cuInputType=DICT, chString=FRIENDTALK,RCS,SMS, cuInfo=[{"phone":"01054113739","mergeData":{}}], corpId=COM2104142281316, campaignId=, recvInfoLst=[{phone=01054113739, mergeData={}}], tmpltCode=TPLHMCtokK, chTypeList=[FRIENDTALK, RCS, SMS], requiredCuid=false, requiredCuPhone=true, rsrvDate=2022-03-14, testSendYn=N, chMappingVarList=[{ch=FRIENDTALK, varNms=[]}, {ch=RCS, varNms=[]}, {ch=SMS, varNms=[]}], webReqId=ITGMV4lOPQ, smartPrdFee=0, userId=MBR2104261075129, excelLimitRow=0, testRecvInfoLst=[], rsrvHH=00, rsrvMM=00, senderType=M, contsVarNms=[], projectId=313431323336706A74, rsrvSendYn=N}

    	//테스트발송인 경우 패스(sms, lms, mms, alimTalk, frndTalk, push, all(통합))
    	if(params.containsKey("testSendYn") && CommonUtils.getString(params.get("testSendYn")).equals("Y")) {
    		return true;
    	}else {

	    	//야간발송 제한 프로젝트인지 확인
	    	String nightSendYn = CommonUtils.getString(generalDao.selectGernalObject(DB.QRY_SELECT_PROJECT_NIGHT_SEND_YN, params));

	    	if(nightSendYn.equals("N")) {
	    		
	    		Map<String, Object> nightSendMap = commonService.selectNightSendTime();
	    		
	    		String nightSendLimitSt = CommonUtils.getString(nightSendMap.get("nightSendSthh")) + CommonUtils.getString(nightSendMap.get("nightSendStmm"));
	    		String nightSendLimitEd = CommonUtils.getString(nightSendMap.get("nightSendEdhh")) + CommonUtils.getString(nightSendMap.get("nightSendEdmm"));

		    	if(CommonUtils.getString(params.get("rsrvSendYn")).equals("Y")) {
		    		//예약발송
		    		String rsrvTime = CommonUtils.getString(params.get("rsrvHH")) + CommonUtils.getString(params.get("rsrvMM"));

		    		if(CommonUtils.getInt(rsrvTime) >= CommonUtils.getInt(nightSendLimitSt)) {
		    			return false;
		    		}

		    		if(CommonUtils.getInt(rsrvTime) < CommonUtils.getInt(nightSendLimitEd)) {
		    			return false;
		    		}

		    	}else {
		    		//즉시발송
		    		LocalTime now = LocalTime.now();
		    		DateTimeFormatter format = DateTimeFormatter.ofPattern("HHmm");

		    		String currTime = now.format(format);

		    		if(CommonUtils.getInt(currTime) >= CommonUtils.getInt(nightSendLimitSt)) {
		    			return false;
		    		}

		    		if(CommonUtils.getInt(currTime) < CommonUtils.getInt(nightSendLimitEd)) {
		    			return false;
		    		}
		    	}
	    	}
    	}
        return true;
    }

}