package com.yudaiyaguchi.helloalonebackend.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
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
	
    public ActivityEntry addActivityEntry(String userId, ActivityEntry activityEntry) throws Exception {
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
        
	public ActivityCategoryEntry getActivityCategoryEntityById(String userId, String activityCategoryId)
		throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference activityCategoryEntryRef = db.collection("users").document(userId).collection("activityCategory")
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
		
	public List<ActivityCategoryEntry> getActivityCategoryEntries(String userId) {
        List<ActivityCategoryEntry> entryList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference entries = db.collection("users").document(userId).collection("activityCategory");
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
	
    public ActivityCategoryEntry addActivityCategoryEntry(String userId, ActivityCategoryEntry activityCategoryEntry) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference activityCategoryEntries = db.collection("users").document(userId).collection("activityCategory");
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

	public ActivityTypeEntry getActivityTypeEntityById(String userId, String activityTypeId)
		throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference activityTypeEntryRef = db.collection("users").document(userId).collection("activityType")
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
			
	public List<ActivityTypeEntry> getActivityTypeEntries(String userId) {
        List<ActivityTypeEntry> entryList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference entries = db.collection("users").document(userId).collection("activityType");
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
	
    public ActivityTypeEntry addActivityTypeEntry(String userId, ActivityTypeEntry activityTypeEntry) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference activityCategoryEntries = db.collection("users").document(userId).collection("activityType");
        try {
            ApiFuture<DocumentReference> res = activityCategoryEntries.add(activityTypeEntry);
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
}
