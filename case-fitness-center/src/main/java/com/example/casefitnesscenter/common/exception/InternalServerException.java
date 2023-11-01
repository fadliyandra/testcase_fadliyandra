package com.example.casefitnesscenter.common.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ResponseStatus(INTERNAL_SERVER_ERROR)
public class InternalServerException extends Exception {
    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(Exception e) {
        super(e);
    }

    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
