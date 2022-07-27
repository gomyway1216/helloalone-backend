package com.yudaiyaguchi.helloalonebackend.payload.response;


import java.util.Date;
import java.util.List;
import java.util.Map;
import com.google.cloud.firestore.GeoPoint;
import com.yudaiyaguchi.helloalonebackend.models.ActivityEntry;
import com.yudaiyaguchi.helloalonebackend.models.ActivityTypeEntry;
import com.yudaiyaguchi.helloalonebackend.models.CollegeEntry;
import com.yudaiyaguchi.helloalonebackend.models.FoodEntry;
import com.yudaiyaguchi.helloalonebackend.models.FriendEntry;
import com.yudaiyaguchi.helloalonebackend.models.JobEntry;
import com.yudaiyaguchi.helloalonebackend.models.NationalityEntry;

public class FriendResponse {
	
	private String id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String firstNameNative;
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
	// list of activities
	private List<ActivityEntry> activities;
	private NationalityEntry nationality;
	// list of favorite foods
	private List<FoodEntry> favoriteFoods;
	// list of activity types for hobby
	private List<ActivityTypeEntry> hobbies;
	// list of colleges
	private List<CollegeEntry> colleges;
	// list of jobs
	private List<JobEntry> jobs;
	
	public FriendResponse() {
	}
	
	public FriendResponse(FriendEntry friendEntry, List<ActivityEntry> activities, NationalityEntry nationality, 
			List<FoodEntry> favoriteFoods, List<ActivityTypeEntry> hobbies, List<CollegeEntry> colleges, List<JobEntry> jobs) {
		this.id = friendEntry.getId();
		this.firstName = friendEntry.getFirstName();
		this.middleName = friendEntry.getMiddleName();
		this.lastName = friendEntry.getLastName();
		this.firstNameNative = friendEntry.getFirstNameNative();
		this.lastNameNative = friendEntry.getLastNameNative();
		this.firstNameNativePhonetic = friendEntry.getFirstNameNativePhonetic();
		this.lastNameNativePhonetic = friendEntry.getLastNameNativePhonetic();
		this.gender = friendEntry.getGender();
		this.birthday = friendEntry.getBirthday();
		this.profileImageLink = friendEntry.getProfileImageLink();
		this.otherImageLinks = friendEntry.getOtherImageLinks();
		this.shortDescription = friendEntry.getShortDescription();
		this.description = friendEntry.getDescription();
		this.socialMedias = friendEntry.getSocialMedias();
		this.phoneNumber = friendEntry.getPhoneNumber();
		this.emailAddress = friendEntry.getEmailAddress();
		this.created = friendEntry.getCreated();
		this.lastUpdated = friendEntry.getLastUpdated();
		this.locationMet = friendEntry.getLocationMet();
		this.activities = activities;
		this.nationality = nationality;
		this.favoriteFoods = favoriteFoods;
		this.hobbies = hobbies;
		this.colleges = colleges;
		this.jobs = jobs;
	}
	
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
	
	public List<ActivityEntry> getActivities() {
		return activities;
	}
	
	public void setActivities(List<ActivityEntry> activities) {
		this.activities = activities;
	}
	
	public NationalityEntry getNationality() {
		return nationality;
	}
	
	public void setNationality(NationalityEntry nationality) {
		this.nationality = nationality;
	}
	
	public List<FoodEntry> getFavoriteFoods() {
		return favoriteFoods;
	}
	
	public void setFavoriteFoods(List<FoodEntry> favoriteFoods) {
		this.favoriteFoods = favoriteFoods;
	}
	
	public List<ActivityTypeEntry> getHobbies() {
		return hobbies;
	}
	
	public void setHobbies(List<ActivityTypeEntry> hobbies) {
		this.hobbies = hobbies;
	}
	
	public List<CollegeEntry> getColleges() {
		return colleges;
	}
	
	public void setColleges(List<CollegeEntry> colleges) {
		this.colleges = colleges;
	}
	
	public List<JobEntry> getJobs() {
		return jobs;
	}
	
	public void setJobs(List<JobEntry> jobs) {
		this.jobs = jobs;
	}
}
