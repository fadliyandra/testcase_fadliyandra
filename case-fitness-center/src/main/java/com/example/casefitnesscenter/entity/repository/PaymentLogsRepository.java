package com.example.casefitnesscenter.entity.repository;


import com.example.casefitnesscenter.entity.model.PaymentLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PaymentLogsRepository extends JpaRepository<PaymentLogs, String> {

    @Query("""
            SELECT SUM(pl.paymentAmount) FROM PaymentLogs pl
            """)
    BigDecimal sumAllPaymentAmount();
}