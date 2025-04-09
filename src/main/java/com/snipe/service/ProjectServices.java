package com.snipe.service;

import java.util.List;

import com.snipe.dto.ProjectDTO;

public interface ProjectServices {

	ProjectDTO createProject(ProjectDTO projectDTO);

	ProjectDTO getProjectById(Integer projectId);

	List<ProjectDTO> getAllProjects();

	List<ProjectDTO> getProjectsByOrganization(Integer orgId);

	void deleteProject(Integer projectId);

	// Additional services for better UI functionality
	ProjectDTO updateProject(Integer projectId, ProjectDTO projectDTO); // Update project details

	List<ProjectDTO> searchProjectsByName(String keyword); // Search projects by name

	List<ProjectDTO> getProjectsCreatedByUser(Integer userId); // Get projects created by a specific user

	long countProjectsByOrganization(Integer orgId); // Count projects under an organization

	List<ProjectDTO> getProjectsByStatus(String status); // e.g., Active,

	// ✅ Add these two methods
	List<ProjectDTO> getProjectsByManagerUserId(Integer userId); // Get projects where user is a manager

	List<ProjectDTO> getProjectsByEmployeeUserId(Integer userId); // Get projects where user is an employee

}
