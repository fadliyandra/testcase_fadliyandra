package com.example.casefitnesscenter.common.exception;

import com.example.casefitnesscenter.common.FailResponse;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import static com.example.casefitnesscenter.utils.DateConverter.TIMESTAMP_NOW;


@RestControllerAdvice
public class ControllerAdvisor extends ResponseStatusExceptionHandler {
    @ExceptionHandler({AppRuntimeException.class, ResourceNotFoundException.class, DataIntegrityViolationException.class,
            InternalServerException.class, NumberFormatException.class})
    public ResponseEntity<Object> illegalActionDataHandler(RuntimeException exception,
                                                           HttpServletRequest request) {
        FailResponse response = FailResponse.builder()
                .timestamp(TIMESTAMP_NOW)
                .message(exception.getMessage())
                .code(String.valueOf(HttpStatus.CONFLICT.value()))
                .path(request.getServletPath())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                             HttpServletRequest request) {
        StringBuilder message = new StringBuilder();
        ex.getBindingResult().getFieldErrors()
                .stream().findFirst()
                .ifPresent(fieldError -> {
                    String defaultMessage = fieldError.getDefaultMessage();
                    String field = fieldError.getField();
                    message.append(StringUtils.capitalize(field))
                            .append(" ")
                            .append(defaultMessage);
                });

        FailResponse response = FailResponse.builder()
                .timestamp(TIMESTAMP_NOW)
                .message(message.toString())
                .code(String.valueOf(HttpStatus.CONFLICT.value()))
                .path(request.getServletPath())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
