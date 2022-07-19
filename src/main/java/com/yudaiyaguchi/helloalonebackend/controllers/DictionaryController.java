package com.yudaiyaguchi.helloalonebackend.controllers;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yudaiyaguchi.helloalonebackend.models.DictionaryEntry;
import com.yudaiyaguchi.helloalonebackend.models.TagEntry;
import com.yudaiyaguchi.helloalonebackend.payload.request.AddDictionaryEntryRequest;
import com.yudaiyaguchi.helloalonebackend.payload.request.AddTagEntryRequest;
import com.yudaiyaguchi.helloalonebackend.repository.DictionaryRepository;
import com.yudaiyaguchi.helloalonebackend.repository.UserRepository;
import com.yudaiyaguchi.helloalonebackend.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/my-dictionary")
public class DictionaryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DictionaryController.class);

    @Autowired
    DictionaryRepository dictionaryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/")
    public ResponseEntity<?> getDictionryEntities(HttpServletRequest request) {
        String jwt = jwtUtils.parseJwt(request);
        String userId = userRepository.getUserId(jwt);
        List<DictionaryEntry> dictionaryEntryList = null;
        try {
            dictionaryEntryList = this.dictionaryRepository.getDictionaryEntries(userId);
        } catch (Exception e) {
            LOGGER.warn("error finding record for user: {} Error: {}", userId, e);
        }
        return ResponseEntity.ok(dictionaryEntryList);
    }

    @GetMapping("/{dictionaryEntryId}")
    public ResponseEntity<?> getDictionaryEntity(HttpServletRequest request, @Valid String dictionaryEntryId) {
        String jwt = jwtUtils.parseJwt(request);
        String userId = userRepository.getUserId(jwt);
        DictionaryEntry dictionaryEntry = null;
        try {
            dictionaryEntry = this.dictionaryRepository.getDictionaryEntryById(userId, dictionaryEntryId);
        } catch (Exception e) {
            LOGGER.warn("error finding record for user: {} entryId: {}, Error: {}", userId, dictionaryEntryId, e);
        }
        return ResponseEntity.ok(dictionaryEntry);
    }
    
    @PostMapping("/insert")
    public ResponseEntity<?> addDictionaryEntity(HttpServletRequest request, @Valid @RequestBody AddDictionaryEntryRequest entRequest) {
    	String jwt = jwtUtils.parseJwt(request);
		String userId = userRepository.getUserId(jwt);
		DictionaryEntry entry = new DictionaryEntry();
		entry.setTitle(entRequest.getTitle());
		entry.setTitle_japanese(entRequest.getTitle_japanese());
		entry.setTags(entRequest.getTags());
		entry.setDescription(entRequest.getDescription());
		entry.setPriority(entRequest.getPriority());
		entry.setCreated(new Date());
		try {
			entry = this.dictionaryRepository.addDictionaryEntry(userId, entry);
		} catch (Exception e) {
			LOGGER.info("error adding record for user: {} entry: {}, Error: {}", userId, entry, e);
		}
		
		return ResponseEntity.ok(entry);
    }
    
    @GetMapping("/tags")
    public ResponseEntity<?> getTags(HttpServletRequest request) {
    	String jwt = jwtUtils.parseJwt(request);
    	String userId = userRepository.getUserId(jwt);
    	List<TagEntry> tags = null;
    	try {
    		tags = this.dictionaryRepository.getTagEntries(userId);
    	} catch (Exception e) {
    		LOGGER.warn("error finding record for user: {} Error: {}", userId, e);
    	}
    	return ResponseEntity.ok(tags);
    }
    
    @PostMapping("/add-tag")
    public ResponseEntity<?> addTagEntity(HttpServletRequest request, @Valid @RequestBody AddTagEntryRequest entRequest) {
    	String jwt = jwtUtils.parseJwt(request);
    	String userId = userRepository.getUserId(jwt);
    	TagEntry entry = new TagEntry();
    	entry.setName(entRequest.getName());
    	try {
    		entry = this.dictionaryRepository.addTagEntry(userId, entry);
    	} catch (Exception e) {
    		LOGGER.info("error adding redord for user: {} entry: {}, Error: {}", userId, entry, e);
    	}
    	return ResponseEntity.ok(entry);
    } 
}