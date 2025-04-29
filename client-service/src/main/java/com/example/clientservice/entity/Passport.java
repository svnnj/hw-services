package com.example.clientservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Passport {

    @Column(name = "passport_serial", nullable = false)
    private String serial;

    @Column(name = "passport_number", nullable = false)
    private String number;
} 