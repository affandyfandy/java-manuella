package com.week8.FeignClientDemo.config;

import com.week8.FeignClientDemo.exception.BadRequestException;
import com.week8.FeignClientDemo.exception.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        return switch (response.status()) {
            case 400 -> new BadRequestException();
            case 404 -> new NotFoundException();
            default -> new Exception("Generic error");
        };
    }
}