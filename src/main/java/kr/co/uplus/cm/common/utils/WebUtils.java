package kr.co.uplus.cm.common.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.AntPathMatcher;

import kr.co.uplus.cm.common.consts.Const;

public class WebUtils {

	public static boolean isAjaxRequest(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}

	public static boolean isJwtTokenExist(HttpServletRequest request) {
		Boolean api = (Boolean) request.getAttribute(Const.KEY_TOKEN_EXIST);
		return api != null && api == Boolean.TRUE;
	}

	public static boolean isExpiredSession(HttpServletRequest request) {
		return request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid();
	}

	public static void responseJson(HttpServletResponse response, int status) {
		response.setStatus(status);
		response.setContentType("application/json;charset=UTF-8");
		try {
			response.getWriter().write("");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void printRequestHeader(HttpServletRequest request) {
		Enumeration<String> names = request.getHeaderNames();
		System.out.println("-------------- request header --------------");
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			System.out.println(name + " = " + request.getHeader(name));
		}
	}

	public static void printRequestParameters(HttpServletRequest request) {
		Enumeration<String> names = request.getParameterNames();
		System.out.println("-------------- request parameter --------------");
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			System.out.println(name + " = " + request.getParameter(name));
		}
	}

	public static void printSessionAttributes(HttpSession session) {
		Enumeration<String> names = session.getAttributeNames();
		System.out.println("-------------- request attribute --------------");
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			System.out.println(name + " = " + session.getAttribute(name));
		}
	}

	public static boolean isMatchedUri(HttpServletRequest request, String... uris) {
		String path = request.getRequestURI();
		return Arrays.stream(uris).anyMatch(s -> path.equals(s));
	}

	/**
	 * 현재 요청 URL(request.getRequestURI())이 urlPattern으로 지정된 패턴과 일치하면 true 반환.
	 * 
	 * @param uriPatterns ant pattern을 사용한 url. (ex. /common/api/**)
	 */
	public static boolean isMatchedUriPattern(HttpServletRequest request, String... uriPatterns) {
//		String ctxroot = request.getContextPath();
//		if ("/".equals(ctxroot))
//			ctxroot = "";
//		
//		String path = request.getRequestURI().replaceFirst(ctxroot, "");
//		AntPathMatcher antMatcher = new AntPathMatcher();
//		boolean match = false;
//		
//		for (String pattern : uriPatterns) {
//			match = antMatcher.match(pattern, path);
//			if (match)
//				break;
//		}
//		
//		return match;

		String path = request.getRequestURI();
		AntPathMatcher antMatcher = new AntPathMatcher();
		return Arrays.stream(uriPatterns).anyMatch(s -> antMatcher.match(s, path));
	}

	public static boolean isResourceRequest(HttpServletRequest request) {
		String path = request.getRequestURI();
		return path.startsWith("/static/");
	}

}
