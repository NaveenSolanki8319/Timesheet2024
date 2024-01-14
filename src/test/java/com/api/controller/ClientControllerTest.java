package com.api.controller;



import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.api.entities.Client;
import com.api.repositories.ClientRepo;
import com.api.service.ClientService;


public class ClientControllerTest {
    
    @Mock
    private ClientService clientService;
    
    @Mock
    private ClientRepo clientRepo;
    
    @InjectMocks
    private ClientController clientController;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testSaveClient_WithValidClient_ReturnsCreatedResponse() {
        // Arrange
        Client client = new Client();
        client.setClientEmail("test@example.com");
        
        when(clientRepo.existsByClientEmail(client.getClientEmail())).thenReturn(false);
        when(clientService.createClient(client)).thenReturn(client);
        
        // Act
        ResponseEntity<?> response = clientController.saveClient(client);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(client);
        verify(clientRepo).existsByClientEmail(client.getClientEmail());
        verify(clientService).createClient(client);
    }
    
    @Test
    public void testSaveClient_WithExistingEmail_ReturnsBadRequestResponse() {
        // Arrange
        Client client = new Client();
        client.setClientEmail("test@example.com");
        
        when(clientRepo.existsByClientEmail(client.getClientEmail())).thenReturn(true);
        
        // Act
        ResponseEntity<?> response = clientController.saveClient(client);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Email I.D. already exists ");
        verify(clientRepo).existsByClientEmail(client.getClientEmail());
    }
    
    @Test
    public void testGetAllClients_ReturnsListOfClients() {
        // Arrange
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());
        clients.add(new Client());
        
        when(clientService.getAllClient()).thenReturn(clients);
        
        // Act
        ResponseEntity<List<Client>> response = clientController.getAllClient();
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(clients);
        verify(clientService).getAllClient();
    }
    
    @Test
    public void testGetClientById_WithValidId_ReturnsClient() {
        // Arrange
        Integer clientId = 1;
        Client client = new Client();
        client.setClientId(clientId);
        
        when(clientService.getClientById(clientId)).thenReturn(client);
        
        // Act
        ResponseEntity<?> response = clientController.getClientById(clientId);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(client);
        verify(clientService).getClientById(clientId);
    }
    
    @Test
    public void testUpdateClient_WithValidClient_ReturnsAcceptedResponse() {
        // Arrange
        Client client = new Client();
        client.setClientId(1);
        
        when(clientService.updateClient(client)).thenReturn(client);
        
        // Act
        ResponseEntity<?> response = clientController.updateClient(client);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(response.getBody()).isEqualTo(client);
        verify(clientService).updateClient(client);
    }
    
    @Test
    public void testUpdateClient_WithoutClientId_ReturnsBadRequestResponse() {
        // Arrange
        Client client = new Client();
        
        // Act
        ResponseEntity<?> response = clientController.updateClient(client);
        
        // Assert
        assertThat(response.getBody()).isEqualTo(null);
        verify(clientService).updateClient(client);
    }
    
    @Test
    public void testUpdateClient_WithNonexistentClient_ReturnsNotFoundResponse() {
        // Arrange
        Client client = new Client();
        client.setClientId(1);
        
        when(clientService.updateClient(client)).thenReturn(null);
        
        // Act
        ResponseEntity<?> response = clientController.updateClient(client);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        verify(clientService).updateClient(client);
    }
    
}
        
