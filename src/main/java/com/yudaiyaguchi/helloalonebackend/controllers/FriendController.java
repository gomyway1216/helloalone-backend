package com.yudaiyaguchi.helloalonebackend.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yudaiyaguchi.helloalonebackend.models.FriendEntry;
import com.yudaiyaguchi.helloalonebackend.payload.request.FriendEntryRequest;
import com.yudaiyaguchi.helloalonebackend.payload.response.FriendResponse;
import com.yudaiyaguchi.helloalonebackend.payload.response.MessageResponse;
import com.yudaiyaguchi.helloalonebackend.repository.ActivityRepository;
import com.yudaiyaguchi.helloalonebackend.repository.CollegeRepository;
import com.yudaiyaguchi.helloalonebackend.repository.FoodRepository;
import com.yudaiyaguchi.helloalonebackend.repository.FriendRepository;
import com.yudaiyaguchi.helloalonebackend.repository.JobRepository;
import com.yudaiyaguchi.helloalonebackend.repository.NationalityRepository;
import com.yudaiyaguchi.helloalonebackend.repository.UserRepository;
import com.yudaiyaguchi.helloalonebackend.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/friend-tracker")
public class FriendController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FriendController.class);	
    
    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    UserRepository userRepository;
    
	@Autowired
	ActivityRepository activityRepository;
	
	@Autowired
	NationalityRepository nationalityRepository;
	
	@Autowired
	FoodRepository foodRepository;
	
	@Autowired
	CollegeRepository collegeRepository;
	
	@Autowired
	JobRepository jobRepository;
	
	@Autowired
	FriendRepository friendRepository;
  
    @GetMapping("/")
    public ResponseEntity<?> getFriendEntries(HttpServletRequest request) {
        String jwt = jwtUtils.parseJwt(request);
        String userId = userRepository.getUserId(jwt);
        List<FriendResponse> friendResponseList = new ArrayList<>();
        try {
        	List<FriendEntry> friendEntryList = this.friendRepository.getFriendEntries(userId);
        	friendEntryList.forEach(entry -> {
        		FriendResponse response = null;
        		try {
        			response = new FriendResponse(entry, 
        				this.activityRepository.getActivityEntries(userId, entry.getActivityIds()),
        				this.nationalityRepository.getNationalityEntryById(entry.getNationalityId()),
        				this.foodRepository.getFoodEntries(),
        				this.activityRepository.getActivityTypeEntries(entry.getHobbyIds()),
        				this.collegeRepository.getCollegeEntries(entry.getCollegeIds()),
        				this.jobRepository.getJobEntries(entry.getJobIds()));
					friendResponseList.add(response);
				} catch (InterruptedException | ExecutionException e) {
					LOGGER.warn("error finding child record for user: {} Error: {}", userId, e);
				}
        	});
        } catch (Exception e) {
            LOGGER.warn("error finding record for user: {} Error: {}", userId, e);
            ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to get FriendEntries"));
        }
        return ResponseEntity.ok(friendResponseList);
    }
    
    @GetMapping("/{responseEntryId}")
    public ResponseEntity<?> getFriendEntity(HttpServletRequest request, @Valid String responseEntryId) {
    	String jwt = jwtUtils.parseJwt(request);
        String userId = userRepository.getUserId(jwt);
        FriendResponse response = null;
        try {
        	FriendEntry entry = this.friendRepository.getFriendEntryById(userId, responseEntryId);
			response = new FriendResponse(entry, 
    				this.activityRepository.getActivityEntries(userId, entry.getActivityIds()),
    				this.nationalityRepository.getNationalityEntryById(entry.getNationalityId()),
    				this.foodRepository.getFoodEntries(),
    				this.activityRepository.getActivityTypeEntries(entry.getHobbyIds()),
    				this.collegeRepository.getCollegeEntries(entry.getCollegeIds()),
    				this.jobRepository.getJobEntries(entry.getJobIds()));
        } catch (Exception e) {
        	LOGGER.warn("error finding friend entry for user: {} Error: {}", userId, e);
        	ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to get FriendEntry"));
        }
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/insert")
    public ResponseEntity<?> insertFriendEntity(HttpServletRequest request, @Valid @RequestBody FriendEntryRequest entRequest) {
    	String jwt = jwtUtils.parseJwt(request);
		String userId = userRepository.getUserId(jwt);
		FriendEntry entry = new FriendEntry(entRequest);
		try {
			entry = this.friendRepository.insertFriendEntry(userId, entry);
		} catch (Exception e) {
			LOGGER.info("error adding friend for user: {} entry: {}, Error: {}", userId, entry, e);
			ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to insert data"));
		}
		return ResponseEntity.ok(entry);
    }
    
    @PutMapping("/update")
    public ResponseEntity<?> updateFriendEntity(HttpServletRequest request, @Valid @RequestBody FriendEntryRequest entRequest) {
    	String jwt = jwtUtils.parseJwt(request);
		String userId = userRepository.getUserId(jwt);
		FriendEntry entry = new FriendEntry(entRequest);
		String timeStamp = null;
		try {
			timeStamp = this.friendRepository.updateFriendEntry(userId, entry);
		} catch (Exception e) {
			LOGGER.info("error adding friend for user: {} entry: {}, Error: {}", userId, entry, e);
			ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to update data"));
		}
		return ResponseEntity.ok(timeStamp);
    }
}
