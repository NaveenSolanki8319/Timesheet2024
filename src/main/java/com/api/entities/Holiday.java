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
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Holiday {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int holidayid;

	@NotEmpty
	@Size(min = 4, max = 30, message = "Description must be between 4 and 30 characters")
	@Column(name = "holidayDescription", columnDefinition = "varchar(30) default 'Public_Holiday'", nullable = false, length = 30)
	private String holidayDesp = "Public_Holiday";

	@Future(message = "Enter Valid Date. It's a Past date")
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "holidayDate", nullable = false, unique = true)
	private Date holidayDate;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "admin_id")
	private Employee employee;
}
