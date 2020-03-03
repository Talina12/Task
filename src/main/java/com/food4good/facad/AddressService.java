		package com.food4good.facad;

import java.util.ArrayList;
import java.util.HashMap;
import javax.persistence.EntityNotFoundException;

import com.food4good.dto.DestinationRequest;
import com.food4good.dto.SingleDestinationRequest;
import com.food4good.dto.geocoding.GoogleDistanceResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.food4good.config.BadRequestException;
import com.food4good.dto.CoordinatesRequest;
import com.food4good.dto.CoordinatesResponse;
import com.food4good.config.GeocodingConfig;
import com.food4good.database.entities.Supplier;
import com.food4good.database.repositories.SupplierRepository;
import com.food4good.dto.geocoding.Element;
import com.food4good.dto.geocoding.GeoPoint;
import com.food4good.dto.geocoding.GoogleCoordinatesResults;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AddressService {

	private GeocodingConfig geoConfig;
	private SupplierRepository supplierRepository;
	private WebClient client; 
	
	public AddressService(GeocodingConfig geoConfig, SupplierRepository supplierRepository) {
		this.supplierRepository=supplierRepository;
		this.geoConfig= geoConfig;
		client = WebClient
				  .builder()
				    .baseUrl(geoConfig.getBaseUrl())
				  .build();
	}

	public CoordinatesResponse getCoordinates(CoordinatesRequest coordinatesRequest) throws EntityNotFoundException {
		checkAddress(coordinatesRequest);
		StringBuilder address = new StringBuilder(25);
		address.append(coordinatesRequest.getHousNumber()).append("+").append(coordinatesRequest.getStreet())
		       .append("+").append(coordinatesRequest.getCity())
		       .append("+").append(coordinatesRequest.getCountry());
		
		GoogleCoordinatesResults result=client.post()
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

	public HashMap<Long,String> getDestination(DestinationRequest destinationRequest) throws Exception {
		StringBuilder origin = buildOrigin(destinationRequest.getMyPossition());
		StringBuilder destination = buildDestinations(destinationRequest.getSupplierId());
				GoogleDistanceResponse result= client.post()
				// mode may be driving/walking/bicycling/transit
				.uri(geoConfig.getGetDestinationUrl()+"origins={origin}&destinations={destination}&key={googleKey}&mode={mode}&language={language}"
						,origin,destination,geoConfig.getKey(),geoConfig.getMode(),geoConfig.getLanguage())
				.retrieve().bodyToMono(GoogleDistanceResponse.class).block();
		checkTopLevelResult(result);
		return parseDestinationResult(result,destinationRequest.getSupplierId());
		}

	private boolean checkTopLevelResult(GoogleDistanceResponse result) {
		if (result.getRows().size()==0) {
			log.debug("Status= "+result.getStatus()+'\n'+"failed to calculate route");
		    throw new EntityNotFoundException("Status= "+result.getStatus()+'\n'+result.getErrorMessage());
		    }
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
	
	private StringBuilder  buildSingleDestinationString(long supplierId) throws Exception {
		StringBuilder des = new StringBuilder(23);
		Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(() -> new Exception("supplier not found"));
		des.append(supplier.getLatetude()).append(",").append(supplier.getLongtitude());
		return des;
	}
	
	private HashMap<Long, String> parseDestinationResult(GoogleDistanceResponse result,ArrayList<Long> suppliersId) {
		ArrayList<Element> elements = result.getRows().get(0).getElements();
		HashMap<Long, String> distanceMap = new HashMap<Long, String>(elements.size());
		for (int i=0;i<elements.size();i++)
			if (elements.get(i).getStatus().equals("OK"))
				distanceMap.put(suppliersId.get(i), elements.get(i).getDistance().getText());
			else distanceMap.put(suppliersId.get(i), "");
	    return distanceMap;
	}
	
	private StringBuilder buildOrigin(GeoPoint myPossition) {
		StringBuilder origin = new StringBuilder(23);
		origin.append(String.valueOf(myPossition.getLatitude())).append(",").append(String.valueOf(myPossition.getLongitude()));
		return origin;
		}
	
	private StringBuilder buildDestinations(ArrayList<Long> suppliersId) throws Exception {
		int numOfsulppiers=suppliersId.size();
		if ( numOfsulppiers<1) throw new Exception("no supliers in the request");
		StringBuilder destination = new StringBuilder(23*numOfsulppiers);
		for (long supplierId:suppliersId) destination.append(buildSingleDestinationString(supplierId).append("|"));
		return destination;
	}

	public String getDestination(SingleDestinationRequest singleDestinationRequest) throws Exception {
		HashMap<Long, String> distances = this.getDestination(new DestinationRequest(singleDestinationRequest));
		return distances.get(singleDestinationRequest.getSupplierId());
	}
	}

