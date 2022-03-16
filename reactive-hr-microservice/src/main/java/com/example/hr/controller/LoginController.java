package com.example.hr.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.hr.security.dto.request.WebUser;
import com.example.hr.service.JwtTokenProvider;

@RestController
@RequestMapping("login")
@RequestScope
@Validated
@CrossOrigin
public class LoginController {
	private final Logger logger = LoggerFactory.getLogger(LoginController.class);

	private AuthenticationManager authenticationManager;
	private JwtTokenProvider jwtTokenProvider;

	public LoginController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@PostMapping
	public String authenticateAndCreateToken(@RequestBody @Validated WebUser user) {
		try {
			var authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
			authenticationManager.authenticate(authentication);
			return jwtTokenProvider.generateToken(user);
		} catch (AuthenticationException e) {
			logger.error("Wrong username/password.");
			logger.error("Reason is {}", e.getMessage());
			throw e;
		}
	};
}
