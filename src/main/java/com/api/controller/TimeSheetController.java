package com.api.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.entities.Employee;
import com.api.entities.Project;
import com.api.entities.TimeSheet;
import com.api.model.AssignTimesheet;
import com.api.model.UpdateTimesheet;
import com.api.repositories.TimeSheetRepo;
import com.api.service.EmployeeService;
import com.api.service.ProjectService;
import com.api.service.TimeSheetService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/timesheet")
public class TimeSheetController {
	@Autowired
	private TimeSheetService timeSheetService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	TimeSheetRepo timeSheetRepo;

	@Autowired
	ProjectService projectService;

	// Read all timesheets
	@PreAuthorize("hasAuthority('EMPLOYEE')")
	@GetMapping("/getAll")
	public ResponseEntity<List<TimeSheet>> readTimeSheets() {
		return ResponseEntity.ok(this.timeSheetService.getAllTimeSheet());
	}

	// Read timesheet by id
	@GetMapping("/get/{id}")
	public ResponseEntity<?> readTimeSheetById(@Valid @PathVariable("id") Integer id) {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.timeSheetService.getTimeSheetById(id));
	}

	// Fill timesheet by employee
	@PreAuthorize("hasAuthority('EMPLOYEE')")
	@PostMapping("/fillTimesheet")
	public ResponseEntity<?> assignTimeSheet(@Valid @RequestBody AssignTimesheet assignTimesheet) {

		Employee emp = this.employeeService.getEmployeeById(assignTimesheet.getEmpId());

		String userRole = emp.getRole();
		if (userRole.equalsIgnoreCase("ADMIN")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't fill timesheet by admin");
		}

		Project project = emp.getProject();
		if (project == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Don't assign a project yet so you can't fill timesheet.");
		}

		Date currDate = new Date();
		Date projectEndDate = emp.getProject().getEndDate();
		if (currDate.compareTo(projectEndDate) > 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Project is Expired.");
		}
		TimeSheet timeSheet = new TimeSheet();
		int projectIdOfEmployee = emp.getProject().getProject_id();
		timeSheet.setProjectId(projectIdOfEmployee);
		timeSheet.setEmployeeObj(emp);
		timeSheet.setFillDate(assignTimesheet.getFillDate());
//		timeSheet.setFillDate(Calendar.getInstance().getTime());
		timeSheet.setWorkinghour(assignTimesheet.getWorkingHour());
		return ResponseEntity.status(HttpStatus.CREATED).body(this.timeSheetService.createTimeSheet(timeSheet));
	}

	// To get list of timesheet by project Id
	@GetMapping("/timesheetByProjectId")
	public ResponseEntity<List<TimeSheet>> getTimesheetByProjectId(@RequestParam int projectId) {
		List<TimeSheet> timesheet = this.timeSheetRepo.findByProjectId(projectId);

		return ResponseEntity.ok(timesheet);
	}

	// To get list of timesheet by employee Id
	@PreAuthorize("hasAuthority('EMPLOYEE')")
	@GetMapping("/timeSheetByempId")
	public ResponseEntity<?> getEmployeesByEmployeeId(@RequestParam int empId) {
		try {
			Employee emp = this.employeeService.getEmployeeById(empId);
			List<TimeSheet> timesheetList = this.timeSheetRepo.findByEmployeeObj(emp);
			return ResponseEntity.ok(timesheetList);
		} catch (Exception e) {
			return ResponseEntity.ofNullable("Emploee not exist by I.D.");
		}
	}

	// To get Working Hour Of Year
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/getWorkingHourOfYear")
	public ResponseEntity<?> getWorkingHourOfYear(@RequestParam int projectId) {

		try {
			Project project = this.projectService.getProjectById(projectId);
			int projectIdToPass = project.getProject_id();
			List<Object[]> result = this.timeSheetRepo.getWokingHourByYear(projectIdToPass);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.ofNullable(null);
		}

	}

	// To get Working Hour Of Month
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/getWorkingHourOfMonth")
	public ResponseEntity<?> getWorkingHourOfMonth(@RequestParam int projectId, @RequestParam int year) {
		try {
			Project project = this.projectService.getProjectById(projectId);
			int projectIdToPass = project.getProject_id();
			List<Object[]> result = this.timeSheetRepo.getWokingHourByMonth(projectIdToPass, year);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.ofNullable("No Woking Hour");
		}
	}

	// To get Working Hour Of Week
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/getWorkingHourOfWeek")
	public ResponseEntity<?> getWorkingHourOfWeek(@RequestParam int projectId, @RequestParam int year) {
		try {
			Project project = this.projectService.getProjectById(projectId);
			int projectIdToPass = project.getProject_id();
			List<Object[]> result = this.timeSheetRepo.getWokingHourByWeek(projectIdToPass, year);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.ofNullable("No Woking Hour");
		}
	}

	// Update Timesheet By Employee I.D.
	@PreAuthorize("hasAuthority('EMPLOYEE')")
	@PutMapping("/updateTimesheet")
	public ResponseEntity<?> updateTimesheetByEmployeeID(@Valid @RequestBody UpdateTimesheet updateTimesheet) {
		TimeSheet result = this.timeSheetService.updateTimeSheet(updateTimesheet);
		return ResponseEntity.ok(result);

	}

	// Get Previous Timesheet Fill Date
	@GetMapping("/getPreviousTimesheetFillDate")
	public ResponseEntity<?> getTimesheetFillDateOfWeek(@RequestParam int eid) {
		try {
			List<Object[]> result = this.timeSheetRepo.getTimesheetPreviousFilledDate(eid);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.ofNullable("No Holiday");
		}
	}

	// Checking Employee filled timesheet of current week or not
	@GetMapping("/getPreviousTimesheet")
	public ResponseEntity<?> getWeekOfTSFilledByEmployee(@RequestParam int employeeId, @RequestParam int week) {
		List<Object[]> result = this.timeSheetRepo.getWeekOfTSFilledByEMployee(employeeId, week);
		
		return ResponseEntity.ok(result);
	}
}
