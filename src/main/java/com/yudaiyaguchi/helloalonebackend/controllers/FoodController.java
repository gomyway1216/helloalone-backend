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
import com.yudaiyaguchi.helloalonebackend.models.FoodEntry;
import com.yudaiyaguchi.helloalonebackend.payload.request.FoodEntryRequest;
import com.yudaiyaguchi.helloalonebackend.payload.response.FoodResponse;
import com.yudaiyaguchi.helloalonebackend.payload.response.MessageResponse;
import com.yudaiyaguchi.helloalonebackend.repository.FoodRepository;
import com.yudaiyaguchi.helloalonebackend.repository.UserRepository;
import com.yudaiyaguchi.helloalonebackend.security.jwt.JwtUtils;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/food")
public class FoodController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FoodController.class);	
	
    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    UserRepository userRepository;
    
	@Autowired
	FoodRepository foodRepository;
	
    @GetMapping("/")
    public ResponseEntity<?> getFoodEntries(HttpServletRequest request) {
        String jwt = jwtUtils.parseJwt(request);
        String userId = userRepository.getUserId(jwt);
        List<FoodResponse> foodResponseList = new ArrayList<>();
        try {
        	List<FoodEntry> foodEntryList = this.foodRepository.getFoodEntries();
        	foodEntryList.forEach(entry -> {
        		FoodResponse response = new FoodResponse(entry);
        		foodResponseList.add(response);
        	});
        } catch (Exception e) {
            LOGGER.warn("error finding record for user: {} Error: {}", userId, e);
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to get FoodEntries"));
        }
        return ResponseEntity.ok(foodResponseList);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getFoodEntity(HttpServletRequest request, @PathVariable("id") String id) {
    	String jwt = jwtUtils.parseJwt(request);
        String userId = userRepository.getUserId(jwt);
        FoodResponse response = null;
        try {
        	FoodEntry entry = this.foodRepository.getFoodEntryById(id);
        	response = new FoodResponse(entry);
        } catch (Exception e) {
        	LOGGER.warn("error finding food entry for user: {} Error: {}", userId, e);
        	return ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to get FoodEntry"));
        }
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/")
    public ResponseEntity<?> insertFoodEntity(HttpServletRequest request, @Valid @RequestBody FoodEntryRequest entRequest) {
    	String jwt = jwtUtils.parseJwt(request);
    	FoodEntry entry = new FoodEntry(entRequest);
		try {
	        // admin user can only modify the global values.
			if(!userRepository.isUserIdAdminByToken(jwt)) {
				LOGGER.warn("user is not admin and this food insertion is prohibited");
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not admin and this food insertion is prohibited"));
			}
			entry = this.foodRepository.insertFoodEntry(entry);
		} catch (Exception e) {
			LOGGER.warn("error adding food for entry: {}, Error: {}", entry, e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to insert data"));
		}
		return ResponseEntity.ok(entry);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFoodEntity(HttpServletRequest request, @Valid @RequestBody FoodEntryRequest entRequest,
    		@PathVariable("id") String id) {
    	String jwt = jwtUtils.parseJwt(request);
		FoodEntry entry = new FoodEntry(entRequest);
		entry.setId(id);
		String timeStamp = null;
		try {
	        // admin user can only modify the global values.
			if(!userRepository.isUserIdAdminByToken(jwt)) {
				LOGGER.warn("user is not admin and this food update is prohibited");
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not admin and this food update is prohibited"));
			}
			timeStamp = this.foodRepository.updateFoodEntry(entry);
		} catch (Exception e) {
			LOGGER.info("error adding food for entry: {}, Error: {}", entry, e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to update data"));
		}
		return ResponseEntity.ok(timeStamp);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFoodCategoryEntity(HttpServletRequest request, @PathVariable("id") String id) {
    	String jwt = jwtUtils.parseJwt(request);
    	String userId = userRepository.getUserId(jwt);
    	String timeStamp = null;
    	try {
            // admin user can only modify the global values.
			if(!userRepository.isUserIdAdminByToken(jwt)) {
				LOGGER.warn("user is not admin and this food deletion is prohibited");
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not admin and this food deletion is prohibited"));
			}
    		timeStamp = this.foodRepository.deleteFoodEntry(id);
    	} catch (Exception e) {
			LOGGER.info("error deleting food for user: {} id: {}, Error: {}", userId, id, e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to delete data"));
    	}
    	return ResponseEntity.ok(timeStamp);
    }
}