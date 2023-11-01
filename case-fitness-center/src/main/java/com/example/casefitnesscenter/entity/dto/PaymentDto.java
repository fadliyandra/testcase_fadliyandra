package com.example.casefitnesscenter.entity.dto;


import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
public record PaymentDto(
        String paymentId,
        String subscriptionId,
        String memberId,
        String memberName,
        String paymentStatus,
        String paymentDate,
        BigDecimal paymentAmount) implements Serializable {
}


