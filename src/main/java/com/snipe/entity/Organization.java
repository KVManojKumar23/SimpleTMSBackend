package com.snipe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;



@Entity
@Table(name = "organizations", indexes = {
    @Index(name = "idx_org_name", columnList = "orgName"),
    @Index(name = "idx_org_email", columnList = "email"),
    @Index(name = "idx_org_phone", columnList = "phoneNumber")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orgId;

    @Column(unique = true, nullable = false, length = 100)
    private String orgName;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Column(length = 150)
    private String website;

    @Column(nullable = false, length = 50)
    private String industryType;

    @Column(nullable = false)
    private Boolean status = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(nullable = false, updatable = false, length = 50)
    private String createdBy;

    @Column(nullable = false, length = 50)
    private String updatedBy;

    @PreUpdate
    private void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    private void setCreationTimestamps() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }
}