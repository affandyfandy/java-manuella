package com.week8.Assignment0203.config;

import com.week8.Assignment0203.interceptor.ResponseInterceptor;
import com.week8.Assignment0203.service.ApiKeyService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
    private final ApiKeyService apiKeyService;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ResponseInterceptor(apiKeyService));
    }
}