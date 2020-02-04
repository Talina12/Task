package com.food4good.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.food4good.dto.CoordinatesRequest;
import com.food4good.dto.CoordinatesResponse;
import com.food4good.dto.NewOrderResponse;
import com.food4good.dto.geocoding.GoogleCoordinatesResults;
import com.food4good.facad.AddressService;



@RestController
@RequestMapping("/address")
public class AddressController {
	
	private AddressService addressService;
	
	public AddressController (AddressService addressService) {
		this.addressService=addressService;
	}
	
	@PostMapping (value="/validation")
	public ResponseEntity<CoordinatesResponse> getCoordinates(@Validated @RequestBody CoordinatesRequest coordinatesRequest1) throws Exception
	{
		CoordinatesRequest coordinatesRequest=new CoordinatesRequest();
		coordinatesRequest.setCity("Краснодар ");
		coordinatesRequest.setCountry("Россия ");
		coordinatesRequest.setHousNumber("200");
		coordinatesRequest.setStreet("Аэродромная ");
		CoordinatesResponse	result = addressService.getCoordinates(coordinatesRequest);
		return(ResponseEntity.ok(result));
	}

}
