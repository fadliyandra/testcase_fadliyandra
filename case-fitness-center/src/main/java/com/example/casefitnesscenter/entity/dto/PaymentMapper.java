package com.example.casefitnesscenter.entity.dto;


import com.example.casefitnesscenter.entity.model.Member;
import com.example.casefitnesscenter.entity.model.Payment;
import com.example.casefitnesscenter.entity.model.Subscription;
import com.example.casefitnesscenter.utils.DateConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;



@Component
public class PaymentMapper {
    public PaymentDto toDto(Payment payment) {
        LocalDateTime dateTime = DateConverter.toLocalDateTime(payment.getPaymentLogs().getPaymentDate());
        String date = DateConverter.formatDate(DateConverter.DATETIME_FORMAT, dateTime);

        Subscription subscription = payment.getSubscription();
        Member member = subscription.getMember();
        return PaymentDto.builder()
                .paymentId(payment.getId())
                .subscriptionId(subscription.getId())
                .memberId(member.getId())
                .memberName(member.getName())
                .paymentStatus(payment.getPaymentStatus().name())
                .paymentDate(date)
                .paymentAmount(payment.getAmount())
                .build();
    }
}
