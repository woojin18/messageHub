package kr.co.uplus.cloud.sample.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cloud.sample.dto.RestResult;
import kr.co.uplus.cloud.sample.model.AuthUser;
import kr.co.uplus.cloud.sample.service.AuthService;

/**
 * <pre>
 * AuthSampleController
 *
 * </pre>
 *
 * @author  : savrani (Son Wang Gu)
 * @Date    : 2020. 12. 18.
 * @Version : 1.0
 * Copyright 2020 LG Uplus Corp. All Rights Reserved.
 */
@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	private AuthService authSvc;

	@PostMapping("/auth/login")
	public RestResult<?> login(
				@RequestBody AuthUser user,
				HttpServletRequest request,
				HttpServletResponse response) {
		
		System.out.println("asd4f5asd45f4asd65f4asd6");

		return authSvc.chkLogin(user, request, response);
    }
	
	@GetMapping("/auth/logoutTest")
	public RestResult<?> logoutTest(
				HttpServletRequest request,
				HttpServletResponse response) {
		
		System.out.println("asdfasdfasdfasdfsdf");

		return authSvc.logout(request, response);
	}
	
	@PostMapping("/public/token")
	public RestResult<?> publicToken(
				@RequestBody AuthUser user) {

		return authSvc.genPublicToken(user);
	}
	
	@PostMapping("/public/refreshToken")
	public RestResult<?> refreshToken(
				String refreshToken) {

		return authSvc.refreshPublicToken(refreshToken);
	}
	
	@GetMapping("/testmapping/test")
	public RestResult<?> test(
			HttpServletRequest request,
			HttpServletResponse response) {
		
		System.out.println("123123123");
		
		return authSvc.logout(request, response);
	}
	
//	@GetMapping("/auth/logout2")
//	@ResponseBody
//	public String logout2(
//				HttpServletRequest request,
//				HttpServletResponse response) {
//		
//		System.out.println("+++++" + request.toString());
//		
//		System.out.println("asdfasdfasdfasdfsdf");
//
//		return "TEST";
//	}
}
