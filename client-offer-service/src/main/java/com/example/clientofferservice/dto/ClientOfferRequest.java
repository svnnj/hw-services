package com.example.clientofferservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ClientOfferRequest {
    
    @NotNull(message = "Client ID is required")
    @Positive(message = "Client ID must be positive")
    private Long clientId;
    
    @NotBlank(message = "Offer text is required")
    private String offerText;
} 