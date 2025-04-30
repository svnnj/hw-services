package com.example.productservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueProductRequest {
    @NotNull
    private Long clientId;
    
    @NotNull
    private Long productId;
    
    private String productName;
    
    @NotNull
    private String accountNumber;
} 