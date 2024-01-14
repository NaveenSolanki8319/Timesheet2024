package com.api.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.api.entities.Employee;
import com.api.entities.Project;
import com.api.entities.TimeSheet;
import com.api.repositories.EmployeeRepo;
import com.api.service.EmployeeService;



@SpringBootTest
public class EmployeeServiceImplTest {

	@MockBean
	EmployeeRepo employeeRepo;

	@Autowired
	EmployeeService employeeService;

	@Test
	public void test_saveEmployee() {
		Project project = null;
		List<TimeSheet> timesheet = null;
		Employee employee = new Employee(100, "Test", "test@nucleusteq.com", "EMPLOYEE", "123456", "IT", 
				"Indore", "Inactive", project, timesheet,null);
		when(employeeRepo.save(employee)).thenReturn(employee);
		assertEquals(employee, employeeService.createEmployee(employee));
	}

	@Test
	public void test_saveEmployeeCrossCheck() {
		Project project = null;
		List<TimeSheet> timesheet = null;
		Employee employee1 = new Employee(101, "Name1", "emp1@nucleusteq.com", "EMPLOYEE", "123456", "IT", 
				"Indore", "Inactive", project, timesheet,null);
		Employee employee2 = new Employee(102, "Name2", "emp2@nucleusteq.com", "EMPLOYEE", "123456", "IT", 
				"Indore", "Inactive", project, timesheet,null);
		when(employeeRepo.save(employee1)).thenReturn(employee1);
		assertNotEquals(employee1, employeeService.createEmployee(employee2));
	}

	@Test
	public void test_getEmployeeById() {
		Project project = null;
		List<TimeSheet> timesheet = null;
		Employee employee = new Employee(103, "Name", "emp3@nucleusteq.com", "EMPLOYEE", "123456", "IT",
				"Indore", "Inactive", project, timesheet,null);
		when(employeeRepo.findById(103)).thenReturn(Optional.of(employee));
		assertEquals("Name", employeeService.getEmployeeById(103).getEmpName());

	}
	
	@Test
	public void test_getEmployeeByIdCrossCheck() {
		Project project = null;
		List<TimeSheet> timesheet = null;
		Employee employee = new Employee(104, "Name", "emp4@nucleusteq.com", "EMPLOYEE", "123456", "IT",
				"Indore", "Inactive", project, timesheet,null);
		when(employeeRepo.findById(104)).thenReturn(Optional.of(employee));
		assertNotEquals("Unmatched_Name", employeeService.getEmployeeById(104).getEmpName());
		
	}


	@Test
	public void test_getEmployeeList() {
		Project project = null;
		List<TimeSheet> timesheet = null;
		Employee employee1 = new Employee(105, "Name1", "emp5@nucleusteq.com", "EMPLOYEE", "123456", "IT",
				"Indore", "Inactive", project, timesheet,null);
		Employee employee2 = new Employee(106, "Name2", "emp6@nucleusteq.com", "EMPLOYEE", "123456", "IT",
				"Indore", "Inactive", project, timesheet,null);
		List<Employee> employeeList = Arrays.asList(employee1, employee2);
		when(employeeRepo.findAll()).thenReturn(employeeList);
		assertEquals(2, employeeService.getAllEmployee().size());
	}
	
	@Test
	public void test_getEmployeeListCrossCheck() {
		Project project = null;
		List<TimeSheet> timesheet = null;
		Employee employee1 = new Employee(107, "Name1", "emp7@nucleusteq.com", "EMPLOYEE", "123456", "IT",
				"Indore", "Inactive", project, timesheet,null);
		Employee employee2 = new Employee(108, "Name2", "emp8@nucleusteq.com", "EMPLOYEE", "123456", "IT",
				"Indore", "Inactive", project, timesheet,null);
		List<Employee> employeeList = Arrays.asList(employee1, employee2);
		when(employeeRepo.findAll()).thenReturn(employeeList);
		assertNotEquals(3, employeeService.getAllEmployee().size());
	}

	@Test
	public void test_updateEmployee() {
		Project project = null;
		List<TimeSheet> timesheet = null;
		Employee employee = new Employee(109, "Name2", "emp9@nucleusteq.com", "EMPLOYEE", "123456", "IT",
				"Indore", "Inactive", project, timesheet,null);
		when(employeeRepo.save(employee)).thenReturn(employee);
		when(employeeRepo.findById(109)).thenReturn(Optional.of(employee));
		employee.setEmpName("Rahul");
		Employee updatedEmployee = employeeRepo.save(employee);
		assertEquals("Rahul", updatedEmployee.getEmpName());
	}
	
	@Test
	public void test_updateEmployeeCrossCheck() {
		Project project = null;
		List<TimeSheet> timesheet = null;
		Employee employee = new Employee(110, "Name", "emp10@nucleusteq.com", "EMPLOYEE", "123456", "IT",
				"Indore", "Inactive", project, timesheet,null);
		when(employeeRepo.save(employee)).thenReturn(employee);
		when(employeeRepo.findById(110)).thenReturn(Optional.of(employee));
		employee.setEmpName("updatedName");
		Employee updatedEmployee = employeeRepo.save(employee);
		assertNotEquals("Name", updatedEmployee.getEmpName());
	}

}
