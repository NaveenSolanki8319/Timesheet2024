package com.api.repositories;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class HolidayRepoTest {

	@MockBean
	private HolidayRepo holidayRepo;

	@Test
	public void testGetHolidayOfAllOverYears() {
		// Prepare mock data
		Object[] row1 = new Object[] { 2L, 2021 };
		Object[] row2 = new Object[] { 1L, 2022 };
		List<Object[]> expectedResult = Arrays.asList(row1, row2);
		Mockito.when(holidayRepo.getHolidayOfAllOverYears()).thenReturn(expectedResult);

		// Perform the query
		List<Object[]> result = holidayRepo.getHolidayOfAllOverYears();

		// Assert the result
		assertEquals(expectedResult.size(), result.size());
		assertEquals(expectedResult.get(0)[0], result.get(0)[0]);
		assertEquals(expectedResult.get(0)[1], result.get(0)[1]);
		assertEquals(expectedResult.get(1)[0], result.get(1)[0]);
		assertEquals(expectedResult.get(1)[1], result.get(1)[1]);

		// Verify the method call
		Mockito.verify(holidayRepo).getHolidayOfAllOverYears();
	}

	@Test
	public void testGetHolidayOfProjectYears() {
		// Prepare mock data
		Object[] row1 = new Object[] { 1L, 2022 };
		Object[] row2 = new Object[] { 1L, 2023 };
		List<Object[]> expectedResult = Arrays.asList(row1, row2);
		Mockito.when(holidayRepo.getHolidayOfProjectYears(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(expectedResult);

		// Perform the query
		List<Object[]> result = holidayRepo.getHolidayOfProjectYears(2022, 2023);

		// Assert the result
		assertEquals(expectedResult.size(), result.size());
		assertEquals(expectedResult.get(0)[0], result.get(0)[0]);
		assertEquals(expectedResult.get(0)[1], result.get(0)[1]);
		assertEquals(expectedResult.get(1)[0], result.get(1)[0]);
		assertEquals(expectedResult.get(1)[1], result.get(1)[1]);

		// Verify the method call
		Mockito.verify(holidayRepo).getHolidayOfProjectYears(Mockito.anyInt(), Mockito.anyInt());
	}

	@Test
	public void testGetHolidayOfYearWise() {
		// Prepare mock data
		Object[] row1 = new Object[] { 2L, 2021 };
		Object[] row2 = new Object[] { 1L, 2022 };
		List<Object[]> expectedResult = Arrays.asList(row1, row2);
		Mockito.when(holidayRepo.getHolidayOfYearWise(Mockito.anyInt())).thenReturn(expectedResult);

		// Perform the query
		List<Object[]> result = holidayRepo.getHolidayOfYearWise(2022);

		// Assert the result
		assertEquals(expectedResult.size(), result.size());
		assertEquals(expectedResult.get(0)[0], result.get(0)[0]);
		assertEquals(expectedResult.get(0)[1], result.get(0)[1]);
		assertEquals(expectedResult.get(1)[0], result.get(1)[0]);
		assertEquals(expectedResult.get(1)[1], result.get(1)[1]);

		// Verify the method call
		Mockito.verify(holidayRepo).getHolidayOfYearWise(Mockito.anyInt());
	}

	@Test
	public void testGetHolidayOfMonthOfYear() {
		// Prepare mock data
		Object[] row1 = new Object[] { 2L, 1 };
		Object[] row2 = new Object[] { 1L, 2 };
		List<Object[]> expectedResult = Arrays.asList(row1, row2);
		Mockito.when(holidayRepo.getHolidayOfMonthOfYear(Mockito.anyInt())).thenReturn(expectedResult);

		// Perform the query
		List<Object[]> result = holidayRepo.getHolidayOfMonthOfYear(2022);

		// Assert the result
		assertEquals(expectedResult.size(), result.size());
		assertEquals(expectedResult.get(0)[0], result.get(0)[0]);
		assertEquals(expectedResult.get(0)[1], result.get(0)[1]);
		assertEquals(expectedResult.get(1)[0], result.get(1)[0]);
		assertEquals(expectedResult.get(1)[1], result.get(1)[1]);

		// Verify the method call
		Mockito.verify(holidayRepo).getHolidayOfMonthOfYear(Mockito.anyInt());
	}

	@Test
	public void testGetHolidayOfWeekOfYear() {
		// Prepare mock data
		Object[] row1 = new Object[] { 2L, 1 };
		Object[] row2 = new Object[] { 1L, 2 };
		List<Object[]> expectedResult = Arrays.asList(row1, row2);
		Mockito.when(holidayRepo.getHolidayOfWeekOfYear(Mockito.anyInt())).thenReturn(expectedResult);

		// Perform the query
		List<Object[]> result = holidayRepo.getHolidayOfWeekOfYear(2022);

		// Assert the result
		assertEquals(expectedResult.size(), result.size());
		assertEquals(expectedResult.get(0)[0], result.get(0)[0]);
		assertEquals(expectedResult.get(0)[1], result.get(0)[1]);
		assertEquals(expectedResult.get(1)[0], result.get(1)[0]);
		assertEquals(expectedResult.get(1)[1], result.get(1)[1]);

		// Verify the method call
		Mockito.verify(holidayRepo).getHolidayOfWeekOfYear(Mockito.anyInt());
	}

	@Test
	public void testGetHolidayByDate() {
		// Prepare mock data
		Object[] row1 = new Object[] { "Sunday" };
		Object[] row2 = new Object[] { "Monday" };
		List<Object[]> expectedResult = Arrays.asList(row1, row2);
		Mockito.when(holidayRepo.getHolidayByDate(Mockito.anyInt(), Mockito.anyInt())).thenReturn(expectedResult);

		// Perform the query
		List<Object[]> result = holidayRepo.getHolidayByDate(1, 2022);

		// Assert the result
		assertEquals(expectedResult.size(), result.size());
		assertEquals(expectedResult.get(0)[0], result.get(0)[0]);
		assertEquals(expectedResult.get(1)[0], result.get(1)[0]);

		// Verify the method call
		Mockito.verify(holidayRepo).getHolidayByDate(Mockito.anyInt(), Mockito.anyInt());
	}

	@Test
	public void testExistsByHolidayDate() {
		// Prepare mock data
		Date holidayDate = new Date();
		Mockito.when(holidayRepo.existsByHolidayDate(Mockito.any())).thenReturn(true);

		// Perform the query
		boolean result = holidayRepo.existsByHolidayDate(holidayDate);

		// Assert the result
		assertTrue(result);

		// Verify the method call
		Mockito.verify(holidayRepo).existsByHolidayDate(Mockito.any());
	}
	
	@Test
    public void testGetHolidayOfWeekOfYearTimeSheet() {
        // Define test data
        int weekArg = 1;
        int yearArg = 2023;

        // Create a list of Object[] to represent the expected query result
        List<Object[]> expectedQueryResult = new ArrayList<>();
        Object[] row1 = { 1 };
        Object[] row2 = { 4 };
        expectedQueryResult.add(row1);
        expectedQueryResult.add(row2);

        // Mock the behavior of the holidayRepo.getHolidayOfWeekOfYearTimeSheet() method
        when(holidayRepo.getHolidayOfWeekOfYearTimeSheet(weekArg, yearArg))
                .thenReturn(expectedQueryResult);

        // Call the method being tested
        List<Object[]> actualQueryResult = holidayRepo.getHolidayOfWeekOfYearTimeSheet(weekArg, yearArg);

        // Assert the expected and actual results
        assertEquals(expectedQueryResult, actualQueryResult);
    }
}
