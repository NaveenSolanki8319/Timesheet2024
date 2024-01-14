package com.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.api.entities.Project;

@EnableJpaRepositories
public interface ProjectRepo extends JpaRepository<Project, Integer> {

	public List<Project> findByClientIsNull();

}
