package com.api.repositories;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.api.entities.Employee;
import com.api.entities.Project;

@SpringBootTest
public class EmployeeRepoTest {

	@MockBean
	private EmployeeRepo employeeRepo;

	@Test
	public void testFindByProject() {
		// Create a project with mock data
		Project project = new Project(1, "Project 1", null, null, null, null, null);

		// Create a list of employees with mock data
		List<Employee> employeeList = new ArrayList<>();
		employeeList.add(new Employee(1, "Rohan", "Rohan@nucleusteq.com", "EMPLOYEE", null, null, null, "Active", project,null, null));
		employeeList.add(new Employee(2, "Sita", "Sita@nucleusteq.com", "EMPLOYEE", null, null, null, "Active", project, null, null));

		// Mock the repository method to return the list of employees
		when(employeeRepo.findByProject(project)).thenReturn(employeeList);

		// Call the repository method
		List<Employee> result = employeeRepo.findByProject(project);

		// Verify the result
		assertEquals(2, result.size());
		assertEquals(1, result.get(0).getEmpId());
		assertEquals("Rohan", result.get(0).getEmpName());
		assertEquals(1, result.get(0).getProject().getProject_id());
		assertEquals("Project 1", result.get(0).getProject().getProjectName());
		assertEquals(2, result.get(1).getEmpId());
		assertEquals("Sita", result.get(1).getEmpName());
		assertEquals(1, result.get(1).getProject().getProject_id());
		assertEquals("Project 1", result.get(1).getProject().getProjectName());
		verify(employeeRepo, times(1)).findByProject(project);
	}

	@Test
    public void testFindByProjectAndRoleAndStatus() {
		List<Employee> employeeList = new ArrayList<>();
        // Create a test project and save it to the database
        Project testProject = null;
        // Create test employees
        Employee employee1 = new Employee(1, "Rohan", "Rohan@nucleusteq.com", "EMPLOYEE", null, null, null, "Active", testProject,null, null);
        Employee employee2 = new Employee(2, "Sita", "Sita@nucleusteq.com", "EMPLOYEE", null, null, null, "Active", testProject, null, null);
        Employee employee3 = new Employee(3, "Mohan", "Mohan@nucleusteq.com", "EMPLOYEE", null, null, null, "Active", testProject, null, null);
        employeeList.add(employee1);
        employeeList.add(employee2);
        employeeList.add(employee3);
        // Save the employees to the database
        when(employeeRepo.findByProjectAndRoleAndStatus(testProject,"EMPLOYEE", "Active")).thenReturn(employeeList);
        // Perform the query
        List<Employee> foundEmployees = employeeRepo.findByProjectAndRoleAndStatus(testProject, "EMPLOYEE", "Active");

        // Assert the results
        assertEquals(3, foundEmployees.size());
        assertEquals(employee1.getEmpId(), foundEmployees.get(0).getEmpId());
    }
	
	@Test
	public void testFindByRoleAndStatus() {

		// Create a list of employees with mock data
		List<Employee> employeeList = new ArrayList<>();
		Project project = new Project(100, "ProjectName", null, null, null, null, null);
		employeeList.add(new Employee(1, "Rohan", "Rohan@nucleusteq.com", "EMPLOYEE", null, null, null, "Active",
				project, null, null));
		employeeList.add(new Employee(2,"Sita", "Sita@nucleusteq.com", "EMPLOYEE" , null, null, null, "Active", project,
				null, null));

		// Mock the repository method to return a list of employees with the specified
		// role and status
		when(employeeRepo.findByRoleAndStatus("EMPLOYEE", "Active")).thenReturn(employeeList);

		// Call the repository method
		List<Employee> result = employeeRepo.findByRoleAndStatus("EMPLOYEE", "Active");

		// Verify the result
		assertEquals(2, result.size());
		// Assert employee details
		assertEquals(1, result.get(0).getEmpId());
		assertEquals("Rohan", result.get(0).getEmpName());
		assertEquals("EMPLOYEE", result.get(0).getRole());
		assertEquals("Active", result.get(0).getStatus());
		assertEquals(100, result.get(0).getProject().getProject_id());
		assertEquals("ProjectName", result.get(0).getProject().getProjectName());
		assertEquals(2, result.get(1).getEmpId());
		assertEquals("Sita", result.get(1).getEmpName());
		assertEquals("Active", result.get(1).getStatus());
		assertEquals("ProjectName", result.get(1).getProject().getProjectName());

		verify(employeeRepo, times(1)).findByRoleAndStatus("EMPLOYEE", "Active");
	}

