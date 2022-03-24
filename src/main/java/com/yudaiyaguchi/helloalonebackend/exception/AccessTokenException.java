package com.yudaiyaguchi.helloalonebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessTokenException extends RuntimeException {
	
	private static final long serialVersionUID = 2L;
	
	public AccessTokenException(String token, String message) {
		super(String.format("Failed for [%s]: %s", token, message));
	}

}
