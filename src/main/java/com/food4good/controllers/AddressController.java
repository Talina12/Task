package com.food4good.controllers;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
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

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/address")
public class AddressController {
	
	private AddressService addressService;
	
	public AddressController (AddressService addressService) {
		this.addressService=addressService;
	}
	
	@PostMapping (value="/validation")
	public ResponseEntity<CoordinatesResponse> getCoordinates(@Validated @RequestBody CoordinatesRequest coordinatesRequest) throws ResponseStatusException
	{CoordinatesResponse	result;
		try {
		 	result = addressService.getCoordinates(coordinatesRequest);	
		} catch (EntityNotFoundException e) {
			log.debug(e.getMessage());
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity Not Found", e);
		}
		return(ResponseEntity.ok(result));
	}

}
