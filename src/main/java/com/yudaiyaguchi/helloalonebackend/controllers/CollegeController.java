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
import com.yudaiyaguchi.helloalonebackend.models.CollegeEntry;
import com.yudaiyaguchi.helloalonebackend.payload.request.CollegeEntryRequest;
import com.yudaiyaguchi.helloalonebackend.payload.response.CollegeResponse;
import com.yudaiyaguchi.helloalonebackend.payload.response.MessageResponse;
import com.yudaiyaguchi.helloalonebackend.repository.CollegeRepository;
import com.yudaiyaguchi.helloalonebackend.repository.UserRepository;
import com.yudaiyaguchi.helloalonebackend.security.jwt.JwtUtils;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/college")
public class CollegeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CollegeController.class);	
	
    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    UserRepository userRepository;
    
	@Autowired
	CollegeRepository collegeRepository;
	
    @GetMapping("/")
    public ResponseEntity<?> getCollegeEntries(HttpServletRequest request) {
        String jwt = jwtUtils.parseJwt(request);
        String userId = userRepository.getUserId(jwt);
        List<CollegeResponse> collegeResponseList = new ArrayList<>();
        try {
        	List<CollegeEntry> collegeEntryList = this.collegeRepository.getCollegeEntries(userId);
        	collegeEntryList.forEach(entry -> {
        		CollegeResponse response = new CollegeResponse(entry);
        		collegeResponseList.add(response);
        	});
        } catch (Exception e) {
            LOGGER.warn("error finding record for user: {} Error: {}", userId, e);
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to get CollegeEntries"));
        }
        return ResponseEntity.ok(collegeResponseList);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getCollegeEntity(HttpServletRequest request, @PathVariable("id") String id) {
    	String jwt = jwtUtils.parseJwt(request);
        String userId = userRepository.getUserId(jwt);
        CollegeResponse response = null;
        try {
        	CollegeEntry entry = this.collegeRepository.getCollegeEntryById(userId, id);
        	response = new CollegeResponse(entry);
        } catch (Exception e) {
        	LOGGER.warn("error finding college entry for user: {} Error: {}", userId, e);
        	return ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to get CollegeEntry"));
        }
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/")
    public ResponseEntity<?> insertCollegeEntity(HttpServletRequest request, @Valid @RequestBody CollegeEntryRequest entRequest) {
    	String jwt = jwtUtils.parseJwt(request);
    	String userId = userRepository.getUserId(jwt);
    	CollegeEntry entry = new CollegeEntry(entRequest);
		try {
			entry = this.collegeRepository.insertCollegeEntry(userId, entry);
		} catch (Exception e) {
			LOGGER.warn("error adding college for entry: {}, Error: {}", entry, e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to insert data"));
		}
		return ResponseEntity.ok(entry);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCollegeEntity(HttpServletRequest request, @Valid @RequestBody CollegeEntryRequest entRequest,
    		@PathVariable("id") String id) {
    	String jwt = jwtUtils.parseJwt(request);
    	String userId = userRepository.getUserId(jwt);
		CollegeEntry entry = new CollegeEntry(entRequest);
		entry.setId(id);
		String timeStamp = null;
		try {
			timeStamp = this.collegeRepository.updateCollegeEntry(userId, entry);
		} catch (Exception e) {
			LOGGER.info("error adding college for entry: {}, Error: {}", entry, e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to update data"));
		}
		return ResponseEntity.ok(timeStamp);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCollegeCategoryEntity(HttpServletRequest request, @PathVariable("id") String id) {
    	String jwt = jwtUtils.parseJwt(request);
    	String userId = userRepository.getUserId(jwt);
    	String timeStamp = null;
    	try {
    		timeStamp = this.collegeRepository.deleteCollegeEntry(userId, id);
    	} catch (Exception e) {
			LOGGER.info("error deleting college for user: {} id: {}, Error: {}", userId, id, e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to delete data"));
    	}
    	return ResponseEntity.ok(timeStamp);
    }
}