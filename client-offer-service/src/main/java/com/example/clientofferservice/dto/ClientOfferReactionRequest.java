package com.example.clientofferservice.dto;

import com.example.clientofferservice.entity.ReactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientOfferReactionRequest {
    
    @NotNull(message = "Reaction type is required")
    private ReactionType reactionType;
} 