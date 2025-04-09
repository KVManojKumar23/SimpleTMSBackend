package com.snipe.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.snipe.entity.Employee;
import com.snipe.entity.Employee.Status;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	List<Employee> findByOrganizationOrgId(Integer orgId);

    List<Employee> findByStatus(Status status);

    List<Employee> findByDepartment(String department);

    List<Employee> findByDesignation(String designation);

    List<Employee> findByReportingTo(String reportingTo);

    List<Employee> findByJoiningDateBetween(LocalDate startDate, LocalDate endDate);

    long countByOrganizationOrgId(Integer orgId);

    long countByDepartment(String department);

}
