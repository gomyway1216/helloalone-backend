package com.yudaiyaguchi.helloalonebackend.models;

import java.util.Date;
import java.util.List;

public class DictionaryEntry {

    private String id;
    private String title;
    private String title_japanese;
    private List<String> tags;
    private String description;
    private int priority;
    private Date created;
    private Date lastUpdated;
    private boolean isCompleted;

    public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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

    @Override
    public String toString() {
        return "DictionaryEntry [title=" + title + ", title_ruby=" + title_japanese + ", tags=" + tags + ", description="
                + description + ", priority=" + priority + ", created=" + created + ", lastUpdated=" + lastUpdated
                + "]";
    }
}
