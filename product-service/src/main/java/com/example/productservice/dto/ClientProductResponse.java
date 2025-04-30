package com.example.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientProductResponse {
    private Long id;
    private Long clientId;
    private Long productId;
    private String productName;
    private String accountNumber;
    private LocalDateTime issueDate;
} 