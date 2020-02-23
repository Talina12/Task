package com.food4good.controllers;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.food4good.dto.CoordinatesRequest;
import com.food4good.dto.CoordinatesResponse;
import com.food4good.dto.Destination;
import com.food4good.dto.DestinationRequest;
import com.food4good.facad.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {
	
	private AddressService addressService;
	
	public AddressController (AddressService addressService) {
		this.addressService=addressService;
	}
	
	@PostMapping (value="/validation")
    public ResponseEntity<CoordinatesResponse> getCoordinates(@Validated @RequestBody @NonNull CoordinatesRequest coordinatesRequest) throws EntityNotFoundException
	{   
		CoordinatesResponse	result;
		result = addressService.getCoordinates(coordinatesRequest);	
		return(ResponseEntity.ok(result));
	}
	
	@PostMapping (value="/destination")
    public ResponseEntity<HashMap<Long,String>> getDestination(@Validated @RequestBody @NonNull DestinationRequest destinationRequest) throws Exception
	{
        return ResponseEntity.ok(addressService.getDestination(destinationRequest));
	}
	

}
