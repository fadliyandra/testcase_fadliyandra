package com.example.casefitnesscenter.entity.dto;

import lombok.Builder;

import java.io.Serializable;

/**
 * DTO for {@link com.jabar.app.fitnesscenter.feature.membership.entity.model.Member}
 */
@Builder
public record MemberDto(
        String id,
        String name,
        String email,
        String phoneNumber,
        String creditCard) implements Serializable {
}