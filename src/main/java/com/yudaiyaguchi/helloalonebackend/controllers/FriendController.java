package com.yudaiyaguchi.helloalonebackend.controllers;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yudaiyaguchi.helloalonebackend.models.Friend;
import com.yudaiyaguchi.helloalonebackend.models.User;
import com.yudaiyaguchi.helloalonebackend.payload.request.AddFriendRequest;
import com.yudaiyaguchi.helloalonebackend.repository.FriendRepository;
import com.yudaiyaguchi.helloalonebackend.repository.UserRepository2;
import com.yudaiyaguchi.helloalonebackend.security.jwt.JwtUtils;
import com.yudaiyaguchi.helloalonebackend.security.services.UserDetailsServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/friends")
public class FriendController {
	
	@Autowired
	FriendRepository friendRepository;
	
	@Autowired
	UserRepository2 userRepository;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	private static final Logger LOGGER = LoggerFactory.getLogger(FriendController.class);
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllFriend(HttpServletRequest request) {
		String jwt = parseJwt(request);
		String userId = this.getUserId(jwt);
		List<Friend> friendList = null;
		try {
			friendList = this.friendRepository.getFriends(userId);
		} catch (Exception e) {
			LOGGER.info("error finding id");
		}
		return ResponseEntity.ok(friendList);
	}
	
	@GetMapping("/friend")
	public ResponseEntity<?> getAllFriend(HttpServletRequest request, @Valid String friendId) {
		LOGGER.info("friendId : {}", friendId);
		String jwt = parseJwt(request);
		String userId = this.getUserId(jwt);
		Friend friend = null;
		try {
			friend = this.friendRepository.getFriendById(userId, friendId);
		} catch (Exception e) {
			LOGGER.info("error finding id");
		}
		
		return ResponseEntity.ok(friend);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addFriend(HttpServletRequest request, @Valid @RequestBody AddFriendRequest addFriendRequest) {
		LOGGER.info("addFriendRequest : {}", addFriendRequest);
		String jwt = parseJwt(request);
		String userId = this.getUserId(jwt);
		Friend friend = new Friend();
		friend.setFirstName(addFriendRequest.getFirstName());
		friend.setLastName(addFriendRequest.getLastName());
		friend.setOptionalFeatures(addFriendRequest.getOptionalFeatures());
		friend.setRegisteredTime(new Date());
		try {
			friend = this.friendRepository.addFriend(userId, friend);
		} catch (Exception e) {
			LOGGER.info("error finding id");
		}
		
		return ResponseEntity.ok(friend);
	}
	
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}
	
	private String getUserId(String jwt) {
		LOGGER.info("jwt token for Friend: :{}", jwt);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        User user = null;
		try {
			user = this.userRepository.findByUsername(username);
		} catch (Exception e) {
			LOGGER.error("error getting user: {}", e);
		}
        String userId = user.getId();
        LOGGER.info("userId : {}", userId);
        return userId;
	}
}
