package com.arishi.lms_backend.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.arishi.lms_backend.config.security.CurrentUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class AuthUtils {
	
	@Value("${jwt.secret}")
	private String secretKey;
		
	private SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	public String getAccessToken(Long userId, String role) {
		
		String Token = Jwts.builder()
				.subject(userId.toString())
				.claim("role", role)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
				.issuer("LMS")
				.signWith(getSecretKey())
				.compact();
		return Token;
	}
	
	public Object verifyAccessToken(String Token) {
		
		Claims claims = Jwts.parser()
				.requireIssuer("LMS")
				.verifyWith(getSecretKey())
				.build()
				.parseSignedClaims(Token)
				.getPayload();
		
		Long userId =  Long.parseLong(claims.getSubject());
		String role = (String) claims.get("role");
		return CurrentUser.getUserInfo(userId, role);
	}
}