package com.food4good.config;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Entity Not Found")
public class BadRequestException extends EntityNotFoundException{

	public BadRequestException(String string) {
		super(string);
	}
 
	
}
