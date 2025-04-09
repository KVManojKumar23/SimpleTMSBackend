package com.snipe.dto;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    private Integer projectId;

    private Integer orgId;            // To reference Organization

    private String projectName;

    private String description;

    private Integer createdByUserId;  // To reference User

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String status;            // ACTIVE, COMPLETED, etc.

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private boolean isDeleted;
    
    
    private Set<Integer> managerIds;
    private Set<Integer> employeeIds;
}