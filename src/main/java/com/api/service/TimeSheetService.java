package com.api.service;

import java.util.List;

import com.api.entities.TimeSheet;
import com.api.model.UpdateTimesheet;

public interface TimeSheetService {
	// Create
	public TimeSheet createTimeSheet(TimeSheet timeSheet);

	// Read
	public List<TimeSheet> getAllTimeSheet();

	public TimeSheet getTimeSheetById(int id);

	// Update
	public TimeSheet updateTimeSheet(UpdateTimesheet updateTimesheet);

}
