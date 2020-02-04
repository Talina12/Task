package com.food4good.dto;

import java.util.ArrayList;

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
