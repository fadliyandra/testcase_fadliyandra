package com.example.casefitnesscenter.entity.model;

import com.example.casefitnesscenter.entity.PaymentStatus;
import com.example.casefitnesscenter.utils.BaseEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment extends BaseEntity<String> {
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "subscription_id",
            foreignKey = @ForeignKey(name = "subscription_fk_id")
    )
    private Subscription subscription;

    private PaymentStatus paymentStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    @Column(name = "amount")
    private BigDecimal amount = BigDecimal.ZERO;

    @ManyToOne(targetEntity = PaymentLogs.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "payment_logs_id",
            foreignKey = @ForeignKey(name = "payment_logs_fk_id"))
    private PaymentLogs paymentLogs;

    @Override
    public void initialize() {
        if (getId() != null) {
            return;
        }
        setId(RandomStringUtils.randomAlphanumeric(16));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Payment payment = (Payment) o;
        return Objects.equals(subscription, payment.subscription)
               && Objects.equals(paymentDate, payment.paymentDate)
               && Objects.equals(amount, payment.amount)
               && Objects.equals(paymentLogs, payment.paymentLogs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subscription, paymentDate, amount, paymentLogs);
    }
}
