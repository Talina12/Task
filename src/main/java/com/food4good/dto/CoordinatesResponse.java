package com.food4good.dto;

import javax.persistence.EntityNotFoundException;

import com.food4good.dto.geocoding.GoogleCoordinatesResults;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoordinatesResponse {
 
	private double longitude;
	private double latitude;
	
	public CoordinatesResponse(GoogleCoordinatesResults googleresult) throws EntityNotFoundException
	{
		if (googleresult.getResults().size()!=1) 
			throw new EntityNotFoundException("Status= "+googleresult.getStatus()+'\n'+googleresult.getError_message());
		this.latitude=googleresult.getResults().get(0).getGeometry().getLocation().getLat();
		this.longitude=googleresult.getResults().get(0).getGeometry().getLocation().getLng();}
		
}

