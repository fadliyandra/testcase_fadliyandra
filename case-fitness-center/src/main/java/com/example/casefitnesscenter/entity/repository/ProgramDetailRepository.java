package com.example.casefitnesscenter.entity.repository;


import com.example.casefitnesscenter.entity.model.ProgramDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgramDetailRepository extends JpaRepository<ProgramDetail, String> {
    Optional<ProgramDetail> findByExerciseIgnoreCase(String exercise);
}