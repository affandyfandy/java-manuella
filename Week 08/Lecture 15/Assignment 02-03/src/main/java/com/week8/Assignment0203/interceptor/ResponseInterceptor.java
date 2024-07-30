package com.week8.Assignment0203.interceptor;

import com.week8.Assignment0203.service.ApiKeyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;

@Component
@AllArgsConstructor
public class ResponseInterceptor implements HandlerInterceptor {
    private final ApiKeyService apiKeyService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Add headers to the response after processing
        response.setHeader("timestamp", Instant.now().toString());

        String apiKey = request.getHeader("api-key");
        if (apiKey != null) {
            apiKeyService.getApiKey(apiKey).ifPresent(apiKeyEntity -> {
                response.setHeader("username", apiKeyEntity.getUsername());
            });
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}