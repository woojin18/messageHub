package kr.co.uplus.cm.project.controller;

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

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.project.service.BaseInfoService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/baseInfoApi/manage")

public class BaseInfoController {

	@Autowired
	private BaseInfoService baseInfoSvc;

	@Autowired
	private CommonService commonService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}
	
	/**
	 * 프로젝트 기본정보 조회
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectProjectBaseInfo")
	public RestResult<?> selectProjectBaseInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = baseInfoSvc.selectProjectBaseInfo(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}
	
	/**
	 * 프로젝트 기본정보 저장
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/saveProjectBaseInfo")
	public RestResult<?> saveProjectBaseInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
 
		try {
			rtn = baseInfoSvc.saveProjectBaseInfo(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
}

