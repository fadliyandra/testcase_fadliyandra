package com.example.casefitnesscenter.entity.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;


@Builder
public record UpdateMemberDetailRequest(
        String name,

        @Valid
        CreditCardDto creditCard) implements Serializable {
    @Builder
    public record CreditCardDto(
            String placeholder,

            @Size(max = 20, min = 16)
            @NumberFormat(style = NumberFormat.Style.NUMBER)
            String cardNumber,

            @DateTimeFormat(pattern = "mm/yyyy")
            String expiredDate,

            @NumberFormat(style = NumberFormat.Style.NUMBER)
            @Size(max = 3, min = 3)
            String cvv) implements Serializable {
    }
}