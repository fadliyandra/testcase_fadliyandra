package com.example.casefitnesscenter.controller;


import com.example.casefitnesscenter.common.BaseResponse;
import com.example.casefitnesscenter.constant.ApiUrlConstant;
import com.example.casefitnesscenter.entity.dto.PaymentDto;
import com.example.casefitnesscenter.entity.dto.PaymentRequest;
import com.example.casefitnesscenter.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@Tag(name = "Payment Endpoint",
        description = "Add new Payment for customer order")
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiUrlConstant.PAYMENT_URL)
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Add payment",
            description = "Payment customer by order id")
    @PostMapping
    public ResponseEntity<BaseResponse<PaymentDto>> addPaymentSubscription(@Valid @RequestBody PaymentRequest request) {
        BaseResponse<PaymentDto> response = paymentService.creditPayment(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
