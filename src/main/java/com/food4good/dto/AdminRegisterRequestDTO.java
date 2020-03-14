package com.food4good.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRegisterRequestDTO {
 
	private String name;
	private String email;
    private String password;
	private String phone;
	private long suplierId;
 }
