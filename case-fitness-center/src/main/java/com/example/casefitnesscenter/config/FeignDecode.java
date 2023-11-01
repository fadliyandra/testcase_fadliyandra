package com.example.casefitnesscenter.config;

import com.example.casefitnesscenter.common.exception.AppRuntimeException;
import com.example.casefitnesscenter.common.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Slf4j
public class FeignDecode implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {
        AppRuntimeException ex;
        if (Objects.isNull(response.body())
                && Objects.equals(response.status(), 404)
                && response.reason().equals("Not Found")) {
            log.info("Target endpoint from external service is not found: {}", response);
            throw new ResourceNotFoundException("Url / endpoint is not found on this server");
        }

        if (Objects.nonNull(response.body())) {
            try (InputStream bodyIs = response.body().asInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                ex = mapper.readValue(bodyIs, AppRuntimeException.class);
            } catch (IOException e) {
                throw new AppRuntimeException(response.reason());
            }
        }

        return errorDecoder.decode(s, response);
    }
}

