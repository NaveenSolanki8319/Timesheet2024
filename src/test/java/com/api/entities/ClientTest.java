package com.api.entities;

import org.force66.beantester.BeanTester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class ClientTest {

	@Test
	@DisplayName("Test client entity class ")
	void clientEntity() {

		BeanTester beanTester = new BeanTester();
		beanTester.testBean(Client.class);
	}

}