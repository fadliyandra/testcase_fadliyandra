package com.example.casefitnesscenter.entity.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record MemberSubscribedRequest(
        @NotBlank
        String paymentStatus) implements Serializable {
}
