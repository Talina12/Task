package com.food4good.dto.geocoding;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleCoordinatesResults {
 private ArrayList<AddresInfoResult> results;
 private String status;
 private String error_message;
}
