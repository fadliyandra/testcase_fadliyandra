package com.example.casefitnesscenter.entity;

import lombok.Getter;


@Getter
public enum PaymentStatus {
    PAYOFF("Pay off"),
    PENDING("Pending"),
    UNPAID("Unpaid"),
    FAILED("Failed");

    final String name;

    PaymentStatus(String name) {
        this.name = name;
    }
}
