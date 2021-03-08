package kr.co.uplus.cloud.sample.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import kr.co.uplus.cloud.sample.config.SecurityConfig;
import kr.co.uplus.cloud.sample.consts.Const;
import kr.co.uplus.cloud.sample.utils.WebUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

public class JwtExceptionFilter extends OncePerRequestFilter {

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		}
		catch (JwtException e) {
			if (WebUtils.isJwtTokenExist(request) && WebUtils.isMatchedUriPattern(request, SecurityConfig.REST_API_URLS)) {
				if (e instanceof ExpiredJwtException)
					WebUtils.responseJson(response, Const.SESSION_EXPIRED);
				else
					WebUtils.responseJson(response, HttpServletResponse.SC_UNAUTHORIZED);
			}
			else {
				throw e;
			}
		}
		catch (Exception e) {
			throw e;
		}
	}

}
