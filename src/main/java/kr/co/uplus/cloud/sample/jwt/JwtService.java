package kr.co.uplus.cloud.sample.jwt;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import kr.co.uplus.cloud.sample.model.AuthUser;
import kr.co.uplus.cloud.sample.model.PublicToken;

@Service
public class JwtService {

	@Autowired
	private JwtProperties jwtProps;
	
	public void generatePrivateToken(HttpServletResponse response, Authentication auth) {
		Claims claims = coreClaims(auth, jwtProps.getPrivateTokenExpiration());
		
		// 필요하면 다른 정보 추가
		JwtData data = new JwtData();
		data.setInfo("추가 claim 정보");
		claims.put("data", data);

		String token = generateToken(claims);
		// 쿠키에 토큰 추가 - 보안 강화
		setTokenToCookie(response, token);
	}
	
	private Claims coreClaims(Authentication auth, int expire) {
		String subject = auth.getName();
		DateTime now = DateTime.now();
		Date expiration = now.plusSeconds(expire).toDate();

		Claims claims = Jwts.claims()
				.setSubject(subject)
				.setIssuedAt(now.toDate())
				.setExpiration(expiration);

		AuthUser user = (AuthUser) auth.getPrincipal();
		JwtUser jwtUser = JwtUser.createJwtUser(user);
		claims.put("principal", jwtUser);
		
		return claims;
	}
	
	private String generateToken(Claims claims) {
		String token = Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.decode(jwtProps.getKeyString()))
				.compact();
		return token;
	}

	private void setTokenToCookie(HttpServletResponse response, String token) {
		int idx = token.lastIndexOf(".");
		String payload = token.substring(0, idx);
		String signature = token.substring(idx+1);
		
		// header.paload 부분만 일반 쿠키에 저장 - JS로 읽기 가능
		Cookie part1 = new Cookie(jwtProps.getPart1(), payload);
		part1.setPath("/");
		response.addCookie(part1);
		
		// signature 부분만 httpOnly 쿠키에 저장 - JS로 읽기 불가능
		Cookie part2 = new Cookie(jwtProps.getPart2(), signature);
		part2.setHttpOnly(true);
		part2.setPath("/");
		response.addCookie(part2);
	}
	
	public void destroyPrivateToken(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookie.setValue("");
		        cookie.setPath("/");
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
	}

	public void updatePrivateToken(HttpServletResponse response, Claims claims) {
		DateTime now = DateTime.now();
		Date expiration = now.plusSeconds(jwtProps.getPrivateTokenExpiration()).toDate();
		claims.setIssuedAt(now.toDate()).setExpiration(expiration);
		
		String token = generateToken(claims);
		setTokenToCookie(response, token);
	}
	
	@SuppressWarnings("unused")
	private String generateToken(String subject, int expire, Map<String,Object> map) {
		DateTime now = DateTime.now();
		Date expiration = now.plusSeconds(expire).toDate();
		
		Claims claims = Jwts.claims()
				.setSubject(subject)
				.setIssuedAt(now.toDate())
				.setExpiration(expiration);
		claims.putAll(map);
		
		return generateToken(claims);
	}

	public PublicToken generatePublicToken(Authentication auth) {
		String access = accessToken(auth);
		String refresh = refreshToken(auth);
		return new PublicToken(access, refresh);
	}
	
	public String accessToken(Authentication auth) {
		Claims claims = coreClaims(auth, jwtProps.getAccessTokenExpiration());
		
		// server to server API에 필요한 claims 설정
		JwtData data = new JwtData();
		data.setInfo("서버 claim 정보");
		claims.put("data", data);

		return generateToken(claims);
	}
	
	public String accessToken(String refreshToken) {
		DateTime now = DateTime.now();
		Date expiration = now.plusSeconds(jwtProps.getAccessTokenExpiration()).toDate();

		// refreshToken의 principal 재사용
		Claims claims = parseToken(refreshToken)
				.setIssuedAt(now.toDate())
				.setExpiration(expiration);

		// server to server API에 필요한 claims 설정
		JwtData data = new JwtData();
		data.setInfo("서버 claim 정보");
		claims.put("data", data);

		return generateToken(claims);
	}
	
	private String refreshToken(Authentication auth) {
		Claims claims = coreClaims(auth, jwtProps.getRefreshTokenExpiration());
		return generateToken(claims);
	}

	private Claims parseToken(String token) {
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(TextCodec.BASE64.decode(jwtProps.getKeyString()))
					.parseClaimsJws(token)
					.getBody();
			return claims;
		}
		catch(JwtException e) {
			throw e;			
		}
	}

}
