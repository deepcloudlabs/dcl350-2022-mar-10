package com.example.hr.filter;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Jwts;

public class JwtFilter extends GenericFilterBean {
	private String secret;
	private UserDetailsService userDetailsService;

	public JwtFilter(String secret, UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
		this.secret = secret;
	}

	// Authorization: Bearer <jwt token>
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		var authorizationValue = ((HttpServletRequest) request).getHeader("Authorization");
		System.err.println("do filter is running");
		if (Objects.nonNull(authorizationValue) && authorizationValue.startsWith("Bearer")) {
			var jwtToken = authorizationValue.replace("Bearer", "").trim();
			System.err.println(jwtToken);
			var claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken).getBody();
			var username = claims.getSubject();
			var userDetails = userDetailsService.loadUserByUsername(username);
			Date expiration = claims.getExpiration();
			Date now = new Date();
			if (userDetails.getUsername().equals(username) && expiration.after(now)) {
				System.err.println("token is valid!");
				Authentication usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				System.err.println("token is NOT valid!");
			}
			chain.doFilter(request, response);
		}

	}

}
