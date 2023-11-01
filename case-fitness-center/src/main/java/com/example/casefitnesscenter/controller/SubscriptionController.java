package com.example.casefitnesscenter.controller;


import com.example.casefitnesscenter.common.BaseResponse;
import com.example.casefitnesscenter.constant.ApiUrlConstant;
import com.example.casefitnesscenter.entity.dto.CanceledSubscriptionRequest;
import com.example.casefitnesscenter.entity.dto.MemberSubscribedRequest;
import com.example.casefitnesscenter.entity.dto.SubscriptionRequest;
import com.example.casefitnesscenter.entity.dto.SubscriptionResponse;
import com.example.casefitnesscenter.service.SubscriptionService;
import com.example.casefitnesscenter.service.impl.SubscriptionServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping(ApiUrlConstant.SUBSCRIPTION_URL)
@RequiredArgsConstructor
@RestController
public class SubscriptionController {

    private final SubscriptionService service;

    @GetMapping
    public BaseResponse<List<SubscriptionResponse>> getAllSubscription() {
        return service.findAllSubscription();
    }

    @PostMapping
    public BaseResponse<SubscriptionResponse> createSubscriptionMember(
            @Valid @RequestBody SubscriptionRequest request) {

        return service.createSubscriptionMember(request);
    }

    @GetMapping("/{subscriptionId}/payment-status")
    public BaseResponse<Void> memberSubscribed(@PathVariable String subscriptionId,
                                               @RequestBody MemberSubscribedRequest request) {
        return service.memberSubscribed(request.paymentStatus(), subscriptionId);
    }

    @PostMapping("member/{memberId}/cancel")
    public BaseResponse<String> memberCancelSubscribed(@PathVariable String memberId,
                                                       @RequestBody CanceledSubscriptionRequest request) {
        return service.memberCancelSubscribed(memberId, request);
    }
}
