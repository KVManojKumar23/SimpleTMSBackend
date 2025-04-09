package com.snipe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sprints", 
       indexes = {
           @Index(name = "idx_sprint_project", columnList = "project_id"),
           @Index(name = "idx_sprint_dates", columnList = "startDate,endDate"),
           @Index(name = "idx_sprint_status", columnList = "status")
       })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sprintId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    
    @Column(nullable = false, length = 100)
    private String sprintName;
    
    @Column(nullable = false)
    private LocalDate startDate;
    
    @Column(nullable = false)
    private LocalDate endDate;
    
    @Column(length = 500)
    private String goal;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Status status = Status.PLANNED;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column
    private LocalDateTime completedAt;
    
    @Column
    private Integer velocity; // Team's velocity in this sprint
    
    @OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL)
    private List<SprintTask> tasks = new ArrayList<>();
    
    @PrePersist
    protected void validateDates() {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
    }
    
    public boolean isActive() {
        LocalDate today = LocalDate.now();
        return status == Status.IN_PROGRESS 
               && !today.isBefore(startDate) 
               && !today.isAfter(endDate);
    }
    
    public enum Status {
        PLANNED,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED
    }
}