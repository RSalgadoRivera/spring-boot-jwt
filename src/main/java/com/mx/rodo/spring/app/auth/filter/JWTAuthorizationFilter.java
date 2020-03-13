package com.mx.rodo.spring.app.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.mx.rodo.spring.app.auth.service.IJwtService;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private IJwtService jwtService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, IJwtService jwtService) {
		super(authenticationManager);
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader("Authorization");

		if (!requiresAuthentication(header)) {
			chain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken authenticationToken = null;
		if (jwtService.validate(header)) {
			authenticationToken = new UsernamePasswordAuthenticationToken(jwtService.getUsername(header), null,
					jwtService.getAuthorities(header));
		}
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		chain.doFilter(request, response);
	}

	protected boolean requiresAuthentication(String header) {

		if (header == null || !header.toLowerCase().startsWith("bearer ")) {
			return false;
		} else {
			return true;
		}
	}
}
