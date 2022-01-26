package kr.co.uplus.cm.login.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import kr.co.uplus.cm.common.config.SecurityConfig;
import kr.co.uplus.cm.common.consts.Const;
import kr.co.uplus.cm.common.consts.DB;
import kr.co.uplus.cm.common.consts.ResultCode;
import kr.co.uplus.cm.common.crypto.Sha512PasswordEncoder;
import kr.co.uplus.cm.common.dao.AuthUserDao;
import kr.co.uplus.cm.common.dto.Menu;
import kr.co.uplus.cm.common.dto.RestResult;
import kr.co.uplus.cm.common.handler.LoginFailureHandler;
import kr.co.uplus.cm.common.handler.LoginSuccessHandler;
import kr.co.uplus.cm.common.jwt.JwtService;
import kr.co.uplus.cm.common.model.AuthUser;
import kr.co.uplus.cm.common.model.PublicToken;
import kr.co.uplus.cm.common.service.CommonService;
import kr.co.uplus.cm.common.utils.SpringUtils;
import kr.co.uplus.cm.utils.CommonUtils;
import kr.co.uplus.cm.utils.GeneralDao;
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
	private Sha512PasswordEncoder sha512;

	@Autowired
	private AuthUserDao dao;

	@Autowired
	private GeneralDao generalDao;

	@Autowired
	private CommonService commonService;

	@Value("${bo.domain.baseUrl}") String boBaseUrl;

	@Value("${sso.secret.key}") String authKey;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("username = [{}]", username);
		AuthUser user = dao.getByUsername(username);
		log.debug("user = [{}]", user);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		HttpServletRequest request = SpringUtils.getCurrentRequest();
		user.setBoUserId((String) request.getAttribute("boUserId"));
		request.setAttribute(Const.KEY_LOAD_USER, user);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities = AuthorityUtils.createAuthorityList(user.getRole());
		user.setAuthorities(authorities);

		return user;
	}

	public RestResult<?> chkLogin(Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		Authentication authentication = null;

		String userId = (String) params.get("userId");
		String userPwd = (String) params.get("userPwd");
		String salt = "";

		try {
			// userId에 해당되는 salt 문자열 취득
			salt = CommonUtils.getString(generalDao.selectGernalObject(DB.QRY_SELECT_SALT_INFO, params));
		} catch (Exception e) {
			return new RestResult<String>(false).setCode(ResultCode.SE_INTERNAL);
		}

		try {
			params.put("userPwd", sha512.encode(salt + userPwd));
			userPwd = (String) params.get("userPwd");
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userId, userPwd);
			authentication = authManager.authenticate(token);
		} catch (AuthenticationException ae) {
			request.setAttribute(SecurityConfig.LOGIN_ID_PARAM, userId);
			ResultCode resultCode = loginFailureHandler.process(request, response, ae);

			if (ResultCode.CE_ID_PWD.equals(resultCode)) {
				try {
					// 로그인 실패 카운트 update
					generalDao.updateGernal(DB.QRY_UPDATE_LOGIN_FAIL_CNT, params);
				} catch (Exception e) {
					return new RestResult<String>(false).setCode(ResultCode.SE_INTERNAL);
				}
			}
			return new RestResult<String>(false).setCode(resultCode);
		}

		AuthUser user = (AuthUser) authentication.getPrincipal();
		String delYn = user.getDelYn();
		String corpStatus = user.getCorpStatus();
		int diffDate = user.getDiffDate();
		if ("UC".equals(user.getSvcTypeCd())) {
			if ("".equals(user.getRepProjectId()) || user.getRepProjectId() == null) {
				return new RestResult<String>(false).setCode(ResultCode.SS_NOT_PROJECT);
			}
		}
		if ("Y".equals(delYn)) {
			return new RestResult<String>(false).setCode(ResultCode.SS_DEL_USE);
		}
		if (!"USE".equals(corpStatus)) {
			if ("STOP".equals(corpStatus)) {
				return new RestResult<String>(false).setCode(ResultCode.SS_NOT_USE_CORP);
			}
			if ("DEL".equals(corpStatus)) {
				return new RestResult<String>(false).setCode(ResultCode.SS_NOT_EXIST_CORP);
			}
		}

		ResultCode rcode = loginSuccessHandler.process(request, response, authentication);
		jwtSvc.generatePrivateToken(response, authentication);

		String nextUrl = getReturnUrl(request, response, diffDate);
		log.debug("login success - nextUrl = [{}]", nextUrl);

		if (rcode != ResultCode.SUCCESS) {
			return new RestResult<String>(false).setCode(rcode);
		} else {
			try {
				// 최근 접속일시 update
				generalDao.updateGernal(DB.QRY_UPDATE_RECENT_CONN_DT, params);
			} catch (Exception e) {
				return new RestResult<String>(false).setCode(ResultCode.SE_INTERNAL);
			}
		}

		return new RestResult<String>().setData(nextUrl);
	}

	public RestResult<?> sso(Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) {
		Authentication authentication = null;

		String referrer = (String) params.get("referrer");
		// referrer 체크로 보안 강화 
		if (referrer == null || referrer.startsWith(boBaseUrl) == false) {
			return new RestResult<String>(false);
		}
		
		String boUserId = (String) params.get("userId");
		request.setAttribute("boUserId", boUserId);
		String[] auth = ((String) params.get("token")).split("\\|");
		String authKey2 = auth[0]; 
		String userId = auth[1];
		String salt = "";
		String userPwd = "";
		
		// authKey 체크로 보안 강화 
		if (authKey.equals(authKey2) == false) {
			return new RestResult<String>(false);
		}
		
		params.put("userId", userId);

		try {
			// userId에 해당되는 salt 문자열 취득
			salt = CommonUtils.getString(generalDao.selectGernalObject(DB.QRY_SELECT_SALT_INFO, params));
			userPwd = CommonUtils.getString(generalDao.selectGernalObject("login.qrySelectPwd", params));
		} catch (Exception e) {
			return new RestResult<String>(false).setCode(ResultCode.SE_INTERNAL);
		}

		try {
			params.put("userPwd", userPwd);
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userId, userPwd);
			authentication = authManager.authenticate(token);
		} catch (AuthenticationException ae) {
			request.setAttribute(SecurityConfig.LOGIN_ID_PARAM, userId);
			ResultCode resultCode = loginFailureHandler.process(request, response, ae);

//			if (ResultCode.CE_ID_PWD.equals(resultCode)) {
//				try {
//					// 로그인 실패 카운트 update
//					generalDao.updateGernal(DB.QRY_UPDATE_LOGIN_FAIL_CNT, params);
//				} catch (Exception e) {
//					return new RestResult<String>(false).setCode(ResultCode.SE_INTERNAL);
//				}
//			}
			return new RestResult<String>(false).setCode(resultCode);
		}

		AuthUser user = (AuthUser) authentication.getPrincipal();
		String delYn = user.getDelYn();
		String corpStatus = user.getCorpStatus();
		int diffDate = user.getDiffDate();
		if ("UC".equals(user.getSvcTypeCd())) {
			if ("".equals(user.getRepProjectId()) || user.getRepProjectId() == null) {
				return new RestResult<String>(false).setCode(ResultCode.SS_NOT_PROJECT);
			}
		}
		if ("Y".equals(delYn)) {
			return new RestResult<String>(false).setCode(ResultCode.SS_DEL_USE);
		}
		if (!"USE".equals(corpStatus)) {
			if ("STOP".equals(corpStatus)) {
				return new RestResult<String>(false).setCode(ResultCode.SS_NOT_USE_CORP);
			}
			if ("DEL".equals(corpStatus)) {
				return new RestResult<String>(false).setCode(ResultCode.SS_NOT_EXIST_CORP);
			}
		}

		ResultCode rcode = loginSuccessHandler.process(request, response, authentication);
		jwtSvc.generatePrivateToken(response, authentication);

		String nextUrl = getReturnUrl(request, response, diffDate);
		log.debug("login success - nextUrl = [{}]", nextUrl);

		// BO 사용자라사 최근 접속일시 업데이트 하지 않음. 
