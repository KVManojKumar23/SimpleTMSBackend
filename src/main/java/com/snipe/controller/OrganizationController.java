package com.snipe.controller;

import com.snipe.dto.OrganizationDTO;
import com.snipe.service.OrganizationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationServices organizationServices;

    @GetMapping
    public ResponseEntity<List<OrganizationDTO>> getAllOrganizations() {
        return ResponseEntity.ok(organizationServices.getAllOrganizations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDTO> getOrganizationById(@PathVariable Integer id) {
        return ResponseEntity.ok(organizationServices.getOrganizationById(id));
    }

    @PostMapping
    public ResponseEntity<OrganizationDTO> createOrganization(@RequestBody OrganizationDTO organizationDTO) {
        return ResponseEntity.ok(organizationServices.createOrganization(organizationDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganizationDTO> updateOrganization(@PathVariable Integer id, @RequestBody OrganizationDTO organizationDTO) {
        return ResponseEntity.ok(organizationServices.updateOrganization(id, organizationDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Integer id) {
        organizationServices.deleteOrganization(id);
        return ResponseEntity.noContent().build();
    }
}

