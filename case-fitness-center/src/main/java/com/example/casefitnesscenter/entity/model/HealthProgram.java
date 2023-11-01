package com.example.casefitnesscenter.entity.model;

import com.example.casefitnesscenter.utils.BaseEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "health_programs",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"program_name"}
        )
)
public class HealthProgram extends BaseEntity<String> implements Serializable {

    @Column(name = "program_name")
    private String programName;

    private Double price;

    @Temporal(TemporalType.TIMESTAMP)
    private Date schedule;

    private Integer totalMeetings;

    @ManyToMany
    @JoinColumn(name = "program_detail_id")
    @JoinTable(
            name = "exercise_program_health",
            joinColumns = @JoinColumn(name = "health_program_id"),
            inverseJoinColumns = @JoinColumn(name = "program_detail_id")
    )
    private Set<ProgramDetail> programDetails = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "healthPrograms")
    private Set<Subscription> subscriptions;

    public void addProgramDetails(ProgramDetail programDetails) {
        this.programDetails.add(programDetails);
    }

    public void addSubscription(Subscription subscription) {
        if (Objects.isNull(this.subscriptions) || this.subscriptions.isEmpty()) {
            this.subscriptions = new LinkedHashSet<>();
            this.subscriptions.add(subscription);
            return;
        }
        this.subscriptions.add(subscription);
    }

    @Override
    public void initialize() {
        if (this.getId() == null) {
            setId(UUID.randomUUID().toString());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HealthProgram that = (HealthProgram) o;
        return Objects.equals(programName, that.programName)
               && Objects.equals(price, that.price)
               && Objects.equals(schedule, that.schedule)
               && Objects.equals(totalMeetings, that.totalMeetings)
               && Objects.equals(programDetails, that.programDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), programName, price, schedule, totalMeetings, programDetails);
    }
}
