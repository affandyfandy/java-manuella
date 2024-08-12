package com.fpt.midterm_g1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InactiveProductException extends RuntimeException {
    public InactiveProductException(String message) {
        super(message);
    }
}
