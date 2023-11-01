package com.example.casefitnesscenter.entity.dto;


import java.io.Serializable;
import java.util.Date;


public record SubscriptionDto(
        String id,
        Date createdAt,
        Date lastModifiedDate,
        MemberDto member,
        Boolean isSubscribe,
        Integer remainingMeeting) implements Serializable {
}