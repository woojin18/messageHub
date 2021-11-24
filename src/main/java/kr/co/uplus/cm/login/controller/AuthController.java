package kr.co.uplus.cm.login.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.login.service.AuthService;
import kr.co.uplus.cm.user.service.UserService;
import lombok.extern.log4j.Log4j2;

/**
 * <pre>
 * AuthSampleController
 *
 * </pre>
 *
 * @author : savrani (Son Wang Gu)
 * @Date : 2020. 12. 18.
 * @Version : 1.0 Copyright 2020 LG Uplus Corp. All Rights Reserved.
 */
@Log4j2
@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	private AuthService authSvc;
	
	@Autowired
	private UserService userSvc;
	
    @InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields(Const.DISALLOWED_FIELDS);
	}

	@PostMapping("/auth/login")
	public RestResult<?> login(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {

//		System.out.println("Start CheckLogin!!!");

		return authSvc.chkLogin(params, request, response);
	}

	@PostMapping("/auth/sso")
	public RestResult<?> sso(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		return authSvc.sso(params, request, response);
	}

	@GetMapping("/auth/logout")
	public RestResult<?> logoutTest(HttpServletRequest request, HttpServletResponse response) {

//		System.out.println("Logout!!!");

		return authSvc.logout(request, response);
	}

	@PostMapping("/auth/getMenuForRole")
	public RestResult<?> getMenuForRole(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return authSvc.getMenuForRole(params, request, response);
	}

	@PostMapping("/auth/getProjectForUser")
	public RestResult<?> getProjectForUser(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return authSvc.getProjectForUser(params, request, response);
	}

	@PostMapping("/auth/getProjectChUseListForUser")
	public RestResult<?> getProjectChUseListForUser(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return authSvc.getProjectChUseListForUser(params, request, response);
	}

	@PostMapping("/public/token")
	public RestResult<?> publicToken(@RequestBody Map<String, Object> params) {

		return authSvc.genPublicToken(params);
	}

	@PostMapping("/public/refreshToken")
	public RestResult<?> refreshToken(String refreshToken) {

		return authSvc.refreshPublicToken(refreshToken);
	}

	@GetMapping("/testmapping/test")
	public RestResult<?> test(HttpServletRequest request, HttpServletResponse response) {

		return authSvc.logout(request, response);
	}

	
	@PostMapping("/public/updatePassword")
	public RestResult<?> updatePassword(@RequestBody Map<String, Object> params){
		return authSvc.updatePassword(params);
	}
	
	// 아이디 찾기
	@PostMapping("/public/findLoginId")
	public RestResult<?> findLoginId(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		return authSvc.findLoginId(params);
	}
	
	@PostMapping("/public/sendCertifyMail")
	public RestResult<?> sendCertifyMail(@RequestBody Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		try {
			userSvc.sendCertifyMail(params);
			rtn.setSuccess(true);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
			log.error("{} Error : {}", this.getClass(), e);
		}
		return rtn;
	}

}