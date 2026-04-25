package com.bank.auth.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {

	private static final String SECRET_KEY = 
			"mysecretkeymysecreetkeymysecretkey12";
	
	public String generateToken(String email) {
		SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());	// creates secret signing key. Used so token cannot be changed by others.
		
		return Jwts.builder()
				.subject(email)	// Stores logged-in User identity inside token
				.issuedAt(new Date())	// Token creation time
				.expiration(new  Date(System.currentTimeMillis() + 1000 * 60 * 60)) // token expiration time
				.signWith(key)	// digitally signed token
				.compact();		// final JWT token string is generated.
	}
	
	public boolean validateToken(String token) {
		
		try {
			SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
			
			Jwts.parser()
					.verifyWith(key)
					.build()
					.parseSignedClaims(token);
			
			return true;
		}catch (Exception e) {
			return false;
		}
		
	}
	
	public String extractEmail(String token) {
		
		SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		
		return Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
	
}
