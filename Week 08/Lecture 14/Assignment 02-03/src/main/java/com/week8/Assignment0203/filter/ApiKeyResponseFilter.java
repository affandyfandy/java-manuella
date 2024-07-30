package com.week8.Assignment0203.filter;

import com.week8.Assignment0203.service.ApiKeyService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
@AllArgsConstructor
public class ApiKeyResponseFilter implements Filter {
    private final ApiKeyService apiKeyService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // API key validation
        String apiKey = httpRequest.getHeader("api-key");
        if (apiKey == null || apiKeyService.getApiKey(apiKey).isEmpty()) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API Key");
            return;
        }

        // Update API key usage
        apiKeyService.getApiKey(apiKey).ifPresent(apiKeyService::updateApiKeyUsage);

        // Add headers to the response after processing
        httpResponse.setHeader("source", "fpt-software");

        // Continue with the filter chain
        chain.doFilter(request, response);
    }
}
