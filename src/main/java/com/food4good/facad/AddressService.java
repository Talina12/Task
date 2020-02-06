package com.food4good.facad;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.food4good.config.BadRequestException;
import com.food4good.dto.CoordinatesRequest;
import com.food4good.dto.CoordinatesResponse;
import com.food4good.dto.geocoding.GoogleCoordinatesResults;

import lombok.extern.slf4j.Slf4j;

@Service
public class AddressService {

	private WebClient client = WebClient
			  .builder()
			    .baseUrl("https://maps.googleapis.com/maps/api")
			  .build();
	
	private String key="AIzaSyC8vq02IetvzCNGAWAHIJIumg15D-NIh3c";
	
	public CoordinatesResponse getCoordinates(CoordinatesRequest coordinatesRequest) throws EntityNotFoundException {
		String address=coordinatesRequest.getHousNumber().concat("+")
			       .concat(coordinatesRequest.getStreet()+"+")
			       .concat(coordinatesRequest.getCity()+"+")
			       .concat(coordinatesRequest.getCountry());
		String region="il";
		GoogleCoordinatesResults result=client.post()
				.uri("/geocode/json?address={address}&key={key}&region={region}",address,key,region)
				.retrieve().bodyToMono(GoogleCoordinatesResults.class).block();	
		checkStatus(result);
		return new CoordinatesResponse(result);
	}
	
	public boolean checkStatus(GoogleCoordinatesResults results)  throws EntityNotFoundException{
		if (results.getResults().size()>1) 
			throw new EntityNotFoundException("Status= "+results.getStatus()+'\n'+"ambiguous result");
		switch (results.getStatus()){
			case ("OK"):return true;
			case("ZERO_RESULTS") :
			  throw new EntityNotFoundException("Status= "+results.getStatus()+'\n'+results.getError_message());
			default :throw new  BadRequestException("Status= "+results.getStatus()+'\n'+results.getError_message());
			}
	}
}
