package com.example.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegistrationRequest {
    @NotBlank(message = "Login is required")
    private String login;
    
    @NotBlank(message = "Password is required")
    private String password;
    
    @NotBlank(message = "Full name is required")
    private String fullName;
} 