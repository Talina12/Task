package com.food4good.controllers;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.food4good.config.BadRequestException;
import com.food4good.dto.CoordinatesRequest;
import com.food4good.dto.CoordinatesResponse;
import com.food4good.dto.DestinationRequest;
import com.food4good.dto.geocoding.Distance;
import com.food4good.facad.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {
	
	private AddressService addressService;
	
	public AddressController (AddressService addressService) {
		this.addressService=addressService;
	}
	
	@PostMapping (value="/validation")
    public ResponseEntity<CoordinatesResponse> getCoordinates(@Validated @RequestBody CoordinatesRequest coordinatesRequest) throws EntityNotFoundException
	{
		CoordinatesResponse	result;
		result = addressService.getCoordinates(coordinatesRequest);	
		return(ResponseEntity.ok(result));
	}
	
	@PostMapping (value="/destination")
	public ResponseEntity<String> getDestination(@Validated @RequestBody DestinationRequest destinationRequest) throws Exception{
		String distance = addressService.getDestination(destinationRequest);
		return ResponseEntity.ok(distance);
	}
	

}
