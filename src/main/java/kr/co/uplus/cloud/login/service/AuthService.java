package kr.co.uplus.cloud.login.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import kr.co.uplus.cloud.common.config.SecurityConfig;
import kr.co.uplus.cloud.common.consts.Const;
import kr.co.uplus.cloud.common.consts.ResultCode;
import kr.co.uplus.cloud.common.crypto.Sha256PasswordEncoder;
import kr.co.uplus.cloud.common.dao.AuthUserDao;
import kr.co.uplus.cloud.common.dto.RestResult;
import kr.co.uplus.cloud.common.handler.LoginFailureHandler;
import kr.co.uplus.cloud.common.handler.LoginSuccessHandler;
import kr.co.uplus.cloud.common.jwt.JwtService;
import kr.co.uplus.cloud.common.model.AuthUser;
import kr.co.uplus.cloud.common.model.PublicToken;
import kr.co.uplus.cloud.common.utils.SpringUtils;
import kr.co.uplus.cloud.utils.GeneralDao;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AuthService implements UserDetailsService {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JwtService jwtSvc;

	@Autowired
	private LoginSuccessHandler loginSuccessHandler;

	@Autowired
	private LoginFailureHandler loginFailureHandler;

	@Autowired
	private Sha256PasswordEncoder sha256;

	@Autowired
	private AuthUserDao dao;

	@Autowired
	private GeneralDao generalDao;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("username = [{}]", username);

		AuthUser user = dao.getByUsername(username);
		log.debug("user = [{}]", user);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		HttpServletRequest request = SpringUtils.getCurrentRequest();
		request.setAttribute(Const.KEY_LOAD_USER, user);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities = AuthorityUtils.createAuthorityList(user.getRole().getValue());
		user.setAuthorities(authorities);

		return user;
	}

	public RestResult<?> chkLogin(Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		Authentication authentication = null;

		String userId = (String) params.get("userId");
		String userPwd = (String) params.get("userPwd");

		try {
			params.put("userPwd", sha256.encode(userPwd));
			userPwd = (String) params.get("userPwd");
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userId, userPwd);
			authentication = authManager.authenticate(token);

		} catch (AuthenticationException e) {
			request.setAttribute(SecurityConfig.LOGIN_ID_PARAM, userId);
			ResultCode resultCode = loginFailureHandler.process(request, response, e);

			return new RestResult<String>(false).setCode(resultCode);
		}

		ResultCode rcode = loginSuccessHandler.process(request, response, authentication);
		String token = jwtSvc.generatePrivateToken(response, authentication);
		response.setHeader("jwt-auth-token", token);

		String nextUrl = getReturnUrl(request, response);
		log.debug("login success - nextUrl = [{}]", nextUrl);

		if (rcode != ResultCode.SUCCESS)
			return new RestResult<String>(false).setCode(rcode);

		return new RestResult<String>().setData(nextUrl);
	}

	public RestResult<?> genPublicToken(@RequestBody Map<String, Object> params) {
		Authentication authentication = null;

		String userId = (String) params.get("userId");
		String userPwd = (String) params.get("userPwd");

		try {
			params.put("userPwd", sha256.encode(userPwd));
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userId, userPwd);
			authentication = authManager.authenticate(token);
		} catch (AuthenticationException e) {
			return new RestResult<String>(false);
		}

		PublicToken pubToken = jwtSvc.generatePublicToken(authentication);
		return new RestResult<PublicToken>().setData(pubToken);
	}

	public RestResult<?> refreshPublicToken(String refreshToken) {
		try {
			PublicToken pubToken = new PublicToken();
			pubToken.setAccessToken(jwtSvc.accessToken(refreshToken));

			return new RestResult<PublicToken>().setData(pubToken);
		} catch (Exception e) {
			return new RestResult<PublicToken>(false);
		}
	}

	public RestResult<?> logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}

		jwtSvc.destroyPrivateToken(request, response);
		return new RestResult<String>();
	}

	/**
	 * 로그인 전에 요청했던 URL 반환
	 */
	private String getReturnUrl(HttpServletRequest request, HttpServletResponse response) {
		RequestCache requestCache = new HttpSessionRequestCache();

		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest == null) {
			AuthUser user = (AuthUser) request.getAttribute(Const.KEY_LOAD_USER);
			return SecurityConfig.LOGIN_SUCC_URL;
		}
		return savedRequest.getRedirectUrl();
	}

	// 메뉴 조회
	@SuppressWarnings({ "unchecked" })
	public RestResult<?> getMenuForRole(Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		params.put("user_id", params.get("user_id"));
		params.put("role_cd", params.get("role_cd"));
		params.put("menus_level", "0");
		List<Object> rtnList = generalDao.selectGernalList("login.getMenuForRole", params);

		for (Object rtnMap : rtnList) {
			params.put("menus_level", "1");
			params.put("top_menus_cd", ((Map<String, Object>) rtnMap).get("MENUS_CD"));
			List<Object> childList = generalDao.selectGernalList("login.getMenuForRole", params);
			for (Object childMap : childList) {
				params.put("menus_level", "2");
				params.put("top_menus_cd", ((Map<String, Object>) childMap).get("MENUS_CD"));
				List<Object> child2List = generalDao.selectGernalList("login.getMenuForRole", params);
				((Map<String, Object>) childMap).put("children", child2List);
			}
			((Map<String, Object>) rtnMap).put("children", childList);
		}

		rtn.setData(rtnList);

		return rtn;
	}
}
