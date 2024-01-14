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
import com.api.entities.Holiday;
import com.api.repositories.HolidayRepo;
import com.api.service.HolidayService;


@SpringBootTest
public class HolidayServiceImplTest {

	@MockBean
	HolidayRepo holidayRepo;

	@Autowired
	HolidayService holidayService;

	@Test
	public void test_saveHoliday() {
		Date date = new Date();
		Employee emp=null;
		Holiday holiday = new Holiday(100, "Test Holiday", date,emp);
		when(holidayRepo.save(holiday)).thenReturn(holiday);
		assertEquals(holiday, holidayService.createHoliday(holiday));
	}
	
	@Test
	public void test_saveHolidayCrossCheck() {
		Date date = new Date();
		Employee emp=null;
		Holiday holiday1 = new Holiday(101, "Test Holiday", date,emp);
		Holiday holiday2 = new Holiday(102, "Test2 Holiday", date,emp);
		when(holidayRepo.save(holiday1)).thenReturn(holiday1);
		assertNotEquals(holiday1, holidayService.createHoliday(holiday2));
	}


	@Test
	public void test_getHolidayById() {
		Date date = new Date();
		Employee emp=null;
		Holiday holiday = new Holiday(1001, "Holiday Name", date,emp);
		when(holidayRepo.findById(1001)).thenReturn(Optional.of(holiday));
		assertEquals("Holiday Name", holidayService.getHolidayById(1001).getHolidayDesp());

	}
	
	
	@Test
	public void test_getHolidayByIdCrossCheck() {
		Date date = new Date();
		Employee emp=null;
		Holiday holiday = new Holiday(1021, "Holiday Name", date,emp);
		when(holidayRepo.findById(1021)).thenReturn(Optional.of(holiday));
		assertNotEquals("Unmatched Name", holidayService.getHolidayById(1021).getHolidayDesp());
	}


	@Test
	public void test_getHolidayList() {
		Date date = new Date();
		Employee emp=null;
		Holiday holiday1 = new Holiday(103, "Test1 Holiday", date ,emp);
		Holiday holiday2 = new Holiday(104, "Test2 Holiday", date ,emp);
		List<Holiday> holidayList = Arrays.asList(holiday1, holiday2);

		when(holidayRepo.findAll()).thenReturn(holidayList);
		assertEquals(2, holidayService.getAllHoliday().size());
	}
	
	@Test
	public void test_getHolidayListCrossCheck() {
		Date date = new Date();
		Employee emp=null;
		Holiday holiday1 = new Holiday(1503, "Test1 Holiday", date ,emp);
		Holiday holiday2 = new Holiday(1504, "Test2 Holiday", date ,emp);
		List<Holiday> holidayList = Arrays.asList(holiday1, holiday2);
		
		when(holidayRepo.findAll()).thenReturn(holidayList);
		assertNotEquals(3, holidayService.getAllHoliday().size());
	}
}
