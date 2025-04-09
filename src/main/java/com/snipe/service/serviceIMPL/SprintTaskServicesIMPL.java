package com.snipe.service.serviceIMPL;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.snipe.dto.SprintTaskDTO;
import com.snipe.entity.Sprint;
import com.snipe.entity.SprintTask;
import com.snipe.entity.SprintTask.PriorityLevel;
import com.snipe.entity.SprintTask.TaskType;
import com.snipe.entity.User;
import com.snipe.repository.SprintRepository;
import com.snipe.repository.SprintTaskRepository;
import com.snipe.service.SprintTaskServices;

import jakarta.transaction.Transactional;

@Service
public class SprintTaskServicesIMPL implements SprintTaskServices{
	
	private final SprintTaskRepository sprintTaskRepository;	
	
	public SprintTaskServicesIMPL(SprintTaskRepository sprintTaskRepository, SprintRepository sprintRepository) {
		super();
		this.sprintTaskRepository = sprintTaskRepository;
	}

	public SprintTaskDTO convertToDTO(SprintTask sprintTask) {
	    if (sprintTask == null) return null;

	    return SprintTaskDTO.builder()
	            .taskId(sprintTask.getTaskId())
	            .sprintId(sprintTask.getSprint() != null ? sprintTask.getSprint().getSprintId() : null)
	            .assigneeId(sprintTask.getAssignee() != null ? sprintTask.getAssignee().getUserId() : null)
	            .title(sprintTask.getTitle())
	            .description(sprintTask.getDescription())
	            .type(sprintTask.getType())
	            .status(sprintTask.getStatus())
	            .storyPoints(sprintTask.getStoryPoints())
	            .priority(sprintTask.getPriority())
	            .createdAt(sprintTask.getCreatedAt())
	            .startedAt(sprintTask.getStartedAt())
	            .completedAt(sprintTask.getCompletedAt())
	            .hoursEstimate(sprintTask.getHoursEstimate())
	            .hoursActual(sprintTask.getHoursActual())
	            .ticketNumber(sprintTask.getTicketNumber())
	            .pullRequestUrl(sprintTask.getPullRequestUrl())
	            .version(sprintTask.getVersion())
	            // Calculate remaining hours dynamically
	            .remainingHours((sprintTask.getHoursEstimate() != null && sprintTask.getHoursActual() != null)
	                    ? sprintTask.getHoursEstimate() - sprintTask.getHoursActual() : null)
	            .build();
	}

	public SprintTask convertToEntity(SprintTaskDTO dto) {
	    if (dto == null) return null;

	    Sprint sprint = (dto.getSprintId() != null) ? Sprint.builder().sprintId(dto.getSprintId()).build() : null;
	    User assignee = (dto.getAssigneeId() != null) ? User.builder().userId(dto.getAssigneeId()).build() : null;

	    return SprintTask.builder()
	            .taskId(dto.getTaskId())
	            .sprint(sprint)
	            .assignee(assignee)
	            .title(dto.getTitle())
	            .description(dto.getDescription())
	            .type(dto.getType())
	            .status(dto.getStatus())
	            .storyPoints(dto.getStoryPoints())
	            .priority(dto.getPriority())
	            .createdAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now())
	            .startedAt(dto.getStartedAt())
	            .completedAt(dto.getCompletedAt())
	            .hoursEstimate(dto.getHoursEstimate())
	            .hoursActual(dto.getHoursActual())
	            .ticketNumber(dto.getTicketNumber())
	            .pullRequestUrl(dto.getPullRequestUrl())
	            .version(dto.getVersion())
	            .build();
	}

    @Override
    @Transactional
    public SprintTaskDTO createTask(SprintTaskDTO sprintTaskDTO) {
        SprintTask sprintTask = convertToEntity(sprintTaskDTO);
        sprintTask.setCreatedAt(LocalDateTime.now());
        SprintTask savedTask = sprintTaskRepository.save(sprintTask);
        return convertToDTO(savedTask);
    }

    @Override
    @Transactional
    public SprintTaskDTO updateTask(Long taskId, SprintTaskDTO sprintTaskDTO) {
        SprintTask existingTask = sprintTaskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        existingTask.setTitle(sprintTaskDTO.getTitle());
        existingTask.setDescription(sprintTaskDTO.getDescription());
        existingTask.setType(sprintTaskDTO.getType());
        existingTask.setStatus(sprintTaskDTO.getStatus());
        existingTask.setStoryPoints(sprintTaskDTO.getStoryPoints());
        existingTask.setPriority(sprintTaskDTO.getPriority());
        existingTask.setStartedAt(sprintTaskDTO.getStartedAt());
        existingTask.setCompletedAt(sprintTaskDTO.getCompletedAt());
        existingTask.setHoursEstimate(sprintTaskDTO.getHoursEstimate());
        existingTask.setHoursActual(sprintTaskDTO.getHoursActual());
        existingTask.setTicketNumber(sprintTaskDTO.getTicketNumber());
        existingTask.setPullRequestUrl(sprintTaskDTO.getPullRequestUrl());
        existingTask.setVersion(sprintTaskDTO.getVersion());

        SprintTask updatedTask = sprintTaskRepository.save(existingTask);
        return convertToDTO(updatedTask);
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId) {
        sprintTaskRepository.deleteById(taskId);
    }

    @Override
    public SprintTaskDTO getTaskById(Long taskId) {
        return sprintTaskRepository.findById(taskId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @Override
    public List<SprintTaskDTO> getAllTasks() {
        return sprintTaskRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SprintTaskDTO> getTasksBySprintId(Integer sprintId) {
        return sprintTaskRepository.findBySprint_SprintId(sprintId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SprintTaskDTO> getTasksBySprintIdAndStatus(Integer sprintId, SprintTask.Status status) {
        return sprintTaskRepository.findBySprint_SprintIdAndStatus(sprintId, status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SprintTaskDTO> getTasksByAssigneeId(Integer assigneeId) {
        return sprintTaskRepository.findByAssignee_UserId(assigneeId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SprintTaskDTO> getTasksByType(TaskType type) {
        return sprintTaskRepository.findByType(type).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SprintTaskDTO> getHighPriorityTasks() {
        return sprintTaskRepository.findByPriority(PriorityLevel.HIGH).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long countTasksBySprintIdAndStatus(Integer sprintId, SprintTask.Status status) {
        return sprintTaskRepository.countBySprint_SprintIdAndStatus(sprintId, status);
    }

    @Override
    public SprintTaskDTO getTaskByTicketNumber(String ticketNumber) {
        return sprintTaskRepository.findByTicketNumber(ticketNumber)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @Override
    public List<SprintTaskDTO> getIncompleteTasksBySprint(Integer sprintId) {
        return sprintTaskRepository.findBySprint_SprintIdAndStatusNot(sprintId, SprintTask.Status.DONE).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
