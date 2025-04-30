package com.example.productservice.service;

import com.example.productservice.dto.ClientProductResponse;
import com.example.productservice.dto.IssueProductRequest;
import com.example.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    List<ProductResponse> getAvailableProducts();
    List<ClientProductResponse> getClientProducts(Long clientId);
    ClientProductResponse issueProductToClient(IssueProductRequest request);
} 