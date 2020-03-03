package com.food4good.dto;

import com.food4good.facad.SupplierByUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierByUserDTO  {
	private SupplierInfoDTO supplierInfoDTO;
	private boolean isUserFavorite;
	private String distance;
	
	public static SupplierByUserDTO convertFromEntity(SupplierByUser supplierByUser) {
		SupplierByUserDTO supplierByUserDTO = new SupplierByUserDTO(); 
		supplierByUserDTO.setSupplierInfoDTO(SupplierInfoDTO.convertFromEntity(supplierByUser.getSupplier()));
		supplierByUserDTO.setDistance(supplierByUser.getDistance());
		supplierByUserDTO.setUserFavorite(supplierByUser.isFavorite());
		return supplierByUserDTO; 
	}
}
