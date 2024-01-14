package com.api.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.entities.Client;
import com.api.entities.Employee;
import com.api.entities.Project;
import com.api.model.AssignProject;
import com.api.model.MapProjectToClient;
import com.api.repositories.ProjectRepo;
import com.api.service.ClientService;
import com.api.service.EmployeeService;
import com.api.service.ProjectService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/project")
@PreAuthorize("hasAuthority('ADMIN')")
public class ProjectController {
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private ProjectRepo projectRepo;

	// Create project
	@PostMapping("/save")
	public ResponseEntity<?> addProject(@Valid @RequestBody Project project) {

		Date projectStartDateToInsert = project.getStartDate();
		Date projectEndToInsert = project.getEndDate();

		if (projectStartDateToInsert.compareTo(projectEndToInsert) > 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enter Correct Dates");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(this.projectService.createProject(project));
	}

	// Get all projects
	@GetMapping("/getAll")
	public ResponseEntity<List<Project>> getProjects() {

		return ResponseEntity.ok(this.projectService.getAllProject());
	}

	// Get all projects which are not expired
	@GetMapping("/getAllActiveProject")
	public ResponseEntity<List<Project>> readAllValidProjects() {
		List<Project> projectList = this.projectService.getAllProject();
		List<Project> validProjectList = new ArrayList<>();
		for (Project p : projectList) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.now();

			if ((p.getEndDate().toString()).compareTo(dtf.format(localDate).toString()) >= 0) {
				validProjectList.add(p);
			}

		}
		return ResponseEntity.ok(validProjectList);
	}

	// Get all projects which are expired
	@GetMapping("/getAllCompletedProject")
	public ResponseEntity<List<Project>> getAllCompletedProject() {
		List<Project> projectList = this.projectService.getAllProject();
		List<Project> completedProjectList = new ArrayList<>();
		for (Project p : projectList) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.now();

			if ((dtf.format(localDate).toString()).compareTo(p.getEndDate().toString()) >= 0) {
				completedProjectList.add(p);
			}

		}
		return ResponseEntity.ok(completedProjectList);
	}

	// Get all projects which are not mapped with client
	@GetMapping("/getAllUnmappedProject")
	public ResponseEntity<List<Project>> getAllUnmappedProject() {
		List<Project> projectList = this.projectRepo.findByClientIsNull();
		List<Project> validProjectList = new ArrayList<>();
		for (Project p : projectList) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.now();

			if ((p.getEndDate().toString()).compareTo(dtf.format(localDate).toString()) >= 0) {
				validProjectList.add(p);
			}

		}
		return ResponseEntity.ok(validProjectList);
	}

	// Read project by id
	@GetMapping("/get/{id}")
	public ResponseEntity<?> readProjectById(@Valid @PathVariable("id") Integer id) {

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.projectService.getProjectById(id));

	}

	// Update project
	@PutMapping("/update")
	public ResponseEntity<?> updateProject(@RequestBody Project project) {

		Date startDate = project.getStartDate();
		Date projectEndDate = project.getEndDate();

		if (startDate.compareTo(projectEndDate) >= 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please give correct end date");
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.projectService.updateProject(project));

	}

	// Assigning Project to Employee
	@PutMapping("/mapProjectWithClient")
	public ResponseEntity<?> mapProjectWithClient(@RequestBody MapProjectToClient mapProjectToClient) {

		Client client = clientService.getClientById(mapProjectToClient.getClientId());
		Project mapProject = projectService.getProjectById(mapProjectToClient.getProjectId());

		if (mapProject.getClient() != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ploject is already mapped with client.");
		}

		mapProject.setClient(client);
		this.projectService.updateProject(mapProject);
		return ResponseEntity.status(HttpStatus.CREATED).body(mapProject);
	}

	// Assigning Project to Employee
	@PutMapping("/assignProject")
	public ResponseEntity<?> assignProject(@RequestBody AssignProject assignProject) {
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
		Employee employee = employeeService.getEmployeeById(assignProject.getEmployeeId());
		Project allocateProject = projectService.getProjectById(assignProject.getProjectId());

		if (allocateProject.getClient() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Can't assign project to employee because it is not mapped with client.");

		}

		Date currDate = Calendar.getInstance().getTime();
		Date projectEndDate = allocateProject.getEndDate();
		if (currDate.compareTo(projectEndDate) >= 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Project is Expired.");
		}

		String userRole = employee.getRole();
		if (userRole.equalsIgnoreCase("ADMIN")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't assign project to admin");
		}

		Project project = employee.getProject();
		if (project != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee is already assignd to project.");
		}

		employee.setStatus("Active");
		employee.setProject(allocateProject);
		this.employeeService.updateEmployee(employee);
		return ResponseEntity.status(HttpStatus.CREATED).body(employee);
	}
}
