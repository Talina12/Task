package com.food4good.dto.geocoding;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Route {
 
	private String summary;
	private ArrayList<Leg> legs;
	private String[] waypoint_order;
	private OverViewPolyline overview_polyline;
	private Viewport bounds;
	private String copyrights;
	private ArrayList<String> warnings;
	private Fare fare;
}
