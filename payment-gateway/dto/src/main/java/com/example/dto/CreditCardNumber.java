package com.example.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record CreditCardNumber(
        String cardNumber,
        Integer cvv) implements Serializable {
}