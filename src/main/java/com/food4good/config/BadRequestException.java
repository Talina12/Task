package com.food4good.config;

import javax.persistence.EntityNotFoundException;

public class BadRequestException extends EntityNotFoundException{

	public BadRequestException(String string) {
		super(string);
	}
 
	
}
