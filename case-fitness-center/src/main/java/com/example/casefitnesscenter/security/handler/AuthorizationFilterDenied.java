package com.example.casefitnesscenter.security.handler;

import com.example.casefitnesscenter.common.FailResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.example.casefitnesscenter.utils.DateConverter.TIMESTAMP_NOW;


@Component
@Slf4j
public class AuthorizationFilterDenied implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        FailResponse failureResponse = FailResponse.builder()
                .timestamp(TIMESTAMP_NOW)
                .message(accessDeniedException.getMessage())
                .code(String.valueOf(response.getStatus()))
                .path(request.getServletPath())
                .build();

        log.error("Access denied: {}", accessDeniedException.getMessage());
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), failureResponse);
    }
}
