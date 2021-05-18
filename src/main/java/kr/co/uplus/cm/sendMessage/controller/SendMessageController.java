package kr.co.uplus.cm.sendMessage.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
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
import kr.co.uplus.cm.sendMessage.dto.FrndTalkRequestData;
import kr.co.uplus.cm.sendMessage.dto.MmsRequestData;
import kr.co.uplus.cm.sendMessage.dto.PushRequestData;
import kr.co.uplus.cm.sendMessage.dto.RecvInfo;
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
@RequestMapping("/api/public/sendMessage")
public class SendMessageController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private SendMessageService sendMsgService;

    @Autowired
    ApiInterface apiInterface;

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
            log.error("{} Error : {}", this.getClass(), e);
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
            log.error("{} Error : {}", this.getClass(), e);
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
            log.error("{} Error : {}", this.getClass(), e);
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
            log.error("{} Error : {}", this.getClass(), e);
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
        model.addObject("excelFileName", "pushTemplate_"+DateUtil.getCurrnetDate("yyyyMMddHHmmss"));
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
            params = commonService.setUserInfo(multipartFileDTO.getParams());
            String testSendYn = CommonUtils.getStrValue(params, "testSendYn");
            log.info("{}.sendPushMessage Start ====> params : {}", this.getClass(), params);

            /** 유효성 체크 */
            requestData = sendMsgService.setPushSendData(rtn, params);
            if(rtn.isSuccess() == false) {
                log.info("{}.sendPushMessage validation Check fail: {}", this.getClass(), rtn.getMessage());
                return rtn;
            }
            log.info("{}.sendPushMessage pushRequestData: {}", this.getClass(), requestData.toString());

            /** 푸시 수신자 리스트*/
            recvInfoLst = sendMsgService.getRecvInfoLst(params, multipartFileDTO.getFile());
            if(recvInfoLst == null || recvInfoLst.size() == 0) {
                rtn.setSuccess(false);
                rtn.setMessage("잘못된 푸시 수신자 정보입니다.");
                return rtn;
            }

            /** 잔액확인 */
            String payType = sendMsgService.selectPayType(params);

            //선불일경우
            if(StringUtils.equals(payType, Const.COMM_YES)) {
                //남은 금액 조회
                BigDecimal rmAmount = sendMsgService.getRmAmount(params);
                //개당 가격 조회
                List<String> productCodes = new ArrayList<String>();
                productCodes.add(Const.MsgProductCode.getType(Const.Ch.PUSH));
                if(params.containsKey("rplcSendType")
                        && !CommonUtils.isEmptyValue(params, "rplcSendType")
                        && !StringUtils.equals((CharSequence) params.get("rplcSendType"), "NONE")) {
                    productCodes.add(Const.MsgProductCode.getType(CommonUtils.getStrValue(params, "rplcSendType")));
                }

                sParam = new HashMap<>();
                sParam.put("corpId", CommonUtils.getStrValue(params, "corpId"));
                sParam.put("productCodes", productCodes);
                BigDecimal feePerOne = sendMsgService.selectMsgFeePerOne(sParam);
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

            /** 예약건인지 확인(10분) */
            //즉시건일때 웹 발송 내역(CM_WEB_MSG)은 발송건을 모두 G/W로 보내고 등록해달라는 요청
            //예약건과 즉시건의 웹 발송 내역 등록 분리
            String rsrvSendYn = (CommonUtils.getStrValue(params, "rsrvSendYn"));
            if(StringUtils.equals(rsrvSendYn, Const.COMM_YES)) {
                return sendMsgService.insertPushCmWebMsg(rtn, params, requestData, recvInfoLst);
            }

            /** 테스트발송(동기화) */
            if(StringUtils.equals(testSendYn, Const.COMM_YES)) {
                return sendMsgService.testSendPushMsg(params, requestData, recvInfoLst);
            }

        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
            return rtn;
        }

        /** 비동기화 발송 */
        try {
            List<Object> reSendCdList = sendMsgService.selectGernalList(null);
            log.info("{}.sendPushMessage aSync API send Start ====>");
            sendMsgService.sendPushMsgAsync(rtn, 0, params, requestData, recvInfoLst, reSendCdList);
        } catch (Exception e) {
            log.info("{}.sendPushMessage aSync API send Error : {}", this.getClass(), e);
        }
        rtn.setMessage("푸시 발송 요청처리 되었습니다.");

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
        model.addObject("excelFileName", "smsTemplate_"+DateUtil.getCurrnetDate("yyyyMMddHHmmss"));
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
            params = commonService.setUserInfo(multipartFileDTO.getParams());
            String testSendYn = CommonUtils.getStrValue(params, "testSendYn");
            log.info("{}.sendSmsMessage Start ====> paramString : {}", this.getClass(), params);

            /** 유효성 체크 */
            requestData = sendMsgService.setSmsSendData(rtn, params);
            if(rtn.isSuccess() == false) {
                log.info("{}.sendSmsMessage validation Check fail: {}", this.getClass(), rtn.getMessage());
                return rtn;
            }
            log.info("{}.sendSmsMessage requestData: {}", this.getClass(), requestData.toString());

            /** SMS 수신자 리스트*/
            recvInfoLst = sendMsgService.getRecvInfoLst(params, multipartFileDTO.getFile());
            if(recvInfoLst == null || recvInfoLst.size() == 0) {
                rtn.setSuccess(false);
                rtn.setMessage("잘못된 SMS 수신자 정보입니다.");
                return rtn;
            }

            /** 잔액확인 */
            String payType = sendMsgService.selectPayType(params);

            //선불일경우
            if(StringUtils.equals(payType, Const.COMM_YES)) {
                //남은 금액 조회
                BigDecimal rmAmount = sendMsgService.getRmAmount(params);
                //개당 가격 조회
                List<String> productCodes = new ArrayList<String>();
                productCodes.add(Const.MsgProductCode.getType(Const.Ch.SMS));

                sParam = new HashMap<>();
                sParam.put("corpId", CommonUtils.getStrValue(params, "corpId"));
                sParam.put("productCodes", productCodes);
                BigDecimal feePerOne = sendMsgService.selectMsgFeePerOne(sParam);
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

            /** 예약건인지 확인(10분) */
            //즉시건일때 웹 발송 내역(CM_WEB_MSG)은 발송건을 모두 G/W로 보내고 등록해달라는 요청
            //예약건과 즉시건의 웹 발송 내역 등록 분리
            String rsrvSendYn = (CommonUtils.getStrValue(params, "rsrvSendYn"));
            if(StringUtils.equals(rsrvSendYn, Const.COMM_YES)) {
                return sendMsgService.insertSmsCmWebMsg(rtn, params, requestData, recvInfoLst);
            }

            /** 테스트발송(동기화) */
            if(StringUtils.equals(testSendYn, Const.COMM_YES)) {
                return sendMsgService.testSendSmsMsg(params, requestData, recvInfoLst);
            }

        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
            return rtn;
        }

        try {
            List<Object> reSendCdList = sendMsgService.selectGernalList(null);
            log.info("{}.sendSmsMessage aSync API send Start ====>");
            sendMsgService.sendSmsMsgAsync(rtn, 0, params, requestData, recvInfoLst, reSendCdList);
        } catch (Exception e) {
            log.info("{}.sendSmsMessage aSync API send Error : {}", this.getClass(), e);
        }

        rtn.setMessage("SMS 발송 요청처리 되었습니다.");
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
            params = commonService.setUserInfo(multipartFileDTO.getParams());
            String testSendYn = CommonUtils.getStrValue(params, "testSendYn");
            log.info("{}.sendMmsMessage Start ====> paramString : {}", this.getClass(), params);

            /** 유효성 체크 */
            requestData = sendMsgService.setMmsSendData(rtn, params);
            if(rtn.isSuccess() == false) {
                log.info("{}.sendMmsMessage validation Check fail: {}", this.getClass(), rtn.getMessage());
                return rtn;
            }

            /** MMS 수신자 리스트*/
            recvInfoLst = sendMsgService.getRecvInfoLst(params, multipartFileDTO.getFile());
            if(recvInfoLst == null || recvInfoLst.size() == 0) {
                rtn.setSuccess(false);
                rtn.setMessage("잘못된 MMS 수신자 정보입니다.");
                return rtn;
            }

            /** 잔액확인 */
            String payType = sendMsgService.selectPayType(params);

            //선불일경우
            if(StringUtils.equals(payType, Const.COMM_YES)) {
                //남은 금액 조회
                BigDecimal rmAmount = sendMsgService.getRmAmount(params);
                //개당 가격 조회
                List<String> productCodes = new ArrayList<String>();
                productCodes.add(Const.MsgProductCode.getType(Const.Ch.MMS));

                sParam = new HashMap<>();
                sParam.put("corpId", CommonUtils.getStrValue(params, "corpId"));
                sParam.put("productCodes", productCodes);
                BigDecimal feePerOne = sendMsgService.selectMsgFeePerOne(sParam);
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

            /** 예약건인지 확인(10분) */
            //즉시건일때 웹 발송 내역(CM_WEB_MSG)은 발송건을 모두 G/W로 보내고 등록해달라는 요청
            //예약건과 즉시건의 웹 발송 내역 등록 분리
            String rsrvSendYn = (CommonUtils.getStrValue(params, "rsrvSendYn"));
            if(StringUtils.equals(rsrvSendYn, Const.COMM_YES)) {
                return sendMsgService.insertMmsCmWebMsg(rtn, params, requestData, recvInfoLst);
            }

            /** 테스트발송(동기화) */
            if(StringUtils.equals(testSendYn, Const.COMM_YES)) {
                return sendMsgService.testSendMmsMsg(params, requestData, recvInfoLst);
            }

        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.sendMmsMessage Error : {}", this.getClass(), e);
            return rtn;
        }

        try {
            List<Object> reSendCdList = sendMsgService.selectGernalList(null);
            log.info("{}.sendMmsMessage aSync API send Start ====>");
            sendMsgService.sendMmsMsgAsync(rtn, 0, params, requestData, recvInfoLst, reSendCdList);
        } catch (Exception e) {
            log.info("{}.sendMmsMessage aSync API send Error : {}", this.getClass(), e);
        }

        rtn.setMessage("MMS 발송 요청처리 되었습니다.");
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
        model.addObject("excelFileName", "frndTalkTemplate_"+DateUtil.getCurrnetDate("yyyyMMddHHmmss"));
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
            params = commonService.setUserInfo(multipartFileDTO.getParams());
            String testSendYn = CommonUtils.getStrValue(params, "testSendYn");
            log.info("{}.sendFrndTalkMessage Start ====> paramString : {}", this.getClass(), params);

            /** 유효성 체크 */
            requestData = sendMsgService.setFrndTalkSendData(rtn, params);
            if(rtn.isSuccess() == false) {
                log.info("{}.sendFrndTalkMessage validation Check fail: {}", this.getClass(), rtn.getMessage());
                return rtn;
            }

            /** 친구톡 수신자 리스트*/
            recvInfoLst = sendMsgService.getRecvInfoLst(params, multipartFileDTO.getFile());
            if(recvInfoLst == null || recvInfoLst.size() == 0) {
                rtn.setSuccess(false);
                rtn.setMessage("잘못된 친구톡 수신자 정보입니다.");
                return rtn;
            }

            /** 잔액확인 */
            String payType = sendMsgService.selectPayType(params);

            //선불일경우
            if(StringUtils.equals(payType, Const.COMM_YES)) {
                //남은 금액 조회
                BigDecimal rmAmount = sendMsgService.getRmAmount(params);
                //개당 가격 조회(frendtalkText/frendtalkImage/frendtalkWide)
                String productType = Const.MsgProductCode.getType("FRENDTALK_TEXT");
                if(StringUtils.equals(requestData.getWideImageYn(), Const.COMM_YES)) {
                    productType = Const.MsgProductCode.getType("FRENDTALK_WIDE");
                } else if(requestData.getImage() != null && StringUtils.isNotBlank(requestData.getImage().getImageUrl())) {
                    productType = Const.MsgProductCode.getType("FRENDTALK_IMAGE");
                }
                List<String> productCodes = new ArrayList<String>();
                productCodes.add(productType);
                if(params.containsKey("rplcSendType")
                        && !CommonUtils.isEmptyValue(params, "rplcSendType")
                        && !StringUtils.equals((CharSequence) params.get("rplcSendType"), "NONE")) {
                    productCodes.add(Const.MsgProductCode.getType(CommonUtils.getStrValue(params, "rplcSendType")));
                }

                sParam = new HashMap<>();
                sParam.put("corpId", CommonUtils.getStrValue(params, "corpId"));
                sParam.put("productCodes", productCodes);
                BigDecimal feePerOne = sendMsgService.selectMsgFeePerOne(sParam);
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

            /** 예약건인지 확인(10분) */
            //즉시건일때 웹 발송 내역(CM_WEB_MSG)은 발송건을 모두 G/W로 보내고 등록해달라는 요청
            //예약건과 즉시건의 웹 발송 내역 등록 분리
            String rsrvSendYn = (CommonUtils.getStrValue(params, "rsrvSendYn"));
            if(StringUtils.equals(rsrvSendYn, Const.COMM_YES)) {
                return sendMsgService.insertFrndTalkCmWebMsg(rtn, params, requestData, recvInfoLst);
            }

            /** 테스트발송(동기화) */
            if(StringUtils.equals(testSendYn, Const.COMM_YES)) {
                //return sendMsgService.testSendPushMsg(params, requestData, recvInfoLst);
            }

        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{}.sendFrndTalkMessage Error : {}", this.getClass(), e);
            return rtn;
        }

        /** 비동기화 발송 */
        try {
            List<Object> reSendCdList = sendMsgService.selectGernalList(null);
            log.info("{}.sendFrndTalkMessage aSync API send Start ====>");
            sendMsgService.sendFrndTalkMsgAsync(rtn, 0, params, requestData, recvInfoLst, reSendCdList);
        } catch (Exception e) {
            log.info("{}.sendFrndTalkMessage aSync API send Error : {}", this.getClass(), e);
        }
        rtn.setMessage("친구톡 발송 요청처리 되었습니다.");

        rtn.setSuccess(true);
        rtn.setData(rtnMap);

        return rtn;
    }

}

