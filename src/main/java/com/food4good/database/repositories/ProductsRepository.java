package com.food4good.database.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food4good.database.entities.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long>{
	
	Optional<Products> findById(Long id); 
}
