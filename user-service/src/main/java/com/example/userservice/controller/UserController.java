package com.example.userservice.controller;

import com.example.userservice.dto.UserRegistrationRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        return ResponseEntity.ok(userService.registerUser(request));
    }

    @GetMapping("/{login}")
    public ResponseEntity<UserResponse> getUserByLogin(@PathVariable("login") String login) {
        return ResponseEntity.ok(userService.getUserByLogin(login));
    }
    
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return ResponseEntity.ok(userService.getUserByLogin(login));
    }
} 