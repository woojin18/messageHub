package kr.co.uplus.cm.sendMessage.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.MultipartFileDTO;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.config.ApiConfig;
import kr.co.uplus.cm.sendMessage.dto.AlimTalkRequestData;
import kr.co.uplus.cm.sendMessage.dto.FrndTalkRequestData;
import kr.co.uplus.cm.sendMessage.dto.MmsRequestData;
import kr.co.uplus.cm.sendMessage.dto.PushRequestData;
import kr.co.uplus.cm.sendMessage.dto.RecvInfo;
import kr.co.uplus.cm.sendMessage.dto.SmartRequestData;
import kr.co.uplus.cm.sendMessage.dto.SmsRequestData;
import kr.co.uplus.cm.sendMessage.service.SendMessageService;
import kr.co.uplus.cm.utils.ApiInterface;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.DateUtil;
import lombok.extern.log4j.Log4j2;

/**
 * <pre>
 * SendMessageController
 * 각 채널별 메시지 발송처리 Controller
 * </pre>
 *
 * @author : liam (Lee Sung Min)
 * @Date : 2021.03.29.
 * @Version : 1.0 Copyright 2020 LG Uplus Corp. All Rights Reserved.
 */
@Log4j2
@RestController
@RequestMapping("/uc/sendMessage")
public class SendMessageController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private SendMessageService sendMsgService;

    @Autowired
    ApiInterface apiInterface;

    @InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}

    /**
     * APP ID 리스트 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/selectAppIdList")
    public RestResult<?> selectAppIdList(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = sendMsgService.selectAppIdList(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.selectAppIdList Error : {}", this.getClass(), e);
        }
        return rtn;
    }

    /**
     * 발신번호 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/selectCallbackList")
    public RestResult<?> selectCallbackList(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = sendMsgService.selectCallbackList(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.selectCallbackList Error : {}", this.getClass(), e);
        }
        return rtn;
    }

    /**
     * 주소록 리스트 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/selectAddressList")
    public RestResult<?> selectAddressList(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = sendMsgService.selectAddressList(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.selectAddressList Error : {}", this.getClass(), e);
        }
        return rtn;
    }

    /**
     * 주소록 카테고리 구성원 목록 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/selectCmCuList")
    public RestResult<?> selectCmCuList(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = sendMsgService.selectCmCuList(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.selectCmCuList Error : {}", this.getClass(), e);
        }
        return rtn;
    }

    /**
     * 푸시 발송 수신자 엑셀업로드 템플릿 다운로드
     * @param request
     * @param response
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @PostMapping(path = "/excelDownSendPushRecvTmplt")
    public ModelAndView excelDownSendPushRecvTmplt(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) throws Exception {

        List<String> colLabels = new ArrayList<String>();
        if(params.containsKey("requiredCuid") && (Boolean) params.get("requiredCuid")) {
            colLabels.add("APP 로그인 ID");
        }
        if(params.containsKey("requiredCuPhone") && (Boolean) params.get("requiredCuPhone")) {
            colLabels.add("휴대폰 번호");
        }
        if(params.containsKey("contsVarNms")) {
            List<String> contsVarNms = (ArrayList<String>)params.get("contsVarNms");
            for(String varNm : contsVarNms) {
                colLabels.add(varNm);
            }
        }

        List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetTitle", "Template");
        map.put("colLabels", colLabels.toArray(new String[0]));
        map.put("colIds", new String[] {});
        map.put("numColIds", new String[] {});
        map.put("figureColIds", new String[] {});
        map.put("colDataList", new ArrayList<T>());
        sheetList.add(map);

        ModelAndView model = new ModelAndView("commonXlsxView");
        model.addObject("excelFileName", "pushTemplate_"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
        model.addObject("sheetList", sheetList);

        return model;
    }

    /**
     * 푸시 메시지 발송처리
     *
     * @param request
     * @param response
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping(path="/sendPushMessage")
    public RestResult<?> sendPushMessage(HttpServletRequest request, HttpServletResponse response
            , @ModelAttribute MultipartFileDTO multipartFileDTO) throws Exception {

        List<RecvInfo> recvInfoLst = null;
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> sParam = new HashMap<String, Object>();
        RestResult<Object> rtn = new RestResult<Object>();
        Map<String, Object> rtnMap = new HashMap<>();
        PushRequestData requestData = null;

        try {
            params = commonService.setUserInfo(multipartFileDTO.getUnescapeParams());
            String testSendYn = CommonUtils.getStrValue(params, "testSendYn");
            log.info(Const.PRIVACY_MARKER, "{}.sendPushMessage Start ====> params : {}", this.getClass(), params);

            /** 사용 가능 채널 확인 */
            if(StringUtils.isBlank((String) sendMsgService.selectValidUseChGrp(params).getData())) {
                log.info(Const.PRIVACY_MARKER, "{}.sendPushMessage validation use channel fail - params: {}", this.getClass(), params);
                rtn.setFail("이용하실 수 없는 채널입니다.");
                return rtn;
            }

            /** 유효성 체크 */
            requestData = sendMsgService.setPushSendData(rtn, params);
            if(rtn.isSuccess() == false) {
                log.info("{}.sendPushMessage validation Check fail: {}", this.getClass(), rtn.getMessage());
                return rtn;
            }
            log.info(Const.PRIVACY_MARKER, "{}.sendPushMessage pushRequestData: {}", this.getClass(), requestData.toString());

            /** 푸시 수신자 리스트*/
            recvInfoLst = sendMsgService.getRecvInfoLst(params, multipartFileDTO.getFile());
            if(recvInfoLst == null || recvInfoLst.size() == 0) {
                rtn.setFail(Const.SendMsgErrorSet.INVALID_RECIPIENT_INFO);
                return rtn;
            }

            /** 예약건인지 확인 */
            String rsrvSendYn = (CommonUtils.getStrValue(params, "rsrvSendYn"));
            if(StringUtils.equals(rsrvSendYn, Const.COMM_YES)) {
                return sendMsgService.insertPushCmWebMsg(rtn, params, requestData, recvInfoLst);
            }

            /** 잔액확인 */
            String payType = sendMsgService.selectPayType(params);

            //선불일경우
            if(StringUtils.equals(payType, Const.PayType.PRE_FEE)) {
                //남은 금액 조회
                BigDecimal rmAmount = sendMsgService.getRmAmount(params);
                //개당 가격 조회
                List<String> productCodes = new ArrayList<String>();
                productCodes.add(Const.MsgProductCode.getCodeByName(Const.Ch.PUSH));
                if(params.containsKey("rplcSendType")
                        && !CommonUtils.isEmptyValue(params, "rplcSendType")
                        && !StringUtils.equals((CharSequence) params.get("rplcSendType"), "NONE")) {
                    productCodes.add(Const.MsgProductCode.getCodeByName(CommonUtils.getStrValue(params, "rplcSendType")));
                }

                sParam = new HashMap<>();
                sParam.put("corpId", CommonUtils.getStrValue(params, "corpId"));
                sParam.put("productCodes", productCodes);
                BigDecimal feePerOne = sendMsgService.selectMsgFeePerOne(sParam);
                if(rmAmount.compareTo(feePerOne) < 0) {
                    rtn.setFail("잔액 부족으로 메시지를 발송할 수 없습니다.");
                    return rtn;
                }

                BigDecimal feePerAll = feePerOne.multiply(new BigDecimal(recvInfoLst.size()));
                if(rmAmount.compareTo(feePerAll) < 0) {
                    if(StringUtils.equals(testSendYn, Const.COMM_YES)) {
                        rtn.setSuccess(false);
                        rtn.setMessage("잔액 부족으로 메시지를 발송할 수 없습니다.");
                        return rtn;
                    } else {
                        rtnMap.put("feeMsg", "잔액 부족으로 메시지가 발송되지 않을 수도 있습니다.");
                    }
                }
            }

            /** 테스트발송(동기화) */
            if(StringUtils.equals(testSendYn, Const.COMM_YES)) {
                return sendMsgService.testSendPushMsg(params, requestData, recvInfoLst);
            }

        } catch (Exception e) {
            rtn.setFail(CommonUtils.getCMExceptionMsg(e, "실패하였습니다."));
            log.error("{}.sendPushMessage Error : {}", this.getClass(), e);
            return rtn;
        }

        /** 비동기화 발송 */
        try {
            List<Object> reSendCdList = sendMsgService.reSendCdList(null);
            log.info("{}.sendPushMessage aSync API send Start ====>", this.getClass());
            sendMsgService.sendPushMsgAsync(rtn, 0, params, requestData, recvInfoLst, reSendCdList);
        } catch (Exception e) {
            log.error("{}.sendPushMessage aSync API send Error : {}", this.getClass(), e);
        }
        rtn.setMessage("푸시 "+ApiConfig.MSG_SEND_REQUET_TEXT);

        rtn.setSuccess(true);
        rtn.setData(rtnMap);

        return rtn;
    }

    /**
     * 푸시 발송 수신자 엑셀업로드 템플릿 다운로드
     * @param request
     * @param response
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @PostMapping(path = "/excelDownSendSmsRecvTmplt")
    public ModelAndView excelDownSendSmsRecvTmplt(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) throws Exception {

        List<String> colLabels = new ArrayList<String>();
        colLabels.add("휴대폰 번호");
        if(params.containsKey("contsVarNms")) {
            List<String> contsVarNms = (ArrayList<String>)params.get("contsVarNms");
            for(String varNm : contsVarNms) {
                colLabels.add(varNm);
            }
        }

        List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetTitle", "Template");
        map.put("colLabels", colLabels.toArray(new String[0]));
        map.put("colIds", new String[] {});
        map.put("numColIds", new String[] {});
        map.put("figureColIds", new String[] {});
        map.put("colDataList", new ArrayList<T>());
        sheetList.add(map);

        ModelAndView model = new ModelAndView("commonXlsxView");
        model.addObject("excelFileName", "smsTemplate_"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
        model.addObject("sheetList", sheetList);

        return model;
    }

    /**
     * SMS 메시지 발송처리
     * @param request
     * @param response
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping(path="/sendSmsMessage")
    public RestResult<?> sendSmsMessage(HttpServletRequest request, HttpServletResponse response
            , @ModelAttribute MultipartFileDTO multipartFileDTO) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> sParam = new HashMap<String, Object>();
        SmsRequestData requestData = null;
        List<RecvInfo> recvInfoLst = null;
        Map<String, Object> rtnMap = new HashMap<>();

        try {
            params = commonService.setUserInfo(multipartFileDTO.getUnescapeParams());
            String testSendYn = CommonUtils.getStrValue(params, "testSendYn");
            log.info(Const.PRIVACY_MARKER, "{}.sendSmsMessage Start ====> paramString : {}", this.getClass(), params);

            /** 사용 가능 채널 확인 */
            if(StringUtils.isBlank((String) sendMsgService.selectValidUseChGrp(params).getData())) {
                log.info(Const.PRIVACY_MARKER, "{}.sendSmsMessage validation use channel fail - params: {}", this.getClass(), params);
                rtn.setFail("이용하실 수 없는 채널입니다.");
                return rtn;
            }

            /** 유효성 체크 */
            requestData = sendMsgService.setSmsSendData(rtn, params);
            if(rtn.isSuccess() == false) {
                log.info("{}.sendSmsMessage validation Check fail: {}", this.getClass(), rtn.getMessage());
                return rtn;
            }
            log.info(Const.PRIVACY_MARKER, "{}.sendSmsMessage requestData: {}", this.getClass(), requestData.toString());

            /** SMS 수신자 리스트*/
            recvInfoLst = sendMsgService.getRecvInfoLst(params, multipartFileDTO.getFile());
            if(recvInfoLst == null || recvInfoLst.size() == 0) {
                rtn.setFail(Const.SendMsgErrorSet.INVALID_RECIPIENT_INFO);
                return rtn;
            }

            /** 예약건인지 확인 */
            String rsrvSendYn = (CommonUtils.getStrValue(params, "rsrvSendYn"));
            if(StringUtils.equals(rsrvSendYn, Const.COMM_YES)) {
                return sendMsgService.insertSmsCmWebMsg(rtn, params, requestData, recvInfoLst);
            }

            /** 잔액확인 */
            String payType = sendMsgService.selectPayType(params);

            //선불일경우
            if(StringUtils.equals(payType, Const.PayType.PRE_FEE)) {
                //남은 금액 조회
                BigDecimal rmAmount = sendMsgService.getRmAmount(params);
                //개당 가격 조회
                List<String> productCodes = new ArrayList<String>();
                productCodes.add(Const.MsgProductCode.getCodeByName(Const.Ch.SMS));

                sParam = new HashMap<>();
                sParam.put("corpId", CommonUtils.getStrValue(params, "corpId"));
                sParam.put("productCodes", productCodes);
                BigDecimal feePerOne = sendMsgService.selectMsgFeePerOne(sParam);
                if(rmAmount.compareTo(feePerOne) < 0) {
                    rtn.setFail("잔액 부족으로 메시지를 발송할 수 없습니다.");
                    return rtn;
                }

                BigDecimal feePerAll = feePerOne.multiply(new BigDecimal(recvInfoLst.size()));
                if(rmAmount.compareTo(feePerAll) < 0) {
                    if(StringUtils.equals(testSendYn, Const.COMM_YES)) {
                        rtn.setSuccess(false);
                        rtn.setMessage("잔액 부족으로 메시지를 발송할 수 없습니다.");
                        return rtn;
                    } else {
                        rtnMap.put("feeMsg", "잔액 부족으로 메시지가 발송되지 않을 수도 있습니다.");
                    }
                }
            }

            /** 테스트발송(동기화) */
            if(StringUtils.equals(testSendYn, Const.COMM_YES)) {
                return sendMsgService.testSendSmsMsg(params, requestData, recvInfoLst);
            }

        } catch (Exception e) {
            rtn.setFail(CommonUtils.getCMExceptionMsg(e, "실패하였습니다."));
            log.error("{}.sendSmsMessage Error : {}", this.getClass(), e);
            return rtn;
        }

        try {
            List<Object> reSendCdList = sendMsgService.reSendCdList(null);
            log.info("{}.sendSmsMessage aSync API send Start ====>", this.getClass());
            sendMsgService.sendSmsMsgAsync(rtn, 0, params, requestData, recvInfoLst, reSendCdList);
        } catch (Exception e) {
            log.error("{}.sendSmsMessage aSync API send Error : {}", this.getClass(), e);
        }

        rtn.setMessage("SMS "+ApiConfig.MSG_SEND_REQUET_TEXT);
        rtn.setSuccess(true);
        rtn.setData(rtnMap);

        return rtn;
    }

    /**
     * MMS 메시지 발송처리
     * @param request
     * @param response
     * @param paramString
     * @param excelFile
     * @return
     * @throws Exception
     */
    @PostMapping(path="/sendMmsMessage")
    public RestResult<?> sendMmsMessage(HttpServletRequest request, HttpServletResponse response
            , @ModelAttribute MultipartFileDTO multipartFileDTO) throws Exception {

        RestResult<Object> rtn = new RestResult<Object>();
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> sParam = new HashMap<String, Object>();
        MmsRequestData requestData = null;
        List<RecvInfo> recvInfoLst = null;
        Map<String, Object> rtnMap = new HashMap<>();

        try {
            params = commonService.setUserInfo(multipartFileDTO.getUnescapeParams());
            String testSendYn = CommonUtils.getStrValue(params, "testSendYn");
            log.info(Const.PRIVACY_MARKER, "{}.sendMmsMessage Start ====> paramString : {}", this.getClass(), params);

            /** 사용 가능 채널 확인 */
            if(StringUtils.isBlank((String) sendMsgService.selectValidUseChGrp(params).getData())) {
                log.info(Const.PRIVACY_MARKER, "{}.sendMmsMessage validation use channel fail - params: {}", this.getClass(), params);
                rtn.setFail("이용하실 수 없는 채널입니다.");
                return rtn;
            }

            /** 유효성 체크 */
            requestData = sendMsgService.setMmsSendData(rtn, params);
            if(rtn.isSuccess() == false) {
                log.info("{}.sendMmsMessage validation Check fail: {}", this.getClass(), rtn.getMessage());
                return rtn;
            }

            /** MMS 수신자 리스트*/
            recvInfoLst = sendMsgService.getRecvInfoLst(params, multipartFileDTO.getFile());
            if(recvInfoLst == null || recvInfoLst.size() == 0) {
                rtn.setFail(Const.SendMsgErrorSet.INVALID_RECIPIENT_INFO);
                return rtn;
            }

            /** 예약건인지 확인 */
            String rsrvSendYn = (CommonUtils.getStrValue(params, "rsrvSendYn"));
            if(StringUtils.equals(rsrvSendYn, Const.COMM_YES)) {
                return sendMsgService.insertMmsCmWebMsg(rtn, params, requestData, recvInfoLst);
            }

            /** 잔액확인 */
            String payType = sendMsgService.selectPayType(params);

            //선불일경우
            if(StringUtils.equals(payType, Const.PayType.PRE_FEE)) {
                //남은 금액 조회
                BigDecimal rmAmount = sendMsgService.getRmAmount(params);
                //개당 가격 조회
                List<String> productCodes = new ArrayList<String>();
                productCodes.add(Const.MsgProductCode.getCodeByName(Const.Ch.MMS));

                sParam = new HashMap<>();
                sParam.put("corpId", CommonUtils.getStrValue(params, "corpId"));
                sParam.put("productCodes", productCodes);
                BigDecimal feePerOne = sendMsgService.selectMsgFeePerOne(sParam);
                if(rmAmount.compareTo(feePerOne) < 0) {
                    rtn.setFail("잔액 부족으로 메시지를 발송할 수 없습니다.");
                    return rtn;
                }

                BigDecimal feePerAll = feePerOne.multiply(new BigDecimal(recvInfoLst.size()));
                if(rmAmount.compareTo(feePerAll) < 0) {
                    if(StringUtils.equals(testSendYn, Const.COMM_YES)) {
                        rtn.setSuccess(false);
                        rtn.setMessage("잔액 부족으로 메시지를 발송할 수 없습니다.");
                        return rtn;
                    } else {
                        rtnMap.put("feeMsg", "잔액 부족으로 메시지가 발송되지 않을 수도 있습니다.");
                    }
                }
            }

            /** 테스트발송(동기화) */
            if(StringUtils.equals(testSendYn, Const.COMM_YES)) {
                return sendMsgService.testSendMmsMsg(params, requestData, recvInfoLst);
            }

        } catch (Exception e) {
            rtn.setFail(CommonUtils.getCMExceptionMsg(e, "실패하였습니다."));
            log.error("{}.sendMmsMessage Error : {}", this.getClass(), e);
            return rtn;
        }

        try {
            List<Object> reSendCdList = sendMsgService.reSendCdList(null);
            log.info("{}.sendMmsMessage aSync API send Start ====>", this.getClass());
            sendMsgService.sendMmsMsgAsync(rtn, 0, params, requestData, recvInfoLst, reSendCdList);
        } catch (Exception e) {
            log.error("{}.sendMmsMessage aSync API send Error : {}", this.getClass(), e);
        }

        rtn.setMessage("MMS "+ApiConfig.MSG_SEND_REQUET_TEXT);
        rtn.setSuccess(true);
        rtn.setData(rtnMap);

        return rtn;
    }

    /**
     * 친구톡 발송 수신자 엑셀업로드 템플릿 다운로드
     * @param request
     * @param response
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @PostMapping(path = "/excelDownSendFrndTalkRecvTmplt")
    public ModelAndView excelDownSendFrndTalkRecvTmplt(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) throws Exception {

        List<String> colLabels = new ArrayList<String>();
        if(params.containsKey("requiredCuid") && (Boolean) params.get("requiredCuid")) {
            colLabels.add("APP 로그인 ID");
        }
        if(params.containsKey("requiredCuPhone") && (Boolean) params.get("requiredCuPhone")) {
            colLabels.add("휴대폰 번호");
        }
        if(params.containsKey("contsVarNms")) {
            List<String> contsVarNms = (ArrayList<String>)params.get("contsVarNms");
            for(String varNm : contsVarNms) {
                colLabels.add(varNm);
            }
        }

        List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetTitle", "Template");
        map.put("colLabels", colLabels.toArray(new String[0]));
        map.put("colIds", new String[] {});
        map.put("numColIds", new String[] {});
        map.put("figureColIds", new String[] {});
        map.put("colDataList", new ArrayList<T>());
        sheetList.add(map);

        ModelAndView model = new ModelAndView("commonXlsxView");
        model.addObject("excelFileName", "frndTalkTemplate_"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
        model.addObject("sheetList", sheetList);

        return model;
    }

    /**
     * 친구톡 메시지 발송처리
     * @param request
     * @param response
     * @param multipartFileDTO
     * @return
     * @throws Exception
     */
    @PostMapping(path="/sendFrndTalkMessage")
    public RestResult<?> sendFrndTalkMessage(HttpServletRequest request, HttpServletResponse response
            , @ModelAttribute MultipartFileDTO multipartFileDTO) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> sParam = new HashMap<String, Object>();
        FrndTalkRequestData requestData = null;
        List<RecvInfo> recvInfoLst = null;
        Map<String, Object> rtnMap = new HashMap<>();

        try {
            params = commonService.setUserInfo(multipartFileDTO.getUnescapeParams());
            String testSendYn = CommonUtils.getStrValue(params, "testSendYn");
            log.info(Const.PRIVACY_MARKER, "{}.sendFrndTalkMessage Start ====> paramString : {}", this.getClass(), params);

            /** 사용 가능 채널 확인 */
            if(StringUtils.isBlank((String) sendMsgService.selectValidUseChGrp(params).getData())) {
                log.info(Const.PRIVACY_MARKER, "{}.sendFrndTalkMessage validation use channel fail - params: {}", this.getClass(), params);
                rtn.setFail("이용하실 수 없는 채널입니다.");
                return rtn;
            }

            /** 유효성 체크 */
            requestData = sendMsgService.setFrndTalkSendData(rtn, params);
            if(rtn.isSuccess() == false) {
                log.info("{}.sendFrndTalkMessage validation Check fail: {}", this.getClass(), rtn.getMessage());
                return rtn;
            }

            /** 친구톡 수신자 리스트*/
            recvInfoLst = sendMsgService.getRecvInfoLst(params, multipartFileDTO.getFile());
            if(recvInfoLst == null || recvInfoLst.size() == 0) {
                rtn.setFail(Const.SendMsgErrorSet.INVALID_RECIPIENT_INFO);
                return rtn;
            }

            /** 예약건인지 확인 */
            String rsrvSendYn = (CommonUtils.getStrValue(params, "rsrvSendYn"));
            if(StringUtils.equals(rsrvSendYn, Const.COMM_YES)) {
                return sendMsgService.insertFrndTalkCmWebMsg(rtn, params, requestData, recvInfoLst);
            }

            /** 잔액확인 */
            String payType = sendMsgService.selectPayType(params);

            //선불일경우
            if(StringUtils.equals(payType, Const.PayType.PRE_FEE)) {
                //남은 금액 조회
                BigDecimal rmAmount = sendMsgService.getRmAmount(params);
                //개당 가격 조회(frendtalkText/frendtalkImage/frendtalkWide)
                String productType = Const.MsgProductCode.FRENDTALK_TEXT.getCode();
                if(StringUtils.equals(requestData.getWideImageYn(), Const.COMM_YES)) {
                    productType = Const.MsgProductCode.FRENDTALK_WIDE.getCode();
                } else if(requestData.getImage() != null && StringUtils.isNotBlank(requestData.getImage().getImageUrl())) {
                    productType = Const.MsgProductCode.FRENDTALK_IMAGE.getCode();
                }
                List<String> productCodes = new ArrayList<String>();
                productCodes.add(productType);
                if(params.containsKey("rplcSendType")
                        && !CommonUtils.isEmptyValue(params, "rplcSendType")
                        && !StringUtils.equals((CharSequence) params.get("rplcSendType"), "NONE")) {
                    productCodes.add(Const.MsgProductCode.getCodeByName(CommonUtils.getStrValue(params, "rplcSendType")));
                }

                sParam = new HashMap<>();
                sParam.put("corpId", CommonUtils.getStrValue(params, "corpId"));
                sParam.put("productCodes", productCodes);
                BigDecimal feePerOne = sendMsgService.selectMsgFeePerOne(sParam);
                if(rmAmount.compareTo(feePerOne) < 0) {
                    rtn.setFail("잔액 부족으로 메시지를 발송할 수 없습니다.");
                    return rtn;
                }

                BigDecimal feePerAll = feePerOne.multiply(new BigDecimal(recvInfoLst.size()));
                if(rmAmount.compareTo(feePerAll) < 0) {
                    if(StringUtils.equals(testSendYn, Const.COMM_YES)) {
                        rtn.setSuccess(false);
                        rtn.setMessage("잔액 부족으로 메시지를 발송할 수 없습니다.");
                        return rtn;
                    } else {
                        rtnMap.put("feeMsg", "잔액 부족으로 메시지가 발송되지 않을 수도 있습니다.");
                    }
                }
            }

            /** 테스트발송(동기화) */
            if(StringUtils.equals(testSendYn, Const.COMM_YES)) {
                return sendMsgService.testSendFrndTalkMsg(params, requestData, recvInfoLst);
            }

        } catch (Exception e) {
            rtn.setFail(CommonUtils.getCMExceptionMsg(e, "실패하였습니다."));
            log.error("{}.sendFrndTalkMessage Error : {}", this.getClass(), e);
            return rtn;
        }

        /** 비동기화 발송 */
        try {
            List<Object> reSendCdList = sendMsgService.reSendCdList(null);
            log.info("{}.sendFrndTalkMessage aSync API send Start ====>");
            sendMsgService.sendFrndTalkMsgAsync(rtn, 0, params, requestData, recvInfoLst, reSendCdList);
        } catch (Exception e) {
            log.error("{}.sendFrndTalkMessage aSync API send Error : {}", this.getClass(), e);
        }
        rtn.setMessage("친구톡 "+ApiConfig.MSG_SEND_REQUET_TEXT);

        rtn.setSuccess(true);
        rtn.setData(rtnMap);

        return rtn;
    }

    /**
     * 알림톡 발송 수신자 엑셀업로드 템플릿 다운로드
     * @param request
     * @param response
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @PostMapping(path = "/excelDownSendAlimTalkRecvTmplt")
    public ModelAndView excelDownSendAlimTalkRecvTmplt(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) throws Exception {

        List<String> colLabels = new ArrayList<String>();
        if(params.containsKey("requiredCuid") && (Boolean) params.get("requiredCuid")) {
            colLabels.add("APP 로그인 ID");
        }
        if(params.containsKey("requiredCuPhone") && (Boolean) params.get("requiredCuPhone")) {
            colLabels.add("휴대폰 번호");
        }
        if(params.containsKey("contsVarNms")) {
            List<String> contsVarNms = (ArrayList<String>)params.get("contsVarNms");
            for(String varNm : contsVarNms) {
                colLabels.add(varNm);
            }
        }

        List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetTitle", "Template");
        map.put("colLabels", colLabels.toArray(new String[0]));
        map.put("colIds", new String[] {});
        map.put("numColIds", new String[] {});
        map.put("figureColIds", new String[] {});
        map.put("colDataList", new ArrayList<T>());
        sheetList.add(map);

        ModelAndView model = new ModelAndView("commonXlsxView");
        model.addObject("excelFileName", "alimTalkTemplate_"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
        model.addObject("sheetList", sheetList);

        return model;
    }

    /**
     * 알림톡 메시지 발송처리
     * @param request
     * @param response
     * @param multipartFileDTO
     * @return
     * @throws Exception
     */
    @PostMapping(path="/sendAlimTalkMessage")
    public RestResult<?> sendAlimTalkMessage(HttpServletRequest request, HttpServletResponse response
            , @ModelAttribute MultipartFileDTO multipartFileDTO) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> sParam = new HashMap<String, Object>();
        AlimTalkRequestData requestData = null;
        List<RecvInfo> recvInfoLst = null;
        Map<String, Object> rtnMap = new HashMap<>();

        try {
            params = commonService.setUserInfo(multipartFileDTO.getUnescapeParams());
            String testSendYn = CommonUtils.getStrValue(params, "testSendYn");
            log.info(Const.PRIVACY_MARKER, "{}.sendAlimTalkMessage Start ====> paramString : {}", this.getClass(), params);

            /** 사용 가능 채널 확인 */
            if(StringUtils.isBlank((String) sendMsgService.selectValidUseChGrp(params).getData())) {
                log.info(Const.PRIVACY_MARKER, "{}.sendAlimTalkMessage validation use channel fail - params: {}", this.getClass(), params);
                rtn.setFail("이용하실 수 없는 채널입니다.");
                return rtn;
            }

            /** 유효성 체크 */
            requestData = sendMsgService.setAlimTalkSendData(rtn, params);
            if(rtn.isSuccess() == false) {
                log.info("{}.sendAlimTalkMessage validation Check fail: {}", this.getClass(), rtn.getMessage());
                return rtn;
            }

            /** 알림톡 수신자 리스트*/
            recvInfoLst = sendMsgService.getRecvInfoLst(params, multipartFileDTO.getFile());
            if(recvInfoLst == null || recvInfoLst.size() == 0) {
                rtn.setFail(Const.SendMsgErrorSet.INVALID_RECIPIENT_INFO);
                return rtn;
            }

            /** 예약건인지 확인 */
            String rsrvSendYn = (CommonUtils.getStrValue(params, "rsrvSendYn"));
            if(StringUtils.equals(rsrvSendYn, Const.COMM_YES)) {
                return sendMsgService.insertAlimTalkCmWebMsg(rtn, params, requestData, recvInfoLst);
            }

            /** 잔액확인 */
            String payType = sendMsgService.selectPayType(params);

            //선불일경우
            if(StringUtils.equals(payType, Const.PayType.PRE_FEE)) {
                //남은 금액 조회
                BigDecimal rmAmount = sendMsgService.getRmAmount(params);
                //개당 가격 조회
                List<String> productCodes = new ArrayList<String>();
                productCodes.add(Const.MsgProductCode.getCodeByName(Const.Ch.ALIMTALK));
                if(params.containsKey("rplcSendType")
                        && !CommonUtils.isEmptyValue(params, "rplcSendType")
                        && !StringUtils.equals((CharSequence) params.get("rplcSendType"), "NONE")) {
                    productCodes.add(Const.MsgProductCode.getCodeByName(CommonUtils.getStrValue(params, "rplcSendType")));
                }

                sParam = new HashMap<>();
                sParam.put("corpId", CommonUtils.getStrValue(params, "corpId"));
                sParam.put("productCodes", productCodes);
                BigDecimal feePerOne = sendMsgService.selectMsgFeePerOne(sParam);
                if(rmAmount.compareTo(feePerOne) < 0) {
                    rtn.setFail("잔액 부족으로 메시지를 발송할 수 없습니다.");
                    return rtn;
                }

                BigDecimal feePerAll = feePerOne.multiply(new BigDecimal(recvInfoLst.size()));
                if(rmAmount.compareTo(feePerAll) < 0) {
                    if(StringUtils.equals(testSendYn, Const.COMM_YES)) {
                        rtn.setSuccess(false);
                        rtn.setMessage("잔액 부족으로 메시지를 발송할 수 없습니다.");
                        return rtn;
                    } else {
                        rtnMap.put("feeMsg", "잔액 부족으로 메시지가 발송되지 않을 수도 있습니다.");
                    }
                }
            }

            /** 테스트발송(동기화) */
            if(StringUtils.equals(testSendYn, Const.COMM_YES)) {
                return sendMsgService.testSendAlimTalkMsg(params, requestData, recvInfoLst);
            }

        } catch (Exception e) {
            rtn.setFail(CommonUtils.getCMExceptionMsg(e, "실패하였습니다."));
            log.error("{}.sendAlimTalkMessage Error : {}", this.getClass(), e);
            return rtn;
        }

        /** 비동기화 발송 */
        try {
            List<Object> reSendCdList = sendMsgService.reSendCdList(null);
            log.info("{}.sendAlimTalkMessage aSync API send Start ====>");
            sendMsgService.sendAlimTalkMsgAsync(rtn, 0, params, requestData, recvInfoLst, reSendCdList);
        } catch (Exception e) {
            log.error("{}.sendAlimTalkMessage aSync API send Error : {}", this.getClass(), e);
        }
        rtn.setMessage("알림톡 "+ApiConfig.MSG_SEND_REQUET_TEXT);

        rtn.setSuccess(true);
        rtn.setData(rtnMap);

        return rtn;
    }

    /**
     * 통합발송 발송처리
     * @param request
     * @param response
     * @param multipartFileDTO
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @PostMapping(path="/sendSmartMessage")
    public RestResult<?> sendSmartMessage(HttpServletRequest request, HttpServletResponse response
            , @ModelAttribute MultipartFileDTO multipartFileDTO) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> sParam = new HashMap<String, Object>();
        SmartRequestData requestData = null;
        List<RecvInfo> recvInfoLst = null;
        Map<String, Object> rtnMap = new HashMap<>();

        try {
            params = commonService.setUserInfo(multipartFileDTO.getParams());
            String testSendYn = CommonUtils.getStrValue(params, "testSendYn");
            log.info(Const.PRIVACY_MARKER, "{}.sendSmartMessage Start ====> paramString : {}", this.getClass(), params);

            /** 유효성 체크 */
            requestData = sendMsgService.setSmartSendData(rtn, params);
            if(rtn.isSuccess() == false) {
                log.info(Const.PRIVACY_MARKER, "{}.sendSmartMessage validation Check fail: {}", this.getClass(), rtn.getMessage());
                return rtn;
            }

            /** 수신자 리스트*/
            recvInfoLst = sendMsgService.getRecvInfoLst(params, multipartFileDTO.getFile());
            if(recvInfoLst == null || recvInfoLst.size() == 0) {
                rtn.setFail(Const.SendMsgErrorSet.INVALID_RECIPIENT_INFO);
                return rtn;
            }

            /** RCS 변수 매핑 */
            List<String> chTypeList = (List<String>) params.get("chTypeList");
            if(CollectionUtils.isNotEmpty(chTypeList) && chTypeList.stream().anyMatch(Const.Ch.RCS::equals)) {
                recvInfoLst = sendMsgService.replaceRcsMsgVar(recvInfoLst, params);
            }

            /** 예약건인지 확인 */
            String rsrvSendYn = (CommonUtils.getStrValue(params, "rsrvSendYn"));
            if(StringUtils.equals(rsrvSendYn, Const.COMM_YES)) {
                return sendMsgService.insertSmartCmWebMsg(rtn, params, requestData, recvInfoLst);
            }

            /** 잔액확인 */
            String payType = sendMsgService.selectPayType(params);

            //선불일경우
            if(StringUtils.equals(payType, Const.PayType.PRE_FEE)) {
                //남은 금액 조회
                BigDecimal rmAmount = sendMsgService.getRmAmount(params);
                //개당 가격 조회
                List<String> productCodes = new ArrayList<String>(chTypeList);

                //Friend Talk
                if(CollectionUtils.isNotEmpty(productCodes) && productCodes.stream().anyMatch(Const.Ch.FRIENDTALK::equals)) {
                    Map<String, Object> frndPrdtInfo = sendMsgService.selectSmartTmpltFrndPrdtInfo(params);
                    String msgType = CommonUtils.getStrValue(frndPrdtInfo, "msgType");
                    String wideImageYn = CommonUtils.getStrValue(frndPrdtInfo, "wideImageYn");
                    String productType = "";

                    if(StringUtils.equals(msgType, Const.MsgType.BASE)) {
                        productType = Const.MsgProductCode.FRENDTALK_TEXT.getCode();
                    }else if(StringUtils.equals(wideImageYn, Const.COMM_YES)) {
                        productType = Const.MsgProductCode.FRENDTALK_WIDE.getCode();
                    } else {
                        productType = Const.MsgProductCode.FRENDTALK_IMAGE.getCode();
                    }
                    Collections.replaceAll(productCodes, Const.Ch.FRIENDTALK, productType);
                }
                //RCS
                if(CollectionUtils.isNotEmpty(productCodes) && productCodes.stream().anyMatch(Const.Ch.RCS::equals)) {
                    Map<String, Object> rcsInfo = sendMsgService.selectSmartTmpltRcsInfo(params);
                    String rcsPrdType = CommonUtils.getStrValue(rcsInfo, "rcsPrdType");
                    String productType = "";

                    for(String key : Const.rcsPrdGrp.keySet()) {
                        if(Const.rcsPrdGrp.get(key).stream().anyMatch(rcsPrdType::equals)) {
                            productType = Const.MsgProductCode.getCodeByName(key);
                            break;
                        }
                    }
                    if(StringUtils.isNotBlank(productType)) {
                        Collections.replaceAll(productCodes, Const.Ch.RCS, productType);
                    }
                }

                sParam = new HashMap<>();
                sParam.put("corpId", CommonUtils.getStrValue(params, "corpId"));
                sParam.put("productCodes", productCodes);
                BigDecimal feePerOne = sendMsgService.selectMsgFeePerOne(sParam);
                if(rmAmount.compareTo(feePerOne) < 0) {
                    rtn.setFail("잔액 부족으로 메시지를 발송할 수 없습니다.");
                    return rtn;
                }

                BigDecimal feePerAll = feePerOne.multiply(new BigDecimal(recvInfoLst.size()));
                if(rmAmount.compareTo(feePerAll) < 0) {
                    if(StringUtils.equals(testSendYn, Const.COMM_YES)) {
                        rtn.setSuccess(false);
                        rtn.setMessage("잔액 부족으로 메시지를 발송할 수 없습니다.");
                        return rtn;
                    } else {
                        rtnMap.put("feeMsg", "잔액 부족으로 메시지가 발송되지 않을 수도 있습니다.");
                    }
                }
            }

            /** 테스트발송(동기화) */
            if(StringUtils.equals(testSendYn, Const.COMM_YES)) {
                return sendMsgService.testSendSmartMsg(params, requestData, recvInfoLst);
            }


        } catch (Exception e) {
            rtn.setFail(CommonUtils.getCMExceptionMsg(e, "실패하였습니다."));
            return rtn;
        }

        /** 비동기화 발송 */
        try {
            List<Object> reSendCdList = sendMsgService.reSendCdList(null);
            log.info("{}.sendSmartMessage aSync API send Start ====>");
            sendMsgService.sendSmartMsgAsync(rtn, 0, params, requestData, recvInfoLst, reSendCdList);
        } catch (Exception e) {
            log.error("{}.sendSmartMessage aSync API send Error : {}", this.getClass(), e);
        }
        String senderType = CommonUtils.getStrValue(params, "senderType");
        String msg = (StringUtils.equals(senderType, Const.SenderType.SMART) ? "스마트" : "통합");
        rtn.setMessage(msg+StringUtils.SPACE+ApiConfig.MSG_SEND_REQUET_TEXT);

        rtn.setSuccess(true);
        rtn.setData(rtnMap);

        return rtn;
    }

    /**
     * 스마트 템플릿 리스트 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/selectSmartTmpltList")
    public RestResult<?> selectSmartTmpltList(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = sendMsgService.selectSmartTmpltList(params);
        } catch (Exception e) {
            rtn.setFail("실패하였습니다");
            log.error("{}.selectSmartTmpltList Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 스마트 템플릿 정보 조회
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/selectSmartTmpltInfo")
    public RestResult<?> selectSmartTmpltInfo(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = sendMsgService.selectSmartTmpltInfo(params);
        } catch (Exception e) {
            rtn.setFail("실패하였습니다.");
            log.error("{}.selectSmartTmpltInfo Error : {}", this.getClass(), e);
        }

        return rtn;
    }

    /**
     * 통합/스마트 발송 수신자 엑셀업로드 템플릿 다운로드
     * @param request
     * @param response
     * @param params
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @PostMapping(path = "/excelDownSendSmartRecvTmplt")
    public ModelAndView excelDownSendIntegRecvTmplt(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) throws Exception {

        List<String> colLabels = new ArrayList<String>();
        if(params.containsKey("requiredCuid") && (Boolean) params.get("requiredCuid")) {
            colLabels.add("APP 로그인 ID");
        }
        if(params.containsKey("requiredCuPhone") && (Boolean) params.get("requiredCuPhone")) {
            colLabels.add("휴대폰 번호");
        }
        if(params.containsKey("contsVarNms")) {
            List<String> contsVarNms = (ArrayList<String>)params.get("contsVarNms");
            for(String varNm : contsVarNms) {
                colLabels.add(varNm);
            }
        }

        List<Map<String, Object>> sheetList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetTitle", "Template");
        map.put("colLabels", colLabels.toArray(new String[0]));
        map.put("colIds", new String[] {});
        map.put("numColIds", new String[] {});
        map.put("figureColIds", new String[] {});
        map.put("colDataList", new ArrayList<T>());
        sheetList.add(map);

        ModelAndView model = new ModelAndView("commonXlsxView");
        model.addObject("excelFileName", "smartTemplate_"+DateUtil.getCurrentDate("yyyyMMddHHmmss"));
        model.addObject("sheetList", sheetList);

        return model;
    }

    /**
     * 채널 사용 가능 여부
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/selectValidUseChGrp")
    public RestResult<?> selectValidUseChGrp(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = sendMsgService.selectValidUseChGrp(params);
        } catch (Exception e) {
            rtn.setFail("실패하였습니다.");
            log.error("{}.selectUseChannel Error : {}", this.getClass(), e);
        }
        return rtn;
    }

    /**
     * 수신거부번호관리 등록
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/insertRejectPhone")
    public RestResult<?> insertRejectPhone(@RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = sendMsgService.insertRejectPhone(params);
        } catch (Exception e) {
            rtn.setFail("실패하였습니다.");
            log.error("{}.insertRejectPhone Error : {}", this.getClass(), e);
        }
        return rtn;
    }

    /**
     * 수신거부번호관리 리스트 조회
     * @param params
     * @return
     */
    @PostMapping("/selectRejectPhoneList")
    public RestResult<?> selectRejectPhoneList(@RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = sendMsgService.selectRejectPhoneList(params);
        } catch (Exception e) {
            rtn.setFail("실패하였습니다.");
            log.error("{}.selectRejectPhoneList Error : {}", this.getClass(), e);
        }
        return rtn;
    }

    /**
     * 수신거부번호 삭제처리
     * @param params
     * @return
     */
    @PostMapping("/deleteRejectPhoneList")
    public RestResult<?> deleteRejectPhoneList(@RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = sendMsgService.deleteRejectPhoneList(params);
        } catch (Exception e) {
            rtn.setFail("실패하였습니다.");
            log.error("{}.deleteRejectPhoneList Error : {}", this.getClass(), e);
        }
        return rtn;
    }

}

