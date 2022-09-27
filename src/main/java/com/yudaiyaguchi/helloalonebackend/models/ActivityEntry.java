package com.yudaiyaguchi.helloalonebackend.models;

import java.util.Date;
import java.util.List;
import com.google.cloud.firestore.GeoPoint;
import com.yudaiyaguchi.helloalonebackend.payload.request.ActivityEntryRequest;

public class ActivityEntry {

	private String id;
	private String userId;
	private GeoPoint location;
	private Date startTime;
	private Date endTime;
	private String weatherId;
	// list of friend ids that attended the activity
	private List<String> friendIds;
	// list of activity type ids
	private List<String> activityTypeIds;
	private List<String> links;
	private String description;
	
	public ActivityEntry() {
	}
	
	public ActivityEntry(ActivityEntryRequest request) {
		this.userId = request.getUserId();
		this.location = request.getLocation();
		this.startTime = new Date();
		this.endTime = new Date();
		this.weatherId = request.getWeatherId();
		this.friendIds = request.getFriendIds();
		this.activityTypeIds = request.getActivityTypeIds();
		this.links = request.getLinks();
		this.description = request.getDescription();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public GeoPoint getLocation() {
		return location;
	}
	
	public void setLocation(GeoPoint location) {
		this.location = location;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getWeatherId() {
		return weatherId;
	}
	
	public void setWeatherId(String weatherId) {
		this.weatherId = weatherId;
	}
	
	public List<String> getFriendIds() {
		return friendIds;
	}
	
	public void setFriendIds(List<String> friendIds) {
		this.friendIds = friendIds;
	}
	
	public List<String> getActivityTypeIds() {
		return activityTypeIds;
	}
	
	public void setActivityTypes(List<String> activityTypeIds) {
		this.activityTypeIds = activityTypeIds;
	}
	
	public List<String> getLinks() {
		return links;
	}
	
	public void setLinks(List<String> links) {
		this.links = links;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
