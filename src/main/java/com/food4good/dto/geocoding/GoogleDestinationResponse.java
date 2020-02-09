package com.food4good.dto.geocoding;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleDestinationResponse{
 
	private String status;
	private ArrayList<GeocodedWaypoints> geocoded_waypoints;
	private ArrayList<Route> routes;
	private String[] available_travel_modes;
	private String error_message;
}
