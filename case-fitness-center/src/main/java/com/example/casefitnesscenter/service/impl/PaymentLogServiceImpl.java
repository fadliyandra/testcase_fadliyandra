package com.example.casefitnesscenter.service.impl;

import com.example.casefitnesscenter.entity.repository.PaymentLogsRepository;
import com.example.casefitnesscenter.service.PaymentLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class PaymentLogServiceImpl implements PaymentLogService {

    private final PaymentLogsRepository repository;
    @Override
    public BigDecimal sumTotalAmountPayment() {

        return repository.sumAllPaymentAmount();
    }
}
