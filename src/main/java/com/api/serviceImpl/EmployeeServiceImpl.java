package com.api.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.entities.Employee;
import com.api.exceptions.ResourceNotFoundException;
import com.api.repositories.EmployeeRepo;
import com.api.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeRepo employeeRepo;

	@Override
	public Employee createEmployee(Employee employee) {
		return this.employeeRepo.save(employee);
	}

	@Override
	public List<Employee> getAllEmployee() {
		return this.employeeRepo.findAll();
	}

	@Override
	public Employee getEmployeeById(int id) {
		return this.employeeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", id));
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		Integer empId = employee.getEmpId();
		Employee employee1 = this.employeeRepo.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee Not found with", " Id ", empId));
		employee1.setAddress(employee.getAddress());
		employee1.setEmpDepart(employee.getEmpDepart());
		employee1.setPassword(employee.getPassword());
		return this.employeeRepo.save(employee1);
	}

}
