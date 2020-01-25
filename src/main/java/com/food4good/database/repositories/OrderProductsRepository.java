package com.food4good.database.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food4good.database.entities.OrderProducts;

@Repository
public interface OrderProductsRepository extends JpaRepository<OrderProducts, Long>{
  
	}
