package com.microservices.bookservice.repository;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.microservices.bookservice.model.BookData;


@Repository
public interface BookDataRepository extends JpaRepository<BookData, UUID> {

	Optional<BookData> findByBookname(String bookname);
	
	@Modifying
	@Transactional
	@Query(value = "update books_data set quantity=quantity-:quantity where book_id=:bookId", nativeQuery = true)
        public void updatetheValues(@Param("quantity") int quantity, @Param("bookId")UUID bookId);
}
