package com.example.productservice.repository;

import com.example.productservice.entity.ClientProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientProductRepository extends JpaRepository<ClientProduct, Long> {
    List<ClientProduct> findByClientId(Long clientId);
    Optional<ClientProduct> findByClientIdAndProductId(Long clientId, Long productId);
} 