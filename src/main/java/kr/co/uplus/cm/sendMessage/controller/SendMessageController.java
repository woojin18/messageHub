package kr.co.uplus.cm.sendMessage.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.controller.BaseController;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.sendMessage.dto.PushRecvInfo;
import kr.co.uplus.cm.sendMessage.dto.PushRequestData;
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
public class SendMessageController extends BaseController {

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
            super.setContainIgnoreUserInfo(params);
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
            super.setContainIgnoreUserInfo(params);
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
            super.setContainIgnoreUserInfo(params);
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
    @SuppressWarnings("unchecked")
    @PostMapping(path="/sendPushMessage")
    public RestResult<?> sendPushMessage(HttpServletRequest request, HttpServletResponse response
            , @RequestParam(value = "paramString") String paramString
            , @RequestParam(value="excelFile", required=false) MultipartFile excelFile) throws Exception {

        List<PushRecvInfo> recvInfoLst = null;
        Map<String, Object> params = null;
        RestResult<Object> rtn = new RestResult<Object>();
        Map<String, Object> rtnMap = new HashMap<>();
        PushRequestData requestData = null;

        int fromIndex = 0;
        int toIndex = 1;

        try {
            log.info("{}.sendPushMessage Start ====> paramString : {}", this.getClass(), paramString);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> sParam = new HashMap<>();
            params = mapper.readValue(paramString, Map.class);

            super.setContainIgnoreUserInfo(params);
            String testSendYn = CommonUtils.getStrValue(params, "testSendYn");

            /** 유효성 체크 */
            requestData = sendMsgService.setPushSendData(rtn, params);
            if(rtn.isSuccess() == false) {
                return rtn;
            }
            log.info("{}.sendPushMessage pushRequestData: {}", this.getClass(), requestData.toString());

            /** 푸시 수신자 리스트*/
            recvInfoLst = sendMsgService.getRecvInfoLst(params, excelFile);
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
                BigDecimal rmAmount = sendMsgService.getRmAmount();
                //개당 가격 조회
                sParam = new HashMap<>();
                sParam.put("push", Const.COMM_YES);
                if(params.containsKey("rplcSendType")
                        && !CommonUtils.isEmptyValue(params, "rplcSendType")
                        && !StringUtils.equals((CharSequence) params.get("rplcSendType"), "NONE")) {
                    String key = ((String) params.get("rplcSendType")).toLowerCase();
                    sParam.put(key, Const.COMM_YES);
                }
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
                sendMsgService.insertPushCmWebMsg(rtn, params, requestData, recvInfoLst);
                if(rtn.isSuccess() == false) {
                    return rtn;
                }
            }

            /** 테스트발송(동기화) */
            if(StringUtils.equals(testSendYn, Const.COMM_YES)) {
                rtn = sendMsgService.testSendPushMsg(params, requestData, recvInfoLst);
                return rtn;
            }

            /** 1건 발송(결과값 확인용)(동기화) */
            /* http 통신 오류 확인을 위해 보냈던 1건 처리(전송에 실패하였습니다.) 일단 보류
             * 현재 로직도 일단 http 통신 오류 뿐만 아니라 기타 오류건도 잡기 때문에 추후 수정 필요(79998: 기타오류, 29000:CPS 초과, 재시도 필요)
            log.info("{}.sendPushMessage sync API send Start ====>");
            Map<String, Object> responseBody = sendMsgService.sendPushMsg(rtn, params, requestData, recvInfoLst.subList(fromIndex, toIndex));
            log.info("{}.sendPushMessage sync API send Response Body : {}", this.getClass(), responseBody);

            if(CommonUtils.isEmptyValue(responseBody, "rslt")
                    || !StringUtils.equals(Const.API_SUCCESS, CommonUtils.getString(responseBody.get("rslt")))) {
                rtn.setSuccess(false);
                rtn.setMessage("푸시 발송 요청에 실패하였습니다.");
                return rtn;
            }
            fromIndex = toIndex;
            */

        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
            return rtn;
        }

        /** 1건 이후 발송(비동기화) */
        if(recvInfoLst.size() > fromIndex) {
            try {
                log.info("{}.sendPushMessage aSync API send Start ====>");
                sendMsgService.sendPushMsgAsync(rtn, fromIndex, params, requestData, recvInfoLst);
            } catch (Exception e) {
                log.info("{}.sendPushMessage aSync API send Error : {}", this.getClass(), e);
            }
            rtn.setMessage("푸시 발송 요청처리 되었습니다.");

        } else {
            sendMsgService.insertPushCmWebMsg(rtn, params, requestData, recvInfoLst);
            rtn.setMessage("푸시 발송 처리 되었습니다.");
        }

        rtn.setSuccess(true);
        rtn.setData(rtnMap);

        return rtn;
    }

}

