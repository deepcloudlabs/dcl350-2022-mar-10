package com.example.hr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.hr.filter.JwtFilter;

@Configuration
@ConditionalOnProperty(value = "securityType", havingValue = "jwt")
public class TokenBasedSecurityConfig extends WebSecurityConfigurerAdapter {
	@Value("${spring.security.user.name}")
	private String username;
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${spring.security.user.password}")
	private String password;


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.err.println("Configuring token based security");
		http.csrf().disable().authorizeRequests()
		.antMatchers("/hr/api/v1/login").permitAll()
		.anyRequest().authenticated()
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(new JwtFilter(secret,userDetailsService),UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	private UserDetailsService userDetailsService;
	
}