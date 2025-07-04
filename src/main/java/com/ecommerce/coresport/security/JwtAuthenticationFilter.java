package com.ecommerce.coresport.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    JwtHelper jwtHelper;
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if (isExcludedPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            sendError(response, "Authorization header is missing or invalid");
            return;
        }

        String token = header.substring(7);
        try {
            if (!processAuthentication(token)) {
                sendError(response, "Invalid token");
                return;
            }
        } catch (ExpiredJwtException e) {
            sendError(response, "Token expired");
            return;
        } catch (SignatureException e) {
            sendError(response, "Invalid JWT signature");
            return;
        } catch (JwtException | IllegalArgumentException e) {
            sendError(response, "Invalid or malformed token");
            return;
        }

        filterChain.doFilter(request, response);
    }
    private boolean isExcludedPath(String path) {
        List<String> excludedPaths = List.of(
                "/api/v1/auth/login", "/swagger-ui", "/v3/api-docs", "/swagger-resources",
                "/swagger-ui.html", "/webjars", "/favicon.ico"
        );
        return excludedPaths.stream().anyMatch(path::startsWith);
    }

    private boolean processAuthentication(String token) {
        String username = jwtHelper.getUsernameFromToken(token);
        if (username == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            return false;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!Boolean.TRUE.equals(jwtHelper.validateToken(token, userDetails))) {
            return false;
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
    }

    private void sendError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"" + message + "\"}");
    }
}
