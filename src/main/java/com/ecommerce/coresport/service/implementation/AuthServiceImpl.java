package com.ecommerce.coresport.service.implementation;

import com.ecommerce.coresport.constant.ErrorMessages;
import com.ecommerce.coresport.constant.PredefinedRole;
import com.ecommerce.coresport.entity.Role;
import com.ecommerce.coresport.entity.User;
import com.ecommerce.coresport.exception.ConflictException;
import com.ecommerce.coresport.mapper.UserMapper;
import com.ecommerce.coresport.model.*;
import com.ecommerce.coresport.repository.RoleRepository;
import com.ecommerce.coresport.repository.UserRepository;
import com.ecommerce.coresport.security.CustomUserDetails;
import com.ecommerce.coresport.security.CustomUserDetailsService;
import com.ecommerce.coresport.security.JwtHelper;
import com.ecommerce.coresport.service.AuthService;
import com.ecommerce.coresport.service.RefreshTokenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    UserMapper userMapper;
    CustomUserDetailsService service;
    AuthenticationManager manager;
    JwtHelper helper;
    RoleRepository roleRepository;
    RefreshTokenService refreshTokenService;

    @Override
    public UserResponse register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.email()))
            throw new ConflictException(ErrorMessages.EMAIL_EXISTED);
        if(userRepository.existsByUsername(request.username()))
            throw new ConflictException(ErrorMessages.USERNAME_EXISTED);
        User newUser = userMapper.toUser(request);
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setEnabled(true);
        HashSet<Role> roles = new HashSet<>();
        roleRepository.findByName(PredefinedRole.ROLE_USER).ifPresent(roles::add);
        newUser.setRoles(roles);
        newUser = userRepository.save(newUser);
        return userMapper.toUserResponse(newUser);
    }

    @Override
    public JwtResponse login(JwtRequest request) {
        try{
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
            manager.authenticate(authenticationToken);
        }catch (BadCredentialsException exception) {
            throw new BadCredentialsException(ErrorMessages.INVALID_INPUT);
        }
        UserDetails customUserDetails = service.loadUserByUsername(request.getUsername());
        String token = helper.generateToken(customUserDetails);

        String deviceId = UUID.randomUUID().toString();
        String refreshToken = UUID.randomUUID().toString();

        refreshTokenService.store((
                (CustomUserDetails)customUserDetails).getId(),
                deviceId,
                refreshToken,
                request.getUserAgent(),
                request.getIpAddress()
        );

        return JwtResponse.builder()
                .username(request.getUsername())
                .token(token)
                .refreshToken(refreshToken)
                .deviceId(deviceId)
                .build();
    }

    @Override
    public JwtResponse refresh(TokenRefreshRequest request) {
        UUID userId = UUID.fromString(request.userId());
        String deviceId = request.deviceId();

        // 1. Validate stored session
        RefreshTokenSession session = refreshTokenService.verifyExpiration(userId, deviceId);
        if (!session.getRefreshToken().equals(request.refreshToken())) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        // 2. Load user and generate new access token
        UserDetails userDetails = service.loadUserByUsername(request.username());
        String newAccessToken = helper.generateToken(userDetails);

        // 3. (Optional) rotate refresh token
        String newRefreshToken = UUID.randomUUID().toString();
        refreshTokenService.store(userId, deviceId, newRefreshToken,
                session.getUserAgent(), session.getIpAddress());

        // 4. Return refreshed tokens
        return JwtResponse.builder()
                .username(request.username())
                .token(newAccessToken)
                .refreshToken(newRefreshToken)
                .deviceId(deviceId)
                .build();
    }
}
