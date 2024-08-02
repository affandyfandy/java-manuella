package com.week8.WebClientDemo.service;

import com.week8.WebClientDemo.model.Foo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FooService {

    private final WebClient webClient;

    public FooService(WebClient.Builder webClientBuilder, @Value("${foo.api.url}") String fooApiUrl) {
        if (fooApiUrl == null || fooApiUrl.isEmpty()) {
            throw new IllegalArgumentException("Foo API URL is not set");
        }
        this.webClient = webClientBuilder.baseUrl(fooApiUrl).build();
    }

    public Mono<Foo> getFooById(Long id) {
        return webClient.get()
                .uri("/foos/{id}", id)
                .retrieve()
                .bodyToMono(Foo.class);
    }

    public Mono<Foo> createFoo(Foo foo) {
        return webClient.post()
                .uri("/foos")
                .bodyValue(foo)
                .retrieve()
                .bodyToMono(Foo.class);
    }

    public Mono<Foo> updateFoo(Long id, Foo foo) {
        return webClient.put()
                .uri("/foos/{id}", id)
                .bodyValue(foo)
                .retrieve()
                .bodyToMono(Foo.class);
    }

    public Mono<Void> deleteFoo(Long id) {
        return webClient.delete()
                .uri("/foos/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Flux<Foo> getAllFoos() {
        return webClient.get()
                .uri("/foos")
                .retrieve()
                .bodyToFlux(Foo.class);
    }
}