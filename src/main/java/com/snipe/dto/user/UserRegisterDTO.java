package com.snipe.dto.user;

import com.snipe.entity.User;
import lombok.Data;
import java.time.LocalDate;

@Data
public class UserRegisterDTO {

    private Integer userId;  // Should be null for new registration
    private String firstName;
    private String lastName;
    private String fullName;  // Will be constructed from firstName + lastName
    private String email;
    private String password;
    private String confirmPassword;
    private String phoneNumber;
    private String address;
    private User.Gender gender;  // Optional
    private LocalDate dateOfBirth;
    private Integer orgID;

    // Auto-generate full name
    public String getFullName() {
        return (firstName != null && lastName != null) 
               ? firstName + " " + lastName 
               : null;
    }

    // Security clear method
    public void clearSensitiveData() {
        this.password = null;
        this.confirmPassword = null;
    }
}
