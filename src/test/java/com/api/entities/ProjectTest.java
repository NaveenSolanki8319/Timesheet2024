package com.api.entities;

import org.force66.beantester.BeanTester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class ProjectTest {

	@Test
	@DisplayName("Test project entity class ")
	void projectEntity() {

		BeanTester beanTester = new BeanTester();
		beanTester.testBean(Project.class);
	}

}