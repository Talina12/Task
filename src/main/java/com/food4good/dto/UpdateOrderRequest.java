package com.food4good.dto;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderRequest extends NewOrderRequest {
	@NonNull
	@JsonProperty("order_id")
	private long orderId;
	
}
