package com.example.userservice.dto;

import lombok.Data;

@Data
public class UserResponse {
    private String fullName;
    private String login;
    private RoleResponse role;

    @Data
    public static class RoleResponse {
        private String name;
        private String description;
    }
} 