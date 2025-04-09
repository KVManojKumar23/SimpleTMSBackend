package com.snipe.service;

import java.time.LocalDate;
import java.util.List;

import com.snipe.dto.EmployeeDTO;
import com.snipe.entity.Employee.Status;

public interface EmployeeServices {

	EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO getEmployeeById(Integer empId);

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO updateEmployee(Integer empId, EmployeeDTO employeeDTO);

    void deleteEmployee(Integer empId);

    List<EmployeeDTO> getEmployeesByOrganization(Integer orgId);

    List<EmployeeDTO> getEmployeesByStatus(Status status);

    List<EmployeeDTO> getEmployeesByDepartment(String department);

    List<EmployeeDTO> getEmployeesByDesignation(String designation);

    List<EmployeeDTO> getEmployeesByReportingManager(String reportingTo);

    List<EmployeeDTO> getEmployeesJoinedBetween(LocalDate startDate, LocalDate endDate);

    long countEmployeesByOrganization(Integer orgId);

    long countEmployeesByDepartment(String department);

    EmployeeDTO updateEmployeeStatus(Integer empId, Status status);

}
