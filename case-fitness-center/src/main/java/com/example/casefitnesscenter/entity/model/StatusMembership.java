package com.example.casefitnesscenter.entity.model;

import lombok.Getter;

@Getter
public enum StatusMembership {
    REGISTERED("registered"),
    UNREGISTERED("unregistered"),
    NOT_VALIDATED("not verified");

    private final String name;
    StatusMembership(String name) {
        this.name = name;
    }
}
