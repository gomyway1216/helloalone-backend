package com.yudaiyaguchi.helloalonebackend.api;

import java.util.Map;

public class Person {
	
	private String mainImage;
	private String firstName;
	private String lastName;
	private Map<String, Object> optionalFeatures;
	private String description;

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
	
	public Map<String, Object> getOptionalFeatures() {
		return optionalFeatures;
	}
	
	public void setTags(Map<String, Object> optionalFeatures) {
		this.optionalFeatures = optionalFeatures;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
