package com.example.clientofferservice.service;

import com.example.clientofferservice.dto.ClientOfferReactionRequest;
import com.example.clientofferservice.dto.ClientOfferRequest;
import com.example.clientofferservice.dto.ClientOfferResponse;
import com.example.clientofferservice.entity.ClientOffer;
import com.example.clientofferservice.entity.ClientOfferReaction;
import com.example.clientofferservice.entity.ReactionType;
import com.example.clientofferservice.repository.ClientOfferReactionRepository;
import com.example.clientofferservice.repository.ClientOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientOfferService {
    private final ClientOfferRepository clientOfferRepository;
    private final ClientOfferReactionRepository reactionRepository;

    @Transactional
    public ClientOfferResponse createOffer(ClientOfferRequest request) {
        ClientOffer offer = new ClientOffer();
        offer.setClientId(request.getClientId());
        offer.setOfferText(request.getOfferText());
        
        ClientOffer savedOffer = clientOfferRepository.save(offer);
        
        ClientOfferReaction initialReaction = new ClientOfferReaction();
        initialReaction.setOffer(savedOffer);
        initialReaction.setReactionType(ReactionType.PENDING);
        reactionRepository.save(initialReaction);
        
        return mapToResponse(savedOffer, ReactionType.PENDING);
    }

    @Transactional(readOnly = true)
    public ClientOfferResponse getOfferById(Long id) {
        ClientOffer offer = clientOfferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found with ID: " + id));
        
        ReactionType currentReaction = getCurrentReaction(offer.getId());
        return mapToResponse(offer, currentReaction);
    }

    @Transactional(readOnly = true)
    public List<ClientOfferResponse> getOffersByClientId(Long clientId) {
        List<ClientOffer> offers = clientOfferRepository.findByClientId(clientId);
        return offers.stream()
                .map(offer -> mapToResponse(offer, getCurrentReaction(offer.getId())))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ClientOfferResponse> getAllOffers() {
        List<ClientOffer> offers = clientOfferRepository.findAll();
        return offers.stream()
                .map(offer -> mapToResponse(offer, getCurrentReaction(offer.getId())))
                .collect(Collectors.toList());
    }

    @Transactional
    public ClientOfferResponse saveReaction(Long offerId, ClientOfferReactionRequest request) {
        ClientOffer offer = clientOfferRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found with ID: " + offerId));
        
        ClientOfferReaction reaction = new ClientOfferReaction();
        reaction.setOffer(offer);
        reaction.setReactionType(request.getReactionType());
        reactionRepository.save(reaction);
        
        return mapToResponse(offer, request.getReactionType());
    }

    private ReactionType getCurrentReaction(Long offerId) {
        Optional<ClientOfferReaction> latestReaction = 
                reactionRepository.findTopByOfferIdOrderByReactionTimestampDesc(offerId);
        
        return latestReaction.map(ClientOfferReaction::getReactionType)
                .orElse(ReactionType.PENDING);
    }

    private ClientOfferResponse mapToResponse(ClientOffer offer, ReactionType reactionType) {
        ClientOfferResponse response = new ClientOfferResponse();
        response.setId(offer.getId());
        response.setClientId(offer.getClientId());
        response.setOfferText(offer.getOfferText());
        response.setCurrentReaction(reactionType);
        response.setCreatedAt(offer.getCreatedAt());
        response.setUpdatedAt(offer.getUpdatedAt());
        return response;
    }
} 