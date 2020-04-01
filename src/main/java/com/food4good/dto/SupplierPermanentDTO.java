package com.food4good.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierPermanentDTO {

	private String address;
	private String name;
	
	@JsonProperty("open_hours")
	private String openHours;
}
