package com.food4good.dto;

import javax.persistence.EntityNotFoundException;

import com.food4good.database.entities.Orders;
import com.food4good.database.entities.Supplier;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderReportDTO extends BaseDTO{
	
	private String suplierName;
	private long numOfProducts;
	private String totalPrice;
	private long userId;
	private String comments;
	
	public static OrderReportDTO convertFromEntity(Orders order) {
		OrderReportDTO orderDto= new OrderReportDTO();
		orderDto.setComments(order.getComments());
		orderDto.setCreatedAt(order.getCreatedAt().toString());
		orderDto.setId(order.getId());
		orderDto.setNumOfProducts(order.getProducts().size());
		Supplier supplier = order.getProducts().stream().findFirst().orElseThrow(() -> new EntityNotFoundException("there are no products in the order"))
				.getProducts().getSupplier();
		orderDto.setSuplierName(supplier.getName());
		orderDto.setTotalPrice(order.getTotalPrice());
		orderDto.setUpdatedAt(order.getUpdatedAt().toString());
		orderDto.setUserId(order.getUser().getId());
		return orderDto;
		}
}
