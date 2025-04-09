package com.snipe.service;

import java.util.List;

import com.snipe.dto.OrganizationDTO;

public interface OrganizationServices {
	List<OrganizationDTO> getAllOrganizations();
    OrganizationDTO getOrganizationById(Integer orgId);
    OrganizationDTO createOrganization(OrganizationDTO organizationDTO);
    OrganizationDTO updateOrganization(Integer orgId, OrganizationDTO organizationDTO);
    void deleteOrganization(Integer orgId);
}
