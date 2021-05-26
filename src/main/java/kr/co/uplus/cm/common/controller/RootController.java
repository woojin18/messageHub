package kr.co.uplus.cm.common.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class RootController implements ErrorController {

	@GetMapping("/error")
	public String redirectRoot() {
		return "index.html";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

}
