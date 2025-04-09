package com.snipe.service.serviceIMPL;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.snipe.Exception.ResourceNotFoundException;
import com.snipe.dto.ProjectDTO;
import com.snipe.entity.Organization;
import com.snipe.entity.Project;
import com.snipe.entity.User;
import com.snipe.repository.OrganizationRepository;
import com.snipe.repository.ProjectRepository;
import com.snipe.repository.UserRepository;
import com.snipe.service.ProjectServices;

@Service
public class ProjectServicesIMPL implements ProjectServices{
	
	private final ProjectRepository projectRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;

    public ProjectServicesIMPL(ProjectRepository projectRepository, OrganizationRepository organizationRepository,
                               UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
    }

    // Convert Entity to DTO
 // Convert Entity to DTO
    private ProjectDTO convertToDTO(Project project) {
        Set<Integer> managerIds = project.getManagers().stream()
                .map(User::getUserId).collect(Collectors.toSet());

        Set<Integer> employeeIds = project.getEmployees().stream()
                .map(User::getUserId).collect(Collectors.toSet());

        return new ProjectDTO(
            project.getProjectId(),
            project.getOrganization().getOrgId(),
            project.getProjectName(),
            project.getDescription(),
            project.getCreatedBy().getUserId(),
            project.getCreatedAt(),
            project.getUpdatedAt(),
            project.getStatus().name(),
            project.getStartDate(),
            project.getEndDate(),
            project.isDeleted(),
            managerIds,
            employeeIds
        );
    }


 // Convert DTO to Entity
    private Project convertToEntity(ProjectDTO dto) {
        Organization org = organizationRepository.findById(dto.getOrgId())
            .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        User creator = userRepository.findById(dto.getCreatedByUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Project project = new Project();
        project.setOrganization(org);
        project.setProjectName(dto.getProjectName());
        project.setDescription(dto.getDescription());
        project.setCreatedBy(creator);
        project.setStatus(Project.ProjectStatus.valueOf(dto.getStatus()));
        project.setStartDate(dto.getStartDate());
        project.setEndDate(dto.getEndDate());
        project.setDeleted(dto.isDeleted());

        // Set Managers
        if (dto.getManagerIds() != null) {
            Set<User> managers = dto.getManagerIds().stream()
                .map(id -> userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Manager ID not found: " + id)))
                .collect(Collectors.toSet());
            project.setManagers(managers);
        }

        // Set Employees
        if (dto.getEmployeeIds() != null) {
            Set<User> employees = dto.getEmployeeIds().stream()
                .map(id -> userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee ID not found: " + id)))
                .collect(Collectors.toSet());
            project.setEmployees(employees);
        }

        return project;
    }
    
    private Project updateProjectFromDTO(Project existingProject, ProjectDTO dto) {
        existingProject.setProjectName(dto.getProjectName());
        existingProject.setDescription(dto.getDescription());
        
        if (dto.getStatus() != null) {
            existingProject.setStatus(Project.ProjectStatus.valueOf(dto.getStatus()));
        }

        existingProject.setStartDate(dto.getStartDate());
        existingProject.setEndDate(dto.getEndDate());
        existingProject.setDeleted(dto.isDeleted());
        existingProject.setUpdatedAt(LocalDateTime.now());

        // Update managers
        if (dto.getManagerIds() != null && !dto.getManagerIds().isEmpty()) {
            Set<User> managers = dto.getManagerIds().stream()
                    .map(id -> userRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Manager not found with ID: " + id)))
                    .collect(Collectors.toSet());
            existingProject.setManagers(managers);
        }

        // Update employees
        if (dto.getEmployeeIds() != null && !dto.getEmployeeIds().isEmpty()) {
            Set<User> employees = dto.getEmployeeIds().stream()
                    .map(id -> userRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id)))
                    .collect(Collectors.toSet());
            existingProject.setEmployees(employees);
        }

        return existingProject;
    }

    // Create a new project
    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = convertToEntity(projectDTO);
        Project savedProject = projectRepository.save(project);
        return convertToDTO(savedProject);
    }

    // Get project by ID
    @Override
    public ProjectDTO getProjectById(Integer projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));
        return convertToDTO(project);
    }

    // Get all projects
    @Override
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Get projects by organization ID
    @Override
    public List<ProjectDTO> getProjectsByOrganization(Integer orgId) {
        List<Project> projects = projectRepository.findByOrganization_OrgId(orgId);
        return projects.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Delete a project
    @Override
    public void deleteProject(Integer projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Project not found with ID: " + projectId);
        }
        projectRepository.deleteById(projectId);
    }

    // Update an existing project
    @Override
    public ProjectDTO updateProject(Integer projectId, ProjectDTO projectDTO) {
        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        existingProject = updateProjectFromDTO(existingProject, projectDTO);
        Project updatedProject = projectRepository.save(existingProject);
        return convertToDTO(updatedProject);
    }

    // Search projects by name (partial match)
    @Override
    public List<ProjectDTO> searchProjectsByName(String keyword) {
        List<Project> projects = projectRepository.findByProjectNameContainingIgnoreCase(keyword);
        return projects.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Get all projects created by a specific user
    @Override
    public List<ProjectDTO> getProjectsCreatedByUser(Integer userId) {
        List<Project> projects = projectRepository.findByCreatedBy_UserId(userId);
        return projects.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Count projects under a specific organization
    @Override
    public long countProjectsByOrganization(Integer orgId) {
        return projectRepository.countByOrganization_OrgId(orgId);
    }

    @Override
    public List<ProjectDTO> getProjectsByStatus(String status) {
        try {
            Project.ProjectStatus projectStatus = Project.ProjectStatus.valueOf(status.toUpperCase());

            List<Project> projects = projectRepository.findByStatus(projectStatus);
            return projects.stream()
                    .map(this::convertToDTO)
                    .toList();

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status provided: " + status + ". Allowed values: ACTIVE, COMPLETED, ON_HOLD, CANCELLED");
        }
    }

    @Override
    public List<ProjectDTO> getProjectsByManagerUserId(Integer userId) {
        List<Project> projects = projectRepository.findByManagers_UserId(userId);
        return projects.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // ✅ Get projects by employee userId
    @Override
    public List<ProjectDTO> getProjectsByEmployeeUserId(Integer userId) {
        List<Project> projects = projectRepository.findByEmployees_UserId(userId);
        return projects.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

}
