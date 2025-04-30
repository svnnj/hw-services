package com.example.clientofferservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "client_offer_reactions")
public class ClientOfferReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "offer_id", nullable = false)
    private ClientOffer offer;

    @Enumerated(EnumType.STRING)
    @Column(name = "reaction_type", nullable = false)
    private ReactionType reactionType;

    @Column(name = "reaction_timestamp")
    private LocalDateTime reactionTimestamp;

    @PrePersist
    protected void onCreate() {
        reactionTimestamp = LocalDateTime.now();
    }
} 