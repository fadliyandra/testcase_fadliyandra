package com.example.casefitnesscenter.entity.repository;


import com.example.casefitnesscenter.entity.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, String> {
}