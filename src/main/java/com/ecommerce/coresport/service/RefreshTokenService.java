package com.ecommerce.coresport.service;

import com.ecommerce.coresport.model.RefreshTokenSession;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {
    void store(UUID userId, String deviceId, String refreshToken, String userAgent, String ipAddress);
    void remove(UUID userId, String deviceId);
    void removeAll(UUID userId);
    RefreshTokenSession verifyExpiration(UUID userId, String deviceId);
    Optional<RefreshTokenSession> get(UUID userId, String deviceId);
}
