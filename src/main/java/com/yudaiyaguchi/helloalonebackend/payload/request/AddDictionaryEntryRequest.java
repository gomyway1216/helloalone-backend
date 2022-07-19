package com.yudaiyaguchi.helloalonebackend.payload.request;

import java.util.List;
import javax.validation.constraints.NotBlank;

public class AddDictionaryEntryRequest {
	
	@NotBlank
	private String title;
	
	private String title_japanese;
	
	@NotBlank
	private List<String> tags;

	@NotBlank
	private String description;
	
	@NotBlank
	private int priority;
	
	@NotBlank
	private boolean isCompleted;

	public String getTitle() {
		return title;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle_japanese() {
		return title_japanese;
	}

	public void setTitle_japanese(String title_japanese) {
		this.title_japanese = title_japanese;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}
