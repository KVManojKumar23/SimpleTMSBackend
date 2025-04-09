package com.snipe.dto;

import com.snipe.entity.SprintTask.PriorityLevel;
import com.snipe.entity.SprintTask.Status;
import com.snipe.entity.SprintTask.TaskType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SprintTaskDTO {

	private Long taskId;
    private Integer sprintId;        // Ref to Sprint
    private Integer assigneeId;      // Ref to User

    private String title;
    private String description;

    private TaskType type;           // Enum: TASK, BUG, STORY, etc.
    private Status status;           // Enum: TODO, DONE, etc.

    private Integer storyPoints;
    private PriorityLevel priority;  // Updated to use Enum instead of Integer

    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    private Integer hoursEstimate;
    private Integer hoursActual;

    private String ticketNumber;     // e.g., JIRA ticket
    private String pullRequestUrl;

    private Integer version;

    // Helper method to calculate remaining work
    private Integer remainingHours;
}

