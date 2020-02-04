package com.food4good.facad;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.food4good.dto.CoordinatesRequest;
import com.food4good.dto.CoordinatesResponse;
import com.food4good.dto.geocoding.GoogleCoordinatesResults;

@Service
public class AddressService {

	private WebClient client = WebClient
			  .builder()
			    .baseUrl("https://maps.googleapis.com/maps/api")
			 //   .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) 
			    .build();
	
	private String key="AIzaSyC8vq02IetvzCNGAWAHIJIumg15D-NIh3c";
	
	public CoordinatesResponse getCoordinates(CoordinatesRequest coordinatesRequest) throws Exception {
		String address=coordinatesRequest.getHousNumber().concat("+")
			       .concat(coordinatesRequest.getStreet()+"+")
			       .concat(coordinatesRequest.getCity()+"+")
			       .concat(coordinatesRequest.getCountry());
		String region="il";
		GoogleCoordinatesResults result=client.post()
				.uri("/geocode/json?address={address}&key={key}&region={region}",address,key,region)
				.retrieve().bodyToMono(GoogleCoordinatesResults.class).block();	
		System.out.println(result.getResults().size());
		checkStatus(result);
		System.out.println(result.getResults().get(0).getFormatted_address());
		return new CoordinatesResponse(result);
	}
	
	public boolean checkStatus(GoogleCoordinatesResults results) throws Exception {
		if (results.getResults().size()>1) 
			throw new EntityNotFoundException("Status= "+results.getStatus()+'\n'+"ambiguous result");
		switch (results.getStatus()){
			case ("OK"):return true;
			case("ZERO_RESULTS") :
			  throw new EntityNotFoundException("Status= "+results.getStatus()+'\n'+results.getError_message());
			default :throw new  Exception("Status= "+results.getStatus()+'\n'+results.getError_message());
			}
	}

}
