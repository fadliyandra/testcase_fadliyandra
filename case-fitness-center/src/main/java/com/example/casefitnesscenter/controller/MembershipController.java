package com.example.casefitnesscenter.controller;

import com.example.casefitnesscenter.common.BaseResponse;
import com.example.casefitnesscenter.constant.ApiUrlConstant;
import com.example.casefitnesscenter.entity.dto.MemberDto;
import com.example.casefitnesscenter.entity.dto.PasswordRequest;
import com.example.casefitnesscenter.entity.dto.SubscriptionResponse;
import com.example.casefitnesscenter.entity.dto.UpdateMemberDetailRequest;
import com.example.casefitnesscenter.service.MembershipService;
import com.example.casefitnesscenter.service.SubscriptionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RequiredArgsConstructor
@RequestMapping(ApiUrlConstant.MEMBERSHIP_URL)
@RestController
public class MembershipController {

    private final MembershipService membershipService;
    private final SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<MemberDto>>> getAllMembers() {
        var baseResponse = membershipService.findAllMember();
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/registration-status")
    public ResponseEntity<BaseResponse<String>> checkStatusMemberRegistration(@RequestParam String email) {
        var baseResponse = membershipService.checkStatusMemberRegistration(email);
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/{memberId}/subscriptions")
    public ResponseEntity<BaseResponse<List<SubscriptionResponse>>> getSubscriptionMember(@PathVariable String memberId) {
        var baseResponse = subscriptionService.findSubscriptionsMember(memberId);
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/{memberId}/subscriptions/{subscriptionId}")
    public ResponseEntity<BaseResponse<SubscriptionResponse>> reSubscribeMember(@PathVariable String memberId, @PathVariable String subscriptionId) {
        var baseResponse = subscriptionService.reSubscribeMember(memberId, subscriptionId);
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<BaseResponse<Void>> changePassword(@PathVariable String id,
                                                             @Valid @RequestBody PasswordRequest request) {
        var baseResponse = membershipService.changePassword(id, request);
        return ResponseEntity.ok(baseResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse<MemberDto>> updateMember(@PathVariable String id,
                                                                @Valid @RequestBody UpdateMemberDetailRequest request) {
        var baseResponse = membershipService.updateMember(id, request);
        return ResponseEntity.ok(baseResponse);
    }
}
