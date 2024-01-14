package com.api.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.api.entities.Client;
import com.api.entities.Employee;
import com.api.entities.Project;
import com.api.repositories.ProjectRepo;
import com.api.service.ProjectService;

@SpringBootTest
public class ProjectServiceImplTest {

	@MockBean
	ProjectRepo projectRepo;

	@Autowired
	ProjectService projectService;

	@Test
	public void test_saveProject() {
		LocalDate localDate = LocalDate.now();
		LocalDate localDateNext = localDate.plusDays(1);
		Date startDate = new Date(localDateNext.atStartOfDay(ZoneId.of("Asia/Kolkata")).toEpochSecond() * 1000);
		LocalDate localTwoDateNext = localDate.plusDays(2);
		Date endDate = new Date(localTwoDateNext.atStartOfDay(ZoneId.of("Asia/Kolkata")).toEpochSecond() * 1000);

		List<Employee> emp = null;
		Client client = null;
		Project project = new Project(1, "ABC Service", "This is ABC project", endDate, startDate, emp, client);
		when(projectRepo.save(project)).thenReturn(project);
		assertEquals(project, projectService.createProject(project));
	}
	
	@Test
	public void test_saveProjectCrossCheck() {
		LocalDate localDate = LocalDate.now();
		LocalDate localDateNext = localDate.plusDays(1);
		Date startDate = new Date(localDateNext.atStartOfDay(ZoneId.of("Asia/Kolkata")).toEpochSecond() * 1000);
		LocalDate localTwoDateNext = localDate.plusDays(2);
		Date endDate = new Date(localTwoDateNext.atStartOfDay(ZoneId.of("Asia/Kolkata")).toEpochSecond() * 1000);

		List<Employee> emp = null;
		Client client = null;
		Project project1 = new Project(2, "ABC Service", "This is ABC project", endDate, startDate, emp, client);
		Project project2 = new Project(3, "ABC Service", "This is ABC project", endDate, startDate, emp, client);
		when(projectRepo.save(project1)).thenReturn(project1);
		assertNotEquals(project1, projectService.createProject(project2));
	}
	

	@Test
	public void test_getProjectList() {
		Date startDate = new Date();
		Date endDate = new Date();
		List<Employee> emp = null;
		Client client = null;
		Project project1 = new Project(4, "ABC Service", "This is ABC project", startDate, endDate, emp, client);
		Project project2 = new Project(5, "XYZ Service", "This is XYZ project", startDate, endDate, emp, client);
		Project project3 = new Project(6, "PQR Service", "This is PQR project", startDate, endDate, emp, client);
		List<Project> projectList = Arrays.asList(project1, project2, project3);
		when(projectRepo.findAll()).thenReturn(projectList);
		assertEquals(3, projectService.getAllProject().size());
	}
	
	@Test
	public void test_getProjectListCrossCheck() {
		Date startDate = new Date();
		Date endDate = new Date();
		List<Employee> emp = null;
		Client client = null;
		Project project1 = new Project(7, "ABC Service", "This is ABC project", startDate, endDate, emp, client);
		Project project2 = new Project(8, "XYZ Service", "This is XYZ project", startDate, endDate, emp, client);
		Project project3 = new Project(9, "PQR Service", "This is PQR project", startDate, endDate, emp, client);
		List<Project> projectList = Arrays.asList(project1, project2, project3);
		when(projectRepo.findAll()).thenReturn(projectList);
		assertNotEquals(4, projectService.getAllProject().size());
	}

	@Test
	public void test_getProjectById() {
		Date startDate = new Date();
		Date endDate = new Date();
		List<Employee> emp = null;
		Client client = null;
		Project project = new Project(10, "ABC Service", "This is ABC project", startDate, endDate, emp, client);
		when(projectRepo.findById(10)).thenReturn(Optional.of(project));
		assertEquals("ABC Service", projectService.getProjectById(10).getProjectName());
	}
	
	@Test
	public void test_getProjectByIdCrossCheck() {
		Date startDate = new Date();
		Date endDate = new Date();
		List<Employee> emp = null;
		Client client = null;
		Project project = new Project(11, "ABC Service", "This is ABC project", startDate, endDate, emp, client);
		when(projectRepo.findById(11)).thenReturn(Optional.of(project));
		assertNotEquals("Unmatched Service", projectService.getProjectById(11).getProjectName());
	}

	@Test
	public void test_updateProject() {
		Date startDate = new Date();
		Date endDate = new Date();
		List<Employee> emp = null;
		Client client = null;
		Project project = new Project(12, "ABC Project", "This is ABC project", startDate, endDate, emp, client);
		when(projectRepo.save(project)).thenReturn(project);
		when(projectRepo.findById(12)).thenReturn(Optional.of(project));
		System.out.println(project);
		project.setProjectName("Updated Project");
		System.out.println(project);
		Project updatedProject = projectRepo.save(project);
		assertEquals("Updated Project", updatedProject.getProjectName());
	}
	
	@Test
	public void test_updateProjectCrossCheck() {
		Date startDate = new Date();
		Date endDate = new Date();
		List<Employee> emp = null;
		Client client = null;
		Project project = new Project(13, "ABC Project", "This is ABC project", startDate, endDate, emp, client);
		when(projectRepo.save(project)).thenReturn(project);
		when(projectRepo.findById(13)).thenReturn(Optional.of(project));
		System.out.println(project);
		project.setProjectName("Updated Project");
		System.out.println(project);
		Project updatedProject = projectRepo.save(project);
		assertNotEquals("Unmatched Name", updatedProject.getProjectName());
	}

//	@Test
//	public void test_deleteProject() {
//		Date startDate = new Date();
//		Date endDate = new Date();
//		List<Employee> emp = null;
//		Client client = null;
//		Project project = new Project(15, "ABC Service", "This is ABC project", startDate, endDate, emp, client);
//		when(projectRepo.findById(15)).thenReturn(Optional.of(project));
//		projectService.deleteProject(15);
//		verify(projectRepo, times(1)).delete(project);
//
//	}
	
	
}
