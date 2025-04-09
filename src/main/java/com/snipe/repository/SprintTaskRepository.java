package com.snipe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snipe.entity.SprintTask;
import com.snipe.entity.SprintTask.PriorityLevel;
import com.snipe.entity.SprintTask.Status;
import com.snipe.entity.SprintTask.TaskType;

public interface SprintTaskRepository  extends JpaRepository<SprintTask, Long>{

    //  Find tasks by sprint ID
    List<SprintTask> findBySprint_SprintId(Integer sprintId);

    //  Find tasks by sprint and status
    List<SprintTask> findBySprint_SprintIdAndStatus(Integer sprintId, Status status);

    //  Find tasks assigned to a specific user
    List<SprintTask> findByAssignee_UserId(Integer assigneeId);

    //  Find tasks by type (BUG, STORY, etc.)
    List<SprintTask> findByType(TaskType type);

    //  Count tasks by sprint
    Long countBySprint_SprintId(Integer sprintId);

    //  Count tasks by status and sprint
    Long countBySprint_SprintIdAndStatus(Integer sprintId, Status status);

    //  Find high priority tasks (priority 1 or 2)
    List<SprintTask> findByPriorityLessThanEqual(PriorityLevel priority);

    //  Find incomplete tasks (excluding DONE)
    List<SprintTask> findBySprint_SprintIdAndStatusNot(Integer sprintId, Status status);

    //  Find task by ticket number
    Optional<SprintTask> findByTicketNumber(String ticketNumber);
    
    // ✅ Correct method to find tasks by priority
    List<SprintTask> findByPriority(PriorityLevel priority);

}
