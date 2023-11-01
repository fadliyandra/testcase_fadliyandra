package com.example.casefitnesscenter.entity.dto;


import lombok.Builder;

import java.io.Serializable;
import java.util.Date;


@Builder
public record AppUserDto(
        String userId,
        String name,
        String email,
        String role,
        Date otpRequestedTime,
        Boolean isCredentialsNonExpired,
        Boolean isEnabled) implements Serializable {
}