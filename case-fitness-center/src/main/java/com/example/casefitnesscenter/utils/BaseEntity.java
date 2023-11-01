package com.example.casefitnesscenter.utils;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> implements Serializable {
    @Id
    private ID id;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, nullable = false)
    private Date createdAt = new Date(System.currentTimeMillis());

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @PrePersist
    public abstract void initialize();

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        if (!(o instanceof BaseEntity<?> that)) return false;
        if (!that.canEqual(this)) return false;

        return id != null && Objects.equals(id, that.id);
    }

    /**
     * This method is meant for allowing to redefine equality on several levels of the class hierarchy
     * while keeping its contract.
     *
     * @param other is the other object use in equality test.
     * @return if the other object can be equal to this object.
     * @see <a href="https://www.artima.com/articles/how-to-write-an-equality-method-in-java">More</a>
     */
    protected boolean canEqual(Object other) {
        return other instanceof BaseEntity;
    }
}
