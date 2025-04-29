package com.example.apigateway.filter;

import com.example.apigateway.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private final AuthService authService;

    public AuthenticationFilter(AuthService authService) {
        super(Config.class);
        this.authService = authService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                logger.warn("No Authorization header found in the request");
                return onError(exchange, "Authorization header is missing", HttpStatus.UNAUTHORIZED);
            }
            
            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            logger.debug("Authorization header: {}", authHeader);
            
            // Extract Basic Auth credentials
            if (!authHeader.startsWith("Basic ")) {
                logger.warn("Wrong authorization header format. Expected Basic Auth");
                return onError(exchange, "Wrong authorization header format", HttpStatus.UNAUTHORIZED);
            }
            
            try {
                // Extract the Base64 encoded credentials
                String base64Credentials = authHeader.substring("Basic ".length());
                String credentials = new String(Base64.getDecoder().decode(base64Credentials));
                
                // Credentials should be "username:password"
                String[] values = credentials.split(":", 2);
                if (values.length != 2) {
                    logger.warn("Wrong credentials format");
                    return onError(exchange, "Wrong credentials format", HttpStatus.BAD_REQUEST);
                }
                
                String username = values[0];
                String password = values[1];
                
                logger.debug("Extracted credentials: username={}", username);
                
                // Validate user credentials with auth service
                return authService.authenticateUser(username, password)
                        .flatMap(authenticated -> {
                            if (authenticated) {
                                // Create a new request with user ID in header
                                ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                                        .header("X-Auth-User", username)
                                        .build();
                                
                                // Forward the request with the new headers
                                return chain.filter(exchange.mutate().request(modifiedRequest).build());
                            } else {
                                // User is not authenticated
                                return onError(exchange, "Wrong credentials", HttpStatus.UNAUTHORIZED);
                            }
                        });
                
            } catch (IllegalArgumentException e) {
                logger.warn("Decoding error in Basic Auth: {}", e.getMessage());
                return onError(exchange, "Wrong credentials format", HttpStatus.BAD_REQUEST);
            }
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }

    public static class Config {
    }
} 