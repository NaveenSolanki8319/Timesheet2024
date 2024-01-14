package com.api.service;

import java.util.List;

import com.api.entities.Project;

public interface ProjectService {
	// Create
	public Project createProject(Project project);

	// Read
	public List<Project> getAllProject();

	public Project getProjectById(int id);

	// Update
	public Project updateProject(Project project);

}
