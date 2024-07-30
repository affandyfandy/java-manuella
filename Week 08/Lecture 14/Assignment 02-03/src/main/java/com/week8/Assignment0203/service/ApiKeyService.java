package com.week8.Assignment0203.service;

import com.week8.Assignment0203.data.model.ApiKey;
import com.week8.Assignment0203.data.repository.ApiKeyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ApiKeyService {
    private final ApiKeyRepository apiKeyRepository;

    public Optional<ApiKey> getApiKey(String key){
        return apiKeyRepository.findByKey(key);
    }

    public void updateApiKeyUsage(ApiKey apiKey){
        apiKey.setLastUsed(LocalDateTime.now());
        apiKeyRepository.save(apiKey);
    }
}
