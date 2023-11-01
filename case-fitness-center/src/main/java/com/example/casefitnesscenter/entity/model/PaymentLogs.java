package com.example.casefitnesscenter.entity.model;

import com.example.casefitnesscenter.utils.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payment_log")
public class PaymentLogs extends BaseEntity<String> {
    private BigDecimal paymentAmount;
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    @Override
    public void initialize() {
        if (this.getId() == null) {
            setId(UUID.randomUUID().toString());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PaymentLogs that = (PaymentLogs) o;
        return Objects.equals(paymentAmount, that.paymentAmount)
               && Objects.equals(paymentDate, that.paymentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), paymentAmount, paymentDate);
    }
}
