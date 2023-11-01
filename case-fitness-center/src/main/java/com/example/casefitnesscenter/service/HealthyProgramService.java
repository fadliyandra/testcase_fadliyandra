package com.example.casefitnesscenter.service;


import com.example.casefitnesscenter.common.BaseResponse;
import com.example.casefitnesscenter.entity.dto.HealthProgramDto;
import com.example.casefitnesscenter.entity.dto.HealthProgramRequest;
import com.example.casefitnesscenter.entity.model.HealthProgram;

import java.util.List;

public interface HealthyProgramService {
    BaseResponse<List<HealthProgramDto>> getAllHealthProgram();

    BaseResponse<HealthProgramDto> createNewProgramHealth(HealthProgramRequest request);

    HealthProgram getHealthyProgramById(String id);

    void updatePersistentProgramHealth(HealthProgram entity);
}
