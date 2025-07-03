package com.ecommerce.coresport.service.implementation;

import com.ecommerce.coresport.constant.ErrorMessages;
import com.ecommerce.coresport.entity.User;
import com.ecommerce.coresport.exception.ResourceNotFoundException;
import com.ecommerce.coresport.mapper.UserMapper;
import com.ecommerce.coresport.model.*;
import com.ecommerce.coresport.repository.UserRepository;
import com.ecommerce.coresport.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsersNotEnabled() {
        return userRepository.findByEnabledFalse().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void updateUserEnabledStatus(UUID userId, boolean enabled) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));

        if (Boolean.TRUE.equals(user.getEnabled()) == enabled) {
            return;
        }

        user.setEnabled(enabled);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    @Override
    public UserResponse getCurrentUser() {
        return userMapper.toUserResponse(getAuthenticatedUser());
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        User user = getAuthenticatedUser();
        boolean isOldPassword = passwordEncoder.matches(request.oldPassword(), user.getPassword());
        if(isOldPassword){
            user.setPassword(passwordEncoder.encode(request.newPassword()));
        }else {
            throw new BadCredentialsException(ErrorMessages.INVALID_OLD_PASSWORD);
        }
        userRepository.save(user);
    }

    private User getAuthenticatedUser(){
        Authentication context = SecurityContextHolder.getContext().getAuthentication();
        String username = context.getName();

        return userRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND)
        );
    }
}
