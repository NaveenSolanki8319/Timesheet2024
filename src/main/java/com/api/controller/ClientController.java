package com.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import com.api.entities.Client;
import com.api.repositories.ClientRepo;
import com.api.service.ClientService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/client")
@PreAuthorize("hasAuthority('ADMIN')")
public class ClientController {
	@Autowired
	private ClientService clientService;

	@Autowired
	private ClientRepo clientRepo;

	// Create Client
	@PostMapping("/save")
	public ResponseEntity<?> saveClient(@Valid @RequestBody Client client) {
		if (clientRepo.existsByClientEmail(client.getClientEmail()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email I.D. already exists ");
		else
			return ResponseEntity.status(HttpStatus.CREATED).body(this.clientService.createClient(client));

	}

	// Read all Client
	@GetMapping("/getAll")
	public ResponseEntity<List<Client>> getAllClient() {
		return ResponseEntity.ok(this.clientService.getAllClient());
	}

	// Read Client by Id
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getClientById(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(this.clientService.getClientById(id));
	}

	// Update Client
	@PutMapping("/update")
	public ResponseEntity<?> updateClient(@Valid @RequestBody Client client) {

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.clientService.updateClient(client));
	}

}
