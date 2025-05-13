package com.snipe.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles", 
       indexes = @Index(name = "idx_role_name", columnList = "roleName"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false, length = 20)
    private RoleType roleName;

    @Column(length = 255)
    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private LocalDateTime lastModifiedAt;

    public Role(RoleType roleName) {
        this.roleName = roleName;
        this.lastModifiedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedAt = LocalDateTime.now();
    }

    public enum RoleType {
        ORGANIZATION("Organization administrator"),
        HR("Human Resources"),
        ADMIN("Department admin"),
        MANAGER("Team manager"),
        EMPLOYEE("Regular employee"),
        USER("Basic user access");
        
        private final String description;
        
        RoleType(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
}