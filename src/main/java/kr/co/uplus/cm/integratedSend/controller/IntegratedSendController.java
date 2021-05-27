package kr.co.uplus.cm.integratedSend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.integratedSend.service.IntegratedSendService;
import kr.co.uplus.cm.utils.DateUtil;
import lombok.extern.log4j.Log4j2;

/**
 * <pre>
 * SendController
 * 통합발송 관리 Controller
 * </pre>
 *
 * @author : liam (Lee Sung Min)
 * @Date : 2021.03.25.
 * @Version : 1.0 Copyright 2020 LG Uplus Corp. All Rights Reserved.
 */
@Log4j2
@RestController
@RequestMapping("/integratedSendApi")
public class IntegratedSendController {

	@Autowired
	private IntegratedSendService integratedSendService;

    @InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}

	/**
	 * 통합 템플릿 리스트 조회
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectIntegratedSendList")
	public RestResult<?> selectIntegratedSendList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = integratedSendService.selectIntegratedSendList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 통합 템플릿 단건 조회
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectIntegratedSendInfo")
	public RestResult<?> selectIntegratedSendInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = integratedSendService.selectIntegratedSendInfo(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}

	/**
	 * 통합 템플릿 저장처리
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
//	@PostMapping("/insertIntegratedSend")
//	public RestResult<?> insertIntegratedSend(HttpServletRequest request, HttpServletResponse response,
//			@RequestBody Map<String, Object> params) {
//		RestResult<Object> rtn = new RestResult<Object>();
//
//		try {
//			rtn = integratedSendService.insertIntegratedSend(params);
//		} catch (Exception e) {
//			rtn.setSuccess(false);
//			rtn.setMessage("실패하였습니다.");
//            log.error("{} Error : {}", this.getClass(), e);
//		}
//		return rtn;
//	}

	/**
	 * 통합 템플릿 삭제처리
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/deleteIntegratedSend")
	public RestResult<?> deleteIntegratedSend(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = integratedSendService.deleteIntegratedSend(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
            log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}





}
