package com.example.casefitnesscenter.entity.repository;


import com.example.casefitnesscenter.entity.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
    @Query("select s from Subscription s where s.member.id = ?1")
    List<Subscription> findByMemberId(String id);

    @Query("""
            select s from Subscription s
            inner join s.healthPrograms hp
            where s.member.id = ?1
            and hp.id = ?2
            """)
    List<Subscription> findSubscriptionHealthProgram(String memberId, String healthProgramId);

    @Query("""
            select (count(s) > 0) from Subscription s
            inner join s.healthPrograms hp
            where hp.id = ?1
            and s.member.id = ?2
            """)
    boolean existHealthPrograms(String healthProgramId, String memberId);
}