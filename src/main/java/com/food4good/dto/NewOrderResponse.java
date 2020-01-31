package com.food4good.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import com.food4good.database.entities.OrderProducts;
import com.food4good.database.entities.Orders;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewOrderResponse  {
 
	private long orderId;
	private String totalPrice;
	private ArrayList<NewOrderProductResponse> products;
	private String pickUpTime;
	private String address;
	
	public NewOrderResponse(Orders order) throws Exception {
		this.orderId=order.getId();
		this.totalPrice=order.getTotalPrice();
		OrderProducts newOrderProduct=order.getProducts().stream().findFirst().orElseThrow(()->new Exception("no products in order"));
		this.pickUpTime=newOrderProduct.getProducts().getSupplier().getOpenHours();
		this.address=newOrderProduct.getProducts().getSupplier().getAddress();
		setProducts(order.getProducts());
	};
	
	private void setProducts(Set<OrderProducts> orderProductsSet){
		this.products= new ArrayList<NewOrderProductResponse>();
		for (OrderProducts orderProduct:orderProductsSet) 
		{
			NewOrderProductResponse responseProduct=new NewOrderProductResponse();
			double originPrice=Double.parseDouble(orderProduct.getProducts().getOrigPrice());
			double dis =originPrice-Double.parseDouble(orderProduct.getPrice()); 
			responseProduct.setDiscount(String.valueOf(dis));
			responseProduct.setPrice(orderProduct.getPrice());
			responseProduct.setProductAmount(orderProduct.getAmount());
			responseProduct.setProductName(orderProduct.getProducts().getName());
			products.add(responseProduct);	
		}
	}
}
