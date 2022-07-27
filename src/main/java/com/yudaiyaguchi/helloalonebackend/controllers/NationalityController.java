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
import com.yudaiyaguchi.helloalonebackend.models.NationalityEntry;
import com.yudaiyaguchi.helloalonebackend.payload.request.NationalityEntryRequest;
import com.yudaiyaguchi.helloalonebackend.payload.response.MessageResponse;
import com.yudaiyaguchi.helloalonebackend.payload.response.NationalityResponse;
import com.yudaiyaguchi.helloalonebackend.repository.NationalityRepository;
import com.yudaiyaguchi.helloalonebackend.repository.UserRepository;
import com.yudaiyaguchi.helloalonebackend.security.jwt.JwtUtils;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/nationality")
public class NationalityController {
	private static final Logger LOGGER = LoggerFactory.getLogger(NationalityController.class);	
	
    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    UserRepository userRepository;
    
	@Autowired
	NationalityRepository nationalityRepository;
	
    @GetMapping("/")
    public ResponseEntity<?> getNationalityEntries(HttpServletRequest request) {
        String jwt = jwtUtils.parseJwt(request);
        String userId = userRepository.getUserId(jwt);
        List<NationalityResponse> nationalityResponseList = new ArrayList<>();
        try {
        	List<NationalityEntry> nationalityEntryList = this.nationalityRepository.getNationalityEntries();
        	nationalityEntryList.forEach(entry -> {
        		NationalityResponse response = new NationalityResponse(entry);
        		nationalityResponseList.add(response);
        	});
        } catch (Exception e) {
            LOGGER.warn("error finding record for user: {} Error: {}", userId, e);
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to get NationalityEntries"));
        }
        return ResponseEntity.ok(nationalityResponseList);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getNationalityEntity(HttpServletRequest request, @PathVariable("id") String id) {
    	String jwt = jwtUtils.parseJwt(request);
        String userId = userRepository.getUserId(jwt);
        NationalityResponse response = null;
        try {
        	NationalityEntry entry = this.nationalityRepository.getNationalityEntryById(id);
        	response = new NationalityResponse(entry);
        } catch (Exception e) {
        	LOGGER.warn("error finding nationality entry for user: {} Error: {}", userId, e);
        	return ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to get NationalityEntry"));
        }
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/")
    public ResponseEntity<?> insertNationalityEntity(HttpServletRequest request, @Valid @RequestBody NationalityEntryRequest entRequest) {
    	String jwt = jwtUtils.parseJwt(request);
    	NationalityEntry entry = new NationalityEntry(entRequest);
		try {
	        // admin user can only modify the global values.
			if(!userRepository.isUserIdAdminByToken(jwt)) {
				LOGGER.warn("user is not admin and this nationality insertion is prohibited");
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not admin and this nationality insertion is prohibited"));
			}
			entry = this.nationalityRepository.insertNationalityEntry(entry);
		} catch (Exception e) {
			LOGGER.warn("error adding nationality for entry: {}, Error: {}", entry, e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to insert data"));
		}
		return ResponseEntity.ok(entry);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNationalityEntity(HttpServletRequest request, @Valid @RequestBody NationalityEntryRequest entRequest,
    		@PathVariable("id") String id) {
    	String jwt = jwtUtils.parseJwt(request);
		NationalityEntry entry = new NationalityEntry(entRequest);
		entry.setId(id);
		String timeStamp = null;
		try {
	        // admin user can only modify the global values.
			if(!userRepository.isUserIdAdminByToken(jwt)) {
				LOGGER.warn("user is not admin and this nationality update is prohibited");
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not admin and this nationality update is prohibited"));
			}
			timeStamp = this.nationalityRepository.updateNationalityEntry(entry);
		} catch (Exception e) {
			LOGGER.info("error adding nationality for entry: {}, Error: {}", entry, e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to update data"));
		}
		return ResponseEntity.ok(timeStamp);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNationalityCategoryEntity(HttpServletRequest request, @PathVariable("id") String id) {
    	String jwt = jwtUtils.parseJwt(request);
    	String userId = userRepository.getUserId(jwt);
    	String timeStamp = null;
    	try {
            // admin user can only modify the global values.
			if(!userRepository.isUserIdAdminByToken(jwt)) {
				LOGGER.warn("user is not admin and this nationality deletion is prohibited");
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not admin and this nationality deletion is prohibited"));
			}
    		timeStamp = this.nationalityRepository.deleteNationalityEntry(id);
    	} catch (Exception e) {
			LOGGER.info("error deleting nationality for user: {} id: {}, Error: {}", userId, id, e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to delete data"));
    	}
    	return ResponseEntity.ok(timeStamp);
    }
}