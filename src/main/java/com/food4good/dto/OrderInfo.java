package com.food4good.dto;

import javax.persistence.EntityNotFoundException;

import com.food4good.database.entities.Orders;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderInfo extends BaseDTO{
 
	private String supplierName;
	private long numOfProducts;
	private String totalPrice;
	private long userId;
	private String status;
	
	public static OrderInfo convertFromEntity(Orders order) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreatedAt(order.getCreatedAt().toString());
		orderInfo.setId(order.getId());
		orderInfo.setNumOfProducts(order.getProducts().size());
		orderInfo.setStatus(order.getStatus());
		orderInfo.setSupplierName(order.getProducts().stream().findFirst().orElseThrow(() -> new EntityNotFoundException("there are no products in the order"))
				.getProducts().getSupplier().getName());
		orderInfo.setTotalPrice(order.getTotalPrice());
		orderInfo.setUpdatedAt(order.getUpdatedAt().toString());
		orderInfo.setUserId(order.getUser().getId());
		return orderInfo;
	}
}
