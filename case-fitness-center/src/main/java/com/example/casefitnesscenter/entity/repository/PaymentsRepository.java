package com.example.casefitnesscenter.entity.repository;


import com.example.casefitnesscenter.entity.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository extends JpaRepository<Payment, String> {
    @Query("select (count(p) > 0) from Payment p where p.subscription.id = ?1")
    boolean existsPaymentByOrderId(String orderId);

    @Query("""
            SELECT py
            FROM Payment py
            WHERE py.subscription.id = ?1
            """)
    Payment findPaymentByHealthyProgramId(String orderId);
}