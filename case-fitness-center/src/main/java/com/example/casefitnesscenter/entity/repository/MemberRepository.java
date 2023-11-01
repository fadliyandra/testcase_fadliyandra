package com.example.casefitnesscenter.entity.repository;



import com.example.casefitnesscenter.entity.model.Member;
import com.example.casefitnesscenter.entity.model.StatusMembership;
import com.example.casefitnesscenter.entity.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, String> {
    @Query("""
            select m from Member m
            where upper(m.email) like upper(?1)
            """)
    Optional<Member> findUserByEmail(String email);

    Page<Member> findBy(Pageable pageable);

    @Query("""
            select sub from Member m
            inner join m.subscriptions sub
            inner join sub.healthPrograms programs
            where sub.member.id = :memberId
            and programs.id = :programId
            """)
    List<Subscription> findSubscriptionMember(@Param("memberId") String memberId,
                                              @Param("programId") String programId);

    @Query("""
            select m.statusMembership from Member m
            where m.email = ?1
            """)
    StatusMembership findStatusMembership(String email);

    @Query("""
            select m from Member m
            where upper(m.email) like upper(:email)
            or upper(m.name) like upper(:name)
            """)
    long countByField(@Param("email") String emailLike, @Param("name") String nameLike);

    @Query("select (count(a) > 0) from Member a where a.email = ?1")
    boolean existsByEmail(String email);
}