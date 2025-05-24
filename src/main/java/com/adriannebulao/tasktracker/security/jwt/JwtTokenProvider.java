package com.adriannebulao.tasktracker.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@AllArgsConstructor
@Component
public class JwtTokenProvider {

    private final SecurityConstants securityConstants;

    public String generateJwtToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        SecretKey key = Keys.hmacShaKeyFor(securityConstants.JWT_SECRET.getBytes());

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(key, Jwts.SIG.HS512)
                .compact();
    }

    public String getUsernameFromJwt(String jwtToken) {
        SecretKey key = Keys.hmacShaKeyFor(securityConstants.JWT_SECRET.getBytes());

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();

        return claims.getSubject();
    }

    public boolean validateJwtToken(String jwtToken) {
        SecretKey key = Keys.hmacShaKeyFor(securityConstants.JWT_SECRET.getBytes());

        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtToken);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Invalid JWT token (expired or incorrect)");
        }
    }
}
