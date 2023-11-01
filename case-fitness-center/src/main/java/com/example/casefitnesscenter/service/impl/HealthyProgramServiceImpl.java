package com.example.casefitnesscenter.service.impl;


import com.example.casefitnesscenter.common.BaseResponse;
import com.example.casefitnesscenter.common.exception.ResourceNotFoundException;
import com.example.casefitnesscenter.entity.dto.HealthProgramDto;
import com.example.casefitnesscenter.entity.dto.HealthProgramRequest;
import com.example.casefitnesscenter.entity.dto.ProgramDetailRequest;
import com.example.casefitnesscenter.entity.mapper.HealthProgramMapper;
import com.example.casefitnesscenter.entity.model.HealthProgram;
import com.example.casefitnesscenter.entity.model.ProgramDetail;
import com.example.casefitnesscenter.entity.repository.HealthProgramRepository;
import com.example.casefitnesscenter.service.HealthyProgramService;
import com.example.casefitnesscenter.service.ProgramDetailService;
import com.example.casefitnesscenter.utils.DateConverter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

import java.util.List;
import java.util.UUID;

import static com.example.casefitnesscenter.constant.AppFitnessConstant.NOT_FOUND_MSG;
import static com.example.casefitnesscenter.constant.AppFitnessConstant.messageFormat;


@Service
@RequiredArgsConstructor
public class HealthyProgramServiceImpl implements HealthyProgramService {
    private final HealthProgramRepository healthProgramRepository;
    private final ProgramDetailService programDetailService;
    private final HealthProgramMapper healthProgramMapper;

    @Override
    public BaseResponse<List<HealthProgramDto>> getAllHealthProgram() {
        List<HealthProgramDto> programDtoPage = healthProgramRepository
                .findAll()
                .stream()
                .map(healthProgramMapper::toDto)
                .toList();
        return new BaseResponse<>(programDtoPage);
    }

    @Transactional
    @Override
    public BaseResponse<HealthProgramDto> createNewProgramHealth(HealthProgramRequest request) {
        Date schedule = DateConverter.parseToDate(request.schedule(), DateConverter.DATETIME_FORMAT);

        HealthProgram healthProgram = new HealthProgram();
        healthProgram.setId(UUID.randomUUID().toString());
        healthProgram.setCreatedAt(new Date());
        healthProgram.setProgramName(request.nameOfProgram());
        healthProgram.setPrice(request.price());
        healthProgram.setSchedule(schedule);
        healthProgram.setTotalMeetings(request.totalMeeting());
        healthProgram = healthProgramRepository.saveAndFlush(healthProgram);

        for (ProgramDetailRequest program : request.programDetails()) {
            ProgramDetail programDetail = programDetailService.createOrUpdate(program);
            healthProgram.addProgramDetails(programDetail);
        }

        healthProgramRepository.save(healthProgram);

        HealthProgramDto mapperDto = healthProgramMapper.toDto(healthProgram);
        return new BaseResponse<>(mapperDto);
    }

    @Override
    public HealthProgram getHealthyProgramById(String id) {
        return this.getHealthProgramById(id);
    }

    @Override
    public void updatePersistentProgramHealth(HealthProgram entity) {
        healthProgramRepository.save(entity);
    }

    private HealthProgram getHealthProgramById(String id) {
        String message = messageFormat(NOT_FOUND_MSG, id);
        return healthProgramRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(message));
    }
}
