package com.food4good.database.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food4good.database.entities.Products;
import com.food4good.database.entities.Supplier;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long>{
	
	Optional<Products> findById(Long id); 
	List<Products> findBySupplier(Supplier supplier);
}
