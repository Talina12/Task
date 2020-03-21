package com.food4good.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponse {
	@JsonProperty("multicast_id")
	private String multicastId;
	private int success;
	private int failure;
	private ArrayList<ResultString> results;
	public NotificationResponse(){
		results = new ArrayList<ResultString>();
	}
}
