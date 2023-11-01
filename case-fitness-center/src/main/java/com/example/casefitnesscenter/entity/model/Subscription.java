package com.example.casefitnesscenter.entity.model;

import com.example.casefitnesscenter.entity.PaymentStatus;
import com.example.casefitnesscenter.entity.StatusSubscribe;
import com.example.casefitnesscenter.utils.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "subcriptions")
public class Subscription extends BaseEntity<String> {
    @ManyToOne
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "member_fk_id"))
    private Member member;

    @Enumerated(EnumType.STRING)
    private StatusSubscribe statusSubscribe;

    private Integer remainingMeeting;

    @ManyToMany
    @JoinColumn(
            name="health_program_id",
            nullable = false
    )
    @JoinTable(
            name = "subscription_health_progam",
            joinColumns = @JoinColumn(name = "subscription_id"),
            inverseJoinColumns = @JoinColumn(name = "health_program_id")
    )
    private Set<HealthProgram> healthPrograms;

    @OneToOne(
            mappedBy = "subscription",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    public void addHealthPrograms(HealthProgram program) {
        if (Objects.isNull(this.healthPrograms) || this.healthPrograms.isEmpty()) {
            this.healthPrograms = new LinkedHashSet<>();
            this.healthPrograms.add(program);
            return;
        }

        this.healthPrograms.add(program);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subscription that = (Subscription) o;
        return Objects.equals(member, that.member)
               && Objects.equals(statusSubscribe, that.statusSubscribe)
               && Objects.equals(remainingMeeting, that.remainingMeeting);
    }

    @Override
    public void initialize() {
        if (this.getId() == null) {
            setId(UUID.randomUUID().toString());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), member, statusSubscribe, remainingMeeting);
    }
}