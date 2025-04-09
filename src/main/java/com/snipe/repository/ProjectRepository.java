package com.snipe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.snipe.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

	// Fetch all projects belonging to a specific organization
	List<Project> findByOrganization_OrgId(Integer orgId);

	// Fetch all projects created by a specific user
	List<Project> findByCreatedBy_UserId(Integer userId);
	
	List<Project> findByManagers_UserId(Integer userId);

	List<Project> findByEmployees_UserId(Integer userId);

	// Search projects by name containing a keyword (case insensitive)
	List<Project> findByProjectNameContainingIgnoreCase(String keyword);

	// Count projects under a specific organization
	long countByOrganization_OrgId(Integer orgId);
	
	List<Project> findByStatus(Project.ProjectStatus status);

}
