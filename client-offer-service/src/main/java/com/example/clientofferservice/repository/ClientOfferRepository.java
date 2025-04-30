package com.example.clientofferservice.repository;

import com.example.clientofferservice.entity.ClientOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientOfferRepository extends JpaRepository<ClientOffer, Long> {
    List<ClientOffer> findByClientId(Long clientId);
} 