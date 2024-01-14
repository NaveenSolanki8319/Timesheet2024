package com.api.service;

import java.util.List;

import com.api.entities.Holiday;

public interface HolidayService {
	// create
	public Holiday createHoliday(Holiday holiday);

	// read
	public Holiday getHolidayById(int holidayId);

	public List<Holiday> getAllHoliday();

}
