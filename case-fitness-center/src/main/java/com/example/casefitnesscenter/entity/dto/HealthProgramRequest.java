package com.example.casefitnesscenter.entity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Set;

public record HealthProgramRequest(
        @NotBlank
        String nameOfProgram,

        @Min(0)
        Double price,

        @NotBlank
        String schedule,

        @Min(3)
        Integer totalMeeting,

        @NotNull
        Set<ProgramDetailRequest> programDetails) implements Serializable {

}
