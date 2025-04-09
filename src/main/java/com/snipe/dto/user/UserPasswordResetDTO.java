package com.snipe.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserPasswordResetDTO {
    @NotBlank(message = "Token is required")
    private String resetToken;
    
    @NotBlank(message = "New password is required")
    @Size(min = 8, max = 20, message = "Password must be 8-20 characters")
    private String newPassword;
    
    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;
}
