package com.yudaiyaguchi.helloalonebackend.payload.response;

import com.yudaiyaguchi.helloalonebackend.models.NationalityEntry;

public class NationalityResponse {
	
	private String id;
	private String name;
	
	public NationalityResponse() {
	}
	
	public NationalityResponse(NationalityEntry nationalityEntry) {
		this.id = nationalityEntry.getId();
		this.name = nationalityEntry.getName();
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