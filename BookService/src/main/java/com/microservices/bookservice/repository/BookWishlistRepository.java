package com.microservices.bookservice.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.bookservice.model.BookWishlistData;

@Repository
public interface BookWishlistRepository extends JpaRepository<BookWishlistData, UUID> {

	Optional<BookWishlistData> findByBookdataBookId(UUID bookId);
	
}
