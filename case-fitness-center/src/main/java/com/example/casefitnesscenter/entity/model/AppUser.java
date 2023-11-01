package com.example.casefitnesscenter.entity.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.*;


@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class AppUser implements UserDetails {
    @Id
    private String userId;

    @Size(min = 6, message = "Password length should be at least 6 characters")
    private String password;

    @Enumerated(EnumType.STRING)
    private ERole eRole = ERole.USER;

    @Column(length = 6)
    private Long otpCode;

    @Temporal(TemporalType.TIMESTAMP)
    private Date otpRequestedTime;

    @Column(unique = true)
    private String refreshToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresRefreshToken;

    private Boolean isCredentialsNonExpired;

    private Boolean isEnabled;

    @OneToOne(
            cascade = CascadeType.ALL,
            optional = false,
            orphanRemoval = true)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "member_fk_id"))
    private Member member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(eRole.getRoleName()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @PrePersist
    @PreUpdate
    private void prepareData() {
        if (userId == null || userId.isEmpty()) {
            setUserId(UUID.randomUUID().toString());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AppUser appUser = (AppUser) o;
        return userId != null && Objects.equals(userId, appUser.getUserId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
