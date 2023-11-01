package com.example.casefitnesscenter.entity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "program_details")
public class ProgramDetail implements Serializable {
    @Id
    private String programDetailId;

    private String exercise;

    private String description;

    private LocalTime duration;

    @ManyToMany(mappedBy = "programDetails")
    private Set<HealthProgram> healthPrograms;

    public void addHealthPrograms(HealthProgram healthProgram) {
        if (Objects.isNull(this.healthPrograms) || this.healthPrograms.isEmpty()) {
            this.healthPrograms = new LinkedHashSet<>();
            this.healthPrograms.add(healthProgram);
            return;
        }

        this.healthPrograms.add(healthProgram);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProgramDetail that = (ProgramDetail) o;
        return Objects.equals(exercise, that.exercise)
               && Objects.equals(description, that.description)
               && Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), exercise, description, duration);
    }

    @PrePersist
    public void initialize() {
        if (this.programDetailId == null) {
            setProgramDetailId(UUID.randomUUID().toString());
        }
    }
}