package com.example.apigateway.config;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Configuration
public class ExceptionHandlerConfig {

    @Bean
    @Order(-2)
    public ErrorWebExceptionHandler customExceptionHandler() {
        return (ServerWebExchange exchange, Throwable ex) -> {
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            String errorMessage = "Internal server error";

            if (ex instanceof ResponseStatusException) {
                status = HttpStatus.valueOf(((ResponseStatusException) ex).getStatusCode().value());
                errorMessage = ex.getMessage();
            }

            response.setStatusCode(status);
            
            String responseBody = String.format(
                "{\"status\":%d,\"error\":\"%s\",\"message\":\"%s\"}",
                status.value(),
                status.getReasonPhrase(),
                errorMessage.replace("\"", "\\\"")
            );

            DataBuffer buffer = response.bufferFactory().wrap(responseBody.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        };
    }
} 