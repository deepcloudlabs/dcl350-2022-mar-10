package com.example.hr.filter;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Jwts;

public class JwtFilter extends GenericFilterBean {
	private final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

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
		logger.info("JwtFilter::doFilter() is running");
		if (Objects.nonNull(authorizationValue) && authorizationValue.startsWith("Bearer")) {
			var jwtToken = authorizationValue.replace("Bearer", "").trim();
			logger.info("Received token is {}.", jwtToken);
			var claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken).getBody();
			var username = claims.getSubject();
			var userDetails = userDetailsService.loadUserByUsername(username);
			Date expiration = claims.getExpiration();
			Date now = new Date();
			if (userDetails.getUsername().equals(username) && expiration.after(now)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				logger.error("Token ({}) is NOT valid!", jwtToken);
			}
		}
		chain.doFilter(request, response);
	}

}
