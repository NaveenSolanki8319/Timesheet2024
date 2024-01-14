package com.api.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "VerifyEmail")
public class VerifyEmail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "empId")
	private int empId;
	
	@Email(message = "Please Enter Email")
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@gmail\\.com$", message = "Please Enter Valid Email Address")
	@Column(name = "empEmail", nullable = false, unique = true)
	private String empEmail;

}
