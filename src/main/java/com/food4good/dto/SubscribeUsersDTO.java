package com.food4good.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food4good.database.entities.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscribeUsersDTO {

	private String to;
	
	@JsonProperty("registration_tokens")
	private List<String> usersTokens;
	
	public  SubscribeUsersDTO(List<User> users, String to) {
		this.usersTokens = new ArrayList<String>();
		users.forEach(u -> usersTokens.add(u.getToken()));
		this.to = to;
	}
}
