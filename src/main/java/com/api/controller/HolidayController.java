package com.api.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.entities.Employee;
import com.api.entities.Holiday;
import com.api.repositories.EmployeeRepo;
import com.api.repositories.HolidayRepo;
import com.api.service.HolidayService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/holiday")
public class HolidayController {

	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	private HolidayService holidayService;

	@Autowired
	HolidayRepo holidayRepo;

	// Create holiday
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/save")
	public ResponseEntity<?> saveholiday(@Valid @RequestBody Holiday holiday, @RequestParam int adminId) {
		Date holidayDate = holiday.getHolidayDate();
		Calendar c = Calendar.getInstance();
		c.setTime(holidayDate);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

		if (holidayRepo.existsByHolidayDate(holidayDate)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Holiday already exists");
		}

		if (dayOfWeek == 1 || dayOfWeek == 7) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("It's a weekend date.");
		}
		Employee emp = employeeRepo.findByEmpId(adminId);
		holiday.setEmployee(emp);
		return ResponseEntity.status(HttpStatus.CREATED).body(this.holidayService.createHoliday(holiday));

	}

	// Read all holidays
	@GetMapping("/getAll")
	public ResponseEntity<List<Holiday>> saveholiday() {
		return ResponseEntity.ok(this.holidayService.getAllHoliday());
	}

	// Get Holiday Of a particular Year
	@GetMapping("/getHolidayOfYear")
	public ResponseEntity<?> getHolidayOfYear(@RequestParam int year) {
		try {
			List<Object[]> result = this.holidayRepo.getHolidayOfYearWise(year);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.ofNullable("No Holiday");
		}

	}

	// Get Holiday Of all Years
	@GetMapping("/getHolidayOfAllYear")
	public ResponseEntity<?> getHolidayOfAllYear() {
		try {
			List<Object[]> result = this.holidayRepo.getHolidayOfAllOverYears();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.ofNullable("No Holiday");
		}

	}

	// Get Holiday by project Year
	@GetMapping("/getHolidayOfProjectYear")
	public ResponseEntity<?> getHolidayOfProjectYear(@RequestParam int startYear, @RequestParam int endYear) {
		try {
			List<Object[]> result = this.holidayRepo.getHolidayOfProjectYears(startYear, endYear);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.ofNullable("No Holiday");
		}

	}

	// Get Holiday Of Month
	@GetMapping("/getHolidayOfMonth")
	public ResponseEntity<?> getHolidayOfMonth(@RequestParam int year) {

		try {
			List<Object[]> result = this.holidayRepo.getHolidayOfMonthOfYear(year);
			return ResponseEntity.ok(result);
		} catch (Exception e) {

			return ResponseEntity.ofNullable("No Holiday");
		}
	}

	// Get Holiday Of Week
	@GetMapping("/getHolidayOfWeek")
	public ResponseEntity<?> getHolidayOfWeek(@RequestParam int year) {
		try {
			List<Object[]> result = this.holidayRepo.getHolidayOfWeekOfYear(year);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.ofNullable("No Holiday  ");
		}
	}

	// Get Holiday By Date
	@GetMapping("/getHolidayByDate")
	public ResponseEntity<?> getHolidayOfWeekTS(@RequestParam int week, @RequestParam int year) {
		try {
			List<Object[]> result = this.holidayRepo.getHolidayByDate(week, year);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.ofNullable("No Holiday");
		}
	}

//	Get Holiday Of Week for timesheet filling
	@GetMapping("/getHolidayOfWeekTS")
	public ResponseEntity<?> getHolidayOfWeekForTimesheet(@RequestParam int week, @RequestParam int year) {
		try {
			List<Object[]> result = this.holidayRepo.getHolidayOfWeekOfYearTimeSheet(week, year);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.ofNullable("No Holiday.");
		}
	}

}
