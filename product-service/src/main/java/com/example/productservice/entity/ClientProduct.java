package com.example.productservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "client_products")
public class ClientProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "issue_date")
    private LocalDateTime issueDate;

    @PrePersist
    protected void onCreate() {
        issueDate = LocalDateTime.now();
    }
} 