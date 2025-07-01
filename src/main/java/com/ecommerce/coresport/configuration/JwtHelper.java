package com.ecommerce.coresport.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtHelper {

    public static final long JWT_EXPIRATION_TIME = 86400000;

    private String jwtSecret = "9552b4701e3e3d18738d2cc791cb97bc5b6206ab04d0878e0574c97a4b3b074f";

    public String getUsernameFromToken(String token) {
        // Logic to extract username from JWT token
        return getClaimFromToken(token, Claims::getSubject); // Placeholder for actual implementation
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration); // Placeholder for actual implementation
    }

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
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
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }


    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        // Logic to parse the JWT token and extract claims
        final Claims claims = getAllClaimsFromToken(token); // Placeholder for actual implementation
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
//        System.out.println("DEBUG: Token received = " + token);
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
