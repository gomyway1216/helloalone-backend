package com.yudaiyaguchi.helloalonebackend.payload.response;

import com.yudaiyaguchi.helloalonebackend.models.JobEntry;

public class JobResponse {

	private String id;
	private String name;
	
	public JobResponse() {
	}
	
	public JobResponse(JobEntry jobEntry) {
		this.id = jobEntry.getId();
		this.name = jobEntry.getName();
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
