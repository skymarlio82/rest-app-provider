
package com.wiz.chat.boot.api.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected")
public class ProtectedRestController {

	@RequestMapping(method=RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getProtectedGreeting(HttpSession session) {
		System.out.println("http-session value (MSG) : " + session.getAttribute("MSG"));
		return ResponseEntity.ok("Greetings from admin protected method!");
	}
}