package kr.co.uplus.cloud.common.jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

public class JwtAuthHeaderFilter extends JwtAuthFilter {

	public JwtAuthHeaderFilter(JwtProperties jwtProps) {
		super(jwtProps);
	}

	@Override
	public String getToken(HttpServletRequest request) {
		// 1. access token이 저장된 헤더 읽기
		String header = request.getHeader(jwtProps.getHeader());

		// 2. 헤더 값 검사
		if (header == null || !header.startsWith(jwtProps.getPrefix())) {
			return null;
		}

		// 3. Authorization 헤더에서 토큰 추출
		return header.replace(jwtProps.getPrefix(), "");
	}

	@Override
	public void onValidateSuccess(HttpServletRequest request, HttpServletResponse response, Claims claims) {
	}

	@Override
	public void onValidateException(HttpServletRequest request, HttpServletResponse response, JwtException exception) {
	}

}