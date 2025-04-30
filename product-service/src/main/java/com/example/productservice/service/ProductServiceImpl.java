package com.example.productservice.service;

import com.example.productservice.dto.ClientProductResponse;
import com.example.productservice.dto.IssueProductRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.entity.ClientProduct;
import com.example.productservice.entity.Product;
import com.example.productservice.repository.ClientProductRepository;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ClientProductRepository clientProductRepository;

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getAvailableProducts() {
        return productRepository.findByIsAvailableTrue().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientProductResponse> getClientProducts(Long clientId) {
        return clientProductRepository.findByClientId(clientId).stream()
                .map(this::mapToClientProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ClientProductResponse issueProductToClient(IssueProductRequest request) {

        Optional<ClientProduct> existingProduct = clientProductRepository
                .findByClientIdAndProductId(request.getClientId(), request.getProductId());
        
        if (existingProduct.isPresent()) {
            throw new IllegalArgumentException("Client already has this product");
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        
        if (!product.getIsAvailable()) {
            throw new IllegalArgumentException("Product is not available");
        }

        ClientProduct clientProduct = new ClientProduct();
        clientProduct.setClientId(request.getClientId());
        clientProduct.setProduct(product);
        clientProduct.setProductName(request.getProductName() != null ? 
                request.getProductName() : product.getName());
        clientProduct.setAccountNumber(request.getAccountNumber());

        ClientProduct savedClientProduct = clientProductRepository.save(clientProduct);
        return mapToClientProductResponse(savedClientProduct);
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .isAvailable(product.getIsAvailable())
                .build();
    }

    private ClientProductResponse mapToClientProductResponse(ClientProduct clientProduct) {
        return ClientProductResponse.builder()
                .id(clientProduct.getId())
                .clientId(clientProduct.getClientId())
                .productId(clientProduct.getProduct().getId())
                .productName(clientProduct.getProductName())
                .accountNumber(clientProduct.getAccountNumber())
                .issueDate(clientProduct.getIssueDate())
                .build();
    }
} 