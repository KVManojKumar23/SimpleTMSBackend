package com.snipe.service;

import java.util.List;

import com.snipe.dto.SprintTaskDTO;
import com.snipe.entity.SprintTask.Status;
import com.snipe.entity.SprintTask.TaskType;

public interface SprintTaskServices {

    // ✅ Create a new Sprint Task
    SprintTaskDTO createTask(SprintTaskDTO sprintTaskDTO);

    // ✅ Update an existing Sprint Task
    SprintTaskDTO updateTask(Long taskId, SprintTaskDTO sprintTaskDTO);

    // ✅ Delete a task by ID
    void deleteTask(Long taskId);

    // ✅ Get a task by ID
    SprintTaskDTO getTaskById(Long taskId);

    // ✅ List all tasks
    List<SprintTaskDTO> getAllTasks();

    // ✅ Get tasks by sprint ID
    List<SprintTaskDTO> getTasksBySprintId(Integer sprintId);

    // ✅ Get tasks by sprint and status
    List<SprintTaskDTO> getTasksBySprintIdAndStatus(Integer sprintId, Status status);

    // ✅ Get tasks assigned to a user
    List<SprintTaskDTO> getTasksByAssigneeId(Integer assigneeId);

    // ✅ Get tasks by type (Bug, Story, Epic, etc.)
    List<SprintTaskDTO> getTasksByType(TaskType type);

    // ✅ Get high priority tasks (priority <= 2)
    List<SprintTaskDTO> getHighPriorityTasks();

    // ✅ Count tasks by status in a sprint
    Long countTasksBySprintIdAndStatus(Integer sprintId, Status status);

    // ✅ Get tasks by ticket number
    SprintTaskDTO getTaskByTicketNumber(String ticketNumber);

    // ✅ Get incomplete tasks (status != DONE)
    List<SprintTaskDTO> getIncompleteTasksBySprint(Integer sprintId);
}
