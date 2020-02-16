package com.food4good.dto;

import org.springframework.lang.NonNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoordinatesRequest {
 
	private String country;
    private  String city;
    private String street;
    private String housNumber;
}
