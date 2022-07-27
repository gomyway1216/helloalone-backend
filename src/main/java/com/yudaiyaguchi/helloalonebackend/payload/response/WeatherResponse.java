package com.yudaiyaguchi.helloalonebackend.payload.response;

import com.yudaiyaguchi.helloalonebackend.models.WeatherEntry;

public class WeatherResponse {

	private String id;
	private String name;
	
	public WeatherResponse() {
	}
	
	public WeatherResponse(WeatherEntry weatherEntry) {
		this.id = weatherEntry.getId();
		this.name = weatherEntry.getName();
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
