package com.food4good.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewOrderRequest {
 
	private long supplier_id;
	private NewOrderProductRequest[] productsRows;
	private String comments;
 
}
