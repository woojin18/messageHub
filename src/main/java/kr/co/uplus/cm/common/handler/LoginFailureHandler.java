package kr.co.uplus.cm.common.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import kr.co.uplus.cm.common.config.SecurityConfig;
import kr.co.uplus.cm.common.consts.ResultCode;

public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	public LoginFailureHandler(String loginFailUrl) {
		super(loginFailUrl);
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		process(request, response, exception);
		super.onAuthenticationFailure(request, response, exception);
	}

	public ResultCode process(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) {
		String userId = request.getParameter(SecurityConfig.LOGIN_ID_PARAM);
		if (userId == null)
			userId = (String) request.getAttribute(SecurityConfig.LOGIN_ID_PARAM);

		ResultCode resultCode = ResultCode.SE_UNKNOWN;
		if (exception instanceof BadCredentialsException) {
			resultCode = ResultCode.CE_ID_PWD;
		} else if (exception instanceof DisabledException) {
			resultCode = ResultCode.SS_NOT_USE;
		} else if (exception instanceof LockedException) {
			resultCode = ResultCode.CE_TO_LOCK;
		} else if (exception instanceof InternalAuthenticationServiceException) {
			resultCode = ResultCode.SS_NOT_AUTH;
		}

		return resultCode;
	}

}
