package com.food4good.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.food4good.dto.CoordinatesRequest;
import com.food4good.dto.CoordinatesResponse;
import com.food4good.facad.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {
	
	private AddressService addressService;
	
	public AddressController (AddressService addressService) {
		this.addressService=addressService;
	}
	
	@PostMapping (value="/validation")
    public ResponseEntity<CoordinatesResponse> getCoordinates(@Validated @RequestBody CoordinatesRequest coordinatesRequest1) throws ResponseStatusException
	{
		CoordinatesRequest coordinatesRequest=new CoordinatesRequest();
		coordinatesRequest.setCity("Krasnodar");
		coordinatesRequest.setCountry("Russia");
		coordinatesRequest.setHousNumber("200");
		coordinatesRequest.setStreet("Aerodromnaya");
		CoordinatesResponse	result;
		result = addressService.getCoordinates(coordinatesRequest);	
		return(ResponseEntity.ok(result));
	}
	
	//@PostMapping (value="/destination")
	

}
