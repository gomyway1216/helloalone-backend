package com.yudaiyaguchi.helloalonebackend.payload.request;

import java.util.List;


public class ActivityTypeEntryRequest {
	
	private String name;
	// list of activity category entry ids
	private List<String> activityCategoryIds;
	
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
