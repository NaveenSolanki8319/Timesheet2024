package com.api.model;

import com.api.entities.Employee;

import lombok.Data;

@Data
public class AuthResponse {

	private String token;
	private Employee employee;
}