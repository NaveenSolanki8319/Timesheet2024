package com.api.controller;


import com.api.entities.Client;
import com.api.entities.Employee;
import com.api.entities.Project;
import com.api.model.AssignProject;
import com.api.model.MapProjectToClient;
import com.api.repositories.ProjectRepo;
import com.api.service.ClientService;
import com.api.service.EmployeeService;
import com.api.service.ProjectService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;



public class ProjectControllerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private ProjectService projectService;

    @Mock
    private ClientService clientService;

    @Mock
    private ProjectRepo projectRepo;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    
    @Test
    public void addProject_ValidProject_ReturnsCreatedResponse() {
        // Arrange
        Project project = new Project();
        project.setStartDate(new Date());
        project.setEndDate(new Date());

        when(projectService.createProject(project)).thenReturn(project);

        // Act
        ResponseEntity<?> response = projectController.addProject(project);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(project, response.getBody());
    }

    @Test
    public void addProject_InvalidDates_ReturnsBadRequestResponse() {
        // Arrange
        Project project = new Project();
        project.setStartDate(new Date());
        project.setEndDate(new Date(System.currentTimeMillis() - 10000));

        // Act
        ResponseEntity<?> response = projectController.addProject(project);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Enter Correct Dates", response.getBody());
    }

    @Test
    public void getProjects_ReturnsAllProjects() {
        // Arrange
        List<Project> projects = Arrays.asList(
                new Project(1, "Project 1", null, new Date(), new Date(), null, null),
                new Project(2, "Project 2", null, new Date(), new Date(), null, null)
        );

        when(projectService.getAllProject()).thenReturn(projects);

        // Act
        ResponseEntity<List<Project>> response = projectController.getProjects();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    public void readAllValidProjects_ReturnsValidProjects() {
        // Arrange
    	List<Project> projects = Arrays.asList(
                new Project(1, "Project 1", null, new Date(), new Date(), null, null),
                new Project(2, "Project 2", null, new Date(), new Date(), null, null)
        );

        when(projectService.getAllProject()).thenReturn(projects);

        // Act
        ResponseEntity<List<Project>> response = projectController.readAllValidProjects();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    public void getAllCompletedProject_ReturnsCompletedProjects() {
        // Arrange
    	List<Project> projects = Arrays.asList(
                new Project(1, "Project 1", null, new Date(), new Date(), null, null),
                new Project(2, "Project 2", null, new Date(), new Date(), null, null)
        );
    	
        when(projectService.getAllProject()).thenReturn(projects);

        // Act
        ResponseEntity<List<Project>> response = projectController.getAllCompletedProject();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getAllUnmappedProject_ReturnsUnmappedProjects() {
        // Arrange
        List<Project> projects = Arrays.asList(
        		new Project(1, "Project 1", null, new Date(), new Date(), null, null),
        		new Project(2, "Project 2", null, new Date(), new Date(), null, null)
        );

        when(projectRepo.findByClientIsNull()).thenReturn(projects);

        // Act
        ResponseEntity<List<Project>> response = projectController.getAllUnmappedProject();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    public void readProjectById_ValidId_ReturnsProject() {
        // Arrange
        int projectId = 1;
        Project project = new Project(1, "Project 1", null, new Date(), new Date(), null, null);

        when(projectService.getProjectById(projectId)).thenReturn(project);

        // Act
        ResponseEntity<?> response = projectController.readProjectById(projectId);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(project, response.getBody());
    }

    @Test
    public void updateProject_InvalidEndDate_ReturnsBadRequestResponse() {
        // Arrange
        Project project = new Project(1, "Project 1", null, new Date(), new Date(System.currentTimeMillis() - 10000),null,null);

        // Act
        ResponseEntity<?> response = projectController.updateProject(project);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Please give correct end date", response.getBody());
    }

    @Test
    public void mapProjectWithClient_ValidMapping_ReturnsMappedProject() {
        // Arrange
        MapProjectToClient mapProjectToClient = new MapProjectToClient(1, 1);
        Client client = new Client();
        Project project = new Project(1, "Project 1", null, new Date(), new Date(), null, null);

        when(clientService.getClientById(mapProjectToClient.getClientId())).thenReturn(client);
        when(projectService.getProjectById(mapProjectToClient.getProjectId())).thenReturn(project);
        when(projectService.updateProject(project)).thenReturn(project);

        // Act
        ResponseEntity<?> response = projectController.mapProjectWithClient(mapProjectToClient);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(project, response.getBody());
        assertEquals(client, project.getClient());
    }

    @Test
    public void mapProjectWithClient_AlreadyMapped_ReturnsBadRequestResponse() {
        // Arrange
        MapProjectToClient mapProjectToClient = new MapProjectToClient(1, 1);
        Client client = new Client();
        Project project = new Project(1, "Project 1", null, new Date(), new Date(), null, null);
        project.setClient(client);

        when(clientService.getClientById(mapProjectToClient.getClientId())).thenReturn(client);
        when(projectService.getProjectById(mapProjectToClient.getProjectId())).thenReturn(project);

        // Act
        ResponseEntity<?> response = projectController.mapProjectWithClient(mapProjectToClient);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ploject is already mapped with client.", response.getBody());
    }

    
    @Test
    public void assignProject_ProjectNotMapped_ReturnsBadRequestResponse() {
        // Arrange
        AssignProject assignProject = new AssignProject(1, 1);
        Project project = new Project(1, "Project 1", null, new Date(), new Date(), null, null);

        when(projectService.getProjectById(assignProject.getProjectId())).thenReturn(project);

        // Act
        ResponseEntity<?> response = projectController.assignProject(assignProject);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Can't assign project to employee because it is not mapped with client.", response.getBody());
    }

    @Test
    public void assignProject_ProjectExpired_ReturnsBadRequestResponse() {
        // Arrange
        AssignProject assignProject = new AssignProject(1, 1);
        Employee employee = new Employee();
        Project project = new Project(1, "Project 1", null, new Date(System.currentTimeMillis() - 10000), new Date(), null, null);
       
        when(employeeService.getEmployeeById(assignProject.getEmployeeId())).thenReturn(employee);
        when(projectService.getProjectById(assignProject.getProjectId())).thenReturn(project);

        // Act
        ResponseEntity<?> response = projectController.assignProject(assignProject);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Can't assign project to employee because it is not mapped with client.", response.getBody());
    }

    @Test
    public void assignProject_AdminRole_ReturnsBadRequestResponse() {
        // Arrange
        AssignProject assignProject = new AssignProject(1, 1);
        Employee employee = new Employee();
        Project project = new Project(1, "Project 1", null, new Date(), new Date(), null, null);
        employee.setRole("ADMIN");

        when(employeeService.getEmployeeById(assignProject.getEmployeeId())).thenReturn(employee);
        when(projectService.getProjectById(assignProject.getProjectId())).thenReturn(project);

        // Act
        ResponseEntity<?> response = projectController.assignProject(assignProject);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Can't assign project to employee because it is not mapped with client.", response.getBody());
    }

    @Test
    public void assignProject_AlreadyAssigned_ReturnsBadRequestResponse() {
        // Arrange
        AssignProject assignProject = new AssignProject(1, 1);
        Employee employee = new Employee();
        Project project = new Project(1, "Project 1", null, new Date(), new Date(), null, null);
        employee.setProject(project);

        when(employeeService.getEmployeeById(assignProject.getEmployeeId())).thenReturn(employee);
        when(projectService.getProjectById(assignProject.getProjectId())).thenReturn(project);

        // Act
        ResponseEntity<?> response = projectController.assignProject(assignProject);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
