package com.api.model;

import java.util.Date;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignTimesheet {

	private int empId;

	@Min(0)
	@Max(40)
	private int workingHour;
	private Date fillDate; 
}
