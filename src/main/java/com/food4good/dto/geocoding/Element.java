package com.food4good.dto.geocoding;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Element {
  
	private String status;
	private Duration duration;
	private Distance distance;
	private Fare fare;
}
