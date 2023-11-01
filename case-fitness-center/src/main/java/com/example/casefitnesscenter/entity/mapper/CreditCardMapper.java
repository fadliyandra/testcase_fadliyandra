package com.example.casefitnesscenter.entity.mapper;


import com.example.casefitnesscenter.entity.dto.MembershipDetailsRequest;
import com.example.casefitnesscenter.entity.model.CreditCard;
import com.example.casefitnesscenter.entity.model.Member;

public interface CreditCardMapper {
    CreditCard toEntity(MembershipDetailsRequest.CreditCardDto creditCardDto, Member member);

    MembershipDetailsRequest.CreditCardDto toDto(CreditCard creditCard);
}