package com.yudaiyaguchi.helloalonebackend.payload.request;

import javax.validation.constraints.NotBlank;

public class AddTagEntryRequest {

	@NotBlank
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
