package com.snipe.service.serviceIMPL;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.snipe.Exception.ResourceNotFoundException;
import com.snipe.dto.EmployeeDTO;
import com.snipe.entity.Employee;
import com.snipe.entity.Employee.Status;
import com.snipe.entity.Organization;
import com.snipe.entity.User;
import com.snipe.repository.EmployeeRepository;
import com.snipe.repository.OrganizationRepository;
import com.snipe.repository.UserRepository;
import com.snipe.service.EmployeeServices;

@Service
public class EmployeeServicesIMPl implements EmployeeServices{
	
	private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
  
    public EmployeeServicesIMPl(EmployeeRepository employeeRepository, UserRepository userRepository,
			OrganizationRepository organizationRepository) {
		super();
		this.employeeRepository = employeeRepository;
		this.userRepository = userRepository;
		this.organizationRepository = organizationRepository;
	}

	// Convert Entity to DTO
    private EmployeeDTO convertToDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getEmpId(),
                employee.getUser().getUserId(),
                employee.getOrganization().getOrgId(),
                employee.getDesignation(),
                employee.getDepartment(),
                employee.getJoiningDate(),
                employee.getExitDate(),
                employee.getStatus(),
                employee.getCreatedAt(),
                employee.getLastModifiedAt(),
                employee.getEmployeeId(),
                employee.getReportingTo(),
                employee.getNotes()
        );
    }

    // Convert DTO to Entity
    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        if (employeeDTO.getUserId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        if (employeeDTO.getOrgId() == null) {
            throw new IllegalArgumentException("Organization ID cannot be null");
        }

        // Fetch User and Organization
        Optional<User> userOpt = userRepository.findById(employeeDTO.getUserId());
        Optional<Organization> orgOpt = organizationRepository.findById(employeeDTO.getOrgId());

        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid User ID: " + employeeDTO.getUserId());
        }

        if (orgOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid Organization ID: " + employeeDTO.getOrgId());
        }

        return new Employee(
                employeeDTO.getEmpId(),
                userOpt.get(),
                orgOpt.get(),
                employeeDTO.getDesignation(),
                employeeDTO.getDepartment(),
                employeeDTO.getJoiningDate(),
                employeeDTO.getExitDate(),
                employeeDTO.getStatus(),
                employeeDTO.getCreatedAt(),
                employeeDTO.getLastModifiedAt(),
                employeeDTO.getEmployeeId(),
                employeeDTO.getReportingTo(),
                employeeDTO.getNotes()
        );
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = convertToEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDTO(savedEmployee);
    }

    @Override
    public EmployeeDTO getEmployeeById(Integer empId) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + empId));
        return convertToDTO(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO updateEmployee(Integer empId, EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + empId));

        // Update fields
        existingEmployee.setDesignation(employeeDTO.getDesignation());
        existingEmployee.setDepartment(employeeDTO.getDepartment());
        existingEmployee.setJoiningDate(employeeDTO.getJoiningDate());
        existingEmployee.setExitDate(employeeDTO.getExitDate());
        existingEmployee.setStatus(employeeDTO.getStatus());
        existingEmployee.setEmployeeId(employeeDTO.getEmployeeId());
        existingEmployee.setReportingTo(employeeDTO.getReportingTo());
        existingEmployee.setNotes(employeeDTO.getNotes());

        // Save updated employee
        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return convertToDTO(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Integer empId) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + empId));

        employeeRepository.delete(employee);
    }

    @Override
    public List<EmployeeDTO> getEmployeesByOrganization(Integer orgId) {
        return employeeRepository.findByOrganizationOrgId(orgId)
                .stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> getEmployeesByStatus(Status status) {
        return employeeRepository.findByStatus(status)
                .stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department)
                .stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> getEmployeesByDesignation(String designation) {
        return employeeRepository.findByDesignation(designation)
                .stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> getEmployeesByReportingManager(String reportingTo) {
        return employeeRepository.findByReportingTo(reportingTo)
                .stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> getEmployeesJoinedBetween(LocalDate startDate, LocalDate endDate) {
        return employeeRepository.findByJoiningDateBetween(startDate, endDate)
                .stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public long countEmployeesByOrganization(Integer orgId) {
        return employeeRepository.countByOrganizationOrgId(orgId);
    }

    @Override
    public long countEmployeesByDepartment(String department) {
        return employeeRepository.countByDepartment(department);
    }

    @Override
    public EmployeeDTO updateEmployeeStatus(Integer empId, Status status) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + empId));

        employee.setStatus(status);
        employeeRepository.save(employee);

        return convertToDTO(employee);
    }

}
