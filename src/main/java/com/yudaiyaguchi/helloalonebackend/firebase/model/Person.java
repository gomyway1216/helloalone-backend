package com.yudaiyaguchi.helloalonebackend.firebase.model;


import java.util.Date;
import java.util.Map;

public class Person {
	
	private Date created;
	private Date lastUpdated;
	private String mainImage;
	private String firstName;
	private String lastName;
	private Map<String, String> tags;
	private String description;

	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public Date getLastUpdated() {
		return lastUpdated;
	}
	
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	public String getMainImage() {
		return mainImage;
	}
	
	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
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
	
	public Map<String, String> getTags() {
		return tags;
	}
	
	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}