package com.mx.rodo.spring.app.auth.service;

import java.io.IOException;
import java.util.Collection;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public interface IJwtService {
	
	public static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	public static final long EXPIRATION = 14400000L;
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "authorities";
	public static final String CONTENT_TYPE = "application/json";
	public static final String RESPONSE_HEADER = "Authtorization";
	
	public String create(Authentication auth) throws IOException;
	public boolean validate(String token); 
	public Claims getClaims(String token);
	public String getUsername(String token);
	public Collection<? extends GrantedAuthority> getAuthorities(String token) throws IOException;
	public String resolve(String token);
	
}
