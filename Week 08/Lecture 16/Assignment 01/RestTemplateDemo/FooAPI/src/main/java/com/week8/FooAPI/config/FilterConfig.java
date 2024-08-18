package com.week8.FooAPI.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class FilterConfig {
    private final HeaderFilter headerFilter;
    public FilterRegistrationBean<HeaderFilter> headerFilter(){
        FilterRegistrationBean<HeaderFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(headerFilter);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
