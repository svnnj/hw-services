package com.example.clientservice.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ClientResponse {
    private Long id;
    private String fullName;
    private LocalDate birthDay;
    private PassportDto passport;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @Data
    public static class PassportDto {
        private String serial;
        private String number;
    }
} 