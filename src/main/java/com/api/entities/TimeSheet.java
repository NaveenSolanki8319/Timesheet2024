package com.api.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TimeSheet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "timesheetid")
	private int timesheetid;

	@Min(0)
	@Max(40)
	@Column(name = "workinghour", columnDefinition = "int default 0")
	private int workinghour = 0;

	@Temporal(TemporalType.DATE)
	@Column(name = "fillDate", nullable = false)
	private Date fillDate;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "emp_id")
	private Employee employeeObj;

	@Column(name = "project_id", nullable = false)
	private int projectId;
}
