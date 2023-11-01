package com.example.casefitnesscenter.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record PasswordRequest(
        @NotBlank
        @NotNull
        String oldPassword,

        @NotBlank
        @NotNull
        String newPassword) implements Serializable {

}
