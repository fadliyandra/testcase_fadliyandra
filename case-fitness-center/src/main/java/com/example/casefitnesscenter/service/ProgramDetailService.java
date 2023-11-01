package com.example.casefitnesscenter.service;


import com.example.casefitnesscenter.entity.dto.ProgramDetailDto;
import com.example.casefitnesscenter.entity.dto.ProgramDetailRequest;
import com.example.casefitnesscenter.entity.model.ProgramDetail;

import java.util.List;

public interface ProgramDetailService {
    ProgramDetailDto findProgramById(String id);

    List<ProgramDetailDto> findAll();

    ProgramDetail createOrUpdate(ProgramDetailRequest request);

}
