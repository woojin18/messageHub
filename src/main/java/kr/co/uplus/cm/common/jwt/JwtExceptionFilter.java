package kr.co.uplus.cm.common.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import kr.co.uplus.cm.common.config.SecurityConfig;
import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.dto.Menu;
import kr.co.uplus.cm.common.utils.WebUtils;
import kr.co.uplus.cm.login.service.AuthService;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JwtExceptionFilter extends OncePerRequestFilter {
	
	private AuthService authService;
	
	public JwtExceptionFilter(AuthService authService) {
		this.authService = authService;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String connUrl = request.getRequestURL().toString();
		String svcTypeCd = request.getHeader("svcType");
		String roleCd = request.getHeader("roleCd");
		String menusCd = request.getHeader("menuCd");
		String activityCd = request.getHeader("activity");
		String userId = request.getHeader("uUserId");
		String boUserId = request.getHeader("boUserId");
//		log.info("권한 체크 : connUrl:" + connUrl + ",svcTypeCd:" + svcTypeCd + ",roleCd:"+roleCd + ",menusCd:" + menusCd + ",activityCd:" + activityCd);
		if (!StringUtils.isEmpty(svcTypeCd) && !StringUtils.isEmpty(roleCd) && !StringUtils.isEmpty(menusCd) && !StringUtils.isEmpty(activityCd)) {
			Map<String, Menu> menus = authService.getMenusByRole(svcTypeCd, roleCd);
			if (menus == null) {
				WebUtils.responseJson(response, Const.NO_ROLE);
				return;
			}
			Menu menu = menus.get(menusCd);
			if (menu == null) {
				WebUtils.responseJson(response, Const.NO_ROLE);
				return;
			}
			if ("READ".equals(activityCd) && menu.isRead() == false) {
				WebUtils.responseJson(response, Const.NO_ROLE);
				return;
			} else if ("SAVE".equals(activityCd) && menu.isSave() == false) {
				WebUtils.responseJson(response, Const.NO_ROLE);
				return;
			}
			log.info("로그 저장 : connUrl:" + connUrl + ",svcTypeCd:" + svcTypeCd + ",roleCd:"+roleCd + ",menusCd:" + menusCd + ",activityCd:" + activityCd + ",uUserId:" + userId+ ",boUserId:" + boUserId);
			Map params = new HashMap();
			params.put("userId", userId);
			params.put("boUserId", boUserId);
			params.put("connUrl", connUrl);
			params.put("menusCd", menusCd);
			params.put("activityCd", activityCd);
			authService.insertConsoleProcLog(params);
		}
		try {
			filterChain.doFilter(request, response);
		} catch (JwtException e) {
			if (WebUtils.isJwtTokenExist(request)
					&& WebUtils.isMatchedUriPattern(request, SecurityConfig.REST_API_URLS)) {
				if (e instanceof ExpiredJwtException)
					WebUtils.responseJson(response, Const.SESSION_EXPIRED);
				else
					WebUtils.responseJson(response, HttpServletResponse.SC_UNAUTHORIZED);
			} else {
				throw e;
			}
		} catch (Exception e) {
			throw e;
		}
	}

}
