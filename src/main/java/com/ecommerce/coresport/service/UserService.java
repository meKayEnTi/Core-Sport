package com.ecommerce.coresport.service;

import com.ecommerce.coresport.model.*;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserResponse> getAllUsersNotEnabled();

    void updateUserEnabledStatus(UUID userId, boolean enabled);

    List<UserResponse> getAllUsers();

    void deleteUser(UUID userId);

    UserResponse getCurrentUser();

    void changePassword(ChangePasswordRequest request);
}