	@Test
	public void testFindByRole() {
		// Mock the repository method to return a list of employees with the specified
		// role
		List<Employee> employeeList = new ArrayList<>();
		Project project = new Project(100, "ProjectName", null, null, null, null, null);
		employeeList.add(new Employee(1, "Rohan", "Rohan@nucleusteq.com", "EMPLOYEE", null, null, null, "Active",
				project, null, null));
		employeeList.add(new Employee(2, "Sita", "Sita@nucleusteq.com", "EMPLOYEE", null, null, null, "Active", project,
				null, null));

		when(employeeRepo.findByRole("EMPLOYEE")).thenReturn(employeeList);
		// Call the repository method
		List<Employee> result = employeeRepo.findByRole("EMPLOYEE");

		// Verify the result
		assertEquals(2, result.size());
		
		// Assert employee details
		assertEquals(1, result.get(0).getEmpId());
		assertEquals("Rohan", result.get(0).getEmpName());
		assertEquals("EMPLOYEE", result.get(0).getRole());
		assertEquals("Active", result.get(0).getStatus());
		assertEquals(100, result.get(0).getProject().getProject_id());
		assertEquals("ProjectName", result.get(0).getProject().getProjectName());
		assertEquals(2, result.get(1).getEmpId());
		assertEquals("Sita", result.get(1).getEmpName());
		assertEquals("EMPLOYEE", result.get(1).getRole());
		assertEquals("Active", result.get(1).getStatus());
		assertEquals(100, result.get(1).getProject().getProject_id());
		assertEquals("ProjectName", result.get(1).getProject().getProjectName());

		verify(employeeRepo, times(1)).findByRole("EMPLOYEE");
	}
	
	@Test
    public void testFindByEmpEmail() {
        // Mock the repository method to return an employee with the specified email
		Project project = null;
		when(employeeRepo.findByEmpEmail("ramanujan@nucleusteq.com")).thenReturn(new Employee(1, "Rohan", "ramanujan@nucleusteq.com", "EMPLOYEE", null, null, null, "Active",
				project, null, null));

        // Call the repository method
        Employee result = employeeRepo.findByEmpEmail("ramanujan@nucleusteq.com");

        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.getEmpId());
        assertEquals("Rohan", result.getEmpName());
        assertEquals("EMPLOYEE", result.getRole());
        assertEquals("Active", result.getStatus());
        assertNull(result.getProject());

        verify(employeeRepo, times(1)).findByEmpEmail("ramanujan@nucleusteq.com");
    }

    @Test
    public void testExistsByEmpEmail() {
        // Mock the repository method to return true for an existing email
        when(employeeRepo.existsByEmpEmail("ramanujan@nucleusteq.com")).thenReturn(true);

        // Call the repository method
        boolean result = employeeRepo.existsByEmpEmail("ramanujan@nucleusteq.com");

        // Verify the result
        assertTrue(result);
        verify(employeeRepo, times(1)).existsByEmpEmail("ramanujan@nucleusteq.com");
    }

    @Test
    public void testExistsByRole() {
        // Mock the repository method to return true for an existing role
        when(employeeRepo.existsByRole("EMPLOYEE")).thenReturn(true);

        // Call the repository method
        boolean result = employeeRepo.existsByRole("EMPLOYEE");

        // Verify the result
        assertTrue(result);

        verify(employeeRepo, times(1)).existsByRole("EMPLOYEE");
    }

    @Test
    public void testFindByEmpId() {
        // Mock the repository method to return an employee with the specified ID
        when(employeeRepo.findByEmpId(1)).thenReturn(new Employee(1, "Rohan", "ramanujan@nucleusteq.com", "EMPLOYEE", null, null, null, "Active",
				null, null, null));

        // Call the repository method
        Employee result = employeeRepo.findByEmpId(1);

        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.getEmpId());
        assertEquals("Rohan", result.getEmpName());
        assertEquals("EMPLOYEE", result.getRole());
        assertEquals("Active", result.getStatus());
        assertNull(result.getProject());

        verify(employeeRepo, times(1)).findByEmpId(1);
    }

}
