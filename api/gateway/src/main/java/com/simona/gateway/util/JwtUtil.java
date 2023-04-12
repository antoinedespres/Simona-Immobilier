package com.simona.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

// See: https://javatechonline.com/how-to-implement-jwt-authentication-in-spring-boot-project/#Step7_Create_JWTUtiljava
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    // code to get Claims
    public Claims getClaims(String token) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // code to check if token is valid
    public boolean isValidToken(String token) {
        try {
            return getClaims(token).getExpiration().after(new Date(System.currentTimeMillis()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
