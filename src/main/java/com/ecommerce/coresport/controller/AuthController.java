package com.ecommerce.coresport.controller;

import com.ecommerce.coresport.configuration.JwtHelper;
import com.ecommerce.coresport.model.JwtReponse;
import com.ecommerce.coresport.model.JwtRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    UserDetailsService userDetailsService;

    AuthenticationManager manager;

    JwtHelper helper;

    @PostMapping("/login")
    ResponseEntity<JwtReponse> login(@RequestBody JwtRequest jwtRequest){
        this.authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = helper.generateToken(userDetails);
        JwtReponse reponse = JwtReponse.builder()
                .username(jwtRequest.getUsername())
                .token(token)
                .build();
        return ResponseEntity.ok().body(reponse);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDetails> getUserDetails(@RequestHeader("Authorization") String tokenHeader){
        String token = extractTokenFromHeader(tokenHeader);
        if(token != null){
            String username = helper.getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return ResponseEntity.ok().body(userDetails);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private String extractTokenFromHeader(String tokenHeader){
        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            return tokenHeader.substring(7);
        }
        return null;
    }

    private void authenticate(String username, String password){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            manager.authenticate(authenticationToken);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid Username or Password");
        }
    }
}
