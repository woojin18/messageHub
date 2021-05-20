package kr.co.uplus.cm.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import kr.co.uplus.cm.common.model.AuthUser;
import kr.co.uplus.cm.login.service.AuthService;

/**
 * 2021-05-14 삭제 공지
 * 2021-05-20 삭제 공지후에도 사용 하는 곳 있음.
 * => 리턴값을 null로 변경
 *
 */
@Deprecated
public class BaseController {

	@Autowired
	AuthService authSvc;

	/**
	 * 사용자 정보 Get
	 *
	 * @param request
	 * @return
	 */
	@Deprecated
	protected AuthUser getUserInfo() {
		return null;
	}

	/**
	 * 사용자 정보 Get
	 *
	 * @param request
	 * @return
	 */
	@Deprecated
	protected AuthUser getUserInfo(String loginId) {
	    return null;
	}

	/**
	 * 사용자 정보 Set
	 *
	 * @param params
	 * @param isContainIgnore
	 * @return
	 */
	@Deprecated
	protected Map<String, Object> setUserInfo(Map<String, Object> params, boolean isContainIgnore) {
	    return null;
	}

	/**
	 * 사용자 정보 Set
	 *
	 * @param params
	 * @return
	 */
	@Deprecated
	protected Map<String, Object> setContainIgnoreUserInfo(Map<String, Object> params) {
	    return null;
	}

	/**
	 * 현재 선택한 프로젝트 ID get
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	protected String getProjectId(HttpServletRequest request) {
	    return null;
	}

}
