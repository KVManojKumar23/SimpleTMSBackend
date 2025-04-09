package com.snipe.dto;

import java.time.LocalDateTime;
import com.snipe.entity.Role.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    
    private Integer roleId;
    private RoleType roleName;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
}
