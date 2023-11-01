/*
 * Copyright (c) 2022.
 */

package com.example.casefitnesscenter.security.handler;

import com.example.casefitnesscenter.common.FailResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.example.casefitnesscenter.utils.DateConverter.TIMESTAMP_NOW;


@Component
@Slf4j
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        FailResponse failureResponse = FailResponse.builder()
                .timestamp(TIMESTAMP_NOW)
                .message(authException.getMessage())
                .code(String.valueOf(response.getStatus()))
                .path(request.getServletPath())
                .build();

        log.error("Unauthorized error: {}", authException.getMessage());
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), failureResponse);
    }
}
