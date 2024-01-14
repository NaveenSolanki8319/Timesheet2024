package com.api.service;

import java.util.List;


import com.api.entities.Employee;

public interface EmployeeService {
	

	// Create
	public Employee createEmployee(Employee employee);

	// Read
	public List<Employee> getAllEmployee();

	public Employee getEmployeeById(int id);

	// Update
	public Employee updateEmployee(Employee employee);
	
	

}
