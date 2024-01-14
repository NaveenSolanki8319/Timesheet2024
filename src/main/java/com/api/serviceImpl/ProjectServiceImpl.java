package com.api.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.entities.Project;
import com.api.exceptions.ResourceNotFoundException;
import com.api.repositories.ProjectRepo;
import com.api.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	private ProjectRepo projectRepo;

	@Override
	public Project createProject(Project project) {
		return this.projectRepo.save(project);
	}

	@Override
	public List<Project> getAllProject() {
		return this.projectRepo.findAll();
	}

	@Override
	public Project getProjectById(int id) {
		return this.projectRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
	}

	@Override
	public Project updateProject(Project project) {

		Project updation = this.projectRepo.findById(project.getProject_id())
				.orElseThrow(() -> new ResourceNotFoundException("Project", "id", project.getProject_id()));
		updation.setEndDate(project.getEndDate());

		return this.projectRepo.save(updation);
	}

}
