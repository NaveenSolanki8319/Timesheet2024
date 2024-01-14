package com.api.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "empId")
	private int empId;

	@NotEmpty
	@Size(min = 4, message = "Username must be min of 4 characters")
	@Column(name = "empName", nullable = false)
	private String empName;

	@Email(message = "Please Enter Email")
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@gmail\\.com$", message = "Please Enter Valid Email Address")
	@Column(name = "empEmail", nullable = false, unique = true)
	private String empEmail;

	@Pattern(regexp = "^(ADMIN|EMPLOYEE)$", message = "Invalid Role")
	@NotEmpty(message = "Please Select Valid Role")
	@Column(nullable = false)
	private String role;

	@Size(min = 5, message = "Password length should be minimum 5")
	@Column(nullable = false)
	@NotEmpty
	private String password;

	@NotEmpty(message = "Please Select Valid Department")
	@Column(nullable = false)
	private String empDepart;

	@NotEmpty
	@Column(nullable = false)
	private String Address;

	@NotEmpty
	@Column(name = "Status", columnDefinition = "varchar(10) default 'Inactive'", nullable = false, length = 10)
	private String status = "Inactive";

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;
	

	@JsonManagedReference
	@OneToMany(mappedBy = "employeeObj", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<TimeSheet> timeSheet = new ArrayList<>();

	@JsonManagedReference
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Holiday> holiday = new ArrayList<>();

	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
}
