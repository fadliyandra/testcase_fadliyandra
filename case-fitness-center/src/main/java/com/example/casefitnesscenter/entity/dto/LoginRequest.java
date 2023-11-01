package com.example.casefitnesscenter.entity.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record LoginRequest(@NotBlank String username, @NotBlank String password) implements Serializable {
}
