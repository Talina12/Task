package com.food4good.dto.geocoding;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
	 private ArrayList<AddressComponent> address_components;
	 private String formatted_address;
	 private Geometry geometry;
	 private boolean partial_match;
	 private String place_id;
	 private String[] types;
	 
}