package com.example.casefitnesscenter.entity.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.math.BigDecimal;

public record PaymentRequest(
        @NotBlank
        String healthyId,
        @Min(value = -1, message = "Payment amount can't be negative")
        BigDecimal paymentAmount) implements Serializable {
}
