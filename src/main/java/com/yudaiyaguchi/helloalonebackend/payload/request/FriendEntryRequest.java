package com.yudaiyaguchi.helloalonebackend.payload.request;


import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import com.google.cloud.firestore.GeoPoint;

public class FriendEntryRequest {
	
	@NotBlank
	private String firstName;
	private String middleName;
	@NotBlank
	private String lastName;
	@NotBlank
	private String firstNameNative;
	@NotBlank
	private String lastNameNative;
	private String firstNameNativePhonetic;
	private String lastNameNativePhonetic;
	private String gender;
	private Date birthday;
	private String profileImageLink;
	private List<String> otherImageLinks;
	private String shortDescription;
	private String description;
	private Map<String, String> socialMedias;
	private int phoneNumber;
	private String emailAddress;
	private Date created;
	private Date lastUpdated;
	private GeoPoint locationMet;
	private String nationalityId;
	// list of favorite food ids
	private List<String> favoriteFoodIds;
	// list of activityType ids for hobby
	private List<String> hobbyIds;
	// list of college ids
	private List<String> collegeIds;
	// list of job ids
	private List<String> jobIds;
	
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
	
	public String getFirstNameNativePhonetic() {
		return firstNameNativePhonetic;
	}
	
	public void setFirstNameNativePhonetic(String firstNameNativePhonetic) {
		this.firstNameNativePhonetic = firstNameNativePhonetic;
	}
	
	public String getLastNameNativePhonetic() {
		return lastNameNativePhonetic;
	}
	
	public void setLastNameNativePhonetic(String lastNameNativePhonetic) {
		this.lastNameNativePhonetic = lastNameNativePhonetic;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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
	
	public List<String> getOtherImageLinks() {
		return otherImageLinks;
	}
	
	public void setOtherImageLinks(List<String> otherImageLinks) {
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
	
	public String getNationalityId() {
		return nationalityId;
	}
	
	public void setNationalityId(String nationalityId) {
		this.nationalityId = nationalityId;
	}
	
	public List<String> getFavoriteFoodIds() {
		return favoriteFoodIds;
	}
	
	public void setFavoriteFoodIds(List<String> favoriteFoodIds) {
		this.favoriteFoodIds = favoriteFoodIds;
	}
	
	public List<String> getHobbyIds() {
		return hobbyIds;
	}
	
	public void setHobbyIds(List<String> hobbyIds) {
		this.hobbyIds = hobbyIds;
	}
	
	public List<String> getCollegeIds() {
		return collegeIds;
	}
	
	public void setCollegeIds(List<String> collegeIds) {
		this.collegeIds = collegeIds;
	}
	
	public List<String> getJobIds() {
		return jobIds;
	}
	
	public void setJobIds(List<String> jobIds) {
		this.jobIds = jobIds;
	}
}