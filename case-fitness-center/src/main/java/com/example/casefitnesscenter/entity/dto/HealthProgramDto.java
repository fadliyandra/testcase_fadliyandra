package com.example.casefitnesscenter.entity.dto;


import lombok.Builder;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


@Builder
public record HealthProgramDto(
        String id,
        Date createdAt,
        Date lastModifiedDate,
        String programName,
        Double price,
        Date schedule,
        Integer totalMeeting,
        Set<ProgramDetailDto> programDetails) implements Serializable {
}