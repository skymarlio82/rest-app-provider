
package com.wiz.chat.boot.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wiz.chat.boot.security.JwtUserFactory;
import com.wiz.chat.boot.security.entity.User;
import com.wiz.chat.boot.security.repository.UserRepository;

@Service("jwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository = null;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {
			return JwtUserFactory.create(user);
		}
	}
}