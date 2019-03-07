
package com.wiz.chat.boot.util;

import org.springframework.security.crypto.password.PasswordEncoder;

public class SimpleMd5PasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		return Md5Parser.parseStrToMd5L32(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encode(rawPassword).equals(encodedPassword);
	}
}