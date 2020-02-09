package com.food4good.dto.geocoding;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeocodedWaypoints {
 
	private String geocoder_status;
	private String partial_match;
	private String place_id;
	private String[] types;
}
