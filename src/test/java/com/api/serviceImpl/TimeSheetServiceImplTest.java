package com.api.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.api.entities.Employee;
import com.api.entities.TimeSheet;
import com.api.repositories.TimeSheetRepo;
import com.api.service.TimeSheetService;

@SpringBootTest
public class TimeSheetServiceImplTest {

	@MockBean
	TimeSheetRepo timeSheetRepo;

	@Autowired
	TimeSheetService timeSheetService;

	@Test
	public void test_saveTimeSheet() {
		Date date = new Date();
		Employee emp = null;
		TimeSheet timesheet = new TimeSheet(1, 8, date, emp, 1);

		when(timeSheetRepo.save(timesheet)).thenReturn(timesheet);
		assertEquals(timesheet, timeSheetService.createTimeSheet(timesheet));
	}
	
	@Test
	public void test_saveTimeSheetCrossCheck() {
		Date date = new Date();
		Employee emp = null;
		TimeSheet timesheet1 = new TimeSheet(2, 8, date, emp, 1);
		TimeSheet timesheet2 = new TimeSheet(3, 8, date, emp, 1);
		when(timeSheetRepo.save(timesheet1)).thenReturn(timesheet1);
		assertNotEquals(timesheet1, timeSheetService.createTimeSheet(timesheet2));
	}
	
	@Test
	public void test_getTimesheetList() {
		Date date = new Date();
		Employee emp = null;
		TimeSheet timesheet1 = new TimeSheet(4, 8, date, emp, 1);
		TimeSheet timesheet2 = new TimeSheet(5, 8, date, emp, 1);
		List<TimeSheet> timesheetList = Arrays.asList(timesheet1, timesheet2);
		when(timeSheetRepo.findAll()).thenReturn(timesheetList);
		assertEquals(2, timeSheetService.getAllTimeSheet().size());
	}
	
	@Test
	public void test_getTimesheetListCrossCkeck() {
		Date date = new Date();
		Employee emp = null;
		TimeSheet timesheet1 = new TimeSheet(6, 8, date, emp, 1);
		TimeSheet timesheet2 = new TimeSheet(7, 8, date, emp, 1);
		List<TimeSheet> timesheetList = Arrays.asList(timesheet1, timesheet2);
		when(timeSheetRepo.findAll()).thenReturn(timesheetList);
		assertNotEquals(3, timeSheetService.getAllTimeSheet().size());
	}

	@Test
	public void test_getTimeSheetById() {
		Date date = new Date();
		Employee emp = null;
		TimeSheet timesheet = new TimeSheet(80, 8, date, emp, 1);
		when(timeSheetRepo.findById(80)).thenReturn(Optional.of(timesheet));
		assertEquals(8, timeSheetService.getTimeSheetById(80).getWorkinghour());
	}
	
	@Test
	public void test_getTimeSheetByIdCrossCkeck() {
		Date date = new Date();
		Employee emp = null;
		TimeSheet timesheet = new TimeSheet(90, 8, date, emp, 1);
		when(timeSheetRepo.findById(90)).thenReturn(Optional.of(timesheet));
		assertNotEquals(4, timeSheetService.getTimeSheetById(90).getWorkinghour());
	}

	
	@Test
	public void test_updateTimesheet() {
		Date date = new Date();
		Employee emp = null;
		TimeSheet timesheet = new TimeSheet(101, 8, date, emp, 1);
		when(timeSheetRepo.save(timesheet)).thenReturn(timesheet);
		when(timeSheetRepo.findById(101)).thenReturn(Optional.of(timesheet));
		timesheet.setWorkinghour(4);
		TimeSheet updatedProject = timeSheetRepo.save(timesheet);
		assertEquals(4, updatedProject.getWorkinghour());
	}
	
	@Test
	public void test_updateTimesheetCrossCheck() {
		Date date = new Date();
		Employee emp = null;
		TimeSheet timesheet = new TimeSheet(1002, 8, date, emp, 1);
		when(timeSheetRepo.save(timesheet)).thenReturn(timesheet);
		when(timeSheetRepo.findById(1002)).thenReturn(Optional.of(timesheet));
		timesheet.setWorkinghour(4);
		TimeSheet updatedProject = timeSheetRepo.save(timesheet);
		assertNotEquals(8, updatedProject.getWorkinghour());
	}

	
}
