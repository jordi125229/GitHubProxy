package com.homework.microservice;

import lombok.Getter;

@Getter
public class GitProxyException extends RuntimeException {

    private final Integer httpStatus;

    public GitProxyException(String message, Integer httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
