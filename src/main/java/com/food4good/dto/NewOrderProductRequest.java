package com.food4good.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewOrderProductRequest {

	private long productId;
	private int productAmount;
}
