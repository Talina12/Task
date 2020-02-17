		package com.food4good.facad;

import javax.persistence.EntityNotFoundException;

import com.food4good.dto.DestinationRequest;
import com.food4good.dto.geocoding.GoogleDistanceResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.food4good.config.BadRequestException;
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
				    .baseUrl(geoConfig.getBaseUrl())
				  .build();
	}

	public CoordinatesResponse getCoordinates(CoordinatesRequest coordinatesRequest) throws EntityNotFoundException {
		checkAddress(coordinatesRequest);
		String address=coordinatesRequest.getHousNumber().concat("+").concat(coordinatesRequest.getStreet())
				.concat("+").concat(coordinatesRequest.getCity())
				.concat("+").concat(coordinatesRequest.getCountry());GoogleCoordinatesResults result=client.post()
				.uri(geoConfig.getGetCoordinatesUrl(),address,geoConfig.getKey(),geoConfig.getRegion())
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
			log.debug("Status= "+results.getStatus()+'\n'+results.getErrorMessage());
			throw new EntityNotFoundException("Status= "+results.getStatus()+'\n'+results.getErrorMessage());
		}
		else {
			log.debug("Status= "+results.getStatus()+'\n'+results.getErrorMessage());
			throw new  BadRequestException("Status= "+results.getStatus()+'\n'+results.getErrorMessage());
			}
    }

	public String getDestination(DestinationRequest destinationRequest) throws Exception {
		String origin=String.valueOf(destinationRequest.getMyPossition().getLatitude())+","+String.valueOf(destinationRequest.getMyPossition().getLongitude());
	    String destination=supplierService.getById(destinationRequest.getSupplierId()).getLatetude()+","+supplierService.getById(destinationRequest.getSupplierId()).getLongtitude();
	    GoogleDistanceResponse result= client.post()
				// mode may be driving/walking/bicycling/transit
				.uri(geoConfig.getGetDestinationUrl()+"origins={origin}&destinations={destination}&key={googleKey}&mode={mode}&language={language}"
						,origin,destination,geoConfig.getKey(),geoConfig.getMode(),geoConfig.getLanguage())
				.retrieve().bodyToMono(GoogleDistanceResponse.class).block();
		checkDestinationResult(result);
		return result.getRows().get(0).getElements().get(0).getDistance().getText();
		}

	private boolean checkDestinationResult(GoogleDistanceResponse result) {
		if (result.getRows().size()==0) {
			log.debug("Status= "+result.getStatus()+'\n'+"failed to calculate route");
		    throw new EntityNotFoundException("Status= "+result.getStatus()+'\n'+result.getErrorMessage());}
		if (!result.getRows().get(0).getElements().get(0).getStatus().equals("OK")) {
			log.debug("Top-level status = "+result.getStatus()+'\n'+"Element-level Status= "+result.getRows().get(0).getElements().get(0).getStatus()+'\n'
					+"failed to calculate route");
		    throw new EntityNotFoundException("Status= "+result.getStatus()+'\n'+result.getErrorMessage());}
		if (result.getStatus().equals("OK")) return true;
		if (result.getStatus().equals("ZERO_RESULTS")) {
			log.debug("Status= "+result.getStatus()+'\n'+result.getErrorMessage());
			throw new EntityNotFoundException("Status= "+result.getStatus()+'\n'+result.getErrorMessage());
		}
		else {
			log.debug("Status= "+result.getStatus()+'\n'+result.getErrorMessage());
			throw new  BadRequestException("Status= "+result.getStatus()+'\n'+result.getErrorMessage());
			}

	}
	
	private void checkAddress(CoordinatesRequest coordinatesRequest) {
		if (coordinatesRequest.getCity()==null) coordinatesRequest.setCity("");
		if (coordinatesRequest.getCountry()==null) coordinatesRequest.setCountry(geoConfig.getCountry());
		if (coordinatesRequest.getHousNumber()==null) coordinatesRequest.setHousNumber("");
		if (coordinatesRequest.getStreet()==null) coordinatesRequest.setStreet("");
	}
}
