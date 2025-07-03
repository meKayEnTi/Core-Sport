package com.ecommerce.coresport.controller;

import com.ecommerce.coresport.model.ChangePasswordRequest;
import com.ecommerce.coresport.model.UserResponse;
import com.ecommerce.coresport.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAllUser(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/disabled")
    public ResponseEntity<List<UserResponse>> getAllUsersNotEnabled(){
        return  ResponseEntity.ok().body(userService.getAllUsersNotEnabled());
    }

    @PutMapping("/{userId}/disabled")
    public ResponseEntity<Void> updateUserEnabledStatus(@PathVariable("userId")UUID userId, @RequestParam boolean enabled){
        userService.updateUserEnabledStatus(userId, enabled);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") UUID userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(){
        return ResponseEntity.ok().body(userService.getCurrentUser());
    }

    @PostMapping("/me/password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest request){
        userService.changePassword(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
