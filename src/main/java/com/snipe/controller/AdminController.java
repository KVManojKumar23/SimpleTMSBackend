package com.snipe.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.snipe.dto.OrganizationDTO;
import com.snipe.dto.ProjectDTO;
import com.snipe.dto.user.UserDTO;
import com.snipe.entity.User;
import com.snipe.service.EmployeeServices;
import com.snipe.service.OrganizationServices;
import com.snipe.service.ProjectServices;
import com.snipe.service.SprintServices;
import com.snipe.service.SprintTaskServices;
import com.snipe.service.UserServices;

@RestController
@RequestMapping("/admin")
public class AdminController {

	private final UserServices userServices;
	private final ProjectServices projectServices;
	private final OrganizationServices organizationServices;
	private final SprintServices sprintServices;
	private final SprintTaskServices sprintTaskServices;
	private final EmployeeServices employeeServices;

	public AdminController(UserServices userServices, ProjectServices projectServices,
			OrganizationServices organizationServices, SprintServices sprintServices,
			SprintTaskServices sprintTaskServices, EmployeeServices employeeServices) {
		super();
		this.userServices = userServices;
		this.projectServices = projectServices;
		this.organizationServices = organizationServices;
		this.sprintServices = sprintServices;
		this.sprintTaskServices = sprintTaskServices;
		this.employeeServices = employeeServices;
	}

//	User Management (CRUD Operations)

	@PostMapping("/user/createUser")
	public ResponseEntity<?> createUser(@Validated @RequestBody User user) {
		UserDTO createUser = userServices.createUser(user);
		return new ResponseEntity<>(createUser, HttpStatus.CREATED);
	}

	@PutMapping("/user/updateUser/{ID}")
	public ResponseEntity<?> updateUser(@PathVariable Integer ID, @Validated @RequestBody User user) {
		UserDTO updateUser = userServices.updateUserProfile(ID, user);
		return new ResponseEntity<>(updateUser, HttpStatus.OK);
	}

	@GetMapping("/user/allUser")
	public ResponseEntity<?> getAllUser() {
		List<UserDTO> users = userServices.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/user/usersByRole/{roleID}")
	public ResponseEntity<?> getUserByRoleID(@PathVariable Integer roleID) {
		List<UserDTO> usersByRole = userServices.getUsersByRoleID(roleID);
		return new ResponseEntity<>(usersByRole, HttpStatus.OK);
	}

	@DeleteMapping("/user/deleteUser/{ID}")
	public ResponseEntity<?> deleteUserByID(@PathVariable Integer ID) {
		userServices.deleteUser(ID);
		return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
	}

//	Organization Management (CRUD Operations)

	@GetMapping("/org")
	public ResponseEntity<List<OrganizationDTO>> getAllOrganizations() {
		return ResponseEntity.ok(organizationServices.getAllOrganizations());
	}

	@GetMapping("/org/{id}")
	public ResponseEntity<OrganizationDTO> getOrganizationById(@PathVariable Integer id) {
		return ResponseEntity.ok(organizationServices.getOrganizationById(id));
	}

	@PostMapping("/org/create")
	public ResponseEntity<OrganizationDTO> createOrganization(@RequestBody OrganizationDTO organizationDTO) {
		return ResponseEntity.ok(organizationServices.createOrganization(organizationDTO));
	}

	@PutMapping("/org/updateOrg/{id}")
	public ResponseEntity<OrganizationDTO> updateOrganization(@PathVariable Integer id,
			@RequestBody OrganizationDTO organizationDTO) {
		return ResponseEntity.ok(organizationServices.updateOrganization(id, organizationDTO));
	}

	@DeleteMapping("/org/deleteOrg/{id}")
	public ResponseEntity<Void> deleteOrganization(@PathVariable Integer id) {
		organizationServices.deleteOrganization(id);
		return ResponseEntity.noContent().build();
	}

	// Get projects by organization ID
	@GetMapping("/org/organization/{orgId}")
	public ResponseEntity<List<ProjectDTO>> getProjectsByOrganization(@PathVariable Integer orgId) {
		List<ProjectDTO> projects = projectServices.getProjectsByOrganization(orgId);
		return ResponseEntity.ok(projects);
	}

//    Project Management

	// Get all projects
	@GetMapping("/project")
	public ResponseEntity<List<ProjectDTO>> getAllProjects() {
		List<ProjectDTO> projects = projectServices.getAllProjects();
		return ResponseEntity.ok(projects);
	}

	// Count projects under a specific organization
	@GetMapping("/project/count/{orgId}")
	public ResponseEntity<Long> countProjectsByOrganization(@PathVariable Integer orgId) {
		long count = projectServices.countProjectsByOrganization(orgId);
		return ResponseEntity.ok(count);
	}

	// Delete a project
	@DeleteMapping("/project/{projectId}")
	public ResponseEntity<String> deleteProject(@PathVariable Integer projectId) {
		projectServices.deleteProject(projectId);
		return ResponseEntity.ok("Project deleted successfully");
	}

	// Update an existing project
	@PutMapping("/project/{projectId}")
	public ResponseEntity<ProjectDTO> updateProject(@PathVariable Integer projectId,
			@RequestBody ProjectDTO projectDTO) {
		ProjectDTO updatedProject = projectServices.updateProject(projectId, projectDTO);
		return ResponseEntity.ok(updatedProject);
	}

	// Search projects by name (partial match)
	@GetMapping("/project/search")
	public ResponseEntity<List<ProjectDTO>> searchProjectsByName(@RequestParam String keyword) {
		List<ProjectDTO> projects = projectServices.searchProjectsByName(keyword);
		return ResponseEntity.ok(projects);
	}

	
	// Get all projects created by a specific user
	@GetMapping("/project/createdBy/{userId}")
	public ResponseEntity<List<ProjectDTO>> getProjectsCreatedByUser(@PathVariable Integer userId) {
		List<ProjectDTO> projects = projectServices.getProjectsCreatedByUser(userId);
		return ResponseEntity.ok(projects);
	}

	// Get all projects By status
	@GetMapping("/project/Status/{status}")
	public ResponseEntity<?> getProjectsByStatus(@PathVariable String status) {
		List<ProjectDTO> projects = projectServices.getProjectsByStatus(status);
		return ResponseEntity.ok(projects);
	}

	// ✅ Get all projects where user is a manager
	@GetMapping("/project/manager/{userId}")
	public List<ProjectDTO> getProjectsByManager(@PathVariable Integer userId) {
		return projectServices.getProjectsByManagerUserId(userId);
	}

	// ✅ Get all projects where user is an employee
	@GetMapping("/project/employee/{userId}")
	public List<ProjectDTO> getProjectsByEmployee(@PathVariable Integer userId) {
		return projectServices.getProjectsByEmployeeUserId(userId);
	}
	
//	Sprint Management

}
