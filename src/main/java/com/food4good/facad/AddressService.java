package com.food4good.facad;

import javax.persistence.EntityNotFoundException;

import com.food4good.dto.DestinationRequest;
import com.food4good.dto.geocoding.Distance;
import com.food4good.dto.geocoding.GoogleDistanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.food4good.config.BadRequestException;
import com.food4good.controllers.AddressController;
import com.food4good.dto.CoordinatesRequest;
import com.food4good.dto.CoordinatesResponse;
import com.food4good.config.GeocodingConfig;
import com.food4good.dto.geocoding.GoogleCoordinatesResults;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AddressService {

	private GeocodingConfig geoConfig;
	private SupplierService supplierService;
	private WebClient client; 
	
	public AddressService(GeocodingConfig geoConfig, SupplierService supplierService) {
		this.supplierService=supplierService;
		this.geoConfig= geoConfig;
		client = WebClient
				  .builder()
				    .baseUrl(geoConfig.getBase_url())
				  .build();
	}

	public CoordinatesResponse getCoordinates(CoordinatesRequest coordinatesRequest) throws EntityNotFoundException {
		String address=coordinatesRequest.getHousNumber()+"+"+coordinatesRequest.getStreet()+"+"+ coordinatesRequest.getCity()+"+"+coordinatesRequest.getCountry();
		GoogleCoordinatesResults result=client.post()
				.uri(geoConfig.getGet_coordinates_url(),address,geoConfig.getKey(),geoConfig.getRegion())
				.retrieve().bodyToMono(GoogleCoordinatesResults.class).block();	
		checkStatus(result);
		return new CoordinatesResponse(result);
	}
	
	public boolean checkStatus(GoogleCoordinatesResults results)  throws EntityNotFoundException{
		if (results.getResults().size()>1) { 
			log.debug(("Status= "+results.getStatus()+'\n'+"ambiguous result"));
			throw new EntityNotFoundException("Status= "+results.getStatus()+'\n'+"ambiguous result");
		};
		if (results.getStatus().equals("OK")) return true;
		if (results.getStatus().equals("ZERO_RESULTS")) {
			log.debug("Status= "+results.getStatus()+'\n'+results.getError_message());
			throw new EntityNotFoundException("Status= "+results.getStatus()+'\n'+results.getError_message());
		}
		else {
			log.debug("Status= "+results.getStatus()+'\n'+results.getError_message());
			throw new  BadRequestException("Status= "+results.getStatus()+'\n'+results.getError_message());
			}
    }

	public String getDestination(DestinationRequest destinationRequest) throws Exception {
		String origin=String.valueOf(destinationRequest.getMyPossition().getLatitude())+","+String.valueOf(destinationRequest.getMyPossition().getLongitude());
	    String destination=supplierService.getById(destinationRequest.getSupplierId()).getLatetude()+","+supplierService.getById(destinationRequest.getSupplierId()).getLongtitude();
	    GoogleDistanceResponse result= client.post()
				// mode may be driving/walking/bicycling/transit
				.uri(geoConfig.getGet_destination_url()+"origins={origin}&destinations={destination}&key={googleKey}&mode={mode}&language={language}"
						,origin,destination,geoConfig.getKey(),geoConfig.getMode(),geoConfig.getLanguage())
				.retrieve().bodyToMono(GoogleDistanceResponse.class).block();
		checkDestinationResult(result);
		return result.getRows().get(0).getElements().get(0).getDistance().getText();
		}

	private boolean checkDestinationResult(GoogleDistanceResponse result) {
		if (result.getRows().size()==0) {
			log.debug("Status= "+result.getStatus()+'\n'+"failed to calculate route");
		    throw new EntityNotFoundException("Status= "+result.getStatus()+'\n'+result.getError_message());}
		if (!result.getRows().get(0).getElements().get(0).getStatus().equals("OK")) {
			log.debug("Status= "+result.getStatus()+'\n'+"failed to calculate route");
		    throw new EntityNotFoundException("Status= "+result.getStatus()+'\n'+result.getError_message());}
		if (result.getStatus().equals("OK")) return true;
		if (result.getStatus().equals("ZERO_RESULTS")) {
			log.debug("Status= "+result.getStatus()+'\n'+result.getError_message());
			throw new EntityNotFoundException("Status= "+result.getStatus()+'\n'+result.getError_message());
		}
		else {
			log.debug("Status= "+result.getStatus()+'\n'+result.getError_message());
			throw new  BadRequestException("Status= "+result.getStatus()+'\n'+result.getError_message());
			}

	}
}
