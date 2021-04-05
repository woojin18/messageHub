package kr.co.uplus.cloud.common.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import kr.co.uplus.cloud.common.consts.Const;
import kr.co.uplus.cloud.common.utils.WebUtils;

/**
 * Spring Security를 사용하면 폼 로그인 성공/실패 시 302 Redirect가 발생한다. Rest API 호출 시는 인증 실패
 * 시 401 응답만 주면된다. 폼 로그인과 API 로그인을 같이 사용하기 위해 위의 2가지 역할을 수행한다.
 */
public class MixedAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
	private String[] apiUrls;

	public MixedAuthenticationEntryPoint(String loginPage, String... apiUrls) {
		super(loginPage);
		this.apiUrls = apiUrls;
	}

	/**
	 * 인증에 실패하거나 권한 없이 접근하여 login 페이지로 redrect 해야 할 때 호출된다.
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// AJAX 요청이고 세션 만료 상태면 418 (사용자정의) 응답을 한다.
		if (WebUtils.isAjaxRequest(request) && WebUtils.isExpiredSession(request)) {
			response.sendError(Const.SESSION_EXPIRED, "SESSION_TIMED_OUT");
		}
		// RESTful API 요청이면 401 응답을 한다.
		else if (WebUtils.isMatchedUriPattern(request, apiUrls)) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		// 일반 페이지 요청이면 302 응답을 한다.
		else {
			super.commence(request, response, authException);
		}
	}

}
