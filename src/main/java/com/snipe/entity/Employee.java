package com.snipe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "employees", 
       indexes = {
           @Index(name = "idx_employee_user", columnList = "user_id"),
           @Index(name = "idx_employee_org", columnList = "org_id"),
           @Index(name = "idx_employee_status", columnList = "status")
       })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer empId;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization organization;
    
    @Column(nullable = false, length = 100)
    private String designation;
    
    @Column(length = 100)
    private String department;
    
    @Column(nullable = false)
    private LocalDate joiningDate;
    
    private LocalDate exitDate;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status = Status.ACTIVE;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column
    private LocalDateTime lastModifiedAt;
    
    @Column(length = 50)
    private String employeeId; // Company-assigned ID
    
    @Column(length = 50)
    private String reportingTo;
    
    @Column(length = 255)
    private String notes;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        lastModifiedAt = createdAt;
    }
    
    @PreUpdate
    protected void onUpdate() {
        lastModifiedAt = LocalDateTime.now();
    }
    
    public enum Status {
        ACTIVE,
        INACTIVE,
        ON_LEAVE,
        TERMINATED,
        RESIGNED
    }
}