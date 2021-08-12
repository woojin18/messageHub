package kr.co.uplus.cm.common.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kr.co.uplus.cm.common.consts.UserStatus;
import lombok.Data;

@Data
public class AuthUser implements UserDetails {
	private static final long serialVersionUID = -2568297930450189586L;

	private String userId;
	private String userPwd;
	private String loginId;
	private UserStatus status;
	private String role;
	private Collection<? extends GrantedAuthority> authorities;

	private String userName; // 유저명
	private String corpId; // 고객사ID
	private String svcTypeCd; // 서비스타입코드(유저가 보유한 서비스타입코드에 따라서 접속 가능한 서비스 결정(BO전용, AC_UC겸용, UC전용)
	private String repProjectId; // 대표프로젝트ID
	private String currentProjectId;
	private String bizType; // 특수사업자 유형
	private int diffDate; // 현재날짜 - 패스워드 변경일시

	public String getPassword() {
		return userPwd;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return status != UserStatus.LOCK;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return status == UserStatus.USE;
	}

	@Override
	public String getUsername() {
		return userName;
	}
}
