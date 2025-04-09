package com.snipe.controller;
import com.snipe.dto.ProjectDTO;
import com.snipe.service.ProjectServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectServices projectServices;

    public ProjectController(ProjectServices projectServices) {
        this.projectServices = projectServices;
    }

    // Create a new project
    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        ProjectDTO createdProject = projectServices.createProject(projectDTO);
        return ResponseEntity.ok(createdProject);
    }

    // Get project by ID
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Integer projectId) {
        ProjectDTO project = projectServices.getProjectById(projectId);
        return ResponseEntity.ok(project);
    }

    // Get all projects
    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<ProjectDTO> projects = projectServices.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    // Get projects by organization ID
    @GetMapping("/organization/{orgId}")
    public ResponseEntity<List<ProjectDTO>> getProjectsByOrganization(@PathVariable Integer orgId) {
        List<ProjectDTO> projects = projectServices.getProjectsByOrganization(orgId);
        return ResponseEntity.ok(projects);
    }

    // Delete a project
    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable Integer projectId) {
        projectServices.deleteProject(projectId);
        return ResponseEntity.ok("Project deleted successfully");
    }

    // Update an existing project
    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Integer projectId, @RequestBody ProjectDTO projectDTO) {
        ProjectDTO updatedProject = projectServices.updateProject(projectId, projectDTO);
        return ResponseEntity.ok(updatedProject);
    }

    // Search projects by name (partial match)
    @GetMapping("/search")
    public ResponseEntity<List<ProjectDTO>> searchProjectsByName(@RequestParam String keyword) {
        List<ProjectDTO> projects = projectServices.searchProjectsByName(keyword);
        return ResponseEntity.ok(projects);
    }

    // Get all projects created by a specific user
    @GetMapping("/createdBy/{userId}")
    public ResponseEntity<List<ProjectDTO>> getProjectsCreatedByUser(@PathVariable Integer userId) {
        List<ProjectDTO> projects = projectServices.getProjectsCreatedByUser(userId);
        return ResponseEntity.ok(projects);
    }

    // Count projects under a specific organization
    @GetMapping("/count/{orgId}")
    public ResponseEntity<Long> countProjectsByOrganization(@PathVariable Integer orgId) {
        long count = projectServices.countProjectsByOrganization(orgId);
        return ResponseEntity.ok(count);
    }
}
