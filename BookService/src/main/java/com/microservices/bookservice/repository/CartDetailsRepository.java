package com.microservices.bookservice.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.microservices.bookservice.model.CartDetails;

@Repository
public interface CartDetailsRepository extends JpaRepository<CartDetails, UUID> {
	Optional<CartDetails> findByUserId(UUID id);
}
