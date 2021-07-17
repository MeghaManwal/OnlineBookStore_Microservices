package com.microservices.userservices.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.userservices.model.UserData;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, UUID> {
	
	Optional<UserData> findByEmailID(String emailId);
	
}
