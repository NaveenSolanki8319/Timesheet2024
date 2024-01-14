package com.api.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.entities.Employee;
import com.api.repositories.EmployeeRepo;
import com.api.service.EmployeeService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	EmployeeRepo employeeRepo;

	// Read all employees
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/getAll")
	public ResponseEntity<List<Employee>> saveEmployee() {
		List<Employee> employeeList = employeeService.getAllEmployee();
		for (Employee e : employeeList) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.now();
			if (e.getProject() != null) {
				if ((dtf.format(localDate).toString()).compareTo(e.getProject().getEndDate().toString()) >= 0) {
					if (e.getRole().equalsIgnoreCase("EMPLOYEE")) {
						e.setProject(null);
						this.employeeService.updateEmployee(e);
					}
				}
			}
		}
		return ResponseEntity.ok(this.employeeService.getAllEmployee());
	}

	// Read employee by ID
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getEmployeeById(@Valid @PathVariable("id") Integer id) {
		return ResponseEntity.ok(this.employeeService.getEmployeeById(id));
	}

	// Add employee
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/addEmployee")
	public ResponseEntity<?> saveEmployee(@Valid @RequestBody Employee employee) {
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		employee.setRole("EMPLOYEE");
		if (employeeRepo.existsByEmpEmail(employee.getEmpEmail())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email I.D. already exists ");

		} else
			return ResponseEntity.status(HttpStatus.CREATED).body(this.employeeService.createEmployee(employee));
	}

//	 Update employee
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/update")
	public ResponseEntity<?> updateEmployee(@Valid @RequestBody Employee employee) {
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.employeeService.updateEmployee(employee));
	}

	// To find all inactive member
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/inactive")
	public ResponseEntity<List<Employee>> getAllInactiveUser() {
		List<Employee> users = this.employeeRepo.findByProjectAndRoleAndStatus(null, "EMPLOYEE", "Inactive");
		return ResponseEntity.ok(users);
	}

	// To find all Active member
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/active")
	public ResponseEntity<List<Employee>> getAllActiveUser() {
		List<Employee> employees = employeeService.getAllEmployee();
		for (Employee e : employees) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
			if (e.getProject() != null) {
				if (dtf.format(now).toString().compareTo(e.getProject().getEndDate().toString()) >= 0) {
					e.setStatus("Inactive");
					e.setProject(null);
					this.employeeService.updateEmployee(e);

				} else {
					e.setStatus("Active");
					this.employeeService.updateEmployee(e);
				}
			}
		}
		List<Employee> users = this.employeeRepo.findByRoleAndStatus("EMPLOYEE", "Active");
		return ResponseEntity.ok(users);
	}

	// To get list of only employee not admin
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/employeeOnly")
	public ResponseEntity<List<Employee>> getEmployeesList() {
		List<Employee> employee = this.employeeRepo.findByRole("EMPLOYEE");
		return ResponseEntity.ok(employee);
	}

}
