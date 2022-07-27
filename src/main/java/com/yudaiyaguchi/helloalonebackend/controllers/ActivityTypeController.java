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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yudaiyaguchi.helloalonebackend.models.ActivityTypeEntry;
import com.yudaiyaguchi.helloalonebackend.payload.request.ActivityTypeEntryRequest;
import com.yudaiyaguchi.helloalonebackend.payload.response.ActivityTypeResponse;
import com.yudaiyaguchi.helloalonebackend.payload.response.MessageResponse;
import com.yudaiyaguchi.helloalonebackend.repository.ActivityRepository;
import com.yudaiyaguchi.helloalonebackend.repository.FriendRepository;
import com.yudaiyaguchi.helloalonebackend.repository.UserRepository;
import com.yudaiyaguchi.helloalonebackend.repository.WeatherRepository;
import com.yudaiyaguchi.helloalonebackend.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/activity-type")
public class ActivityTypeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityTypeController.class);	
	
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
    public ResponseEntity<?> getActivityTypeEntries(HttpServletRequest request) {
        String jwt = jwtUtils.parseJwt(request);
        String userId = userRepository.getUserId(jwt);
        List<ActivityTypeResponse> activityTypeResponseList = new ArrayList<>();
        try {
        	List<ActivityTypeEntry> activityTypeEntryList = this.activityRepository.getActivityTypeEntries();
        	activityTypeEntryList.forEach(entry -> {
        		ActivityTypeResponse response = null;
        		try {
            		response = new ActivityTypeResponse(entry, 
            				this.activityRepository.getActivityCategoryEntries(entry.getActivityCategoryIds()));
            		activityTypeResponseList.add(response);
        		} catch (InterruptedException | ExecutionException e) {
					LOGGER.warn("error finding child record for user: {} Error: {}", userId, e);
				}
        	});
        } catch (Exception e) {
            LOGGER.warn("error finding record for user: {} Error: {}", userId, e);
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to get ActivityTypeEntries"));
        }
        return ResponseEntity.ok(activityTypeResponseList);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getActivityTypeEntity(HttpServletRequest request, @PathVariable("id") String id) {
    	String jwt = jwtUtils.parseJwt(request);
        String userId = userRepository.getUserId(jwt);
        ActivityTypeResponse response = null;
        try {
        	ActivityTypeEntry entry = this.activityRepository.getActivityTypeEntryById(id);
        	response = new ActivityTypeResponse(entry,
        			this.activityRepository.getActivityCategoryEntries(entry.getActivityCategoryIds()));
        } catch (Exception e) {
        	LOGGER.warn("error finding acitvity type entry for user: {} Error: {}", userId, e);
        	return ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to get ActivityTypeEntry"));
        }
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/")
    public ResponseEntity<?> insertActivityTypeEntity(HttpServletRequest request, @Valid @RequestBody ActivityTypeEntryRequest entRequest) {
    	String jwt = jwtUtils.parseJwt(request);
    	ActivityTypeEntry entry = new ActivityTypeEntry(entRequest);
		try {
	        // admin user can only modify the global values.
			if(!userRepository.isUserIdAdminByToken(jwt)) {
				LOGGER.warn("user is not admin and this activity type insertion is prohibited");
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not admin and this activity type insertion is prohibited"));
			}
			entry = this.activityRepository.insertActivityTypeEntry(entry);
		} catch (Exception e) {
			LOGGER.warn("error adding activity type for entry: {}, Error: {}", entry, e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to insert data"));
		}
		return ResponseEntity.ok(entry);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateActivityTypeEntity(HttpServletRequest request, @Valid @RequestBody ActivityTypeEntryRequest entRequest,
    		@PathVariable("id") String id) {
    	String jwt = jwtUtils.parseJwt(request);
		ActivityTypeEntry entry = new ActivityTypeEntry(entRequest);
		entry.setId(id);
		String timeStamp = null;
		try {
	        // admin user can only modify the global values.
			if(!userRepository.isUserIdAdminByToken(jwt)) {
				LOGGER.warn("user is not admin and this activity type update is prohibited");
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not admin and this activity type update insertion is prohibited"));
			}
			timeStamp = this.activityRepository.updateActivityTypeEntry(entry);
		} catch (Exception e) {
			LOGGER.info("error adding activity type for entry: {}, Error: {}", entry, e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to update data"));
		}
		return ResponseEntity.ok(timeStamp);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteActivityTypeEntity(HttpServletRequest request, @PathVariable("id") String id) {
    	String jwt = jwtUtils.parseJwt(request);
    	String userId = userRepository.getUserId(jwt);
    	String timeStamp = null;
    	try {
            // admin user can only modify the global values.
			if(!userRepository.isUserIdAdminByToken(jwt)) {
				LOGGER.warn("user is not admin and this activity type deletion is prohibited");
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not admin and this activity type deletion is prohibited"));
			}
    		timeStamp = this.activityRepository.deleteActivityTypeEntry(id);
    	} catch (Exception e) {
			LOGGER.info("error deleting activity type for user: {} id: {}, Error: {}", userId, id, e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to delete data"));
    	}
    	return ResponseEntity.ok(timeStamp);
    }
}