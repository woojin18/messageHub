package kr.co.uplus.cm.sendMessage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.sendMessage.dto.PushRequestDto;
import kr.co.uplus.cm.sendMessage.service.SendMessageService;
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
    private SendMessageService sendMsgService;

    @Autowired
    private CommonService commonService;

    @PostMapping("/apiTest")
    public RestResult<?> apiTest(HttpServletRequest request, HttpServletResponse response,
            @Valid @RequestBody PushRequestDto pushRequestDto, BindingResult bindingResult) {
        RestResult<Object> rtn = new RestResult<Object>();
        rtn.setSuccess(true);

        if(bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            String errorMsg = "";
            for(ObjectError error : list) {
                errorMsg += (StringUtils.isBlank(errorMsg) ? "" : "\\n") + error.getDefaultMessage();
                System.out.println(error);
            }
            rtn.setSuccess(false);
            rtn.setMessage(errorMsg);
        }

        try {
            //rtn = sendMsgService.selectAppIdList(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
        }
        return rtn;
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
    @SuppressWarnings("unchecked")
    @PostMapping(path="/sendPushMessage")
    public RestResult<?> sendPushMessage(HttpServletRequest request, HttpServletResponse response
            , @RequestParam String paramString
            , @RequestPart(value="excelFile", required=false) MultipartFile excelFile) throws Exception {
        RestResult<Object> rtn = new RestResult<Object>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> params = new HashMap<String, Object>();
            params = mapper.readValue(paramString, Map.class);

            //TODO : log, 삭제예정
            for(Object key : params.keySet()) {
                log.info("key:{}, value: {} ", key, params.get(key));
            }

            List<Map<String, Object>> excelList = null;
            if(params.containsKey("cuInputType")
                    && StringUtils.equals("EXCEL", (String)params.get("cuInputType"))) {
                //read excelFile
                List<String> colKeys = new ArrayList<String>();
                if(params.containsKey("requiredCuid") && (Boolean) params.get("requiredCuid")) {
                    colKeys.add("APP 로그인 ID");
                }
                if(params.containsKey("requiredCuPhone") && (Boolean) params.get("requiredCuPhone")) {
                    colKeys.add("휴대폰 번호");
                }
                if(params.containsKey("contsVarNms")) {
                    List<String> contsVarNms = (ArrayList<String>)params.get("contsVarNms");
                    for(String varNm : contsVarNms) {
                        colKeys.add(varNm);
                    }
                }
                excelList = commonService.getExcelDataList(excelFile, 2, colKeys);

                //TODO : log, 삭제예정
                for(Map<String, Object> m : excelList) {
                    for(Object key : m.keySet()) {
                        log.info("key:{}, value: {} ", key, params.get(key));
                    }
                }
            }
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
        }

        return rtn;
    }

}























