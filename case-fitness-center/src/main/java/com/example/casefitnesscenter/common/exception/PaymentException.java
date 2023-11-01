package com.example.casefitnesscenter.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PaymentException extends AppRuntimeException{
    public PaymentException(String message) {
        super(message);
    }
}
