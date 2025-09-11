package com.ToDo_App.data.models;

import jakarta.persistence.Column;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

public class BaseEntity {
    @CreatedDate
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "created_by",updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(name = "updated_at",updatable = false)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(name = "updated_by",updatable = false)
    private String updatedBy;
}
