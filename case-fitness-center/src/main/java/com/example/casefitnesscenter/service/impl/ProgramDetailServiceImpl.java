package com.example.casefitnesscenter.service.impl;

import com.example.casefitnesscenter.common.exception.ResourceNotFoundException;
import com.example.casefitnesscenter.entity.dto.ProgramDetailDto;
import com.example.casefitnesscenter.entity.dto.ProgramDetailRequest;
import com.example.casefitnesscenter.entity.mapper.ProgramDetailMapper;
import com.example.casefitnesscenter.entity.model.ProgramDetail;
import com.example.casefitnesscenter.entity.repository.ProgramDetailRepository;
import com.example.casefitnesscenter.service.ProgramDetailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.example.casefitnesscenter.constant.AppFitnessConstant.NOT_FOUND_MSG;
import static com.example.casefitnesscenter.constant.AppFitnessConstant.messageFormat;


@RequiredArgsConstructor
@Service
@Slf4j
public class ProgramDetailServiceImpl implements ProgramDetailService {

    private final ProgramDetailRepository repository;
    private final ProgramDetailMapper mapper;


    @Override
    public ProgramDetailDto findProgramById(String id) {
        log.info("Find id {}", id);

        String message = messageFormat(NOT_FOUND_MSG, id);
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(message));
    }

    @Override
    public List<ProgramDetailDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ProgramDetail createOrUpdate(ProgramDetailRequest request) {
        Optional<ProgramDetail> programDetailPersisted =
                repository.findByExerciseIgnoreCase(request.exercise());

        if(programDetailPersisted.isPresent()) {
            return programDetailPersisted.get();
        }

        String[] times = request.duration().split(":");
        int minutes = Integer.parseInt(times[0]);
        int second = Integer.parseInt(times[1]);
        LocalTime localTime = LocalTime.of(0, minutes, second);

        ProgramDetail programDetail = new ProgramDetail();
        programDetail.setExercise(request.exercise());
        programDetail.setDuration(localTime);
        programDetail.setDescription(request.description());


        repository.save(programDetail);
        log.info("Success to persist {}", programDetail);

        return programDetail;
    }
}
