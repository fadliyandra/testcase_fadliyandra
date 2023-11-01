package com.example.casefitnesscenter.service;



import com.example.casefitnesscenter.common.BaseResponse;
import com.example.casefitnesscenter.entity.dto.MemberDto;
import com.example.casefitnesscenter.entity.dto.MembershipDetailsRequest;
import com.example.casefitnesscenter.entity.dto.PasswordRequest;
import com.example.casefitnesscenter.entity.dto.UpdateMemberDetailRequest;
import com.example.casefitnesscenter.entity.model.Member;
import com.example.casefitnesscenter.entity.model.Subscription;

import java.util.List;

public interface MembershipService {
    BaseResponse<List<MemberDto>> findAllMember();

    Member getMemberById(String id);

    Subscription findSubscriptionProgram(String memberId, String programId);

    BaseResponse<MemberDto> registration(MembershipDetailsRequest request);

    BaseResponse<MemberDto> updateMember(String id, UpdateMemberDetailRequest request);

    BaseResponse<String> checkStatusMemberRegistration(String email);

    BaseResponse<Void> changePassword(String id, PasswordRequest request);
}
