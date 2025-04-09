package com.snipe.service.serviceIMPL;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.snipe.Exception.ResourceNotFoundException;
import com.snipe.dto.OrganizationDTO;
import com.snipe.entity.Organization;
import com.snipe.repository.OrganizationRepository;
import com.snipe.service.OrganizationServices;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrganizationServicesIMPL implements OrganizationServices {
    
    private final OrganizationRepository organizationRepository;

    public OrganizationServicesIMPL(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    private OrganizationDTO convertToDTO(Organization organization) {
        return new OrganizationDTO(
                organization.getOrgId(),
                organization.getOrgName(),
                organization.getAddress(),
                organization.getEmail(),
                organization.getPhoneNumber(),
                organization.getWebsite(),
                organization.getIndustryType(),
                organization.getStatus(),
                organization.getCreatedAt(),
                organization.getUpdatedAt(),
                organization.getCreatedBy(),
                organization.getUpdatedBy()
        );
    }

    private Organization convertToEntity(OrganizationDTO organizationDTO) {
        if (organizationDTO.getOrgName() == null || organizationDTO.getOrgName().trim().isEmpty()) {
            throw new IllegalArgumentException("Organization name cannot be empty");
        }
        
        return new Organization(
                organizationDTO.getOrgId(),
                organizationDTO.getOrgName(),
                organizationDTO.getAddress(),
                organizationDTO.getEmail(),
                organizationDTO.getPhoneNumber(),
                organizationDTO.getWebsite(),
                organizationDTO.getIndustryType(),
                organizationDTO.getStatus(),
                organizationDTO.getCreatedAt(),
                organizationDTO.getUpdatedAt(),
                organizationDTO.getCreatedBy(),
                (organizationDTO.getUpdatedBy() != null) ? organizationDTO.getUpdatedBy() : "System"
        );
    }

    @Override
    public List<OrganizationDTO> getAllOrganizations() {
        List<Organization> organizations = organizationRepository.findAll();
        if (organizations.isEmpty()) {
            throw new ResourceNotFoundException("No organizations found in the database");
        }
        return organizations.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public OrganizationDTO getOrganizationById(Integer orgId) {
        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization with ID " + orgId + " not found"));
        return convertToDTO(organization);
    }

    @Override
    @Transactional
    public OrganizationDTO createOrganization(OrganizationDTO organizationDTO) {
        if (organizationDTO == null) {
            throw new IllegalArgumentException("Organization data cannot be null");
        }
        Organization organization = convertToEntity(organizationDTO);
        organization = organizationRepository.save(organization);
        return convertToDTO(organization);
    }

    @Override
    @Transactional
    public OrganizationDTO updateOrganization(Integer orgId, OrganizationDTO organizationDTO) {
        if (organizationDTO == null) {
            throw new IllegalArgumentException("Update data cannot be null");
        }
        
        Organization existingOrganization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization with ID " + orgId + " not found"));

        existingOrganization.setOrgName(organizationDTO.getOrgName());
        existingOrganization.setAddress(organizationDTO.getAddress());
        existingOrganization.setEmail(organizationDTO.getEmail());
        existingOrganization.setPhoneNumber(organizationDTO.getPhoneNumber());
        existingOrganization.setWebsite(organizationDTO.getWebsite());
        existingOrganization.setIndustryType(organizationDTO.getIndustryType());
        existingOrganization.setStatus(organizationDTO.getStatus());
        existingOrganization.setUpdatedBy(organizationDTO.getUpdatedBy() != null ? organizationDTO.getUpdatedBy() : "System");

        existingOrganization = organizationRepository.save(existingOrganization);
        return convertToDTO(existingOrganization);
    }

    @Override
    @Transactional
    public void deleteOrganization(Integer orgId) {
        if (!organizationRepository.existsById(orgId)) {
            throw new ResourceNotFoundException("Organization with ID " + orgId + " not found");
        }
        organizationRepository.deleteById(orgId);
    }
}