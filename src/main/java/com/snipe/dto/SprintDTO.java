package com.snipe.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SprintDTO {

    private Integer sprintId;
    private Integer projectId; // only the ID for the project to avoid nested object in DTO
    private String sprintName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String goal;
    private String status; // Use String or Enum if you define it in DTO
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private Integer velocity;
    private List<Long> taskIds; // Just storing task IDs (optional)
}