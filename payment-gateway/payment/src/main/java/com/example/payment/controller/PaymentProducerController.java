package com.example.payment.controller;

import com.example.payment.service.PaymentProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/payments")
public class PaymentProducerController {
    private final PaymentProducer paymentProducer;

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> paymentCreditCard(@RequestBody CreditCardRequest request) {

        paymentProducer.sendMessage(request.creditCard);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "PROCESSING");

        return ResponseEntity.ok(response);
    }

    public record CreditCardRequest(String creditCard) {}

}
