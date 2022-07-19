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
import com.yudaiyaguchi.helloalonebackend.models.ActivityEntry;
import com.yudaiyaguchi.helloalonebackend.payload.request.ActivityEntryRequest;
import com.yudaiyaguchi.helloalonebackend.payload.response.ActivityResponse;
import com.yudaiyaguchi.helloalonebackend.payload.response.MessageResponse;
import com.yudaiyaguchi.helloalonebackend.repository.ActivityRepository;
import com.yudaiyaguchi.helloalonebackend.repository.FriendRepository;
import com.yudaiyaguchi.helloalonebackend.repository.UserRepository;
import com.yudaiyaguchi.helloalonebackend.repository.WeatherRepository;
import com.yudaiyaguchi.helloalonebackend.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/activity")
public class ActivityController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityController.class);	
	
    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    UserRepository userRepository;
    
	@Autowired
	ActivityRepository activityRepository;
	
	@Autowired
	FriendRepository friendRepository;
	
	@Autowired
	WeatherRepository weatherRepository;
	
    @GetMapping("/")
    public ResponseEntity<?> getActivityEntries(HttpServletRequest request) {
        String jwt = jwtUtils.parseJwt(request);
        String userId = userRepository.getUserId(jwt);
        List<ActivityResponse> activityResponseList = new ArrayList<>();
        try {
        	List<ActivityEntry> activityEntryList = this.activityRepository.getActivityEntries(userId);
        	activityEntryList.forEach(entry -> {
        		ActivityResponse response = null;
        		try {
        			response = new ActivityResponse(entry,
        					this.weatherRepository.getWeatherEntryById(entry.getWeatherId()),
        					this.friendRepository.getFriendEntries(entry.getUserId(), entry.getFriendIds()),
        					this.activityRepository.getActivityTypeEntries(entry.getActivityTypeIds()));
        			activityResponseList.add(response);
				} catch (InterruptedException | ExecutionException e) {
					LOGGER.warn("error finding child record for user: {} Error: {}", userId, e);
				}
        	});
        } catch (Exception e) {
            LOGGER.warn("error finding record for user: {} Error: {}", userId, e);
            ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to get FriendEntries"));
        }
        return ResponseEntity.ok(activityResponseList);
    }
    
    @GetMapping("/{responseEntryId}")
    public ResponseEntity<?> getActivityEntity(HttpServletRequest request, @Valid String responseEntryId) {
    	String jwt = jwtUtils.parseJwt(request);
        String userId = userRepository.getUserId(jwt);
        ActivityResponse response = null;
        try {
        	ActivityEntry entry = this.activityRepository.getActivityEntryById(userId, responseEntryId);
        	response = new ActivityResponse(entry,
					this.weatherRepository.getWeatherEntryById(entry.getWeatherId()),
					this.friendRepository.getFriendEntries(entry.getUserId(), entry.getFriendIds()),
					this.activityRepository.getActivityTypeEntries(entry.getActivityTypeIds()));
        } catch (Exception e) {
        	LOGGER.warn("error finding friend entry for user: {} Error: {}", userId, e);
        	ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to get FriendEntry"));
        }
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/insert")
    public ResponseEntity<?> insertActivityEntity(HttpServletRequest request, @Valid @RequestBody ActivityEntryRequest entRequest) {
    	String jwt = jwtUtils.parseJwt(request);
		String userId = userRepository.getUserId(jwt);
		ActivityEntry entry = new ActivityEntry(entRequest);
		try {
			entry = this.activityRepository.insertActivityEntry(userId, entry);
		} catch (Exception e) {
			LOGGER.info("error adding activity for user: {} entry: {}, Error: {}", userId, entry, e);
			ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to insert data"));
		}
		return ResponseEntity.ok(entry);
    }
    
    @PutMapping("/update")
    public ResponseEntity<?> updateFriendEntity(HttpServletRequest request, @Valid @RequestBody ActivityEntryRequest entRequest) {
    	String jwt = jwtUtils.parseJwt(request);
		String userId = userRepository.getUserId(jwt);
		ActivityEntry entry = new ActivityEntry(entRequest);
		String timeStamp = null;
		try {
			timeStamp = this.activityRepository.updateActivityEntry(userId, entry);
		} catch (Exception e) {
			LOGGER.info("error adding activity for user: {} entry: {}, Error: {}", userId, entry, e);
			ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to update data"));
		}
		return ResponseEntity.ok(timeStamp);
    }
}
