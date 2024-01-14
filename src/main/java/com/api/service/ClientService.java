package com.api.service;

import java.util.List;

import com.api.entities.Client;

public interface ClientService {

	// Create
	public Client createClient(Client client);

	// Read
	public List<Client> getAllClient();

	public Client getClientById(Integer id);

	// Update
	public Client updateClient(Client client);
}
