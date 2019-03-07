
package com.wiz.chat.boot.security.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JwtAuthenticationRequest implements Serializable {

	private String username = null;
	private String password = null;

	public JwtAuthenticationRequest() {
		super();
	}

	public JwtAuthenticationRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}