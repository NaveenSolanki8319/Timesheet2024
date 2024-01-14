package com.api.controller;

import com.api.entities.Employee;
import com.api.entities.Holiday;
import com.api.repositories.EmployeeRepo;
import com.api.repositories.HolidayRepo;
import com.api.service.HolidayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class HolidayControllerTest {

    @Mock
    private EmployeeRepo employeeRepo;

    @Mock
    private HolidayService holidayService;

    @Mock
    private HolidayRepo holidayRepo;

    @InjectMocks
    private HolidayController holidayController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

   

    @Test
    void saveHoliday_WhenHolidayAlreadyExists_ReturnsBadRequestResponse() {
        // Arrange
        Holiday holiday = new Holiday();
        Date holidayDate = new Date();
        holiday.setHolidayDate(holidayDate);

        when(holidayRepo.existsByHolidayDate(holidayDate)).thenReturn(true);

        // Act
        ResponseEntity<?> response = holidayController.saveholiday(holiday, 1);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Holiday already exists", response.getBody());
    }

   
    @Test
    void getAllHoliday_WhenHolidaysExist_ReturnsListOfHolidays() {
        // Arrange
        List<Holiday> holidays = new ArrayList<>();
        holidays.add(new Holiday());
        holidays.add(new Holiday());

        when(holidayService.getAllHoliday()).thenReturn(holidays);

        // Act
        ResponseEntity<List<Holiday>> response = holidayController.saveholiday();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(holidays, response.getBody());
    }

    @Test
    void getHolidayOfYear_WhenHolidayExists_ReturnsHolidayList() {
        // Arrange
        int year = 2023;
        List<Object[]> holidayData = new ArrayList<>();
        holidayData.add(new Object[]{"Holiday 1", new Date()});
        holidayData.add(new Object[]{"Holiday 2", new Date()});

        when(holidayRepo.getHolidayOfYearWise(year)).thenReturn(holidayData);

        // Act
        ResponseEntity<?> response = holidayController.getHolidayOfYear(year);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(holidayData, response.getBody());
    }

    @Test
    void getHolidayOfYear_WhenNoHolidayExists_ReturnsNoHolidayResponse() {
        // Arrange
        int year = 2023;

        when(holidayRepo.getHolidayOfYearWise(year)).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<?> response = holidayController.getHolidayOfYear(year);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getHolidayOfAllYear_WhenHolidayExists_ReturnsHolidayList() {
        // Arrange
        List<Object[]> holidayData = new ArrayList<>();
        holidayData.add(new Object[]{"Holiday 1", new Date()});
        holidayData.add(new Object[]{"Holiday 2", new Date()});

        when(holidayRepo.getHolidayOfAllOverYears()).thenReturn(holidayData);

        // Act
        ResponseEntity<?> response = holidayController.getHolidayOfAllYear();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(holidayData, response.getBody());
    }

    @Test
    void getHolidayOfAllYear_WhenNoHolidayExists_ReturnsNoHolidayResponse() {
        // Arrange
        when(holidayRepo.getHolidayOfAllOverYears()).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<?> response = holidayController.getHolidayOfAllYear();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getHolidayOfProjectYear_WhenHolidayExists_ReturnsHolidayList() {
        // Arrange
        int startYear = 2022;
        int endYear = 2023;
        List<Object[]> holidayData = new ArrayList<>();
        holidayData.add(new Object[]{"Holiday 1", new Date()});
        holidayData.add(new Object[]{"Holiday 2", new Date()});

        when(holidayRepo.getHolidayOfProjectYears(startYear, endYear)).thenReturn(holidayData);

        // Act
        ResponseEntity<?> response = holidayController.getHolidayOfProjectYear(startYear, endYear);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(holidayData, response.getBody());
    }

    @Test
    void getHolidayOfProjectYear_WhenNoHolidayExists_ReturnsNoHolidayResponse() {
        // Arrange
        int startYear = 2022;
        int endYear = 2023;

        when(holidayRepo.getHolidayOfProjectYears(startYear, endYear)).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<?> response = holidayController.getHolidayOfProjectYear(startYear, endYear);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getHolidayOfMonth_WhenHolidayExists_ReturnsHolidayList() {
        // Arrange
        int year = 2023;
        List<Object[]> holidayData = new ArrayList<>();
        holidayData.add(new Object[]{"Holiday 1", new Date()});
        holidayData.add(new Object[]{"Holiday 2", new Date()});

        when(holidayRepo.getHolidayOfMonthOfYear(year)).thenReturn(holidayData);

        // Act
        ResponseEntity<?> response = holidayController.getHolidayOfMonth(year);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(holidayData, response.getBody());
    }

    @Test
    void getHolidayOfMonth_WhenNoHolidayExists_ReturnsNoHolidayResponse() {
        // Arrange
        int year = 2023;

        when(holidayRepo.getHolidayOfMonthOfYear(year)).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<?> response = holidayController.getHolidayOfMonth(year);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getHolidayOfWeek_WhenHolidayExists_ReturnsHolidayList() {
        // Arrange
        int year = 2023;
        List<Object[]> holidayData = new ArrayList<>();
        holidayData.add(new Object[]{"Holiday 1", new Date()});
        holidayData.add(new Object[]{"Holiday 2", new Date()});

        when(holidayRepo.getHolidayOfWeekOfYear(year)).thenReturn(holidayData);

        // Act
        ResponseEntity<?> response = holidayController.getHolidayOfWeek(year);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(holidayData, response.getBody());
    }

    @Test
    void getHolidayOfWeek_WhenNoHolidayExists_ReturnsNoHolidayResponse() {
        // Arrange
        int year = 2023;

        when(holidayRepo.getHolidayOfWeekOfYear(year)).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<?> response = holidayController.getHolidayOfWeek(year);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }



    @Test
    void getHolidayOfWeekForTimesheet_WhenHolidayExists_ReturnsHolidayList() {
        // Arrange
        int week = 26;
        int year = 2023;
        List<Object[]> holidayData = new ArrayList<>();
        holidayData.add(new Object[]{"Holiday 1", new Date()});
        holidayData.add(new Object[]{"Holiday 2", new Date()});

        when(holidayRepo.getHolidayOfWeekOfYearTimeSheet(week, year)).thenReturn(holidayData);

        // Act
        ResponseEntity<?> response = holidayController.getHolidayOfWeekForTimesheet(week, year);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(holidayData, response.getBody());
    }

    @Test
    void getHolidayOfWeekForTimesheet_WhenNoHolidayExists_ReturnsNoHolidayResponse() {
        // Arrange
        int week = 26;
        int year = 2023;

        when(holidayRepo.getHolidayOfWeekOfYearTimeSheet(week, year)).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<?> response = holidayController.getHolidayOfWeekForTimesheet(week, year);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
