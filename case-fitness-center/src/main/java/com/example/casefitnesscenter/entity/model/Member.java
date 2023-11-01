package com.example.casefitnesscenter.entity.model;

import com.example.casefitnesscenter.utils.BaseEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "member", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class Member extends BaseEntity<String> {

    private String name;

    private String email;

    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "member")
    @PrimaryKeyJoinColumn
    private CreditCard creditCard;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatusMembership statusMembership = StatusMembership.UNREGISTERED;

    @OneToOne(
            mappedBy = "member",
            optional = false
    )
    @JoinColumn(name = "app_user_id", foreignKey = @ForeignKey(name = "app_user_fk_id"))
    private AppUser appUser;

    @OneToMany(
            mappedBy = "member",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Subscription> subscriptions = new LinkedHashSet<>();

    @Override
    public void initialize() {
        if (this.getId() == null) {
            setId(UUID.randomUUID().toString());
        }
        this.email = getEmail() == null ? null : getEmail().toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Member member = (Member) o;
        return Objects.equals(name, member.name)
               && Objects.equals(email, member.email)
               && Objects.equals(phoneNumber, member.phoneNumber)
               && Objects.equals(creditCard, member.creditCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, email, phoneNumber, creditCard);
    }
}
