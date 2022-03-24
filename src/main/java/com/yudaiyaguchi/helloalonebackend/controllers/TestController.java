package com.yudaiyaguchi.helloalonebackend.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yudaiyaguchi.helloalonebackend.payload.response.JwtResponse;
import com.yudaiyaguchi.helloalonebackend.security.services.RefreshTokenService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
	
//	@GetMapping("/all")
//	public ResponseEntity<?> allAccess() {
////		return "Public Content.";
//		return ResponseEntity.ok(new JwtResponse("accessToken", "refreshToken",
//				"val1", 
//				"val2", 
//				"val3"));
//	}
	
	@GetMapping("/all")
	public String allAccess() {
//		return "Public Content.";
		return "Public Content.";
	}
	
	@GetMapping("/user")
	public String userAccess() {
		return "User Content.";
	}

//	@GetMapping("/mod")
//	@PreAuthorize("hasRole('MODERATOR')")
//	public ResponseEntity<?> moderatorAccess() {
////		return "Moderator Board.";
//		return ResponseEntity.ok(new JwtResponse("accessToken", "refreshToken",
//				"val1", 
//				"val2", 
//				"val3"));
//	}
	
	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
}