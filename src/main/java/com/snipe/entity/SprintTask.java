package com.snipe.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sprint_tasks",
       indexes = {
           @Index(name = "idx_task_sprint", columnList = "sprint_id"),
           @Index(name = "idx_task_status", columnList = "status"),
           @Index(name = "idx_task_assignee", columnList = "assignee_id")
       })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SprintTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sprint_id", nullable = false)
    private Sprint sprint;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private TaskType type = TaskType.TASK;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Status status = Status.TODO;

    @Column(nullable = false)
    private Integer storyPoints = 0;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private PriorityLevel priority = PriorityLevel.MEDIUM;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private LocalDateTime startedAt;

    @Column
    private LocalDateTime completedAt;

    @Column
    private Integer hoursEstimate;

    @Column
    private Integer hoursActual;

    @Column(length = 50)
    private String ticketNumber; // e.g., JIRA ticket ID

    @Column(length = 500)
    private String pullRequestUrl;

    @Version
    private Integer version; // For optimistic locking

    public enum TaskType {
        TASK,
        BUG,
        STORY,
        EPIC,
        SUBTASK
    }

    public enum Status {
        TODO,
        IN_PROGRESS,
        CODE_REVIEW,
        QA_TESTING,
        DONE,
        BLOCKED
    }

    public enum PriorityLevel {
        LOW,
        MEDIUM,
        HIGH,
        CRITICAL
    }

    @PrePersist
    protected void onCreate() {
        if (priority == null) {
            priority = PriorityLevel.MEDIUM;
        }
    }

    @PreUpdate
    protected void onStatusChange() {
        if (status == Status.IN_PROGRESS && startedAt == null) {
            startedAt = LocalDateTime.now();
        }
        if (status == Status.DONE && completedAt == null) {
            completedAt = LocalDateTime.now();
        }
    }

    // Helper method to calculate remaining work
    public Integer getRemainingHours() {
        return hoursEstimate != null && hoursActual != null ? 
               hoursEstimate - hoursActual : null;
    }
}
