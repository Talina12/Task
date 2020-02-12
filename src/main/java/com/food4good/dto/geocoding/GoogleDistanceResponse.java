package com.food4good.dto.geocoding;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleDistanceResponse{
 
	private String status;
	private ArrayList<String> origin_addresses;
	private ArrayList<String> destination_addresses;
	private ArrayList<Row> rows;
	private String error_message;
}
