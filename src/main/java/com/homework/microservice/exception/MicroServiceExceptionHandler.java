package com.homework.microservice.exception;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class MicroServiceExceptionHandler {

    @ExceptionHandler(GitProxyException.class)
    public ResponseEntity<ErrorMessage> handleException(GitProxyException exception) {
        log.warn("Error: {}", exception.getMessage());
        return ResponseEntity.status(exception.getStatus()).body(new ErrorMessage(exception.getMessage(), exception.getStatus()));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorMessageForeignException> handleForeignException(FeignException exception) {
        log.warn("External service error: {}", exception.getMessage());
        return ResponseEntity.status(exception.status()).body(new ErrorMessageForeignException(exception.getMessage(), exception.status()));
    }
}
