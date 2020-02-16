package com.food4good.dto.geocoding;

import org.springframework.lang.NonNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeoPoint {
 
	@NonNull
	private double longitude;
	@NonNull
	private double latitude;
}
