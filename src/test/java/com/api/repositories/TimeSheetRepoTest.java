package com.api.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.api.entities.Employee;
import com.api.entities.Project;
import com.api.entities.TimeSheet;

@SpringBootTest
public class TimeSheetRepoTest {

	@MockBean
	TimeSheetRepo timeSheetRepo;

	@Test
	public void testFindByTimesheetid() {

		TimeSheet timeSheet = new TimeSheet();
		timeSheet.setTimesheetid(1);
		when(timeSheetRepo.findByTimesheetid(1)).thenReturn(timeSheet);

		// Calling the repository method
		TimeSheet result = timeSheetRepo.findByTimesheetid(1);

		// Verify the result
		assertEquals(1, result.getTimesheetid());
		verify(timeSheetRepo, times(1)).findByTimesheetid(1);
	}

	@Test
	public void testFindByTimesheetidCrossCheck() {

		TimeSheet timeSheet = new TimeSheet();
		timeSheet.setTimesheetid(2);
		when(timeSheetRepo.findByTimesheetid(2)).thenReturn(timeSheet);

		// Calling the repository method
		TimeSheet result = timeSheetRepo.findByTimesheetid(2);

		// Verify the result
		assertNotEquals(1, result.getTimesheetid());
		verify(timeSheetRepo, times(1)).findByTimesheetid(2);
	}

	@Test
	public void testFindByProjectId() {
		// Mocking the repository method
		Date date = new Date();
		Employee emp = null;
		TimeSheet timeSheet1 = new TimeSheet(1, 8, date, emp, 1);
		TimeSheet timeSheet2 = new TimeSheet(2, 8, date, emp, 1);

		List<TimeSheet> timeSheets = Arrays.asList(timeSheet1, timeSheet2);
		when(timeSheetRepo.findByProjectId(1)).thenReturn(timeSheets);

		// Calling the repository method
		List<TimeSheet> result = timeSheetRepo.findByProjectId(1);

		// Verify the result
		assertEquals(2, result.size());
		verify(timeSheetRepo, times(1)).findByProjectId(1);
	}

	@Test
	public void testFindByProjectIdCrossCheck() {
		// Mocking the repository method
		Date date = new Date();
		Employee emp = null;
		TimeSheet timeSheet1 = new TimeSheet(1, 8, date, emp, 1);
		TimeSheet timeSheet2 = new TimeSheet(2, 8, date, emp, 1);

		List<TimeSheet> timeSheets = Arrays.asList(timeSheet1, timeSheet2);
		when(timeSheetRepo.findByProjectId(1)).thenReturn(timeSheets);

		// Calling the repository method
		List<TimeSheet> result = timeSheetRepo.findByProjectId(1);

		// Verify the result
		assertNotEquals(3, result.size());
		verify(timeSheetRepo, times(1)).findByProjectId(1);
	}

	@Test
	public void testFindByEmployeeObj() {
		// Create an Employee object
		Project project = null;
		List<TimeSheet> timesheet = null;
		Employee employee = new Employee(100, "Test", "test@nucleusteq.com", "EMPLOYEE", "123456", "IT", "Indore",
				"Inactive", project, timesheet, null);

		// Create a list of TimeSheet objects
		List<TimeSheet> timeSheets = new ArrayList<>();
		timeSheets.add(new TimeSheet());
		timeSheets.add(new TimeSheet());

		// Mock the repository method to return the list of TimeSheet objects
		when(timeSheetRepo.findByEmployeeObj(employee)).thenReturn(timeSheets);

		// Call the repository method
		List<TimeSheet> result = timeSheetRepo.findByEmployeeObj(employee);

		// Verify the result
		assertEquals(2, result.size());
		verify(timeSheetRepo, times(1)).findByEmployeeObj(employee);
	}

