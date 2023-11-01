package com.example.casefitnesscenter.entity.dto;

import jakarta.validation.constraints.NotBlank;

public record SubscriptionRequest(
        @NotBlank
        String memberId,

        @NotBlank
        String healthProgramId) {
}
