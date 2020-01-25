package com.food4good.dto;

import java.util.Iterator;

import com.food4good.database.entities.OrderProducts;
import com.food4good.database.entities.Orders;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewOrderResponse  {
 
	private long orderId;
	private String totalPrice;
	private NewOrderProductResponse[] products;
	private String pickUpTime;
	private String address;
	
	public NewOrderResponse(Orders order) {
		this.orderId=order.getId();
		this.totalPrice=order.getTotalPrice();
		Iterator<OrderProducts> iterator= order.getProducts().iterator();
		OrderProducts orderProduct;
		this.products= new NewOrderProductResponse[order.getProducts().size()];
		for (int i=0;i<products.length;i++) products[i]= new NewOrderProductResponse();
		 if (iterator.hasNext()) {
			 orderProduct = iterator.next();
			 this.pickUpTime=orderProduct.getProducts().getSupplier().getOpenHours();
			 this.address=orderProduct.getProducts().getSupplier().getAddress();
		 }
		iterator= order.getProducts().iterator();
		int i=0;
		while (iterator.hasNext()) {
			orderProduct = iterator.next();
			double originPrice=Double.parseDouble(orderProduct.getProducts().getOrigPrice());
			double dis =originPrice-Double.parseDouble(orderProduct.getPrice()); 
			this.products[i].setDiscount(String.valueOf(dis));
			this.products[i].setPrice(orderProduct.getPrice());
			this.products[i].setProductAmount(orderProduct.getAmount());
			this.products[i].setProductName(orderProduct.getProducts().getName());
			i++;
		}
	}
}
