package com.week10.authentication.controller;

import com.week10.authentication.service.ApiKeyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private ApiKeyService apiKeyService;

    @GetMapping("/validate-api-key")
    public boolean validateApiKey(@RequestParam String apiKey) {
        return apiKeyService.isApiKeyValid(apiKey);
    }
}