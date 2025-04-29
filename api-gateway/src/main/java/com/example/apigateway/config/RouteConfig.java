package com.example.apigateway.config;

import com.example.apigateway.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RouteConfig {

    private final AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            // Public routes
            .route("user_service_register", r -> r.path("/api/users/register")
                .uri("http://localhost:8080"))
            
            // Protected routes that require authentication
            .route("user_service_get_user", r -> r.path("/api/users/{login}")
                .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                .uri("http://localhost:8080"))
            .route("user_service_current_user", r -> r.path("/api/users/me")
                .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                .uri("http://localhost:8080"))
            .build();
    }
} 