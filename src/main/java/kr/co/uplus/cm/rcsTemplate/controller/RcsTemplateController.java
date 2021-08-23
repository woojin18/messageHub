package kr.co.uplus.cm.rcsTemplate.controller;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.rcsTemplate.service.RcsTemplateService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/uc/rcsTemplate")
public class RcsTemplateController {
	
	@Autowired
	private RcsTemplateService rcsTemplateSvc;
	
	@Autowired
	private CommonService commonSvc;
	
    @InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}
	
	@PostMapping("/corpBrandCnt")
	public RestResult<?> corpBrandCnt(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			rtn = rcsTemplateSvc.corpBrandCnt(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	@PostMapping("/selectRcsTemplateList")
	public RestResult<?> selectRcsTemplateList(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			rtn = rcsTemplateSvc.selectRcsTemplateList(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	@PostMapping("/rcsTemplateInit")
	public RestResult<?> rcsTemplateInit(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			rtn = rcsTemplateSvc.rcsTemplateInit(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	@PostMapping("/rcsTemplateUpdateInit")
	public RestResult<?> rcsTemplateUpdateInit(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			rtn = rcsTemplateSvc.rcsTemplateUpdateInit(params); 
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	@PostMapping("/rcsTemplateDeleteApi")
	public RestResult<?> rcsTemplateDeleteApi(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			rcsTemplateSvc.rcsTemplateDeleteApi(params); 
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	@PostMapping("/rcsTemplateCancelApi")
	public RestResult<?> rcsTemplateCancelApi(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			rcsTemplateSvc.rcsTemplateCancelApi(params); 
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}
	
	@PostMapping("/rcsTemplateApi")
	public RestResult<?> rcsTemplateApi(@RequestBody Map<String, Object> params, HttpServletRequest request) throws Exception {
		
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			rcsTemplateSvc.rcsTemplateApi(params); 
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}

}
