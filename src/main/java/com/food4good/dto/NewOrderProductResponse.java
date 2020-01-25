package com.food4good.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewOrderProductResponse {
 
	private String productName;
    private int productAmount;
    private String price;
    private String discount;
    
   
}
