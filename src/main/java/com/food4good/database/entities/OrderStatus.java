package com.food4good.database.entities;

public enum OrderStatus {
 
	
	INIT("init");
	private String title;
	
	OrderStatus(String title) {
	 this.title=title;
	}

	public  String getTitle() {
	 return title;
	}
	
}
