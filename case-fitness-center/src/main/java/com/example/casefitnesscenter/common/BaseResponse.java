package com.example.casefitnesscenter.common;

import com.example.casefitnesscenter.constant.OperationStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BaseResponse<T> (
    String responseCode,
    String responseMessage,
    T data) implements Serializable {

    public BaseResponse(T data) {
        this(String.valueOf(HttpStatus.OK.value()), OperationStatus.SUCCESS.getName(), data);
    }

    public BaseResponse(int responseCode, String responseMessage) {
        this(String.valueOf(responseCode), responseMessage, null);
    }

    public BaseResponse(int responseCode, String responseMessage, T data) {
        this(String.valueOf(responseCode), responseMessage, data);
    }

    public BaseResponse() {
        this(String.valueOf(HttpStatus.OK.value()), OperationStatus.SUCCESS.getName(), null);
    }

}
