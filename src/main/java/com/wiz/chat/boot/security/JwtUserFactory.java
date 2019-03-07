
package com.wiz.chat.boot.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.wiz.chat.boot.security.entity.Authority;
import com.wiz.chat.boot.security.entity.User;
import com.wiz.chat.boot.security.model.JwtUser;
import com.wiz.chat.boot.util.SimpleLdapAuthenticator;

public final class JwtUserFactory {

	private JwtUserFactory() {
		
	}

	public static JwtUser create(User user) {
		SimpleLdapAuthenticator authen = new SimpleLdapAuthenticator();
		String password = authen.fetchPassword(user.getUsername());
//		System.out.println("====> start to verify user with password = " + authen.verify("bob", "bobspassword"));
		return new JwtUser(user.getId(), user.getUsername(), password, mapToGrantedAuthorities(user.getAuthorities()), user.getEnabled(), user.getLastPasswordResetDate());
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
		return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName().name())).collect(Collectors.toList());
	}
}