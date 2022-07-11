package com.yudaiyaguchi.helloalonebackend.models;

import java.util.Date;
import java.util.List;
import com.google.cloud.firestore.GeoPoint;

public class ActivityEntry {

	private String id;
	private GeoPoint location;
	private Date startTime;
	private Date endTime;
	private String weather;
	private List<String> people;
	// list of activity type ids
	private List<String> activityTypes;
	private List<String> links;
	private String description;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
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
	
	public String getWeather() {
		return weather;
	}
	
	public void setWeather(String weather) {
		this.weather = weather;
	}
	
	public List<String> getPeople() {
		return people;
	}
	
	public void setPeople(List<String> people) {
		this.people = people;
	}
	
	public List<String> getActivityTypes() {
		return activityTypes;
	}
	
	public void setActivityTypes(List<String> activityTypes) {
		this.activityTypes = activityTypes;
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
