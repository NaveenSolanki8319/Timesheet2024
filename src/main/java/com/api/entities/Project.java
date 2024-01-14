package com.api.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int project_id;

	@NotEmpty
	@Size(min = 10, max = 50, message = "Project Name length should be between 10 and 50 ")
	@Column(nullable = false, length = 100)
	private String projectName;

//	@NotEmpty
//	@Size(min = 5, max = 20, message ="Client Name length should be between 5 and 20 " )
//	@Column(nullable = false, length = 100)
//	private String clientName;

	@NotEmpty
	@Size(min = 10, max = 100, message = "Descrption length should be between 10 and 100 ")
	@Column(nullable = false, length = 100)
	private String description;

	@Future(message = "Enter valid start Date ")
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date StartDate;

	@Future(message = "Enter valid end Date")
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date EndDate;

	// cascade = CascadeType.ALL,
	@JsonManagedReference
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Employee> employee = new ArrayList<>();

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

}
