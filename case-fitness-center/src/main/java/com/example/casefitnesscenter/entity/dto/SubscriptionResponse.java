package com.example.casefitnesscenter.entity.dto;

import lombok.Builder;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Builder
public record SubscriptionResponse(
        String subscriptionId,
        Set<HealthProgramSubscription> healthPrograms,
        String memberId,
        String name,
        String phoneNumber,
        String statusSubscribe,
        Integer remainingMeeting,
        String paymentStatus) {
    @Builder
    public record HealthProgramSubscription(String programId,
                                            String programName,
                                            Double price,
                                            Date schedule,
                                            Integer totalMeeting,
                                            Set<Exercise> exercises) {
        @Builder
        public record Exercise(
                String exercise,
                String description,
                String duration) implements Serializable {
        }
    }

    @Builder
    public record SubscriptionMember(
            String memberId,
            String name,
            String phoneNumber,
            Boolean isSubscribe,
            Integer remainingMeeting) implements Serializable {
    }
}
