package com.example.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record KafkaPaymentResponse(String timestamp, String status) implements Serializable {

}
