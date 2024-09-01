package com.fuenteadictos.fuenteadictos.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long jwtExpirationInMs;

	// Crear un token JWT
	@SuppressWarnings("deprecation")
	public String create(String userId, String email) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("email", email);
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userId)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	// Extraer el email (o nombre de usuario) del token JWT
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// Validar el token JWT
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	// Extraer un claim específico del token JWT
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	// Extraer todos los claims del token JWT
	@SuppressWarnings("deprecation")
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();
	}

	// Verificar si el token ha expirado
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// Extraer la fecha de expiración del token JWT
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
}
