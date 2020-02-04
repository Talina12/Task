package com.food4good.dto.geocoding;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Geometry{
	 private Location location;
	 private String location_type;
	 private Viewport viewport;
}