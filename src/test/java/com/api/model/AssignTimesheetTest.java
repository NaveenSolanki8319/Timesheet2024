package com.api.model;

import org.force66.beantester.BeanTester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class AssignTimesheetTest {
	@Test
	@DisplayName("Test  Assign Timesheet class ")
	void assignTimesheet() {
		BeanTester beanTester = new BeanTester();
		beanTester.testBean(AssignTimesheet.class);
	}
}