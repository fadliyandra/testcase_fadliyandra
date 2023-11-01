package com.example.casefitnesscenter.entity.dto;


import com.example.casefitnesscenter.utils.anotations.PhoneNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;


@Builder
public record MembershipDetailsRequest(
        @NotBlank(message = "is required")
        String name,
        @Email(message = "Email is not valid")
        @NotBlank(message = "is required")
        String email,
        @NotBlank(message = "is required")
        @Size(min = 6, message = "Password length should be at least 6 characters")
        String password,
        @NotBlank(message = "is required")
        @PhoneNumber
        String phoneNumber,
        @NotNull @Valid
        CreditCardDto creditCard) implements Serializable {
    @Builder
    public record CreditCardDto(
            @NotBlank(message = "is required")
            String placeholder,
            @NotBlank(message = "is required")
            @Size(max = 20, min = 16)
            @NumberFormat(style = NumberFormat.Style.NUMBER)
            String cardNumber,
            @DateTimeFormat(pattern = "mm/yyyy")
            String expiredDate,
            @NotBlank(message = "is required")
            @NumberFormat(style = NumberFormat.Style.NUMBER)
            @Size(max = 3, min = 3)
            String cvv) implements Serializable {
    }
}