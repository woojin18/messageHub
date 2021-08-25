package kr.co.uplus.cm.common.crypto;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import yoyozo.security.SHA;

/**
 * <pre>
 * Sha256PasswordEncoder
 *
 * </pre>
 *
 * @author : savrani (Son Wang Gu)
 * @Date : 2020. 12. 18.
 * @Version : 1.0 Copyright 2020 LG Uplus Corp. All Rights Reserved.
 */
public final class Sha512PasswordEncoder implements PasswordEncoder {

	private final SHA sha512 = new SHA(512);

	@Override
	public String encode(CharSequence rawPassword) {
		return sha512.encryptToBase64(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		if (StringUtils.isEmpty(rawPassword))
			return false;
		if (StringUtils.isEmpty(encodedPassword))
			return false;

		if (rawPassword.toString().equals(encodedPassword))
			return true;
		return false;
	}
}
