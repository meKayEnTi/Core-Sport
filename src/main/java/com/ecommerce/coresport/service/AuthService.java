package com.ecommerce.coresport.service;

import com.ecommerce.coresport.model.*;

public interface AuthService {
    // Account Registration
    UserResponse register(RegisterRequest request);

    // Login (kết hợp username/email + password)
    JwtResponse login(JwtRequest request);

    JwtResponse refresh(TokenRefreshRequest request);

}
