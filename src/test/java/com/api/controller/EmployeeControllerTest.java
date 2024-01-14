package com.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.api.entities.Employee;
import com.api.repositories.EmployeeRepo;
import com.api.service.EmployeeService;

public class EmployeeControllerTest {

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private EmployeeService employeeService;

	@Mock
	private EmployeeRepo employeeRepo;

	@InjectMocks
	private EmployeeController employeeController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetAllEmployee_ReturnsAllEmployees() {
		// Arrange
		List<Employee> employeeList = new ArrayList<>();
		employeeList.add(new Employee());
		employeeList.add(new Employee());
		when(employeeService.getAllEmployee()).thenReturn(employeeList);

		// Act
		ResponseEntity<List<Employee>> response = employeeController.saveEmployee();

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(employeeList);
	}

	@Test
	public void testGetEmployeeById_WithValidId_ReturnsEmployee() {
		// Arrange
		int employeeId = 1;
		Employee employee = new Employee();
		when(employeeService.getEmployeeById(employeeId)).thenReturn(employee);

		// Act
		ResponseEntity<?> response = employeeController.getEmployeeById(employeeId);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(employee);
	}

	@Test
	public void testSaveEmployee_WithValidEmployee_ReturnsCreatedResponse() {
		// Arrange
		Employee employee = new Employee();
		employee.setEmpEmail("test@example.com");
		employee.setPassword("password");

		when(passwordEncoder.encode(employee.getPassword())).thenReturn("encodedpassword");
		when(employeeRepo.existsByEmpEmail(employee.getEmpEmail())).thenReturn(false);
		when(employeeService.createEmployee(employee)).thenReturn(employee);

		// Act
		ResponseEntity<?> response = employeeController.saveEmployee(employee);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody()).isEqualTo(employee);
	}

	@Test
	public void testSaveEmployee_WithExistingEmail_ReturnsBadRequestResponse() {
		// Arrange
		Employee employee = new Employee();
		employee.setEmpEmail("test@example.com");
		employee.setPassword("password");

		when(employeeRepo.existsByEmpEmail(employee.getEmpEmail())).thenReturn(true);

		// Act
		ResponseEntity<?> response = employeeController.saveEmployee(employee);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody()).isEqualTo("Email I.D. already exists ");
	}

	@Test
	public void testUpdateEmployee_WithValidEmployee_ReturnsAcceptedResponse() {
		// Arrange
		Employee employee = new Employee();
		employee.setEmpEmail("test@example.com");
		employee.setPassword("password");

		when(passwordEncoder.encode(employee.getPassword())).thenReturn("encodedpassword");
		when(employeeService.updateEmployee(employee)).thenReturn(employee);

		// Act
		ResponseEntity<?> response = employeeController.updateEmployee(employee);

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
		assertThat(response.getBody()).isEqualTo(employee);
	}

	@Test
	public void testGetAllInactiveUser_ReturnsInactiveEmployees() {
		// Arrange
		List<Employee> inactiveEmployees = new ArrayList<>();
		inactiveEmployees.add(new Employee());
		inactiveEmployees.add(new Employee());
		when(employeeRepo.findByProjectAndRoleAndStatus(null, "EMPLOYEE", "Inactive")).thenReturn(inactiveEmployees);

		// Act
		ResponseEntity<List<Employee>> response = employeeController.getAllInactiveUser();

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(inactiveEmployees);
	}

	@Test
	public void testGetAllActiveUser_ReturnsActiveEmployees() {
		// Arrange
		List<Employee> activeEmployees = new ArrayList<>();
		activeEmployees.add(new Employee());
		activeEmployees.add(new Employee());
		when(employeeService.getAllEmployee()).thenReturn(activeEmployees);
		// Assuming some employees have projects with end dates in the future
		when(employeeRepo.findByRoleAndStatus("EMPLOYEE", "Active")).thenReturn(activeEmployees);

		// Act
		ResponseEntity<List<Employee>> response = employeeController.getAllActiveUser();

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(activeEmployees);
	}

	@Test
	public void testGetEmployeesList_ReturnsEmployeesOnly() {
		// Arrange
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee());
		employees.add(new Employee());
		when(employeeRepo.findByRole("EMPLOYEE")).thenReturn(employees);

		// Act
		ResponseEntity<List<Employee>> response = employeeController.getEmployeesList();

		// Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(employees);
	}

}
