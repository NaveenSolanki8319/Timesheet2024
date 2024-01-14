package com.api.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.entities.Client;
import com.api.exceptions.ResourceNotFoundException;
import com.api.repositories.ClientRepo;
import com.api.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepo clientRepo;

	@Override
	public Client createClient(Client client) {
		return this.clientRepo.save(client);
	}

	@Override
	public List<Client> getAllClient() {
		return this.clientRepo.findAll();
	}

	@Override
	public Client getClientById(Integer id) {
		return this.clientRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client", "Id", id));
	}

	@Override
	public Client updateClient(Client client) {
		Integer clientId = client.getClientId();
		Client client1 = this.clientRepo.findById(clientId)
				.orElseThrow(() -> new ResourceNotFoundException("Client", " Id ", clientId));
		client1.setClientName(client.getClientName());
		client1.setClientMobileNo(client.getClientMobileNo());
		return this.clientRepo.save(client1);
	}

}
