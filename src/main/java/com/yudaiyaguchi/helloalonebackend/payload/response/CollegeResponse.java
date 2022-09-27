package com.yudaiyaguchi.helloalonebackend.payload.response;

import com.yudaiyaguchi.helloalonebackend.models.CollegeEntry;

public class CollegeResponse {

	private String id;
	private String name;
	
	public CollegeResponse() {
	}
	
	public CollegeResponse(CollegeEntry collegeEntry) {
		this.id = collegeEntry.getId();
		this.name = collegeEntry.getName();
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