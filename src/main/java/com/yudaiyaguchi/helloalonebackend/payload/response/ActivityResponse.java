package com.yudaiyaguchi.helloalonebackend.payload.response;

import java.util.Date;
import java.util.List;
import com.google.cloud.firestore.GeoPoint;
import com.yudaiyaguchi.helloalonebackend.models.ActivityEntry;
import com.yudaiyaguchi.helloalonebackend.models.ActivityTypeEntry;
import com.yudaiyaguchi.helloalonebackend.models.FriendEntry;
import com.yudaiyaguchi.helloalonebackend.models.WeatherEntry;

public class ActivityResponse {

	private String id;
	private String userId;
	private GeoPoint location;
	private Date startTime;
	private Date endTime;
	private WeatherEntry weather;
	// list of friends that attended the activity
	private List<FriendEntry> friends;
	// list of activity types
	private List<ActivityTypeEntry> activityTypes;
	private List<String> links;
	private String description;
	
	public ActivityResponse() {
	}
	
	public ActivityResponse(ActivityEntry activityEntry, WeatherEntry weather,
			List<FriendEntry> friendEntry, List<ActivityTypeEntry> activityTypes) {
		this.id = activityEntry.getId();
		this.location = activityEntry.getLocation();
		this.startTime = activityEntry.getStartTime();
		this.endTime = activityEntry.getEndTime();
		this.weather = weather;
		this.friends = friendEntry;
		this.activityTypes = activityTypes;
		this.links = activityEntry.getLinks();
		this.description = activityEntry.getDescription();
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

	public WeatherEntry getWeather() {
		return weather;
	}

	public void setWeather(WeatherEntry weather) {
		this.weather = weather;
	}

	public List<FriendEntry> getFriends() {
		return friends;
	}

	public void setFriends(List<FriendEntry> friends) {
		this.friends = friends;
	}

	public List<ActivityTypeEntry> getActivityTypes() {
		return activityTypes;
	}

	public void setActivityTypes(List<ActivityTypeEntry> activityTypes) {
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
