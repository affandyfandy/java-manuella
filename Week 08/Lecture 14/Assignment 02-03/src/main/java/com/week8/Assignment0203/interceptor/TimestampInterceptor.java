package com.week8.Assignment0203.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;

@Component
public class TimestampInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Add headers to the response after processing
        response.setHeader("timestamp", Instant.now().toString());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}