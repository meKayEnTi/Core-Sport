package com.ecommerce.coresport.model;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
    @NotBlank
    String username,

    @NotBlank
    String email,

    @NotBlank
    String password
){}

