package com.yudaiyaguchi.helloalonebackend.models;

import java.util.List;

public class ActivityTypeEntry {
	
	private String id;
	private String name;
	// list of activity category entry ids
	private List<String> activityCategoryId;
	
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
	
	public List<String> getActivityCategoryId() {
		return activityCategoryId;
	}
	
	public void setActivityCategoryId(List<String> activityCategoryId) {
		this.activityCategoryId = activityCategoryId;
	}
}
