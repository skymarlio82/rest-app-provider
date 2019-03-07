
package com.wiz.chat.boot.security.controller;

public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = -3893540221285034773L;

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
}