package com.week10.gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import com.week10.gateway.service.AuthService;

@Component
public class ApiKeyFilter extends AbstractGatewayFilterFactory<ApiKeyFilter.Config> {
    private final AuthService authService;

    public ApiKeyFilter(AuthService authService) {
        super(Config.class);
        this.authService = authService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String apiKey = exchange.getRequest().getHeaders().getFirst("api-key");
            if (apiKey == null) {
                return Mono.error(new RuntimeException("API key is missing"));
            }
            System.out.println("API key received: " + apiKey);

            return authService.validateAPIKey(apiKey)
                    .doOnNext(isValid -> {
                        System.out.println("API key validation result: " + isValid);
                    })
                    .flatMap(isValid -> {
                        System.out.println("Is API key valid inside flatMap: " + isValid);
                        if (isValid) {
                            return chain.filter(exchange);
                        } else {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        }
                    });
        };
    }

    public static class Config {

    }
}
