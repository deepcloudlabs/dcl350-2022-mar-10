package com.example.hr.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.hr.security.dto.request.WebUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenProvider {
	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expire.duration}")
	private long expirationDuration;
	
	public String generateToken(WebUser user) {
		Claims claims = Jwts.claims().setSubject(user.getUsername());
		Date now = new Date();
		return Jwts.builder()
				   .setClaims(claims)
				   .setIssuedAt(now)
				   .setExpiration(new Date(now.getTime()+expirationDuration))
				   .signWith(SignatureAlgorithm.HS256, secret)
				   .compact();
	}

}
