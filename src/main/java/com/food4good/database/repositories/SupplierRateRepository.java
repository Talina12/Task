package com.food4good.database.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food4good.database.entities.Supplier;
import com.food4good.database.entities.SupplierRate;
import com.food4good.database.entities.User;

@Repository
public interface SupplierRateRepository extends JpaRepository<SupplierRate, Long>{

	Optional<SupplierRate> findByUserAndSupplier(User user,Supplier supplier);
	void delete (SupplierRate supplierRate);
	List<SupplierRate> findAllBySupplier (Supplier supplier);
	int countBySupplier(Supplier supplier);
	
};
