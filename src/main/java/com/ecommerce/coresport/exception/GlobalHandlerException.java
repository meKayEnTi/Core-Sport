package com.ecommerce.coresport.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleProductNotFoundException(ProductNotFoundException exception) {
        Map<String,String> error = new HashMap<>();
        error.put("message", "Product not found!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
