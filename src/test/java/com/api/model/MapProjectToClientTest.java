package com.api.model;

import org.force66.beantester.BeanTester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class MapProjectToClientTest {
	@Test
	@DisplayName("Test Map Project To Client class ")
	void mapProject() {
		BeanTester beanTester = new BeanTester();
		beanTester.testBean(MapProjectToClient.class);
	}
}