package com.food4good.dto;

import java.util.ArrayList;

import org.springframework.lang.NonNull;

import com.food4good.dto.geocoding.GeoPoint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleDestinationRequest {

	@NonNull
	 private Long supplierId;
	 @NonNull
	 private GeoPoint myPossition;
}
