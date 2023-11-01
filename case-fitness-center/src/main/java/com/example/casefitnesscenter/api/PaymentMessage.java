package com.example.casefitnesscenter.api;


import com.example.casefitnesscenter.config.OpenFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${payment.name}", url = "${payment.url}", configuration = OpenFeignConfig.class)
public interface PaymentMessage {

    @PostMapping("/api/v2/payments")
    void sendPayment(@RequestBody CreditCardRequest request);

}
