package com.snipe.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.snipe.dto.EmployeeDTO;
import com.snipe.entity.Employee.Status;
import com.snipe.service.EmployeeServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/employees")
@Validated
public class EmployeeController {

    private final EmployeeServices employeeService;

    public EmployeeController(EmployeeServices employeeService) {
        this.employeeService = employeeService;
    }

    // ✅ Create Employee
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    // ✅ Get Employee by ID
    @GetMapping("/{empId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Integer empId) {
        EmployeeDTO employee = employeeService.getEmployeeById(empId);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    // ✅ Get All Employees
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // ✅ Update Employee
    @PutMapping("/{empId}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable Integer empId,
            @Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(empId, employeeDTO);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    // ✅ Delete Employee
    @DeleteMapping("/{empId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer empId) {
        employeeService.deleteEmployee(empId);
        return new ResponseEntity<>("Employee deleted successfully!", HttpStatus.OK);
    }

    // ✅ Get Employees by Organization
    @GetMapping("/organization/{orgId}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByOrganization(@PathVariable Integer orgId) {
        List<EmployeeDTO> employees = employeeService.getEmployeesByOrganization(orgId);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // ✅ Get Employees by Status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByStatus(@PathVariable Status status) {
        List<EmployeeDTO> employees = employeeService.getEmployeesByStatus(status);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // ✅ Get Employees by Department
    @GetMapping("/department/{department}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDepartment(@PathVariable String department) {
        List<EmployeeDTO> employees = employeeService.getEmployeesByDepartment(department);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // ✅ Get Employees by Designation
    @GetMapping("/designation/{designation}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDesignation(@PathVariable String designation) {
        List<EmployeeDTO> employees = employeeService.getEmployeesByDesignation(designation);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // ✅ Get Employees by Reporting Manager
    @GetMapping("/reporting/{reportingTo}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByReportingManager(@PathVariable String reportingTo) {
        List<EmployeeDTO> employees = employeeService.getEmployeesByReportingManager(reportingTo);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // ✅ Get Employees joined between dates
    @GetMapping("/joined-between")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesJoinedBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<EmployeeDTO> employees = employeeService.getEmployeesJoinedBetween(startDate, endDate);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // ✅ Count Employees by Organization
    @GetMapping("/count/organization/{orgId}")
    public ResponseEntity<Long> countEmployeesByOrganization(@PathVariable Integer orgId) {
        long count = employeeService.countEmployeesByOrganization(orgId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    // ✅ Count Employees by Department
    @GetMapping("/count/department/{department}")
    public ResponseEntity<Long> countEmployeesByDepartment(@PathVariable String department) {
        long count = employeeService.countEmployeesByDepartment(department);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    // ✅ Update Employee Status
    @PatchMapping("/{empId}/status")
    public ResponseEntity<EmployeeDTO> updateEmployeeStatus(
            @PathVariable Integer empId,
            @RequestParam Status status) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployeeStatus(empId, status);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }
}