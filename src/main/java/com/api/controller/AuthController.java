package com.api.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.entities.Employee;
import com.api.entities.PaymentHistory;
import com.api.entities.VerifyEmail;
import com.api.model.AuthRequest;
import com.api.model.AuthResponse;
import com.api.repositories.EmployeeRepo;
import com.api.repositories.MyOrderRepo;
import com.api.service.EmailService;
import com.api.service.EmployeeService;
import com.api.service.JwtService;

import jakarta.validation.Valid;

import com.razorpay.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private EmailService emailService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyOrderRepo myOrderRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// Login for employee and admin
	@PostMapping("/login")
	public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			String token = this.jwtService.generateToken(authRequest.getEmail());
			AuthResponse jwtAuthResponse = new AuthResponse();
			jwtAuthResponse.setToken(token);
			jwtAuthResponse.setEmployee(employeeRepo.findByEmpEmail(authRequest.getEmail()));
			return ResponseEntity.status(HttpStatus.OK).body(jwtAuthResponse);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user request.");
		}
	}

	// Add Admin
	@PostMapping("/addAdmin")
	public ResponseEntity<?> saveEmployee(@Valid @RequestBody Employee employee) {
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		employee.setRole("ADMIN");
		if (employeeRepo.existsByEmpEmail(employee.getEmpEmail())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email I.D. already exists ");

		} else
			return ResponseEntity.status(HttpStatus.CREATED).body(this.employeeService.createEmployee(employee));
	}

	// To check admin already exists
	@GetMapping("/oneAdmin")
	public ResponseEntity<?> getOneAdmin() {
		boolean result = this.employeeRepo.existsByRole("ADMIN");
		return ResponseEntity.ok(result);
	}

	@PostMapping("/send")
	public ResponseEntity<?> sendMail(@Valid @RequestBody VerifyEmail employee) {
		String email = employee.getEmpEmail();

		if (employeeRepo.existsByEmpEmail(email))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email I.D. already exists ");
		else
			return ResponseEntity.status(HttpStatus.OK).body(emailService.sendMail(email));
	}

	// Add Payment in database
	@PostMapping("/createOrder")
	public String BuyMeCoffee(@RequestParam String email, @RequestParam int amount) {
		try {
			var client = new RazorpayClient("rzp_test_9ynkNzlEcE7MkG", "xOSAXaFUoIqalqK5dhixRmVn");
			JSONObject obj = new JSONObject();
			obj.put("amount", amount * 100);
			obj.put("currency", "INR");
			obj.put("receipt", "txn_123456");
			obj.put("receipt", "txn_123456");

			// creating new order
			Order order = client.orders.create(obj);

			// save data to database
			Employee emp = employeeRepo.findByEmpEmail(email);
			PaymentHistory paymentHistory = new PaymentHistory();
			paymentHistory.setAmount(order.get("amount") + "");
			paymentHistory.setEmployee(emp);
			paymentHistory.setPaymentId(null);
			paymentHistory.setRazorPayOrderId(order.get("id"));
			paymentHistory.setStatus("Created");
			paymentHistory.setCreated_At(order.get("created_at"));
			this.myOrderRepo.save(paymentHistory);
			return order.toString();
		} catch (RazorpayException e) {

			e.printStackTrace();
			return "Payment Unsuccessful";
		}
	}

	// Update Payment history
	@PutMapping("/updateOrder")
	public ResponseEntity<?> UpdateOrder(@RequestParam String paymentId, @RequestParam String orderId,
			@RequestParam String status) {
		PaymentHistory paymentHistory = this.myOrderRepo.findByrazorPayOrderId(orderId);
		paymentHistory.setPaymentId(paymentId);
		paymentHistory.setStatus(status);
		this.myOrderRepo.save(paymentHistory);
		return ResponseEntity.ok("Updated Successfully");
	}

}