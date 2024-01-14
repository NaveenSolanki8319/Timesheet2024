package com.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.entities.Client;

public interface ClientRepo extends JpaRepository<Client, Integer> {

	boolean existsByClientEmail(String clientEmail);

}
