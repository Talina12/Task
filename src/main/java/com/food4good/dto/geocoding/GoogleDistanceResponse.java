package com.food4good.dto.geocoding;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleDistanceResponse{
 
	private String status;
	
	@JsonProperty("origin_addresses")
	private ArrayList<String> originAddresses;
	
	@JsonProperty("destination_addresses")
	private ArrayList<String> destinationAddresses;
	private ArrayList<Row> rows;
	
	@JsonProperty("error_message")
	private String errorMessage;
}
