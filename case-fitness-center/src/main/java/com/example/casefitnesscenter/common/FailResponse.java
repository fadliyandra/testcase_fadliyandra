package com.example.casefitnesscenter.common;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record FailResponse ( String timestamp, String message, String code, String path) implements Serializable {


}
