package com.week8.Assignment0203.config;

import com.week8.Assignment0203.filter.ApiKeyResponseFilter;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class FilterConfig {
    private final ApiKeyResponseFilter apiKeyFilter;
    @Bean(name = "apiKeyResponseFilterConfig")
    public FilterRegistrationBean<ApiKeyResponseFilter> apiKeyResponseFilter() {
        FilterRegistrationBean<ApiKeyResponseFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(apiKeyFilter);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}