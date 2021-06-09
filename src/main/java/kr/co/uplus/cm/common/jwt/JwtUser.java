package kr.co.uplus.cm.common.jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import kr.co.uplus.cm.common.model.AuthUser;

@JsonIgnoreProperties({ "status", "userPwd", "enabled", "accountNonExpired", "accountNonLocked",
		"credentialsNonExpired" })
public class JwtUser extends AuthUser {
	private static final long serialVersionUID = -1233591656437541107L;

	public static JwtUser createJwtUser(AuthUser user) {
		JwtUser u = new JwtUser();
		u.setUserId(user.getUserId());
		u.setLoginId(user.getLoginId());
		u.setUserName(user.getUsername());
		u.setAuthorities(user.getAuthorities());
		u.setStatus(user.getStatus());
		u.setRole(user.getRole());
		u.setCorpId(user.getCorpId());
		u.setSvcTypeCd(user.getSvcTypeCd());
		u.setRepProjectId(user.getRepProjectId());
		u.setBizType(user.getBizType());
		return u;
	}

	public static JwtUser createAuthUser(Map<String, Object> principal) {
		JwtUser u = new JwtUser();
		u.setUserId((String) principal.get("userId"));
		u.setLoginId((String) principal.get("loginId"));
		u.setUserName((String) principal.get("userNm"));

		@SuppressWarnings({ "unchecked", "rawtypes" })
		Collection<? extends GrantedAuthority> authorities = ((List<Map>) principal.get("authorities")).stream()
				.map(o -> new SimpleGrantedAuthority((String) o.get("authority"))).collect(Collectors.toList());
		u.setAuthorities(authorities);
		u.setCorpId((String) principal.get("corpId"));
		u.setRepProjectId((String) principal.get("repProjectId"));
		return u;
	}

}
