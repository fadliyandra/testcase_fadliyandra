package com.example.casefitnesscenter.entity.dto;


import lombok.Builder;

import java.io.Serializable;
import java.time.LocalTime;


@Builder
public record ProgramDetailDto(
        String id,
        String exercise,
        String description,
        LocalTime duration) implements Serializable {
}