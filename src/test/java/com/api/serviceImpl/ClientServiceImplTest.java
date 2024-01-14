package com.api.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.api.entities.Client;
import com.api.entities.Project;
import com.api.repositories.ClientRepo;
import com.api.service.ClientService;

@SpringBootTest
public class ClientServiceImplTest {

	@MockBean
	ClientRepo clientRepo;

	@Autowired
	ClientService clientService;

	@Test
	public void test_saveClient() {
		List<Project> project = null;
		Client client = new Client(1, "Client_Name", "client1@client.com", "8962546736", project);
		when(clientRepo.save(client)).thenReturn(client);
		assertEquals(client, clientService.createClient(client));
	}

	@Test
	public void test_saveClientCrossCheck() {
		List<Project> project = null;
		Client client1 = new Client(2, "Client_Name2", "client2@client.com", "8962546734", project);
		Client client2 = new Client(3, "Client_Name3", "client3@client.com", "8962546737", project);
		when(clientRepo.save(client1)).thenReturn(client1);
		assertNotEquals(client1, clientService.createClient(client2));
	}

	@Test
	public void test_getClienById() {
		List<Project> project = null;
		Client client = new Client(4, "Client_Name4", "client4@client.com", "8962546739", project);
		when(clientRepo.findById(4)).thenReturn(Optional.of(client));
		assertEquals("Client_Name4", clientService.getClientById(4).getClientName());
	}

	@Test
	public void test_getClienByIdCrossCheck() {
		List<Project> project = null;
		Client client = new Client(5, "Client_Name5", "client5@client.com", "8962546738", project);
		when(clientRepo.findById(5)).thenReturn(Optional.of(client));
		assertNotEquals("Client_Name", clientService.getClientById(5).getClientName());
	}

	@Test
	public void test_getEmployeeList() {
		List<Project> project = null;
		Client client1 = new Client(6, "Client_Name6", "client6@client.com", "8962546754", project);
		Client client2 = new Client(7, "Client_Name7", "client7@client.com", "8962546745", project);
		List<Client> clientList = Arrays.asList(client1, client2);
		when(clientRepo.findAll()).thenReturn(clientList);
		assertEquals(2, clientService.getAllClient().size());
	}

	@Test
	public void test_getEmployeeListCrossCheck() {
		List<Project> project = null;
		Client client1 = new Client(8, "Client_Name8", "client8@client.com", "8962546741", project);
		Client client2 = new Client(9, "Client_Name9", "client9@client.com", "8962546742", project);
		List<Client> clientList = Arrays.asList(client1, client2);
		when(clientRepo.findAll()).thenReturn(clientList);
		assertNotEquals(3, clientService.getAllClient().size());
	}

	@Test
	public void test_updateEmployee() {
		List<Project> project = null;
		Client client = new Client(9, "Client_Name9", "client9@client.com", "8962546779", project);
		when(clientRepo.save(client)).thenReturn(client);
		when(clientRepo.findById(9)).thenReturn(Optional.of(client));
		client.setClientName("Rahul");
		Client updatedClient = clientRepo.save(client);
		assertEquals("Rahul", updatedClient.getClientName());
	}
	
	@Test
	public void test_updateEmployeeCrossCheck() {
		List<Project> project = null;
		Client client = new Client(10, "Client_Name10", "client10@client.com", "8962546778", project);
		when(clientRepo.save(client)).thenReturn(client);
		when(clientRepo.findById(10)).thenReturn(Optional.of(client));
		client.setClientName("Rahul");
		Client updatedClient = clientRepo.save(client);
		assertNotEquals("Unmatched_Name", updatedClient.getClientName());
	}

}
