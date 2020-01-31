package com.food4good.dto;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewOrderRequest {
 
	private long supplier_id;
	private ArrayList<NewOrderProductRequest> productsRows;
	private String comments;
 
}
