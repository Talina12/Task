package com.food4good.dto;

import javax.persistence.EntityNotFoundException;

import com.food4good.database.entities.Orders;
import com.food4good.database.entities.Supplier;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseOrderDTO  extends BaseDTO{
	private String supplierName;
	private long numOfProducts;
	private String totalPrice;
	private String status;
	
	public static BaseOrderDTO convertFromEntity(Orders order) {
		BaseOrderDTO orderDto = new BaseOrderDTO();
		orderDto.setCreatedAt(order.getCreatedAt().toString());
		orderDto.setId(order.getId());
		orderDto.setNumOfProducts(order.getProducts().stream().count());
		orderDto.setStatus(order.getStatus());
		Supplier supplier = order.getProducts().stream().findFirst().orElseThrow(() -> new EntityNotFoundException("there are no products in the order"))
				.getProducts().getSupplier();
		orderDto.setSupplierName(supplier.getName());
		orderDto.setTotalPrice(order.getTotalPrice());
		orderDto.setUpdatedAt(order.getUpdatedAt().toString());
		return orderDto;
		}

}
