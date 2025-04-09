package com.snipe.dto.user;

import com.snipe.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer userId;
    private String fullName;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private User.Gender gender;
    private LocalDate dateOfBirth;
    private String imageUrl;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    
    // Nested DTOs for relationships
    private OrganizationDTO organization;
    private RoleDTO role;
    
    @Data
    public static class OrganizationDTO {
        private Integer orgId;
        private String orgName;
    }
    
    @Data
    public static class RoleDTO {
        private Integer roleId;
        private String roleName;
    }
}