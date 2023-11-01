package com.example.casefitnesscenter.service;


import com.example.casefitnesscenter.common.BaseResponse;
import com.example.casefitnesscenter.entity.dto.PaymentDto;
import com.example.casefitnesscenter.entity.dto.PaymentRequest;
import com.example.casefitnesscenter.entity.model.Payment;
import com.example.casefitnesscenter.entity.model.Subscription;


import java.math.BigDecimal;

public interface PaymentService {

    Payment findPaymentId(String id);
    Payment addPayment(BigDecimal amount, Subscription subscription);
    BaseResponse<PaymentDto> creditPayment(PaymentRequest request);
}
