package com.food4good.dto;

import com.food4good.dto.geocoding.GeoPoint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DestinationRequest {
 private long supplierId;
 private GeoPoint myPossition;
	
}
