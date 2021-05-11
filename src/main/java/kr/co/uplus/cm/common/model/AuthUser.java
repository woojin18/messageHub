package kr.co.uplus.cm.common.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kr.co.uplus.cm.common.consts.Role;
import kr.co.uplus.cm.common.consts.UserStatus;
import lombok.Data;

@Data
public class AuthUser implements UserDetails {
	private static final long serialVersionUID = -2568297930450189586L;

	private String userId;
	private String userPwd;
	private String loginId;
	private UserStatus status;
	private Role role;
	private Collection<? extends GrantedAuthority> authorities;

	private String userName;
	private String corpId;
	private String svcTypeCd;
	private String repProjectId;
	private String currentProjectId;

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
