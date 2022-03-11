package com.example.hr.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class StandardUserDetailsService extends  InMemoryUserDetailsManager {
	@Value("${spring.security.user.name}")
	private String username;
	@Value("${spring.security.user.password}")
	private String password;
	
	public StandardUserDetailsService() {
		super(User.withUsername("admin").password("{noop}secret").roles("ADMIN").build());
	}

}
