package com.example.casefitnesscenter.service;



import com.example.casefitnesscenter.common.BaseResponse;
import com.example.casefitnesscenter.entity.dto.CanceledSubscriptionRequest;
import com.example.casefitnesscenter.entity.dto.SubscriptionRequest;
import com.example.casefitnesscenter.entity.dto.SubscriptionResponse;

import java.util.List;

public interface SubscriptionService {
    BaseResponse<List<SubscriptionResponse>> findAllSubscription();

    BaseResponse<SubscriptionResponse> createSubscriptionMember(SubscriptionRequest request);

    BaseResponse<List<SubscriptionResponse>> findSubscriptionsMember(String memberId);

    BaseResponse<SubscriptionResponse> reSubscribeMember(String memberId, String healthProgramId);

    /***
     * When the payment is successful then change the subscription status to SUBSCRIBE
     * @param paymentStatus for the subscription
     * @param subscriptionId for check and validate the subscription by id
     * */
    BaseResponse<Void> memberSubscribed(String paymentStatus, String subscriptionId);

    BaseResponse<String> memberCancelSubscribed(String memberId, CanceledSubscriptionRequest request);
}
