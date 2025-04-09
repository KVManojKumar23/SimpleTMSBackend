package com.snipe.dto;

import com.snipe.entity.Employee.Status;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private Integer empId;
    private Integer userId; // Only store user ID instead of the full User entity
    private Integer orgId; // Store organization ID instead of full Organization entity
    private String designation;
    private String department;
    private LocalDate joiningDate;
    private LocalDate exitDate;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private String employeeId;
    private String reportingTo;
    private String notes;
}
