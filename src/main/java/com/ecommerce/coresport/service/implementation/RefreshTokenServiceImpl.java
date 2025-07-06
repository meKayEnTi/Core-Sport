package com.ecommerce.coresport.service.implementation;

import com.ecommerce.coresport.exception.TokenRefreshException;
import com.ecommerce.coresport.model.RefreshTokenSession;
import com.ecommerce.coresport.service.RefreshTokenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshTokenServiceImpl implements RefreshTokenService {
    RedisTemplate<String, RefreshTokenSession> sessionRedis;
    StringRedisTemplate stringRedis;

    @Value("${app.refresh-ttl-days}")
    int ttlDays;

    private Duration timeToLive() {
        return Duration.ofDays(ttlDays);
    }

    private String sessionKey(UUID userId, String deviceId) {
        return "refresh:" + userId + ":" + deviceId;
    }

    private String devicesSetKey(UUID userId) {
        return "refresh:devices:" + userId;
    }

    @Override
    public void store(UUID userId, String deviceId, String refreshToken,
                      String userAgent, String ip) {
        RefreshTokenSession session = RefreshTokenSession.builder()
                .refreshToken(refreshToken)
                .userAgent(userAgent)
                .ipAddress(ip)
                .createdAt(Instant.now())
                .build();

        // Store session with TTL
        sessionRedis.opsForValue()
                .set(sessionKey(userId, deviceId), session, timeToLive());

        // Index deviceId in a String set
        stringRedis.opsForSet()
                .add(devicesSetKey(userId), deviceId);
    }

    @Override
    public void remove(UUID userId, String deviceId) {
        sessionRedis.delete(sessionKey(userId, deviceId));
        stringRedis.opsForSet()
                .remove(devicesSetKey(userId), deviceId);
    }

    @Override
    public void removeAll(UUID userId) {
        String setKey = devicesSetKey(userId);
        Set<String> devices = stringRedis.opsForSet().members(setKey);
        if (devices != null && !devices.isEmpty()) {
            devices.forEach(device -> sessionRedis.delete(sessionKey(userId, device)));
        }
        stringRedis.delete(setKey);
    }

    @Override
    public RefreshTokenSession verifyExpiration(UUID userId, String deviceId) {
        String key = sessionKey(userId, deviceId);
        RefreshTokenSession session = sessionRedis.opsForValue().get(key);
        if (session == null) {
            throw new TokenRefreshException("Refresh session not found or expired");
        }
        // Check createdAt + TTL vs now
        if (session.getCreatedAt()
                .plus(timeToLive())
                .isBefore(Instant.now())) {
            // Clean up expired session
            remove(userId, deviceId);
            throw new TokenRefreshException("Refresh token expired. Please login again.");
        }
        return session;
    }


    @Override
    public Optional<RefreshTokenSession> get(UUID userId, String deviceId) {
        return Optional.ofNullable(
                sessionRedis.opsForValue().get(sessionKey(userId, deviceId))
        );
    }
}
