package com.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.api.entities.Employee;
import com.api.model.AuthRequest;
import com.api.repositories.EmployeeRepo;
import com.api.service.EmployeeService;
import com.api.service.JwtService;

public class AuthControllerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepo employeeRepo;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticateAndGetToken_WithValidCredentials_ReturnsTokenAndEmployee() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        AuthRequest authRequest = new AuthRequest(email, password);
        Employee employee = new Employee();
        employee.setEmpEmail(email);
        String token = "token";

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtService.generateToken(email)).thenReturn(token);
        when(employeeRepo.findByEmpEmail(email)).thenReturn(employee);

        // Act
        ResponseEntity<?> response = authController.authenticateAndGetToken(authRequest);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testSaveEmployee_WithValidEmployee_ReturnsCreatedResponse() {
        // Arrange
        Employee employee = new Employee();
        employee.setEmpEmail("test@example.com");
        employee.setPassword("password");

        when(passwordEncoder.encode(employee.getPassword())).thenReturn("encodedpassword");
        when(employeeRepo.existsByEmpEmail(employee.getEmpEmail())).thenReturn(false);
        when(employeeService.createEmployee(employee)).thenReturn(employee);

        // Act
        ResponseEntity<?> response = authController.saveEmployee(employee);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(employee);
    }
    
    @Test
    public void testSaveEmployee_WithExistingEmail_ReturnsBadRequestResponse() {
        // Arrange
        Employee employee = new Employee();
        employee.setEmpEmail("test@example.com");
        employee.setPassword("password");

        when(employeeRepo.existsByEmpEmail(employee.getEmpEmail())).thenReturn(true);

        // Act
        ResponseEntity<?> response = authController.saveEmployee(employee);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Email I.D. already exists ");
    }

    @Test
    public void testGetOneAdmin_WithExistingAdmin_ReturnsTrueResponse() {
        // Arrange
        when(employeeRepo.existsByRole("ADMIN")).thenReturn(true);

        // Act
        ResponseEntity<?> response = authController.getOneAdmin();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(true);
    }

    @Test
    public void testGetOneAdmin_WithoutExistingAdmin_ReturnsFalseResponse() {
        // Arrange
        when(employeeRepo.existsByRole("ADMIN")).thenReturn(false);

        // Act
        ResponseEntity<?> response = authController.getOneAdmin();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(false);
    }

}
