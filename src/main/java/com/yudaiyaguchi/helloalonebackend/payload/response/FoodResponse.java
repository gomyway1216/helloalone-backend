package com.yudaiyaguchi.helloalonebackend.payload.response;

import com.yudaiyaguchi.helloalonebackend.models.FoodEntry;

public class FoodResponse {
	
	private String id;
	private String name;
	
	public FoodResponse() {
	}
	
	public FoodResponse(FoodEntry foodEntry) {
		this.id = foodEntry.getId();
		this.name = foodEntry.getName();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}