//		if (rcode != ResultCode.SUCCESS) {
//			return new RestResult<String>(false).setCode(rcode);
//		} else {
//			try {
//				// 최근 접속일시 update
//				generalDao.updateGernal(DB.QRY_UPDATE_RECENT_CONN_DT, params);
//			} catch (Exception e) {
//				return new RestResult<String>(false).setCode(ResultCode.SE_INTERNAL);
//			}
//		}

		return new RestResult<String>().setData(nextUrl);
	}

	public RestResult<?> genPublicToken(@RequestBody Map<String, Object> params) {
		Authentication authentication = null;

		String userId = (String) params.get("userId");
		String userPwd = (String) params.get("userPwd");
		String salt = "";

		try {
			// userId에 해당되는 salt 문자열 취득
			salt = CommonUtils.getString(generalDao.selectGernalObject(DB.QRY_SELECT_SALT_INFO, params));
		} catch (Exception e) {
			return new RestResult<String>(false).setCode(ResultCode.SE_INTERNAL);
		}

		try {
			params.put("userPwd", sha512.encode(salt + userPwd));
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
	private String getReturnUrl(HttpServletRequest request, HttpServletResponse response, int diffDate) {
		RequestCache requestCache = new HttpSessionRequestCache();
		String resultUrl = "";

		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest == null) {
			AuthUser user = (AuthUser) request.getAttribute(Const.KEY_LOAD_USER);

			if (SecurityConfig.AC_SVC_TP_CD.equals(user.getSvcTypeCd())) {
				resultUrl = SecurityConfig.LOGIN_SUCC_AC_URL;
			} else if (SecurityConfig.UC_SVC_TP_CD.equals(user.getSvcTypeCd())) {
				resultUrl = SecurityConfig.LOGIN_SUCC_UC_URL;
			}
			if (diffDate > 90) {
				resultUrl = SecurityConfig.LOGIN_RESET_PWD_URL;
			}
			return resultUrl;
		}
		return savedRequest.getRedirectUrl();
	}

	// 메뉴 조회
	@SuppressWarnings({ "unchecked" })
	public RestResult<?> getMenuForRole(Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		if ("AC".equals(params.get("svc_type_cd"))) {
			rtn.setData(tACMenuByRole.get(params.get("role_cd")));
		} else {
			rtn.setData(tUCMenuByRole.get(params.get("role_cd")));
		}

		return rtn;
	}

	// 참여되어 있는 프로젝트 정보 조회
	public RestResult<?> getProjectForUser(Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		List<Object> rtnList = generalDao.selectGernalList("login.getProjectForUser", params);
		rtn.setData(rtnList);

		return rtn;
	}

	// 프로젝트 사용 채널 조회
	public RestResult<?> getProjectChUseListForUser(Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();
		List<Object> rtnList = generalDao.selectGernalList("login.getProjectChUseListForUser", params);
		rtn.setData(rtnList);

		return rtn;
	}

	private Map<String, Menu> tACMenuByRole = new HashMap<String, Menu>();
	private Map<String, Map<String, Menu>> tACMenusByRole = new HashMap<String, Map<String, Menu>>();
	private Map<String, Menu> tUCMenuByRole = new HashMap<String, Menu>();
	private Map<String, Map<String, Menu>> tUCMenusByRole = new HashMap<String, Map<String, Menu>>();

	@PostConstruct
	public void init() {
		initMenu("AC");
		initMenu("UC");
	}

	private void initMenu(String svcTypeCd) {
		Map<String, Menu> menuByRole = new HashMap<String, Menu>();
		Map<String, Map<String, Menu>> menusByRole = new HashMap<String, Map<String, Menu>>();
		Map<String, Menu> menus = new HashMap<String, Menu>();
		Map params = new HashMap();
		params.put("svc_type_cd", svcTypeCd);
//		List<Menu> list = dao.getMenuForRole(params);
		
		List<Menu> list = new ArrayList<Menu>();
		try {
			list = dao.getMenuForRole(params);
		} catch(Exception e) {
			log.error("init Error : "+e);
		}

		for (Menu data : list) {
			Menu rootMenu = menuByRole.get(data.getRoleCd());
			if (rootMenu == null) {
				rootMenu = new Menu();
				menuByRole.put(data.getRoleCd(), rootMenu);
				menus = new HashMap<String, Menu>();
				menusByRole.put(data.getRoleCd(), menus);
			}

			Menu menu = menus.get(data.getMenusCd());
			if (menu == null) {
				menu = data;
				menus.put(data.getMenusCd(), menu);

				Menu parMenu = menus.get(menu.getParMenusCd());
				if (parMenu == null && "1".equals(menu.getMenusLevel())) {
					parMenu = rootMenu;
				} else if (parMenu == null) {
					continue;
				}
				parMenu.addChild(menu);
			}
			if ("READ".equals(data.getActivityCd())) {
				menu.setRead(true);
			}
			if ("SAVE".equals(data.getActivityCd())) {
				menu.setSave(true);
			}

		}

		if ("AC".equals(svcTypeCd)) {
			tACMenuByRole = menuByRole;
			tACMenusByRole = menusByRole;
		} else {
			tUCMenuByRole = menuByRole;
			tUCMenusByRole = menusByRole;
		}
	}

	public Map<String, Menu> getMenusByRole(String svcTypeCd, String roleCd) {
		if ("AC".equals(svcTypeCd)) {
			return tACMenusByRole.get(roleCd);
		} else {
			return tUCMenusByRole.get(roleCd);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public RestResult<Object> updatePassword(Map<String, Object> params) {
		RestResult<Object> rtn = new RestResult<Object>();
		String password = CommonUtils.getString(params.get("password"));

		// 비밀번호 유효성 검사
		try {
			commonService.pwdResularExpressionChk(password);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
			return rtn;
		}
		
		// 사용자 비밀번호 암호화 및 기존 비밀번호 check
		String encPwd = "";
		try {
			encPwd = commonService.encryptionUserPwd(params);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage(e.getMessage());
			return rtn;
		}
		params.put("pwd", encPwd);
		// 비밀번호 update
		try {
			generalDao.updateGernal(DB.QRY_UPDATE_USER_PASSWORD, params);
			rtn.setSuccess(true);
		} catch (Exception e) {
			rtn.setSuccess(false);
			rtn.setMessage("비밀번호 설정에 실패하였습니다.");
		}
		return rtn;
	}

	public RestResult<Object> findLoginId(Map<String, Object> params) throws Exception {
		RestResult<Object> rtn = new RestResult<Object>();

		List<Object> findedLoginIdList = generalDao.selectGernalList("login.findLoginId", params);
		String findedLoginId = "";
		if (findedLoginIdList.size() > 0) {
			for (int i = 0; i < findedLoginIdList.size(); i++) {
				Map<String, Object> findedLoginIdMap = (Map<String, Object>) findedLoginIdList.get(i);
				findedLoginId += CommonUtils.setMaskingLoginId(CommonUtils.getString(findedLoginIdMap.get("loginId")));
				if ((i + 1) != findedLoginIdList.size()) {
					findedLoginId += ", ";
				}
			}
		}

		if ("".equals(findedLoginId)) {
			rtn.setSuccess(false);
			rtn.setMessage("검색된 아이디가 없습니다.");
		} else {
			rtn.setSuccess(true);
			rtn.setData(findedLoginId);
		}

		return rtn;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
	public void insertConsoleProcLog(Map params) {
		try {
			generalDao.insertGernal("login.insertConsoleProcLog", params);
		} catch (Exception e) {
		}
	}
}