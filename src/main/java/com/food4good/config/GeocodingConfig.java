package com.food4good.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("custom-properties.geocoding")
@Getter
@Setter
public class GeocodingConfig {
 private String key;
 private String base_url;
 private String get_coordinates_url;
 private String region;
 private String get_destination_url;
 private String mode;
 private String language;
}
