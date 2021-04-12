package kr.co.uplus.cloud.sendMessage.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cloud.common.dto.RestResult;
import kr.co.uplus.cloud.sendMessage.dto.PushRequestDto;
import kr.co.uplus.cloud.sendMessage.service.SendMessageService;
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
        }
        return rtn;
    }

    /**
     * 푸시 메시지 발송처리
     *
     * @param request
     * @param response
     * @param params
     * @return
     */
    @PostMapping("/push")
    public RestResult<?> sendPushMessage(HttpServletRequest request, HttpServletResponse response,
        	@RequestBody Map<String, Object> params) {
        RestResult<Object> rtn = new RestResult<Object>();
        try {
            rtn = sendMsgService.sendPushMessage(params);
        } catch (Exception e) {
            rtn.setSuccess(false);
            rtn.setMessage("실패하였습니다.");
        }

        return rtn;
    }

}
