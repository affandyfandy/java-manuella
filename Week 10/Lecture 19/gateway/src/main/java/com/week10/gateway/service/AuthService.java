package com.week10.gateway.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthService {
    private final WebClient webClient;

    public AuthService(WebClient.Builder webClientBuilder, @Value("${auth.service.url}") String authAPIUrl) {
        if (authAPIUrl == null || authAPIUrl.isEmpty()) {
            throw new IllegalArgumentException("Auth API URL is not set");
        }
        this.webClient = webClientBuilder.baseUrl(authAPIUrl).build();
    }

    public Mono<Boolean> validateAPIKey(String apiKey) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/auth/validate-api-key")
                        .queryParam("apiKey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .map(Boolean::parseBoolean)
                .doOnNext(isValid -> System.out.println("Received validation result from auth-api: " + isValid))
                .onErrorResume(error -> {
                    System.out.println("Error during API key validation: " + error.getMessage());
                    return Mono.just(false);
                });
    }
}
