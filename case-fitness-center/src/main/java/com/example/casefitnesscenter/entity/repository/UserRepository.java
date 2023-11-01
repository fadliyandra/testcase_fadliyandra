package com.example.casefitnesscenter.entity.repository;


import com.example.casefitnesscenter.entity.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<AppUser, String> {
    @Query("""
            select a from AppUser a
            where a.otpCode = ?1
            """)
    Optional<AppUser> findByOtpCode(Long otpCode);

    @Query("""
            select a from AppUser a
            where a.refreshToken = ?1
            """)
    Optional<AppUser> findByRefreshToken(String refreshToken);

    @Query("""
            select a from AppUser a
            where upper(a.member.email) like upper(?1)
            """)
    Optional<AppUser> findUserByEmail(String email);

    @Query("select (count(a) > 0) from AppUser a where a.member.email = ?1")
    boolean existsByEmail(String email);
}