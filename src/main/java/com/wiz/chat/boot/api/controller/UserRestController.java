
package com.wiz.chat.boot.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wiz.chat.boot.security.JwtTokenUtil;
import com.wiz.chat.boot.security.model.JwtUser;

@RestController
public class UserRestController {

	@Value("${jwt.header}")
	private String tokenHeader = null;

	@Autowired
	private JwtTokenUtil jwtTokenUtil = null;

	@Autowired
	@Qualifier("jwtUserDetailsService")
	private UserDetailsService userDetailsService = null;

	@RequestMapping(value="/user", method=RequestMethod.GET)
	public JwtUser getAuthenticatedUser(HttpServletRequest request) {
		String token = request.getHeader(tokenHeader).substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		JwtUser user = (JwtUser)userDetailsService.loadUserByUsername(username);
		return user;
	}
}