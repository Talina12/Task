package com.food4good.controllers;

import java.util.HashMap;
import javax.persistence.EntityNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.food4good.dto.CoordinatesRequest;
import com.food4good.dto.CoordinatesResponse;
import com.food4good.dto.DestinationRequest;
import com.food4good.dto.SingleDestinationRequest;
import com.food4good.dto.geocoding.GeoPoint;
import com.food4good.facad.AddressService;

@RestController
public class AddressController {
	
	private AddressService addressService;
	
	public AddressController (AddressService addressService) {
		this.addressService=addressService;
	}
	
	@PostMapping (value={"admin/address/validation","superAdmin/address/validation"})
	@CrossOrigin
    public ResponseEntity<CoordinatesResponse> getCoordinates(@Validated @RequestBody @NonNull CoordinatesRequest coordinatesRequest) throws EntityNotFoundException
	{
		CoordinatesResponse	result;
		result = addressService.getCoordinates(coordinatesRequest);
		return(ResponseEntity.ok(result));
	}
	
	@PostMapping (value="/address/destination")
    public ResponseEntity<HashMap<Long,String>> getDestination(@Validated @RequestBody @NonNull DestinationRequest destinationRequest) throws Exception
	{
        return ResponseEntity.ok(addressService.getDestination(destinationRequest));
	}

	@PostMapping (value="/address/single_destination")
	public ResponseEntity<String> getDestination(@Validated @RequestBody @NonNull SingleDestinationRequest singleDestinationRequest) throws Exception{
		return ResponseEntity.ok(addressService.getDestination(singleDestinationRequest));
	}
}
