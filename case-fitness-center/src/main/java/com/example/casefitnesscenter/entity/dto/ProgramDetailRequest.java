package com.example.casefitnesscenter.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record ProgramDetailRequest(
        @NotNull
        String exercise,
        @NotBlank
        String description,
        @JsonFormat(pattern = "mm:ss")
        String duration) implements Serializable {
}
