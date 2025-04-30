package com.example.clientofferservice.repository;

import com.example.clientofferservice.entity.ClientOfferReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientOfferReactionRepository extends JpaRepository<ClientOfferReaction, Long> {
    List<ClientOfferReaction> findByOfferId(Long offerId);
    Optional<ClientOfferReaction> findTopByOfferIdOrderByReactionTimestampDesc(Long offerId);
} 