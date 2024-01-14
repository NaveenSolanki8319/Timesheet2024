package com.api.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.entities.Holiday;
import com.api.exceptions.ResourceNotFoundException;
import com.api.repositories.HolidayRepo;
import com.api.service.HolidayService;

@Service
public class HolidayServiceImpl implements HolidayService {
	@Autowired
	private HolidayRepo holidayRepo;

	// Create
	@Override
	public Holiday createHoliday(Holiday holiday) {

		return this.holidayRepo.save(holiday);
	}

	// Read
	@Override
	public List<Holiday> getAllHoliday() {
		return this.holidayRepo.findAll();
	}

	@Override
	public Holiday getHolidayById(int id) {
		return this.holidayRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Holiday", "Id", id));
	}

}
