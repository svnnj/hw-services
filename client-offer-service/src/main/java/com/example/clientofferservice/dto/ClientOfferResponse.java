package com.example.clientofferservice.dto;

import com.example.clientofferservice.entity.ReactionType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ClientOfferResponse {
    private Long id;
    private Long clientId;
    private String offerText;
    private ReactionType currentReaction;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 