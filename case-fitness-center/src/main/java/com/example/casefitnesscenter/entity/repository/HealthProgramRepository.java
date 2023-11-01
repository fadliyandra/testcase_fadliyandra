package com.example.casefitnesscenter.entity.repository;


import com.example.casefitnesscenter.entity.model.HealthProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HealthProgramRepository extends JpaRepository<HealthProgram, String> {

    @Query("select h from HealthProgram h")
    Page<HealthProgram> findBy(Pageable pageable);
}