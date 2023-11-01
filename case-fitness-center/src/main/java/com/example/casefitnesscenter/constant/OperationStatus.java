package com.example.casefitnesscenter.constant;

import lombok.Getter;

@Getter
public enum OperationStatus {
    SUCCESS("Success"),

    FAILURE("Failure"),

    COMPLETED("Completed"),

    NOT_COMPLETED("Not Completed"),

    NOT_FOUND("Not Found"),

    FOUND("Found"),

    ERROR("Error");

    private final String name;

    OperationStatus(String name) {
        this.name = name;
    }
}
