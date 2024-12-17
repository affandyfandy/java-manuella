package com.week10.invoice_api.service;

import com.week10.invoice_api.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ProductService {
    private final WebClient webClient;

    public ProductService(WebClient.Builder webClientBuilder, @Value("http://localhost:8082/api/v1") String productAPIUrl) {
        if (productAPIUrl == null || productAPIUrl.isEmpty()) {
            throw new IllegalArgumentException("Product API URL is not set");
        }
        this.webClient = webClientBuilder.baseUrl(productAPIUrl).build();
    }

    public Product getProductById(Long id) {
        return webClient.get()
                .uri("/products/{id}", id)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }
}