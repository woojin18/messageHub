package kr.co.uplus.cloud.login.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cloud.common.dto.RestResult;
import kr.co.uplus.cloud.login.service.AuthService;

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
@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	private AuthService authSvc;

	@PostMapping("/auth/login")
	public RestResult<?> login(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {

		System.out.println("Start CheckLogin!!!");

		return authSvc.chkLogin(params, request, response);
	}

	@GetMapping("/auth/logout")
	public RestResult<?> logoutTest(HttpServletRequest request, HttpServletResponse response) {

		System.out.println("Logout!!!");

		return authSvc.logout(request, response);
	}

	@PostMapping("/auth/getMenuForRole")
	public RestResult<?> getMenuForRole(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return authSvc.getMenuForRole(params, request, response);
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

}