package com.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.entities.Employee;
import com.api.entities.Project;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

	List<Employee> findByProject(Project project);

	List<Employee> findByProjectAndRoleAndStatus(Project project, String role, String status);

	List<Employee> findByRoleAndStatus(String role, String status);

	List<Employee> findByRole(String role);

	Employee findByEmpEmail(String empEmail);

	Employee findByEmpEmailIgnoreCase(String empEmail);

	boolean existsByEmpEmail(String empEmail);

	boolean existsByRole(String role);

	Employee findByEmpId(int empId);
}
