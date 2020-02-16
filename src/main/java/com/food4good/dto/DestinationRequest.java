package com.food4good.dto;

import org.springframework.lang.NonNull;

import com.food4good.dto.geocoding.GeoPoint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DestinationRequest {
 private long supplierId;
 @NonNull
 private GeoPoint myPossition;
	
}
