package com.api.model;

import org.force66.beantester.BeanTester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class AuthRequestTest {
	@Test
	@DisplayName("Test AuthRequest class ")
	void authRequest() {
		BeanTester beanTester = new BeanTester();
		beanTester.testBean(AuthRequest.class);
	}
}