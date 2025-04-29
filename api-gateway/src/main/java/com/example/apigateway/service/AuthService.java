package com.example.apigateway.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final WebClient webClient;

    @Autowired
    public AuthService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public Mono<Boolean> authenticateUser(String username, String password) {
        logger.debug("Authentication check for user: {}", username);
        
        // Create a request body with the credentials
        Map<String, String> credentials = new HashMap<>();
        credentials.put("login", username);
        credentials.put("password", password);
        
        return webClient.post()
                .uri("/api/users/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(credentials))
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        logger.debug("User {} successfully authenticated", username);
                        return Mono.just(true);
                    } else {
                        logger.debug("Authentication error for user {}: {}", 
                                     username, response.statusCode());
                        return Mono.just(false);
                    }
                })
                .onErrorResume(e -> {
                    logger.error("Authentication error: {}", e.getMessage());
                    return Mono.just(false);
                });
    }
} 