package com.food4good.dto.geocoding;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("geocoding")
@Getter
@Setter
public class GeocodingConfig {
 private String key;
 private String base_url;
 private String get_coordinates_url;
 private String region;
}
