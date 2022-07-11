package com.yudaiyaguchi.helloalonebackend.models;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.cloud.firestore.GeoPoint;

public class FriendEntry {
	
	private String id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String firstNameNative;
	private String lastNameNative;
	private String firstNamePhonetic;
	private String lastNameNativePhonetic;
	private Date birthday;
	private String profileImageLink;
	private String[] otherImageLinks;
	private String shortDescription;
	private String description;
	private Map<String, String> socialMedias;
	private int phoneNumber;
	private String emailAddress;
	private Date created;
	private Date lastUpdated;
	private GeoPoint locationMet;
	// list of activity ids
	private List<String> activityIds;
	private String nationalityId;
	// list of favorite food ids
	private List<String> favoriteFood;
	// list of activity ids for hobby
	private List<String> hobbies;
	// list of college ids
	private List<String> colleges;
	// list of job ids
	private List<String> jobs;
	
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
	
	public String getMiddleName() {
		return middleName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFirstNameNative() {
		return firstNameNative;
	}
	
	public void setFirstNameNative(String firstNameNative) {
		this.firstNameNative = firstNameNative;
	}
	
	public String getLastNameNative() {
		return lastNameNative;
	}
	
	public void setLastNameNative(String lastNameNative) {
		this.lastNameNative = lastNameNative;
	}
	
	public String getFirstNamePhonetic() {
		return firstNamePhonetic;
	}
	
	public void setFirstNamePhonetic(String firstNamePhonetic) {
		this.firstNamePhonetic = firstNamePhonetic;
	}
	
	public String getLastNameNativePhonetic() {
		return lastNameNativePhonetic;
	}
	
	public void setLastNameNativePhonetic(String lastNameNativePhonetic) {
		this.lastNameNativePhonetic = lastNameNativePhonetic;
	}
	public Date getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getProfileImageLink() {
		return profileImageLink;
	}
	
	public void setProfileImageLink(String profileImageLink) {
		this.profileImageLink = profileImageLink;
	}
	
	public String[] getOtherImageLinks() {
		return otherImageLinks;
	}
	
	public void setOtherImageLinks(String[] otherImageLinks) {
		this.otherImageLinks = otherImageLinks;
	}
	
	public String getShortDescription() {
		return shortDescription;
	}
	
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Map<String, String> getSocialMedias() {
		return socialMedias;
	}
	
	public void setSocialMedias(Map<String, String> socialMedias) {
		this.socialMedias = socialMedias;
	}
	
	public int getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
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
	
	public GeoPoint getLocationMet() {
		return locationMet;
	}
	
	public void setLocationMet(GeoPoint locationMet) {
		this.locationMet = locationMet;
	}
	
	public List<String> getActivityIds() {
		return activityIds;
	}
	
	public void setActivityIds(List<String> activityIds) {
		this.activityIds = activityIds;
	}
	
	public String getNationalityId() {
		return nationalityId;
	}
	
	public void setNationalityId(String nationalityId) {
		this.nationalityId = nationalityId;
	}
	
	public List<String> getFavoriteFood() {
		return favoriteFood;
	}
	
	public void setFavoriteFood(List<String> favoriteFood) {
		this.favoriteFood = favoriteFood;
	}
	
	public List<String> getHobbies() {
		return hobbies;
	}
	
	public void setHobbies(List<String> hobbies) {
		this.hobbies = hobbies;
	}
	
	public List<String> getColleges() {
		return colleges;
	}
	
	public void setColleges(List<String> colleges) {
		this.colleges = colleges;
	}
	
	public List<String> getJobs() {
		return jobs;
	}
	
	public void setJobs(List<String> jobs) {
		this.jobs = jobs;
	}
}
