package kr.co.uplus.cloud.sample.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.uplus.cloud.sample.dto.RestResult;
import kr.co.uplus.cloud.sample.model.AuthUser;
import kr.co.uplus.cloud.sample.service.SampleService;

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
@RequestMapping("/sample")
public class SampleController {

	@Autowired
	private SampleService authSvc;

	@PostMapping("/register")
	public RestResult<?> register(
				@RequestBody AuthUser user,
				HttpServletRequest request,
				HttpServletResponse response) {

		return authSvc.register(user, request, response);
    }
	
	@PostMapping("/aesEncrypt")
	public RestResult<?> aesEncrypt(
			@RequestBody AuthUser user) {

		return authSvc.aesEncrypt(user);
	}
	
	@GetMapping("/mongodbData")
	public RestResult<?> sampleData() {

		return authSvc.getMongodbData();
	}
	
}
