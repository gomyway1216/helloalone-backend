package com.yudaiyaguchi.helloalonebackend.models;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.ServerTimestamp;

import java.util.Date;
import java.util.Map;

public class Friend {
	
	private String id;
	private String firstName;
	private String lastName;
	private String image;
	private @ServerTimestamp Date registeredTime;
	private Map<String, Object> optionalFeatures;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getImage() {
		return this.image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public Date getRegisteredTime() {
		return registeredTime;
	}

	public void setRegisteredTime(Date registeredTime) {
		this.registeredTime = registeredTime;
	}

	public Map<String, Object> getOptionalFeatures() {
		return optionalFeatures;
	}

	public void setOptionalFeatures(Map<String, Object> optionalFeatures) {
		this.optionalFeatures = optionalFeatures;
	}

	@Override
	public String toString() {
		return "Friend [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", registeredTime="
				+ registeredTime + ", optionalFeatures=" + optionalFeatures + "]";
	}
}
