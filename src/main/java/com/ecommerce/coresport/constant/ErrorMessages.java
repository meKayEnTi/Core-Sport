package com.ecommerce.coresport.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessages {
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USERNAME_EXISTED = "Username already exists";
    public static final String EMAIL_EXISTED = "Email already exists";
    public static final String INVALID_INPUT = "Invalid username/email or password";
    public static final String INVALID_OLD_PASSWORD = "Invalid old password";
    public static final String UNAUTHORIZED = "You don't have permission";
}
