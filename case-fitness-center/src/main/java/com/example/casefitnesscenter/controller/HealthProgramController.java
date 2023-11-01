package com.example.casefitnesscenter.controller;


import com.example.casefitnesscenter.common.BaseResponse;
import com.example.casefitnesscenter.constant.ApiUrlConstant;
import com.example.casefitnesscenter.entity.dto.HealthProgramDto;
import com.example.casefitnesscenter.entity.dto.HealthProgramRequest;
import com.example.casefitnesscenter.service.HealthyProgramService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(ApiUrlConstant.HEALTH_PROGRAMS_URL)
@RequiredArgsConstructor
public class HealthProgramController {
    private final HealthyProgramService programService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<HealthProgramDto>>> getAllHealthProgram() {
        var allHealthProgram = programService.getAllHealthProgram();
        return new ResponseEntity<>(allHealthProgram, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<HealthProgramDto>> createNewProgramHealth(@Valid @RequestBody HealthProgramRequest request) {
        BaseResponse<HealthProgramDto> baseResponse = programService.createNewProgramHealth(request);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
