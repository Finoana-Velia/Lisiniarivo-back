package com.Lisiniarivo.Application.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Lisiniarivo.Application.Entity.Deliverer;

public interface DelivererRepository extends JpaRepository<Deliverer, Long>{
	
	@Query("select d from Deliverer d where d.name like :x")
	public Page<Deliverer> searchDelivererByName(@Param("x")String name,Pageable pageable);

}
