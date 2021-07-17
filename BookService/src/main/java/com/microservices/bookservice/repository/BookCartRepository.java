package com.microservices.bookservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.microservices.bookservice.model.BookCartData;


@Repository
public interface BookCartRepository extends JpaRepository<BookCartData, UUID> {

	Optional<BookCartData> findByBookdataBookId(UUID bookId);

	List<BookCartData> findByCartDetailsCartIdAndOrderstatusIsFalse(UUID cartdataId);
	
	@Modifying
	@Transactional
	@Query(value = "update cart_data set orderstatus=true where cartdata_id=:cartdataId and orderstatus=false ", nativeQuery = true)
	public int updateOrderStatus(@Param("cartdataId") UUID cartDetailsId);
}
