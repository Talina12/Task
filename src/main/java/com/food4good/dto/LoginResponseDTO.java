package com.food4good.dto;

import com.food4good.database.entities.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
    String token;
    String userId;
    
    public LoginResponseDTO(User user) {
    	this.token = user.getToken();
    	this.userId = String.valueOf(user.getId());
    }
    
    public LoginResponseDTO() {
    	this.token = "";
    	this.userId = "";
    }
}
