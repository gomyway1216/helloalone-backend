package com.yudaiyaguchi.helloalonebackend.payload.response;

import java.util.List;

import com.yudaiyaguchi.helloalonebackend.models.ActivityCategoryEntry;
import com.yudaiyaguchi.helloalonebackend.models.ActivityTypeEntry;


public class ActivityTypeResponse {
	
	private String id;
	private String name;
	// list of activity category entry ids
	private List<ActivityCategoryEntry> activityCategories;
	
	public ActivityTypeResponse() {
	}
	
	public ActivityTypeResponse(ActivityTypeEntry activityTypeEntry, List<ActivityCategoryEntry> activityCategories) {
		this.id = activityTypeEntry.getId();
		this.name = activityTypeEntry.getName();
		this.activityCategories = activityCategories;
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

	public List<ActivityCategoryEntry> getActivityCategories() {
		return activityCategories;
	}

	public void setActivityCategories(List<ActivityCategoryEntry> activityCategories) {
		this.activityCategories = activityCategories;
	}
}