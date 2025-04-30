package com.example.productservice.controller;

import com.example.productservice.dto.ClientProductResponse;
import com.example.productservice.dto.IssueProductRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/available")
    public ResponseEntity<List<ProductResponse>> getAvailableProducts() {
        return ResponseEntity.ok(productService.getAvailableProducts());
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ClientProductResponse>> getClientProducts(@PathVariable Long clientId) {
        return ResponseEntity.ok(productService.getClientProducts(clientId));
    }

    @PostMapping("/issue")
    public ResponseEntity<ClientProductResponse> issueProductToClient(@Valid @RequestBody IssueProductRequest request) {
        ClientProductResponse response = productService.issueProductToClient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
} 