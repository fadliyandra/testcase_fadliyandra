package com.example.casefitnesscenter.entity.mapper;


import com.example.casefitnesscenter.entity.dto.MemberDto;
import com.example.casefitnesscenter.entity.dto.MembershipDetailsRequest;
import com.example.casefitnesscenter.entity.model.Member;

public interface MemberMapper {
    Member toEntity(MembershipDetailsRequest membershipDetailsRequest);

    MemberDto toDto(Member creditCard);
}