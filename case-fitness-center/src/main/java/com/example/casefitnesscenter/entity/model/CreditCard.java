package com.example.casefitnesscenter.entity.model;

import com.example.casefitnesscenter.utils.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "credit_card")
public class CreditCard extends BaseEntity<String> {
    private String placeholder;
    private String cardNumber;
    private Date expiredDate;
    private Integer cvv;

    @OneToOne
    @MapsId
    private Member member;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        CreditCard that = (CreditCard) object;
        return Objects.equals(placeholder, that.placeholder)
               && Objects.equals(cardNumber, that.cardNumber)
               && Objects.equals(expiredDate, that.expiredDate)
               && Objects.equals(cvv, that.cvv);
    }

    @Override
    public void initialize() {
        if (this.getId() == null) {
            setId(UUID.randomUUID().toString());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), placeholder, cardNumber, expiredDate, cvv);
    }
}