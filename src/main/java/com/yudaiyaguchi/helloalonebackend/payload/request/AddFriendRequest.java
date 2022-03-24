package com.yudaiyaguchi.helloalonebackend.payload.request;

import java.util.Map;

import javax.validation.constraints.NotBlank;

public class AddFriendRequest {

	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@NotBlank
	private String image;
	
	public Map<String, Object> optionalFeatures;

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

	public Map<String, Object> getOptionalFeatures() {
		return optionalFeatures;
	}

	public void setOptionalFeatures(Map<String, Object> optionalFeatures) {
		this.optionalFeatures = optionalFeatures;
	}

	@Override
	public String toString() {
		return "AddFriendRequest [firstName=" + firstName + ", lastName=" + lastName + ", optionalFeatures="
				+ optionalFeatures + "]";
	}
}
