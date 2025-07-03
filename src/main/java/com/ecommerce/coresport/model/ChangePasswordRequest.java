package com.ecommerce.coresport.model;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(

        @NotBlank
        String oldPassword,

        @NotBlank
        String newPassword
) {}
