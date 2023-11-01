package com.example.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record CreditCardDetail(
        String placeholder,
        String cardNumber) implements Serializable {
}