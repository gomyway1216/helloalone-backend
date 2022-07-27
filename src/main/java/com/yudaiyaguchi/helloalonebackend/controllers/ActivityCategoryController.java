package com.yudaiyaguchi.helloalonebackend.controllers;


import java.util.ArrayList;
import java.util.List;
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
import com.yudaiyaguchi.helloalonebackend.models.ActivityCategoryEntry;
import com.yudaiyaguchi.helloalonebackend.payload.request.ActivityCategoryEntryRequest;
import com.yudaiyaguchi.helloalonebackend.payload.response.ActivityCategoryResponse;
import com.yudaiyaguchi.helloalonebackend.payload.response.MessageResponse;
import com.yudaiyaguchi.helloalonebackend.repository.ActivityRepository;
import com.yudaiyaguchi.helloalonebackend.repository.UserRepository;
import com.yudaiyaguchi.helloalonebackend.security.jwt.JwtUtils;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/activity-category")
public class ActivityCategoryController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityCategoryController.class);	
	
    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    UserRepository userRepository;
    
	@Autowired
	ActivityRepository activityRepository;
	
    @GetMapping("/")
    public ResponseEntity<?> getActivityCategoryEntries(HttpServletRequest request) {
        String jwt = jwtUtils.parseJwt(request);
        String userId = userRepository.getUserId(jwt);
        // admin user can only modify the global values.
        List<ActivityCategoryResponse> activityCategoryResponseList = new ArrayList<>();
        try {
        	List<ActivityCategoryEntry> activityCategoryEntryList = this.activityRepository.getActivityCategoryEntries();
        	activityCategoryEntryList.forEach(entry -> {
        		ActivityCategoryResponse response = new ActivityCategoryResponse(entry);
        		activityCategoryResponseList.add(response);
        	});
        } catch (Exception e) {
            LOGGER.warn("error finding record for user: {} Error: {}", userId, e);
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to get ActivityCategoryEntries"));
        }
        return ResponseEntity.ok(activityCategoryResponseList);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getActivityCategoryEntity(HttpServletRequest request, @PathVariable("id") String id) {
    	String jwt = jwtUtils.parseJwt(request);
        String userId = userRepository.getUserId(jwt);
        ActivityCategoryResponse response = null;
        try {
        	ActivityCategoryEntry entry = this.activityRepository.getActivityCategoryEntryById(id);
        	response = new ActivityCategoryResponse(entry);
        } catch (Exception e) {
        	LOGGER.warn("error finding acitvity category entry for user: {} Error: {}", userId, e);
        	return ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to get ActivityCategoryEntry"));
        }
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/")
    public ResponseEntity<?> insertActivityCategoryEntity(HttpServletRequest request, @Valid @RequestBody ActivityCategoryEntryRequest entRequest) {
    	String jwt = jwtUtils.parseJwt(request);
    	ActivityCategoryEntry entry = new ActivityCategoryEntry(entRequest);
		try {
			if(!userRepository.isUserIdAdminByToken(jwt)) {
				LOGGER.warn("user is not admin and this activity category insertion is prohibited");
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not admin and this activity category insertion is prohibited"));
			}
			entry = this.activityRepository.insertActivityCategoryEntry(entry);
		} catch (Exception e) {
			LOGGER.warn("error adding activity category for entry: {}, Error: {}", entry, e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to insert data"));
		}
		return ResponseEntity.ok(entry);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateActivityCategoryEntity(HttpServletRequest request, @Valid @RequestBody ActivityCategoryEntryRequest entRequest,
    		@PathVariable("id") String id) {
    	String jwt = jwtUtils.parseJwt(request);
		ActivityCategoryEntry entry = new ActivityCategoryEntry(entRequest);
		entry.setId(id);
		String timeStamp = null;
		try {
			if(!userRepository.isUserIdAdminByToken(jwt)) {
				LOGGER.warn("user is not admin and this activity category update is prohibited");
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not admin and this activity category update is prohibited"));
			}
			timeStamp = this.activityRepository.updateActivityCategoryEntry(entry);
		} catch (Exception e) {
			LOGGER.info("error adding activity category for entry: {}, Error: {}", entry, e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to update data"));
		}
		return ResponseEntity.ok(timeStamp);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteActivityCategoryEntity(HttpServletRequest request, @PathVariable("id") String id) {
    	String jwt = jwtUtils.parseJwt(request);
    	String userId = userRepository.getUserId(jwt);
    	String timeStamp = null;
    	try {
			if(!userRepository.isUserIdAdminByToken(jwt)) {
				LOGGER.warn("user is not admin and this activity category deletion is prohibited");
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not admin and this activity category deletion is prohibited"));
			}
    		timeStamp = this.activityRepository.deleteActivityCategoryEntry(id);
    	} catch (Exception e) {
			LOGGER.info("error deleting activity cateogry for user: {} id: {}, Error: {}", userId, id, e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to delete data"));
    	}
    	return ResponseEntity.ok(timeStamp);
    }
}