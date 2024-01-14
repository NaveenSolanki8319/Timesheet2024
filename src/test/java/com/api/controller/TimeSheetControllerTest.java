package com.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.api.entities.Project;
import com.api.entities.TimeSheet;
import com.api.model.AssignTimesheet;
import com.api.model.UpdateTimesheet;
import com.api.repositories.TimeSheetRepo;
import com.api.service.EmployeeService;
import com.api.service.ProjectService;
import com.api.service.TimeSheetService;

@SpringBootTest
public class TimeSheetControllerTest {

	@Mock
	private TimeSheetRepo timesheetRepo;

	@Mock
	private EmployeeService employeeService;

	@Mock
	private TimeSheetService timeSheetService;

	@Mock
	private ProjectService projectService;

	@Mock
	private AssignTimesheet assignTimeSheet;

	@InjectMocks
	private TimeSheetController timeSheetController;

	@Test
	public void test_getWorkingHourOfYear() {
		List<Object[]> projectList = new ArrayList<>();
		projectList.add(new Object[] { 2020, 8000, 100 });
		projectList.add(new Object[] { 2021, 9000, 100 });
		Project project = new Project(10, null, null, null, null, null, null);

		Mockito.when(timesheetRepo.getWokingHourByYear(10)).thenReturn(projectList);
		Mockito.when(projectService.getProjectById(10)).thenReturn(project);

		ResponseEntity<?> response = timeSheetController.getWorkingHourOfYear(10);
		System.out.println(response);
//		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		assertEquals(projectList, response.getBody());

		Mockito.verify(timesheetRepo).getWokingHourByYear(10);

	}

	@Test
	public void test_getWorkingHourOfMonth() {
		List<Object[]> resultList = new ArrayList<>();
		resultList.add(new Object[] { 1, 2022, 50, 1 });
		resultList.add(new Object[] { 2, 2022, 75, 1 });
		Project project = new Project(10, null, null, null, null, null, null);

		Mockito.when(timesheetRepo.getWokingHourByMonth(10, 2022)).thenReturn(resultList);
		Mockito.when(projectService.getProjectById(10)).thenReturn(project);

		ResponseEntity<?> response = timeSheetController.getWorkingHourOfMonth(10, 2022);
		System.out.println(response);
//		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		assertEquals(resultList, response.getBody());

		Mockito.verify(timesheetRepo).getWokingHourByMonth(10, 2022);

	}

	@Test
	public void test_getWorkingHourOfWeek() {
		List<Object[]> resultList = new ArrayList<>();
		resultList.add(new Object[] { 1, 2022, 30, 1 });
		resultList.add(new Object[] { 2, 2022, 40, 1 });
		Project project = new Project(10, null, null, null, null, null, null);

		Mockito.when(timesheetRepo.getWokingHourByWeek(10, 2022)).thenReturn(resultList);
		Mockito.when(projectService.getProjectById(10)).thenReturn(project);

		ResponseEntity<?> response = timeSheetController.getWorkingHourOfWeek(10, 2022);

//		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
//		assertNotEquals(resultList, response.getBody());
		assertEquals(resultList, response.getBody());

		Mockito.verify(timesheetRepo).getWokingHourByWeek(10, 2022);

	}

	@Test
	public void testUpdateTimesheetByEmployeeID() {
		// Mock the update time sheet data
		UpdateTimesheet updateTimesheet = new UpdateTimesheet();

		// Mock the updated time sheet
		TimeSheet updatedTimeSheet = new TimeSheet();
		when(timeSheetService.updateTimeSheet(updateTimesheet)).thenReturn(updatedTimeSheet);

		// Call the API
		ResponseEntity<?> response = timeSheetController.updateTimesheetByEmployeeID(updateTimesheet);

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(updatedTimeSheet, response.getBody());
	}

	@Test
	public void testGetTimesheetFillDateOfWeek() {
		// Mock the employee ID
		int eid = 1;

		// Mock the result data
		List<Object[]> result = new ArrayList<>();
		when(timesheetRepo.getTimesheetPreviousFilledDate(eid)).thenReturn(result);

		// Call the API
		ResponseEntity<?> response = timeSheetController.getTimesheetFillDateOfWeek(eid);

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(result, response.getBody());
	}

	@Test
	public void testGetWeekOfTSFilledByEmployee() {
		// Mock the employee ID and week
		int employeeId = 1;
		int week = 1;

		// Mock the result data
		List<Object[]> result = new ArrayList<>();
		when(timesheetRepo.getWeekOfTSFilledByEMployee(employeeId, week)).thenReturn(result);

		// Call the API
		ResponseEntity<?> response = timeSheetController.getWeekOfTSFilledByEmployee(employeeId, week);

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(result, response.getBody());
	}
}
