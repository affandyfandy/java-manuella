package com.week10.authentication.service;

import com.week10.authentication.repository.ApiKeyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApiKeyService {
    private final ApiKeyRepository apiKeyRepository;

    public boolean isApiKeyValid(String apiKey) {
        return apiKeyRepository.existsById(apiKey);
    }
}