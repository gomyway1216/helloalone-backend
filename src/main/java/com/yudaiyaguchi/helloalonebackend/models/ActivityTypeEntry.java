package com.yudaiyaguchi.helloalonebackend.models;

import java.util.List;

import com.yudaiyaguchi.helloalonebackend.payload.request.ActivityTypeEntryRequest;

public class ActivityTypeEntry {
	
	private String id;
	private String name;
	// list of activity category entry ids
	private List<String> activityCategoryIds;
	
	public ActivityTypeEntry() {
	}
	
	public ActivityTypeEntry(ActivityTypeEntryRequest request) {
		this.name = request.getName();
		this.activityCategoryIds = request.getActivityCategoryIds();
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
	
	public List<String> getActivityCategoryIds() {
		return activityCategoryIds;
	}
	
	public void setActivityCategoryIds(List<String> activityCategoryIds) {
		this.activityCategoryIds = activityCategoryIds;
	}
}
