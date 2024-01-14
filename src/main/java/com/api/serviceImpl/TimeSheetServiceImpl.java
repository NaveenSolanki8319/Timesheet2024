package com.api.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.entities.TimeSheet;
import com.api.exceptions.ResourceNotFoundException;
import com.api.model.UpdateTimesheet;
import com.api.repositories.TimeSheetRepo;
import com.api.service.TimeSheetService;

@Service
public class TimeSheetServiceImpl implements TimeSheetService {
	@Autowired
	private TimeSheetRepo timeSheetRepo;

	// Create
	@Override
	public TimeSheet createTimeSheet(TimeSheet timeSheet) {
		return this.timeSheetRepo.save(timeSheet);
	}

	// Read
	@Override
	public List<TimeSheet> getAllTimeSheet() {
		return this.timeSheetRepo.findAll();
	}

	@Override
	public TimeSheet getTimeSheetById(int id) {
		return this.timeSheetRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Timesheet", "Id", id));
	}

	// Update timesheet
	@Override
	public TimeSheet updateTimeSheet(UpdateTimesheet updateTimesheet) {
		TimeSheet updatedTimesheet = this.timeSheetRepo.findById(updateTimesheet.getTimesheetId())
				.orElseThrow(() -> new ResourceNotFoundException("Timesheet", "Id", updateTimesheet.getTimesheetId()));
		updatedTimesheet.setWorkinghour(updateTimesheet.getWorkingHour());
		return this.timeSheetRepo.save(updatedTimesheet);
	}

}
