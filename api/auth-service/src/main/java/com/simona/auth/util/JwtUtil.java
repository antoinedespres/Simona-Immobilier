package com.simona.auth.util;

import java.security.Key;
import java.security.SignatureException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

// See: https://javatechonline.com/how-to-implement-jwt-authentication-in-spring-boot-project/#Step7_Create_JWTUtiljava
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    // code to generate Token
    public String generateToken(String subject) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        Date now = new Date();
        Date expiryDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7));

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

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
        return getClaims(token).getExpiration().after(new Date(System.currentTimeMillis()));
    }

    // code to check if token is valid as per username
    public boolean isValidToken(String token,String username) {
        String tokenUserName=getSubject(token);
        return (username.equals(tokenUserName) && !isTokenExpired(token));
    }

    // code to check if token is expired
    public boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date(System.currentTimeMillis()));
    }

    //code to get expiration date
    public Date getExpirationDate(String token) {
        return getClaims(token).getExpiration();
    }

    //code to get expiration date
    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    // code to get the token from the request header

}
