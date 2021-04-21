package kr.co.uplus.cm.common.jwt;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

public class JwtAuthCookieFilter extends JwtAuthFilter {
	@Autowired
	private JwtService jwtSvc;

	public JwtAuthCookieFilter(JwtProperties jwtProps) {
		super(jwtProps);
	}

	@Override
	public String getToken(HttpServletRequest request) {
		String payload = null, signature = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				// 1. 쿠키에서 jwt 토큰 header.paload 부분 읽기
				if (jwtProps.getPart1().equals(cookie.getName())) {
					payload = cookie.getValue();
				}
				// 2. 쿠키에서 jwt 토큰 signature 부분 읽기
				else if (jwtProps.getPart2().equals(cookie.getName())) {
					signature = cookie.getValue();
				}
			}
		}
		if (cookies == null || payload == null || signature == null) {
			return null;
		}
		String token = payload + "." + signature;
		return token;
	}

	@Override
	public void onValidateSuccess(HttpServletRequest request, HttpServletResponse response, Claims claims) {
		// 토큰 업데이트 - Sliding Sessions
		jwtSvc.updatePrivateToken(response, claims);
	}

	@Override
	public void onValidateException(HttpServletRequest request, HttpServletResponse response, JwtException exception) {
		if (exception instanceof ExpiredJwtException) {
			jwtSvc.destroyPrivateToken(request, response);
		}
	}

}