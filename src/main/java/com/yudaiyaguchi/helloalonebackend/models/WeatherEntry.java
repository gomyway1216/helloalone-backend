package com.yudaiyaguchi.helloalonebackend.models;

import com.yudaiyaguchi.helloalonebackend.payload.request.WeatherEntryRequest;

public class WeatherEntry {

	private String id;
	private String name;
	
	public WeatherEntry() {
	}
	
	public WeatherEntry(WeatherEntryRequest request) {
		this.name = request.getName();
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
