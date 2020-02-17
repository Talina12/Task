package com.food4good.dto;

import java.util.ArrayList;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderRequest {
	@NonNull
	@JsonProperty("order_id")
	private long orderId;
	
	@NonNull
	@JsonProperty("supplier_id")
	private long supplierId;
	
	@NonNull
	private ArrayList<NewOrderProductRequest> productsRows;
	private String comments;
}
