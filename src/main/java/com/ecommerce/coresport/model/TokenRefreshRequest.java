package com.ecommerce.coresport.model;


public record TokenRefreshRequest(
        String userId,
        String username,
        String deviceId,
        String refreshToken
) {
}
