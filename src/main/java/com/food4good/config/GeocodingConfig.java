package com.food4good.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("custom-properties.geocoding")
@Getter
@Setter
public class GeocodingConfig {
 private String key;
 
 @JsonProperty("base_url")
 private String baseUrl;
 
 @JsonProperty("get_coordinates_url")
 private String getCoordinatesUrl;
 private String region;
 
 @JsonProperty("get_destination_url")
 private String getDestinationUrl;
 private String mode;
 private String language;
 private String country;
}
