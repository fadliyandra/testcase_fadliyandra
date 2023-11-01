package com.example.casefitnesscenter.entity.mapper;


import com.example.casefitnesscenter.entity.dto.HealthProgramDto;
import com.example.casefitnesscenter.entity.dto.ProgramDetailDto;
import com.example.casefitnesscenter.entity.model.HealthProgram;
import com.example.casefitnesscenter.entity.model.ProgramDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class HealthProgramMapper {
    private final ProgramDetailMapper programDetailMapper;

    public HealthProgram toEntity(HealthProgramDto dto) {
        HealthProgram entity = new HealthProgram();
        Set<ProgramDetail> programDetails = dto.programDetails()
                .stream()
                .map(programDetailMapper::toEntity)
                .collect(Collectors.toUnmodifiableSet());
        entity.setProgramDetails(programDetails);
        entity.setProgramName(entity.getProgramName());
        entity.setPrice(dto.price());
        entity.setSchedule(dto.schedule());
        entity.setTotalMeetings(dto.totalMeeting());
        entity.setCreatedAt(dto.createdAt());
        entity.setLastModifiedDate(dto.lastModifiedDate()) ;
        return entity;
    }

    public HealthProgramDto toDto(HealthProgram entity) {
        Set<ProgramDetailDto> programDetails = entity.getProgramDetails()
                .stream()
                .map(programDetailMapper::toDto)
                .collect(Collectors.toUnmodifiableSet());

        return HealthProgramDto.builder()
                .id(entity.getId())
                .price(entity.getPrice())
                .programDetails(programDetails)
                .programName(entity.getProgramName())
                .createdAt(entity.getCreatedAt())
                .lastModifiedDate(entity.getLastModifiedDate())
                .schedule(entity.getSchedule())
                .totalMeeting(entity.getTotalMeetings())
                .build();
    }
}
