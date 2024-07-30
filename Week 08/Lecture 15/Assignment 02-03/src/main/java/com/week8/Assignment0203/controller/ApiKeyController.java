package com.week8.Assignment0203.controller;

import com.week8.Assignment0203.data.model.ApiKey;
import com.week8.Assignment0203.service.ApiKeyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/api-key")
@AllArgsConstructor
public class ApiKeyController {
    private final ApiKeyService apiKeyService;
    @GetMapping("/header")
    public String getHeader(@RequestHeader(value = "api-key") String apiKey) {
        Optional<ApiKey> apiKeyOptional= apiKeyService.getApiKey(apiKey);
        if (apiKeyOptional.isEmpty()){
            return "Username not found";
        }
        ApiKey apiKeyGet = apiKeyOptional.get();
        return String.format("username: %s, timestamp: %s", apiKeyGet.getUsername(), apiKeyGet.getLastUsed());
    }
}
