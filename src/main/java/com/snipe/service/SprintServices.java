package com.snipe.service;

import com.snipe.dto.SprintDTO;
import com.snipe.entity.Sprint;

import java.time.LocalDate;
import java.util.List;

public interface SprintServices {

    // 🔹 Create a new sprint
    SprintDTO createSprint(SprintDTO sprintDto);

    // 🔹 Update sprint by ID
    SprintDTO updateSprint(Integer sprintId, SprintDTO sprintDto);

    // 🔹 Delete sprint by ID
    void deleteSprint(Integer sprintId);

    // 🔹 Get sprint by ID
    SprintDTO getSprintById(Integer sprintId);

    // 🔹 Get all sprints
    List<SprintDTO> getAllSprints();

    // 🔹 Get sprints by project ID
    List<SprintDTO> getSprintsByProjectId(Integer projectId);

    // 🔹 Get sprints by project and status
    List<SprintDTO> getSprintsByProjectIdAndStatus(Integer projectId, Sprint.Status status);

    // 🔹 Get sprints by status
    List<SprintDTO> getSprintsByStatus(Sprint.Status status);

    // 🔹 Get sprints between dates
    List<SprintDTO> getSprintsBetweenDates(LocalDate start, LocalDate end);

    // 🔹 Get active sprints
    List<SprintDTO> getActiveSprints();

    // 🔹 Get count of sprints for a project
    Long countSprintsByProjectId(Integer projectId);

    // 🔹 Get the latest sprint for a project
    SprintDTO getLatestSprintByProjectId(Integer projectId);
}
