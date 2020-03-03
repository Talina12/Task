package com.food4good.facad;

import com.food4good.database.entities.Supplier;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierByUser {

	private Supplier supplier;
	private String distance;
	private boolean isFavorite;
	
	public SupplierByUser(Supplier supplier){
		this.supplier = supplier;
	} 
}
