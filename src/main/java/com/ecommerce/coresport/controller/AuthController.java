package com.ecommerce.coresport.controller;

import com.ecommerce.coresport.model.*;
import com.ecommerce.coresport.security.JwtHelper;
import com.ecommerce.coresport.service.AuthService;
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

    AuthService authService;

    @PostMapping("/register")
    ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok().body(authService.register(request));
    }

    @PostMapping("/login")
    ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest){
        JwtResponse response = authService.login(jwtRequest);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody TokenRefreshRequest request) {
        JwtResponse response = authService.refresh(request);
        return ResponseEntity.ok(response);
    }

}
