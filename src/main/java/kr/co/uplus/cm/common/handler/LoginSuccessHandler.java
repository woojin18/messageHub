package kr.co.uplus.cm.common.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import kr.co.uplus.cm.common.consts.ResultCode;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		process(request, response, authentication);
		super.onAuthenticationSuccess(request, response, authentication);
	}

	public ResultCode process(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		ResultCode resultCode = ResultCode.SUCCESS;

		super.clearAuthenticationAttributes(request);
		return resultCode;
	}

}
