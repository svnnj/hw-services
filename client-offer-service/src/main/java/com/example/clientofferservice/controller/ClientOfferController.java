package com.example.clientofferservice.controller;

import com.example.clientofferservice.dto.ClientOfferReactionRequest;
import com.example.clientofferservice.dto.ClientOfferRequest;
import com.example.clientofferservice.dto.ClientOfferResponse;
import com.example.clientofferservice.service.ClientOfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
public class ClientOfferController {
    private final ClientOfferService clientOfferService;

    @PostMapping
    public ResponseEntity<ClientOfferResponse> createOffer(@Valid @RequestBody ClientOfferRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientOfferService.createOffer(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientOfferResponse> getOfferById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(clientOfferService.getOfferById(id));
    }

    @GetMapping
    public ResponseEntity<List<ClientOfferResponse>> getAllOffers() {
        return ResponseEntity.ok(clientOfferService.getAllOffers());
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ClientOfferResponse>> getOffersByClientId(@PathVariable("clientId") Long clientId) {
        return ResponseEntity.ok(clientOfferService.getOffersByClientId(clientId));
    }

    @PostMapping("/{id}/reaction")
    public ResponseEntity<ClientOfferResponse> saveReaction(
            @PathVariable("id") Long offerId,
            @Valid @RequestBody ClientOfferReactionRequest request) {
        return ResponseEntity.ok(clientOfferService.saveReaction(offerId, request));
    }
} 