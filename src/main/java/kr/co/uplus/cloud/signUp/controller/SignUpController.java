package kr.co.uplus.cloud.signUp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/public/signUp")
public class SignUpController {

	// 회원
	@RequestMapping("test")
	public RedirectView test (RedirectAttributes redirect, HttpServletResponse response, HttpServletRequest req) {
		req.setAttribute("test", "test");
		return new RedirectView("http://localhost:3000/signUpMain");
	}
}
 