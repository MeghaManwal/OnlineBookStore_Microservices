package com.microservices.bookservice.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.bookservice.model.CustomerData;

@Repository
public interface CustomerDataRepository extends JpaRepository<CustomerData, UUID> {

	 Optional<CustomerData> findByCustomerId(UUID customerId);
	 
	 CustomerData findByUserId(UUID userId);
	 
}
