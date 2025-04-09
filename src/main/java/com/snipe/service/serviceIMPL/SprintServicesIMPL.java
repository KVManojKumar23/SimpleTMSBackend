package com.snipe.service.serviceIMPL;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.snipe.dto.SprintDTO;
import com.snipe.entity.Project;
import com.snipe.entity.Sprint;
import com.snipe.entity.Sprint.Status;
import com.snipe.repository.ProjectRepository;
import com.snipe.repository.SprintRepository;
import com.snipe.service.SprintServices;

@Service
public class SprintServicesIMPL implements SprintServices{
	
	 private final SprintRepository sprintRepository;
	    private final ProjectRepository projectRepository;

	    public SprintServicesIMPL(SprintRepository sprintRepository, ProjectRepository projectRepository) {
	        this.sprintRepository = sprintRepository;
	        this.projectRepository = projectRepository;
	    }
	    
	 // ✅ Convert Entity to DTO
	    private SprintDTO convertToDTO(Sprint sprint) {
	        List<Long> taskIds = sprint.getTasks() != null
	                ? sprint.getTasks().stream()
	                    .map(task -> task.getTaskId()) // assuming SprintTask has getTaskId()
	                    .collect(Collectors.toList())
	                : null;

	        return SprintDTO.builder()
	                .sprintId(sprint.getSprintId())
	                .projectId(sprint.getProject().getProjectId())
	                .sprintName(sprint.getSprintName())
	                .startDate(sprint.getStartDate())
	                .endDate(sprint.getEndDate())
	                .goal(sprint.getGoal())
	                .status(sprint.getStatus().name()) // converting Enum to String
	                .createdAt(sprint.getCreatedAt())
	                .completedAt(sprint.getCompletedAt())
	                .velocity(sprint.getVelocity())
	                .taskIds(taskIds)
	                .build();
	    }

	    // ✅ Convert DTO to Entity
	    private Sprint convertToEntity(SprintDTO dto) {
	        if (dto.getSprintName() == null || dto.getSprintName().trim().isEmpty()) {
	            throw new IllegalArgumentException("Sprint name cannot be empty");
	        }

	        Project project = projectRepository.findById(dto.getProjectId())
	                .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + dto.getProjectId()));

	        return Sprint.builder()
	                .sprintId(dto.getSprintId())
	                .project(project)
	                .sprintName(dto.getSprintName())
	                .startDate(dto.getStartDate())
	                .endDate(dto.getEndDate())
	                .goal(dto.getGoal())
	                .status(Status.valueOf(dto.getStatus())) // converting String to Enum
	                .createdAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : java.time.LocalDateTime.now())
	                .completedAt(dto.getCompletedAt())
	                .velocity(dto.getVelocity())
	                .build();
	    }

	    @Override
	    public SprintDTO createSprint(SprintDTO sprintDto) {
	        Sprint sprint = convertToEntity(sprintDto);
	        Sprint savedSprint = sprintRepository.save(sprint);
	        return convertToDTO(savedSprint);
	    }


		@Override
		public SprintDTO updateSprint(Integer sprintId, SprintDTO sprintDto) {
		    Sprint existingSprint = sprintRepository.findById(sprintId)
		            .orElseThrow(() -> new IllegalArgumentException("Sprint not found with ID: " + sprintId));

		    Sprint updatedSprint = convertToEntity(sprintDto);
		    updatedSprint.setSprintId(existingSprint.getSprintId()); // keep the same ID
		    Sprint saved = sprintRepository.save(updatedSprint);
		    return convertToDTO(saved);
		}

		@Override
		public void deleteSprint(Integer sprintId) {
		    if (!sprintRepository.existsById(sprintId)) {
		        throw new IllegalArgumentException("Sprint not found with ID: " + sprintId);
		    }
		    sprintRepository.deleteById(sprintId);
		}

		@Override
		public SprintDTO getSprintById(Integer sprintId) {
		    Sprint sprint = sprintRepository.findById(sprintId)
		            .orElseThrow(() -> new IllegalArgumentException("Sprint not found with ID: " + sprintId));
		    return convertToDTO(sprint);
		}

		@Override
		public List<SprintDTO> getAllSprints() {
		    return sprintRepository.findAll().stream()
		            .map(this::convertToDTO)
		            .collect(Collectors.toList());
		}

		@Override
		public List<SprintDTO> getSprintsByProjectId(Integer projectId) {
		    return sprintRepository.findByProject_ProjectId(projectId).stream()
		            .map(this::convertToDTO)
		            .collect(Collectors.toList());
		}
		
		@Override
		public List<SprintDTO> getSprintsBetweenDates(LocalDate start, LocalDate end) {
		    return sprintRepository.findByStartDateBetween(start, end).stream()
		            .map(this::convertToDTO)
		            .collect(Collectors.toList());
		}

		@Override
		public List<SprintDTO> getActiveSprints() {
		    return sprintRepository.findAll().stream()
		            .filter(Sprint::isActive)
		            .map(this::convertToDTO)
		            .collect(Collectors.toList());
		}

		@Override
		public Long countSprintsByProjectId(Integer projectId) {
		    return sprintRepository.countByProject_ProjectId(projectId);
		}

		@Override
		public SprintDTO getLatestSprintByProjectId(Integer projectId) {
		    Sprint sprint = sprintRepository.findTopByProjectProjectIdOrderByCreatedAtDesc(projectId)
		            .orElseThrow(() -> new IllegalArgumentException("No sprints found for project ID: " + projectId));
		    return convertToDTO(sprint);
		}

		@Override
		public List<SprintDTO> getSprintsByProjectIdAndStatus(Integer projectId, Status status) {
		    return sprintRepository.findByProject_ProjectIdAndStatus(projectId, status).stream()
		            .map(this::convertToDTO)
		            .collect(Collectors.toList());
		}

		@Override
		public List<SprintDTO> getSprintsByStatus(Status status) {
		    return sprintRepository.findByStatus(status).stream()
		            .map(this::convertToDTO)
		            .collect(Collectors.toList());
		}

	    

}
