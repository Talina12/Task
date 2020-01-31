package com.food4good.facad;

import org.springframework.stereotype.Service;

import com.food4good.database.repositories.ProductsRepository;

@Service
public class ProductsService {

	ProductsRepository productsRepository;
	public ProductsService(ProductsRepository productsRepository) {
		this.productsRepository=productsRepository;
	}
	
	
}
