package com.food4good.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.NonNull;

import com.food4good.dto.geocoding.GeoPoint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DestinationRequest {
 
@NonNull
 private ArrayList<Long> supplierId;
 @NonNull
 private GeoPoint myPossition;
 
 public DestinationRequest(GeoPoint possition, ArrayList<Long> suppliersId) {
		this.supplierId = new ArrayList<Long>(suppliersId);
		this.myPossition = possition;
	}
}
