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
import com.yudaiyaguchi.helloalonebackend.models.JobEntry;
import com.yudaiyaguchi.helloalonebackend.payload.request.JobEntryRequest;
import com.yudaiyaguchi.helloalonebackend.payload.response.JobResponse;
import com.yudaiyaguchi.helloalonebackend.payload.response.MessageResponse;
import com.yudaiyaguchi.helloalonebackend.repository.JobRepository;
import com.yudaiyaguchi.helloalonebackend.repository.UserRepository;
import com.yudaiyaguchi.helloalonebackend.security.jwt.JwtUtils;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/job")
public class JobController {
	private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class);	
	
    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    UserRepository userRepository;
    
	@Autowired
	JobRepository jobRepository;
	
    @GetMapping("/")
    public ResponseEntity<?> getJobEntries(HttpServletRequest request) {
        String jwt = jwtUtils.parseJwt(request);
        String userId = userRepository.getUserId(jwt);
        List<JobResponse> jobResponseList = new ArrayList<>();
        try {
        	List<JobEntry> jobEntryList = this.jobRepository.getJobEntries();
        	jobEntryList.forEach(entry -> {
        		JobResponse response = new JobResponse(entry);
        		jobResponseList.add(response);
        	});
        } catch (Exception e) {
            LOGGER.warn("error finding record for user: {} Error: {}", userId, e);
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to get JobEntries"));
        }
        return ResponseEntity.ok(jobResponseList);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getJobEntity(HttpServletRequest request, @PathVariable("id") String id) {
    	String jwt = jwtUtils.parseJwt(request);
        String userId = userRepository.getUserId(jwt);
        JobResponse response = null;
        try {
        	JobEntry entry = this.jobRepository.getJobEntryById(id);
        	response = new JobResponse(entry);
        } catch (Exception e) {
        	LOGGER.warn("error finding job entry for user: {} Error: {}", userId, e);
        	return ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to get JobEntry"));
        }
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/")
    public ResponseEntity<?> insertJobEntity(HttpServletRequest request, @Valid @RequestBody JobEntryRequest entRequest) {
    	String jwt = jwtUtils.parseJwt(request);
    	JobEntry entry = new JobEntry(entRequest);
		try {
	        // admin user can only modify the global values.
			if(!userRepository.isUserIdAdminByToken(jwt)) {
				LOGGER.warn("user is not admin and this job insertion is prohibited");
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not admin and this job insertion is prohibited"));
			}
			entry = this.jobRepository.insertJobEntry(entry);
		} catch (Exception e) {
			LOGGER.warn("error adding job for entry: {}, Error: {}", entry, e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to insert data"));
		}
		return ResponseEntity.ok(entry);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateJobEntity(HttpServletRequest request, @Valid @RequestBody JobEntryRequest entRequest,
    		@PathVariable("id") String id) {
    	String jwt = jwtUtils.parseJwt(request);
		JobEntry entry = new JobEntry(entRequest);
		entry.setId(id);
		String timeStamp = null;
		try {
	        // admin user can only modify the global values.
			if(!userRepository.isUserIdAdminByToken(jwt)) {
				LOGGER.warn("user is not admin and this job update is prohibited");
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not admin and this job update is prohibited"));
			}
			timeStamp = this.jobRepository.updateJobEntry(entry);
		} catch (Exception e) {
			LOGGER.info("error adding job for entry: {}, Error: {}", entry, e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to update data"));
		}
		return ResponseEntity.ok(timeStamp);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJobCategoryEntity(HttpServletRequest request, @PathVariable("id") String id) {
    	String jwt = jwtUtils.parseJwt(request);
    	String userId = userRepository.getUserId(jwt);
    	String timeStamp = null;
    	try {
            // admin user can only modify the global values.
			if(!userRepository.isUserIdAdminByToken(jwt)) {
				LOGGER.warn("user is not admin and this job deletion is prohibited");
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not admin and this job deletion is prohibited"));
			}
    		timeStamp = this.jobRepository.deleteJobEntry(id);
    	} catch (Exception e) {
			LOGGER.info("error deleting job for user: {} id: {}, Error: {}", userId, id, e);
			return ResponseEntity.badRequest().body(new MessageResponse("Error: some fields are missing and unable to delete data"));
    	}
    	return ResponseEntity.ok(timeStamp);
    }
}