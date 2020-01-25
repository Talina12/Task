package com.food4good.database.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food4good.database.entities.Orders;
import com.food4good.database.entities.Supplier;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long>{
	
	public Optional<Orders> findById(Long id);
}
