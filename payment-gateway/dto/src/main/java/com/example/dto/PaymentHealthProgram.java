package com.example.dto;

import lombok.Builder;

import java.io.Serializable;
import java.util.Date;

@Builder
public record PaymentHealthProgram(
        Double totalPay,
        String placeholder,
        String cardNumber,
        Date expiredDate,
        Integer cvv) implements Serializable {
}