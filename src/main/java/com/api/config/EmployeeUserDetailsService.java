package com.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.api.entities.Employee;
import com.api.repositories.EmployeeRepo;

@Component
public class EmployeeUserDetailsService implements UserDetailsService {
	@Autowired
	private EmployeeRepo employeeRepo;

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		Employee employee = this.employeeRepo.findByEmpEmail(userEmail);
		if (employee == null) {
			throw new UsernameNotFoundException("Could not found employee !!");
		}
		CustomUserInfo customUserInfo = new CustomUserInfo(employee);
		return customUserInfo;
	}

}
