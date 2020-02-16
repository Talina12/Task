package com.food4good.dto.geocoding;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleCoordinatesResults {
 private ArrayList<AddresInfoResult> results;
 private String status;
 
 @JsonProperty("error_message")
 private String errorMessage;
}
