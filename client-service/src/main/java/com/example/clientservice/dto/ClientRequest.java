package com.example.clientservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ClientRequest {
    @NotBlank(message = "Full name is required")
    private String fullName;
    
    @NotNull(message = "Birth day is required")
    private LocalDate birthDay;
    
    @Valid
    @NotNull(message = "Passport information is required")
    private PassportDto passport;
    
    @NotBlank(message = "Address is required")
    private String address;
    
    @Data
    public static class PassportDto {
        @NotBlank(message = "Passport serial is required")
        @Pattern(regexp = "\\d{4}", message = "Passport serial must be 4 digits")
        private String serial;
        
        @NotBlank(message = "Passport number is required")
        @Pattern(regexp = "\\d{3}\\s\\d{3}", message = "Passport number must match format '123 456'")
        private String number;
    }
} 