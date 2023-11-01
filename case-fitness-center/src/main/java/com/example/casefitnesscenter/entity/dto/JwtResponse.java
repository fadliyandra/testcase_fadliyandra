package com.example.casefitnesscenter.entity.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record JwtResponse(
        String userId,
        String username,
        String accessToken,
        Long expirationAccessToken,
        String refreshToken,
        Long expirationRefreshToken,
        String type) implements Serializable {


}
