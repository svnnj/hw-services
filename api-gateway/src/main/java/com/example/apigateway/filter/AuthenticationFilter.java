package com.example.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            
            // Check if the request contains authorization headers
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                logger.warn("No Authorization header found in the request");
            } else {
                String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                logger.debug("Auth header: {}", authHeader);
                
                // Forward the authorization header to the downstream service
                // The actual authentication check will be done by the user-service
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Configuration properties if needed
    }
} 