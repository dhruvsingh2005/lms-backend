package com.arishi.lms_backend.entity;

import java.time.ZonedDateTime;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@MappedSuperclass
@Data
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    @Column(nullable = true)
    private ZonedDateTime updatedAt;

    @Column(nullable = true)
    private ZonedDateTime deletedAt;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = ZonedDateTime.now();
    }
}
