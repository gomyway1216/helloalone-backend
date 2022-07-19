package com.yudaiyaguchi.helloalonebackend.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.yudaiyaguchi.helloalonebackend.models.ActivityCategoryEntry;
import com.yudaiyaguchi.helloalonebackend.models.ActivityEntry;
import com.yudaiyaguchi.helloalonebackend.models.ActivityTypeEntry;

public class ActivityRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityRepository.class);
	
	public ActivityEntry getActivityEntryById(String userId, String activityId)
		throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference activityEntryRef = db.collection("users").document(userId).collection("activity")
				.document(activityId);
		ApiFuture<DocumentSnapshot> future = activityEntryRef.get();
		DocumentSnapshot document = future.get();
		ActivityEntry activityEntry = null;
		try {
			if (document.exists()) {
				LOGGER.info("Activity entry: {}", document.getData());
				activityEntry = document.toObject(ActivityEntry.class);
				activityEntry.setId(document.getId());
			}
		} catch (Exception e) {
			LOGGER.error("error getting Activity by id", e);
		}
		return activityEntry;
	}
	
	public List<ActivityEntry> getActivityEntries(String userId) {
        List<ActivityEntry> entryList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference entries = db.collection("users").document(userId).collection("activity");
        ApiFuture<QuerySnapshot> future = entries.get();
        try {
            QuerySnapshot val = future.get();
            List<QueryDocumentSnapshot> docSnaps = val.getDocuments();
            for (int i = 0; i < docSnaps.size(); i++) {
                QueryDocumentSnapshot document = docSnaps.get(i);
                ActivityEntry entry = document.toObject(ActivityEntry.class);
                entry.setId(document.getId());
                entryList.add(entry);
            }
        } catch (Exception e) {
            // TODO Handle different type of exceptions
            LOGGER.error("Exception getting activities", e);
        }
        return entryList;
	}
	
	public List<ActivityEntry> getActivityEntries(String userId, List<String> activityIds) 
		throws InterruptedException, ExecutionException {
        List<ActivityEntry> entryList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference entries = db.collection("users").document(userId).collection("activity");
        // No batch operation for reading multiple documents from the same collection, so the code needs to traverse the list
        for(int i = 0; i < activityIds.size(); i++) {
        	DocumentReference activityEntryRef = entries.document(activityIds.get(i));
    		ApiFuture<DocumentSnapshot> future = activityEntryRef.get();
    		DocumentSnapshot document = future.get();
    		ActivityEntry activityEntry = null;
    		try {
    			if (document.exists()) {
    				LOGGER.info("Activity entry: {}", document.getData());
    				activityEntry = document.toObject(ActivityEntry.class);
    				activityEntry.setId(document.getId());
    				entryList.add(activityEntry);
    			} else {
    				LOGGER.error("Not able to find the activity with id: " + activityIds.get(i));
    			}
    		} catch (Exception e) {
    			LOGGER.error("error getting Activitys by id", e);
    		}
        }
        return entryList;
	}
	
    public ActivityEntry insertActivityEntry(String userId, ActivityEntry activityEntry) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference activityEntries = db.collection("users").document(userId).collection("activity");
        try {
            ApiFuture<DocumentReference> res = activityEntries.add(activityEntry);
            DocumentReference val = res.get();
            ApiFuture<DocumentSnapshot> future = val.get();
            DocumentSnapshot document = future.get();
            activityEntry = document.toObject(ActivityEntry.class);
            activityEntry.setId(document.getId());
            return activityEntry;
        } catch (Exception e) {
        	// TODO Handle different type of exceptions
            LOGGER.error("ActivityEntry insertion is failing", e);
            throw e;
        }
    }
    
    public String updateActivityEntry(String userId, ActivityEntry activityEntry) throws Exception {
    	if(!StringUtils.hasLength(activityEntry.getId())) {
    		throw new Exception("Entry Id cannot be null for update operation!");
    	}
    	
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference activityEntries = db.collection("users").document(userId).collection("activity");
        try {
            ApiFuture<WriteResult> res = activityEntries.document(activityEntry.getId()).set(activityEntry);
            return res.get().getUpdateTime().toString();
        } catch (Exception e) {
        	// TODO Handle different type of exceptions
            LOGGER.error("ActivityEntry update is failing", e);
            throw e;
        }
    }
    
    public String deleteActivityEntry(String userId, String id) throws Exception {
    	if(!StringUtils.hasLength(id)) {
    		throw new Exception("Entry Id cannot be null or empty for delete operation!");
    	}
    	
    	Firestore db = FirestoreClient.getFirestore();
    	CollectionReference activityEntries = db.collection("users").document(userId).collection("activity");
    	try {
    		ApiFuture<WriteResult> res = activityEntries.document(id).delete();
    		return res.get().getUpdateTime().toString();
    	} catch(Exception e) {
    		// TODO Handle different type of exceptions
    		LOGGER.error("ActivityEntry delete is failing", e);
    		throw e;
    	}
    }
        
	public ActivityCategoryEntry getActivityCategoryEntityById(String activityCategoryId)
		throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference activityCategoryEntryRef = db.collection("common").document("activityCategory").collection("activityCategoryCollection")
				.document(activityCategoryId);
		ApiFuture<DocumentSnapshot> future = activityCategoryEntryRef.get();
		DocumentSnapshot document = future.get();
		ActivityCategoryEntry activityCategoryEntry = null;
		try {
			if (document.exists()) {
				LOGGER.info("ActivityCategory entry: {}", document.getData());
				activityCategoryEntry = document.toObject(ActivityCategoryEntry.class);
				activityCategoryEntry.setId(document.getId());
			}
		} catch (Exception e) {
			LOGGER.error("error getting Activity Category by id", e);
		}
		return activityCategoryEntry;
	}
		
	public List<ActivityCategoryEntry> getActivityCategoryEntries() {
        List<ActivityCategoryEntry> entryList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference entries = db.collection("common").document("activityCategory").collection("activityCategoryCollection");
        ApiFuture<QuerySnapshot> future = entries.get();
        try {
            QuerySnapshot val = future.get();
            List<QueryDocumentSnapshot> docSnaps = val.getDocuments();
            for (int i = 0; i < docSnaps.size(); i++) {
                QueryDocumentSnapshot document = docSnaps.get(i);
                ActivityCategoryEntry entry = document.toObject(ActivityCategoryEntry.class);
                entry.setId(document.getId());
                entryList.add(entry);
            }
        } catch (Exception e) {
            // TODO Handle different type of exceptions
            LOGGER.error("Exception getting activity categories", e);
        }
        return entryList;
	}
	
	public List<ActivityCategoryEntry> getActivityCategoryEntries(List<String> activityCategoryIds) throws InterruptedException, ExecutionException {
        List<ActivityCategoryEntry> entryList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference entries = db.collection("common").document("activityCategory").collection("activityCategoryCollection");
        // No batch operation for reading multiple documents from the same collection, so the code needs to traverse the list
        for(int i = 0; i < activityCategoryIds.size(); i++) {
        	DocumentReference activityEntryRef = entries.document(activityCategoryIds.get(i));
    		ApiFuture<DocumentSnapshot> future = activityEntryRef.get();
    		DocumentSnapshot document = future.get();
    		ActivityCategoryEntry activityCategoryEntry = null;
    		try {
    			if (document.exists()) {
    				LOGGER.info("ActivityCategory entry: {}", document.getData());
    				activityCategoryEntry = document.toObject(ActivityCategoryEntry.class);
    				activityCategoryEntry.setId(document.getId());
    				entryList.add(activityCategoryEntry);
    			} else {
    				LOGGER.error("Not able to find the activity with id: " + activityCategoryIds.get(i));
    			}
    		} catch (Exception e) {
    			LOGGER.error("error getting ActivityCategory by id", e);
    		}
        }
        return entryList;
	}
	
    public ActivityCategoryEntry insertActivityCategoryEntry(ActivityCategoryEntry activityCategoryEntry) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference activityCategoryEntries = db.collection("common").document("activityCategory").collection("activityCategoryCollection");
        try {
            ApiFuture<DocumentReference> res = activityCategoryEntries.add(activityCategoryEntry);
            DocumentReference val = res.get();
            ApiFuture<DocumentSnapshot> future = val.get();
            DocumentSnapshot document = future.get();
            activityCategoryEntry = document.toObject(ActivityCategoryEntry.class);
            activityCategoryEntry.setId(document.getId());
            return activityCategoryEntry;
        } catch (Exception e) {
        	// TODO Handle different type of exceptions
            LOGGER.error("ActivityCategory insertion is failing", e);
            throw e;
        }
    }
    
    public String updateActivityCategoryEntry(ActivityCategoryEntry activityCategoryEntry) throws Exception {
    	if(!StringUtils.hasLength(activityCategoryEntry.getId())) {
    		throw new Exception("Entry Id cannot be null for update operation!");
    	}
    	
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference activityCategoryEntries = db.collection("common").document("activityCategory").collection("activityCategoryCollection");
        try {
            ApiFuture<WriteResult> res = activityCategoryEntries.document(activityCategoryEntry.getId()).set(activityCategoryEntry);
            return res.get().getUpdateTime().toString();
        } catch (Exception e) {
        	// TODO Handle different type of exceptions
            LOGGER.error("ActivityCategoryEntry update is failing", e);
            throw e;
        }
    }
    
    public String deleteActivityCategoryEntry(String id) throws Exception {
    	if(!StringUtils.hasLength(id)) {
    		throw new Exception("Entry Id cannot be null or empty for delete operation!");
    	}
    	
    	Firestore db = FirestoreClient.getFirestore();
    	CollectionReference activityCategoryEntries = db.collection("common").document("activityCategory").collection("activityCategoryCollection");
    	try {
    		ApiFuture<WriteResult> res = activityCategoryEntries.document(id).delete();
    		return res.get().getUpdateTime().toString();
    	} catch(Exception e) {
    		// TODO Handle different type of exceptions
    		LOGGER.error("ActivityCategoryEntry delete is failing", e);
    		throw e;
    	}
    }

	public ActivityTypeEntry getActivityTypeEntityById(String activityTypeId)
		throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference activityTypeEntryRef = db.collection("common").document("activityType").collection("activityTypeCollection")
				.document(activityTypeId);
		ApiFuture<DocumentSnapshot> future = activityTypeEntryRef.get();
		DocumentSnapshot document = future.get();
		ActivityTypeEntry activityTypeEntry = null;
		try {
			if (document.exists()) {
				LOGGER.info("activity type entry: {}", document.getData());
				activityTypeEntry = document.toObject(ActivityTypeEntry.class);
				activityTypeEntry.setId(document.getId());
			}
		} catch (Exception e) {
			LOGGER.error("error getting Activity Type by id", e);
		}
		return activityTypeEntry;
	}
			
	public List<ActivityTypeEntry> getActivityTypeEntries() {
        List<ActivityTypeEntry> entryList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference entries = db.collection("common").document("activityType").collection("activityTypeCollection");
        ApiFuture<QuerySnapshot> future = entries.get();
        try {
            QuerySnapshot val = future.get();
            List<QueryDocumentSnapshot> docSnaps = val.getDocuments();
            for (int i = 0; i < docSnaps.size(); i++) {
                QueryDocumentSnapshot document = docSnaps.get(i);
                ActivityTypeEntry entry = document.toObject(ActivityTypeEntry.class);
                entry.setId(document.getId());
                entryList.add(entry);
            }
        } catch (Exception e) {
            // TODO Handle different type of exceptions
            LOGGER.error("Exception getting activity types", e);
        }
        return entryList;
	}
	
	public List<ActivityTypeEntry> getActivityTypeEntries(List<String> activityTypeIds) throws InterruptedException, ExecutionException {
        List<ActivityTypeEntry> entryList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference entries = db.collection("common").document("activityType").collection("activityTypeCollection");
        // No batch operation for reading multiple documents from the same collection, so the code needs to traverse the list
        for(int i = 0; i < activityTypeIds.size(); i++) {
        	DocumentReference activityTypeEntryRef = entries.document(activityTypeIds.get(i));
    		ApiFuture<DocumentSnapshot> future = activityTypeEntryRef.get();
    		DocumentSnapshot document = future.get();
    		ActivityTypeEntry activityTypeEntry = null;
    		try {
    			if (document.exists()) {
    				LOGGER.info("ActivityType entry: {}", document.getData());
    				activityTypeEntry = document.toObject(ActivityTypeEntry.class);
    				activityTypeEntry.setId(document.getId());
    				entryList.add(activityTypeEntry);
    			} else {
    				LOGGER.error("Not able to find the activity with id: " + activityTypeIds.get(i));
    			}
    		} catch (Exception e) {
    			LOGGER.error("error getting ActivityType by id", e);
    		}
        }
        return entryList;
	}
	
    public ActivityTypeEntry insesrtActivityTypeEntry(ActivityTypeEntry activityTypeEntry) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference activityTypeEntries = db.collection("common").document("activityType").collection("activityTypeCollection");
        try {
            ApiFuture<DocumentReference> res = activityTypeEntries.add(activityTypeEntry);
            DocumentReference val = res.get();
            ApiFuture<DocumentSnapshot> future = val.get();
            DocumentSnapshot document = future.get();
            activityTypeEntry = document.toObject(ActivityTypeEntry.class);
            activityTypeEntry.setId(document.getId());
            return activityTypeEntry;
        } catch (Exception e) {
        	// TODO Handle different type of exceptions
            LOGGER.error("ActivityType insertion is failing", e);
            throw e;
        }
    }
    
    public String updateActivityTypeEntry(ActivityTypeEntry activityTypeEntry) throws Exception {
    	if(!StringUtils.hasLength(activityTypeEntry.getId())) {
    		throw new Exception("Entry Id cannot be null for update operation!");
    	}
    	
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference activityTypeEntries = db.collection("common").document("activityType").collection("activityTypeCollection");
        try {
            ApiFuture<WriteResult> res = activityTypeEntries.document(activityTypeEntry.getId()).set(activityTypeEntry);
            return res.get().getUpdateTime().toString();
        } catch (Exception e) {
        	// TODO Handle different type of exceptions
            LOGGER.error("ActivityTypeEntry update is failing", e);
            throw e;
        }
    }
    
    public String deleteActivityTypeEntry(String id) throws Exception {
    	if(!StringUtils.hasLength(id)) {
    		throw new Exception("Entry Id cannot be null or empty for delete operation!");
    	}
    	
    	Firestore db = FirestoreClient.getFirestore();
    	CollectionReference activityTypeEntries = db.collection("common").document("activityType").collection("activityTypeCollection");
    	try {
    		ApiFuture<WriteResult> res = activityTypeEntries.document(id).delete();
    		return res.get().getUpdateTime().toString();
    	} catch(Exception e) {
    		// TODO Handle different type of exceptions
    		LOGGER.error("ActivityTypeEntry delete is failing", e);
    		throw e;
    	}
    }
}
