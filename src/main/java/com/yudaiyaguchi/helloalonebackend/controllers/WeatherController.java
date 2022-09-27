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
import com.yudaiyaguchi.helloalonebackend.models.WeatherEntry;
import com.yudaiyaguchi.helloalonebackend.payload.request.WeatherEntryRequest;
import com.yudaiyaguchi.helloalonebackend.payload.response.MessageResponse;
import com.yudaiyaguchi.helloalonebackend.payload.response.WeatherResponse;
import com.yudaiyaguchi.helloalonebackend.repository.UserRepository;
import com.yudaiyaguchi.helloalonebackend.repository.WeatherRepository;
import com.yudaiyaguchi.helloalonebackend.security.jwt.JwtUtils;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/weather")
public class WeatherController {
	private static final Logger LOGGER = LoggerFactory.getLogger(WeatherController.class);	
	
    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    UserRepository userRepository;
    
	@Autowired
	WeatherRepository weatherRepository;
	
    @GetMapping("/")
    public ResponseEntity<?> getWeatherEntries(HttpServletRequest request) {
        String jwt = jwtUtils.parseJwt(request);
        String userId = userRepository.getUserId(jwt);
        List<WeatherResponse> weatherResponseList = new ArrayList<>();
        try {
        	List<WeatherEntry> weatherEntryList = this.weatherRepository.getWeatherEntries();
        	weatherEntryList.forEach(entry -> {
        		WeatherResponse response = new WeatherResponse(entry);
        		weatherResponseList.add(response);
        	});
        } catch (Exception e) {
            LOGGER.warn("error finding record for user: {} Error: {}", userId, e);
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to get WeatherEntries"));
        }
        return ResponseEntity.ok(weatherResponseList);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getWeatherEntity(HttpServletRequest request, @PathVariable("id") String id) {
    	String jwt = jwtUtils.parseJwt(request);
        String userId = userRepository.getUserId(jwt);
        WeatherResponse response = null;
        try {
        	WeatherEntry entry = this.weatherRepository.getWeatherEntryById(id);
        	response = new WeatherResponse(entry);
        } catch (Exception e) {
        	LOGGER.warn("error finding weather entry for user: {} Error: {}", userId, e);
        	return ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to get WeatherEntry"));
        }
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/")
    public ResponseEntity<?> insertWeatherEntity(HttpServletRequest request, @Valid @RequestBody WeatherEntryRequest entRequest) {
    	String jwt = jwtUtils.parseJwt(request);
    	WeatherEntry entry = new WeatherEntry(entRequest);
		try {
	        // admin user can only modify the global values.
			if(!userRepository.isUserIdAdminByToken(jwt)) {
				LOGGER.warn("user is not admin and this weather insertion is prohibited");
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not admin and this weather insertion is prohibited"));
			}
			entry = this.weatherRepository.insertWeatherEntry(entry);
		} catch (Exception e) {
			LOGGER.warn("error adding weather for entry: {}, Error: {}", entry, e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to insert data"));
		}
		return ResponseEntity.ok(entry);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateWeatherEntity(HttpServletRequest request, @Valid @RequestBody WeatherEntryRequest entRequest,
    		@PathVariable("id") String id) {
    	String jwt = jwtUtils.parseJwt(request);
		WeatherEntry entry = new WeatherEntry(entRequest);
		entry.setId(id);
		String timeStamp = null;
		try {
	        // admin user can only modify the global values.
			if(!userRepository.isUserIdAdminByToken(jwt)) {
				LOGGER.warn("user is not admin and this weather update is prohibited");
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not admin and this weather update is prohibited"));
			}
			timeStamp = this.weatherRepository.updateWeatherEntry(entry);
		} catch (Exception e) {
			LOGGER.info("error adding weather for entry: {}, Error: {}", entry, e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to update data"));
		}
		return ResponseEntity.ok(timeStamp);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWeatherCategoryEntity(HttpServletRequest request, @PathVariable("id") String id) {
    	String jwt = jwtUtils.parseJwt(request);
    	String userId = userRepository.getUserId(jwt);
    	String timeStamp = null;
    	try {
            // admin user can only modify the global values.
			if(!userRepository.isUserIdAdminByToken(jwt)) {
				LOGGER.warn("user is not admin and this weather deletion is prohibited");
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not admin and this weather deletion is prohibited"));
			}
    		timeStamp = this.weatherRepository.deleteWeatherEntry(id);
    	} catch (Exception e) {
			LOGGER.info("error deleting weather for user: {} id: {}, Error: {}", userId, id, e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to delete data"));
    	}
    	return ResponseEntity.ok(timeStamp);
    }
}