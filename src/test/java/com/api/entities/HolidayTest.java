package com.api.entities;

import org.force66.beantester.BeanTester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class HolidayTest {

	@Test
	@DisplayName("Test holiday entity class ")
	void holidayEntity() {

		BeanTester beanTester = new BeanTester();
		beanTester.testBean(Holiday.class);
	}

}