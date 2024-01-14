package com.api.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "clientId")
	private int clientId;

	@NotEmpty
	@Size(min = 4, message = "Username must be min of 4 characters")
	@Column(name = "clientName", nullable = false)
	private String clientName;

	@Email(message = "Please Enter Email")
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@gmail\\.com$", message = "Please enter valid Email Address")
	@Column(name = "clientEmail", nullable = false, unique = true)
	private String clientEmail;

	@Pattern(regexp = "^[6-9][0-9]{9}$", message = "Please enter valid Mobile No.")
	@Column(length = 20, unique = true)
	private String clientMobileNo;

	@JsonManagedReference
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Project> project = new ArrayList<>();

}
