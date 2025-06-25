package com.Lisiniarivo.Application.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Lisiniarivo.Application.Entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	@Query("select o from Order o where o.reference like :x")
	Page<Order> searchByReference(@Param("x")String reference,Pageable pageable);

}
