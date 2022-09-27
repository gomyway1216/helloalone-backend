package com.yudaiyaguchi.helloalonebackend.payload.response;

import com.yudaiyaguchi.helloalonebackend.models.ActivityCategoryEntry;

public class ActivityCategoryResponse {
	
	private String id;
	private String name;
	
	public ActivityCategoryResponse() {
	}
	
	public ActivityCategoryResponse(ActivityCategoryEntry activityCategoryEntry) {
		this.id = activityCategoryEntry.getId();
		this.name = activityCategoryEntry.getName();
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
