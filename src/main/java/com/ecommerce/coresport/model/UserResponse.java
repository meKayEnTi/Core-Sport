package com.ecommerce.coresport.model;

import java.util.Set;
import java.util.UUID;

public record UserResponse (
    UUID id,

    String email,

    String username,

    Boolean enabled,

    Set<RoleResponse> roles
) {}
