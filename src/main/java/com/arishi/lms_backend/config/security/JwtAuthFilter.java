					package com.arishi.lms_backend.config.security;

import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import com.arishi.lms_backend.util.AuthUtils;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

	private final AuthUtils authUtils;
	
	private final HandlerExceptionResolver handlerExceptionResolver;
	
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		if (!(request.getRequestURI().startsWith("/api/public"))) {
			String accessToken = null;
			Cookie[] cookies = request.getCookies(); 
			
			if (cookies != null) {
				for (Cookie cookie : cookies) {
		            if ("accessToken".equals(cookie.getName())) {
		            	accessToken = cookie.getValue();
		                break;
		            }
		        }
			}
			
			if (accessToken != null && accessToken.length() != 0) {
				try {
					Object currentUser = authUtils.verifyAccessToken(accessToken);
					Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, null);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				} catch (JwtException jwtException) {
					handlerExceptionResolver.resolveException(request, response, null, jwtException);
					// Return here to make sure that request will not go to below method, because response will already be sent
					return;
				}
			}
		}
		filterChain.doFilter(request, response);
	}

	public JwtAuthFilter(AuthUtils authUtils, HandlerExceptionResolver handlerExceptionResolver) {
		this.authUtils = authUtils;
		this.handlerExceptionResolver = handlerExceptionResolver;
	}
}