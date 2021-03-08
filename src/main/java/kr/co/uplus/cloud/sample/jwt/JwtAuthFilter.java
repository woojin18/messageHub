package kr.co.uplus.cloud.sample.jwt;

import static kr.co.uplus.cloud.sample.config.SecurityConfig.LOGIN_API_URL;
import static kr.co.uplus.cloud.sample.config.SecurityConfig.PUBLIC_API_URL;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import kr.co.uplus.cloud.sample.consts.Const;
import kr.co.uplus.cloud.sample.utils.WebUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.TextCodec;

public abstract class JwtAuthFilter extends  OncePerRequestFilter {
    
	protected final JwtProperties jwtProps;
	
	
	public JwtAuthFilter(JwtProperties jwtProps) {
		this.jwtProps = jwtProps;
	}
	
	public abstract String getToken(HttpServletRequest request);
	public abstract void onValidateSuccess(HttpServletRequest request, HttpServletResponse response, Claims claims);
	public abstract void onValidateException(HttpServletRequest request, HttpServletResponse response, JwtException exception);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		if (WebUtils.isResourceRequest(request) || WebUtils.isMatchedUriPattern(request, PUBLIC_API_URL, LOGIN_API_URL)) {
			chain.doFilter(request, response);
			return;
		}
		
		// 쿠키에서 토큰 추출 (client - server token)
		String token = getToken(request);
		
		if(token == null) {
			chain.doFilter(request, response); // go to the next filter in the filter chain
			return;
		}

		Claims claims = null;
		request.setAttribute(Const.KEY_TOKEN_EXIST, true);
		
		try {
			// 4. 토큰 검증
			claims = Jwts.parser()
					.setSigningKey(TextCodec.BASE64.decode(jwtProps.getKeyString()))
					.parseClaimsJws(token)
					.getBody();
			
			String subject = claims.getSubject();
			if(subject != null) {
				onValidateSuccess(request, response, claims);
				
				// 5. 스프링 용 UsernamePasswordAuthenticationToken 객체 생성
				@SuppressWarnings("unchecked")
				Map<String,Object> principalMap = (Map<String,Object>) claims.get("principal");
				JwtUser user = JwtUser.createAuthUser(principalMap);
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				
				// 6. 사용자 인증 처리 (Now, user is authenticated)
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		catch(JwtException e) {
			onValidateException(request, response, e);
			throw e;
		}
		
		chain.doFilter(request, response);
	}

}