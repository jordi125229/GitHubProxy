package com.homework.microservice.exception;

import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MicroServiceExceptionHandler {
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorMessage> handleException(FeignException exception) {
        return ResponseEntity.status(exception.status()).body(new ErrorMessage(exception.getMessage(),exception.status()));
    }
}
