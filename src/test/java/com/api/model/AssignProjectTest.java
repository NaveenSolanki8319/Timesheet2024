package com.api.model;

import org.force66.beantester.BeanTester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class AssignProjectTest {
	@Test
	@DisplayName("Test  AssignProject class ")
	void assignProject() {
		BeanTester beanTester = new BeanTester();
		beanTester.testBean(AssignProject.class);
	}
}