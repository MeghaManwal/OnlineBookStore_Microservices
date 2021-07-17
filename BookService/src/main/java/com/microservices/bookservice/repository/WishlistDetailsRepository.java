package com.microservices.bookservice.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.bookservice.model.UserData;
import com.microservices.bookservice.model.WishlistDetails;

@Repository
public interface WishlistDetailsRepository extends JpaRepository<WishlistDetails, UUID> {

	Optional<WishlistDetails> findByUserData(UserData userdata);
}
