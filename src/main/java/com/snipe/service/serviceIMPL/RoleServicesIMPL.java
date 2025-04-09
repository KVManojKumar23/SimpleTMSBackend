package com.snipe.service.serviceIMPL;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.snipe.Exception.ResourceNotFoundException;
import com.snipe.Exception.ValidationException;
import com.snipe.dto.RoleDTO;
import com.snipe.entity.Role;
import com.snipe.repository.RoleRepository;
import com.snipe.service.RoleServices;

@Service
public class RoleServicesIMPL implements RoleServices {

	private final RoleRepository roleRepository;

	public RoleServicesIMPL(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	private RoleDTO convertToDTO(Role role) {
		return new RoleDTO(
				role.getRoleId(), 
				role.getRoleName(), 
				role.getDescription(), 
				role.getCreatedAt(), 
				role.getLastModifiedAt());
	}

	private Role convertToEntity(RoleDTO roleDTO) {
		Role role = new Role();
		role.setRoleId(roleDTO.getRoleId());
		role.setRoleName(roleDTO.getRoleName());
		role.setDescription(roleDTO.getDescription());

		// Set createdAt only if the role is new
		if (roleDTO.getRoleId() == null) {
			role.setCreatedAt(LocalDateTime.now());
		}

		role.setLastModifiedAt(LocalDateTime.now());
		return role;
	}

	@Override
	public List<RoleDTO> getAllRoles() {
		List<Role> roles = roleRepository.findAll();
		return roles.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public RoleDTO getRoleById(Integer roleId) {
		Role role = roleRepository.findById(roleId)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + roleId));
		return convertToDTO(role);
	}

	@Override
	public RoleDTO createRole(RoleDTO roleDTO) {
		validateRoleDTO(roleDTO); // Validate input
		Role role = convertToEntity(roleDTO);
		role = roleRepository.save(role);
		return convertToDTO(role);
	}

	@Override
	public RoleDTO updateRole(Integer roleId, RoleDTO roleDTO) {
		validateRoleDTO(roleDTO); // Validate input

		Role existingRole = roleRepository.findById(roleId)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + roleId));

		existingRole.setRoleName(roleDTO.getRoleName());
		existingRole.setDescription(roleDTO.getDescription());
		existingRole.setLastModifiedAt(LocalDateTime.now());

		existingRole = roleRepository.save(existingRole);
		return convertToDTO(existingRole);
	}

	private void validateRoleDTO(RoleDTO roleDTO) {
		if (roleDTO == null) {
			throw new ValidationException("Role data cannot be null");
		}
		if (!StringUtils.hasText(roleDTO.getRoleName().name())) {
			throw new ValidationException("Role name cannot be empty");
		}
		if (!StringUtils.hasText(roleDTO.getDescription())) {
			throw new ValidationException("Role description cannot be empty");
		}
	}
}
