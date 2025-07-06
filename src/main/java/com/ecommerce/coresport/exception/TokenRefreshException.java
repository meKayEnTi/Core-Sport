package com.ecommerce.coresport.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenRefreshException extends AuthenticationException {
    public TokenRefreshException(String msg) {
        super(msg);
    }
}
