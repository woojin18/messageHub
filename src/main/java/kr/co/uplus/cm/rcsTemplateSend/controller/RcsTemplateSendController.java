package kr.co.uplus.cm.rcsTemplateSend.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.rcsTemplateSend.Service.RcsTemplateSendService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/uc/rcsTemplateSend")
public class RcsTemplateSendController {
	
	@Autowired
	private RcsTemplateSendService rcsTemplateSendSvc;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}
	
	@PostMapping("/rcsTemplatePopInit")
	public RestResult<?> rcsTemplatePopInit(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		RestResult<Object> rtn = new RestResult<Object>(true);
		
		try {
			rtn = rcsTemplateSendSvc.rcsTemplatePopInit(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}

		return rtn;
	}

}
