package com.ecommerce.coresport.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtHelper {

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.secret}")
    private String jwtSecret ;

    public String getUsernameFromToken(String token) {
        // Logic to extract username from JWT token
        return getClaimFromToken(token, Claims::getSubject); // Placeholder for actual implementation
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration); // Placeholder for actual implementation
    }

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        return generateToken(claims, userDetails.getUsername());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        // Logic to validate the JWT token
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private String generateToken(Map<String, Object> claims, String subject) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }


    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        // Logic to parse the JWT token and extract claims
        final Claims claims = getAllClaimsFromToken(token); // Placeholder for actual implementation
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
