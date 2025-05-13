package com.snipe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.snipe.dto.user.UserDTO.RoleDTO;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_user_email", columnList = "email"),
    @Index(name = "idx_user_phone", columnList = "phoneNumber")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "org_id")
    private Organization organization;
    
    @Column(nullable = false, length = 50)
    private String firstName;
    
    @Column(nullable = false, length = 50)
    private String lastName;
    
    @Deprecated // Marked as deprecated since we'll use firstName + lastName
    @Column(length = 100)
    private String fullName; // Kept for backward compatibility
    
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    
    @Column(length = 60) // For BCrypt hashed passwords
    private String password;
    
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column
    private LocalDateTime lastModifiedAt;
    
    @Column
    private String imageUrl;
    
    @Column(unique = true, length = 20)
    private String phoneNumber;
    
    @Column(length = 255)
    private String address;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;
    
    private LocalDate dateOfBirth;

    private LocalDateTime lastLogin;
    
    @Column(length = 100)
    private String resetToken;

    private LocalDateTime resetTokenExpiry;
    
    private boolean active = true;

    // Helper method to get full name
    public String getFullName() {
        return  
               firstName + " " + lastName;
    }

    @PrePersist
    @PreUpdate
    private void updateFullName() {
        this.fullName = getFullName();
        this.lastModifiedAt = LocalDateTime.now();
        this.lastLogin = LocalDateTime.now();
    }

    public enum Gender {
        MALE, FEMALE, OTHER
    }
    
}