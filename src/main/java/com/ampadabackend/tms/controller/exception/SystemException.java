package com.ampadabackend.tms.controller.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class SystemException extends RuntimeException {

    private final HttpStatus status;

    private final String message;

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
