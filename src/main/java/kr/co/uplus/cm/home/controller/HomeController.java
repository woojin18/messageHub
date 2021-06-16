package kr.co.uplus.cm.home.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.home.service.HomeService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/home")
public class HomeController {

	@Autowired
	private HomeService homeSvc;

	/**
	 * 프로젝트정보 조회
	 * 
	 * @param request
	 * @param response
	 * @param params
	 * @return
	 */
	@PostMapping("/selectProjectInfo")
	public RestResult<?> selectProjectInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> params) {

		RestResult<Object> rtn = new RestResult<Object>();
		try {
			rtn = homeSvc.selectProjectInfo(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("실패하였습니다.");
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}
}