	@Test
	public void testGetWorkingHourByYear() {
		// Create a list of Object arrays with mock data
		List<Object[]> projectList = new ArrayList<>();
		projectList.add(new Object[] { 2020, 8000, 100 });
		projectList.add(new Object[] { 2021, 9000, 100 });

		// Mock the repository method to return the list of Object arrays
		when(timeSheetRepo.getWokingHourByYear(100)).thenReturn(projectList);

		// Call the repository method
		List<Object[]> result = timeSheetRepo.getWokingHourByYear(100);

		// Verify the result
		assertEquals(2, result.size());
		assertEquals(2020, result.get(0)[0]);
		assertEquals(8000, result.get(0)[1]);
		assertEquals(100, result.get(0)[2]);
		assertEquals(2021, result.get(1)[0]);
		assertEquals(9000, result.get(1)[1]);
		assertEquals(100, result.get(1)[2]);
		verify(timeSheetRepo, times(1)).getWokingHourByYear(100);
	}

	@Test
	public void testGetWorkingHourByMonth() {
		// Create a list of Object arrays with mock data
		List<Object[]> resultList = new ArrayList<>();
		resultList.add(new Object[] { 1, 2022, 50, 1 });
		resultList.add(new Object[] { 2, 2022, 75, 1 });

		// Mock the repository method to return the list of Object arrays
		when(timeSheetRepo.getWokingHourByMonth(1, 2022)).thenReturn(resultList);

		// Call the repository method
		List<Object[]> result = timeSheetRepo.getWokingHourByMonth(1, 2022);

		// Verify the result
		assertEquals(2, result.size());
		assertEquals(1, result.get(0)[0]);
		assertEquals(2022, result.get(0)[1]);
		assertEquals(50, result.get(0)[2]);
		assertEquals(1, result.get(0)[3]);
		assertEquals(2, result.get(1)[0]);
		assertEquals(2022, result.get(1)[1]);
		assertEquals(75, result.get(1)[2]);
		assertEquals(1, result.get(1)[3]);
		verify(timeSheetRepo, times(1)).getWokingHourByMonth(1, 2022);
	}

	@Test
	public void testGetWorkingHourByWeek() {
		// Create a list of Object arrays with mock data
		List<Object[]> resultList = new ArrayList<>();
		resultList.add(new Object[] { 1, 2022, 30, 1 });
		resultList.add(new Object[] { 2, 2022, 40, 1 });

		// Mock the repository method to return the list of Object arrays
		when(timeSheetRepo.getWokingHourByWeek(1, 2022)).thenReturn(resultList);

		// Call the repository method
		List<Object[]> result = timeSheetRepo.getWokingHourByWeek(1, 2022);

		// Verify the result
		assertEquals(2, result.size());
		assertEquals(1, result.get(0)[0]);
		assertEquals(2022, result.get(0)[1]);
		assertEquals(30, result.get(0)[2]);
		assertEquals(1, result.get(0)[3]);
		assertEquals(2, result.get(1)[0]);
		assertEquals(2022, result.get(1)[1]);
		assertEquals(40, result.get(1)[2]);
		assertEquals(1, result.get(1)[3]);
		verify(timeSheetRepo, times(1)).getWokingHourByWeek(1, 2022);
	}

	
	@Test
	public void testGetTimesheetPreviousFilledDate() {
		// Create a list of Object arrays with mock data
		List<Object[]> resultList = new ArrayList<>();
		resultList.add(new Object[] { "2022-01-01" });

		// Mock the repository method to return the list of Object arrays
		when(timeSheetRepo.getTimesheetPreviousFilledDate(1)).thenReturn(resultList);

		// Call the repository method
		List<Object[]> result = timeSheetRepo.getTimesheetPreviousFilledDate(1);

		// Verify the result
		assertEquals(1, result.size());
		assertEquals("2022-01-01", result.get(0)[0]);
		verify(timeSheetRepo, times(1)).getTimesheetPreviousFilledDate(1);
	}

}
