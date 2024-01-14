package com.api.model;

import org.force66.beantester.BeanTester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class ViolationResponseTest {
	@Test
	@DisplayName("Test Violation Response class ")
	void violationResponse() {
		BeanTester beanTester = new BeanTester();
		beanTester.testBean(ViolationResponse.class);
	}
}