package com.food4good.scheduled;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.food4good.database.entities.Products;
import com.food4good.database.entities.Supplier;
import com.food4good.database.repositories.ProductsRepository;
import com.food4good.database.repositories.SupplierRepository;

@Component
public class ProductsScheduled {
	
	ProductsRepository productsRepository;
	private SupplierRepository supplierRepository;
	
	public ProductsScheduled(ProductsRepository productsRepository, SupplierRepository supplierRepository) {
		this.productsRepository= productsRepository;
		this.supplierRepository=supplierRepository;
	}

	@Scheduled(cron = "0 0 6 * * ?", zone = "UTC")
	public void setOriginProductAmount() {
		List<Supplier> allSuppliers = supplierRepository.findAll();
		for(Supplier supplier:allSuppliers) {
			List<Products>supplierProducts = productsRepository.findBySupplier(supplier);
			supplierProducts.forEach(s -> resetProductAmount(s));	
		}
	}
	
	private void resetProductAmount(Products product) {
		int amount = product.getAmount();
		product.setRealAmount(amount);
		productsRepository.save(product);
	}
}
