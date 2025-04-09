package com.snipe.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.snipe.entity.Project;
import com.snipe.entity.Sprint;
import com.snipe.entity.Sprint.Status;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Integer> {

    // 🔹 Fetch all sprints by a specific project ID
    List<Sprint> findByProject_ProjectId(Integer projectId);

    // 🔹 Fetch sprints by project ID and status
    List<Sprint> findByProject_ProjectIdAndStatus(Integer projectId, Status status);

    // 🔹 Find all sprints by status
    List<Sprint> findByStatus(Status status);

    // 🔹 Find sprints within a date range
    List<Sprint> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    // 🔹 Find sprints starting before or ending after a certain date
    List<Sprint> findByEndDateBefore(LocalDate date);
    List<Sprint> findByStartDateAfter(LocalDate date);

    // 🔹 Find sprints that are currently active
    List<Sprint> findByStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
        Status status, LocalDate startDate, LocalDate endDate
    );

    // 🔹 Count total sprints for a project
    Long countByProject_ProjectId(Integer projectId);

    // 🔹 Get latest sprint by project (assumes ID increments with time)
    Sprint findTopByProject_ProjectIdOrderBySprintIdDesc(Integer projectId);

    Optional<Sprint> findTopByProjectProjectIdOrderByCreatedAtDesc(Integer projectId);
}

