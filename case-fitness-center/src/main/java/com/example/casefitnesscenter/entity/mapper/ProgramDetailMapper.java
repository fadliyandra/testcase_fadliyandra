package com.example.casefitnesscenter.entity.mapper;


import com.example.casefitnesscenter.entity.dto.ProgramDetailDto;
import com.example.casefitnesscenter.entity.model.ProgramDetail;
import org.springframework.stereotype.Component;

@Component
public class ProgramDetailMapper {

    public ProgramDetail toEntity(ProgramDetailDto programDetailDto) {
        ProgramDetail programDetail = new ProgramDetail();
        programDetail.setProgramDetailId(programDetailDto.id());
        programDetail.setDescription(programDetailDto.description());
        programDetail.setExercise(programDetailDto.exercise());
        programDetail.setDuration(programDetailDto.duration());

        return programDetail;
    }

    public ProgramDetailDto toDto(ProgramDetail programDetail) {
        return ProgramDetailDto.builder()
                .id(programDetail.getProgramDetailId())
                .description(programDetail.getDescription())
                .duration(programDetail.getDuration())
                .exercise(programDetail.getExercise())
                .build();
    }
}
