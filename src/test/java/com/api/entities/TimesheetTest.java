package com.api.entities;

import org.force66.beantester.BeanTester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class TimesheetTest {

	@Test
	@DisplayName("Test TimeSheet entity class ")
	void timeSheetEntity() {
		BeanTester beanTester = new BeanTester();
		beanTester.testBean(TimeSheet.class);
	}

}