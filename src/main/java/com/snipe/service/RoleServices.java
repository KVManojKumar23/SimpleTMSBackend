package com.snipe.service;

import java.util.List;

import com.snipe.dto.RoleDTO;

public interface RoleServices {
	
	public List<RoleDTO> getAllRoles();
	public RoleDTO getRoleById(Integer roleId);
	public RoleDTO createRole(RoleDTO roleDTO);
	public RoleDTO updateRole(Integer roleId, RoleDTO roleDTO);

}
