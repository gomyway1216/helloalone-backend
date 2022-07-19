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
import com.yudaiyaguchi.helloalonebackend.models.FoodEntry;

public class FoodRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FoodRepository.class);
	
	public FoodEntry getFoodEntryById(String foodId) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference foodEntryRef = db.collection("common").document("food").collection("foodCollection")
				.document(foodId);
		ApiFuture<DocumentSnapshot> future = foodEntryRef.get();
		DocumentSnapshot document = future.get();
		FoodEntry foodEntry = null;
		try {
			if (document.exists()) {
				LOGGER.info("Food entry: {}", document.getData());
				foodEntry = document.toObject(FoodEntry.class);
				foodEntry.setId(document.getId());
			}
		} catch (Exception e) {
			LOGGER.error("error getting FoodEntry by id", e);
		}
		return foodEntry;
	}
	
	public List<FoodEntry> getFoodEntries() {
        List<FoodEntry> entryList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference entries = db.collection("common").document("food").collection("foodCollection");
        ApiFuture<QuerySnapshot> future = entries.get();
        try {
            QuerySnapshot val = future.get();
            List<QueryDocumentSnapshot> docSnaps = val.getDocuments();
            for (int i = 0; i < docSnaps.size(); i++) {
                QueryDocumentSnapshot document = docSnaps.get(i);
                FoodEntry entry = document.toObject(FoodEntry.class);
                entry.setId(document.getId());
                entryList.add(entry);
            }
        } catch (Exception e) {
            // TODO Handle different type of exceptions
            LOGGER.error("Exception getting foods", e);
        }
        return entryList;
	}
	
	public List<FoodEntry> getFoodEntries(List<String> foodIds) throws InterruptedException, ExecutionException {
        List<FoodEntry> entryList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference entries = db.collection("common").document("food").collection("foodCollection");
        // No batch operation for reading multiple documents from the same collection, so the code needs to traverse the list
        for(int i = 0; i < foodIds.size(); i++) {
        	DocumentReference foodEntryRef = entries.document(foodIds.get(i));
    		ApiFuture<DocumentSnapshot> future = foodEntryRef.get();
    		DocumentSnapshot document = future.get();
    		FoodEntry foodEntry = null;
    		try {
    			if (document.exists()) {
    				LOGGER.info("Food entry: {}", document.getData());
    				foodEntry = document.toObject(FoodEntry.class);
    				foodEntry.setId(document.getId());
    				entryList.add(foodEntry);
    			} else {
    				LOGGER.error("Not able to find the activity with id: " + foodIds.get(i));
    			}
    		} catch (Exception e) {
    			LOGGER.error("error getting Food by id", e);
    		}
        }
        return entryList;
	}
	
    public FoodEntry addFoodEntry(FoodEntry foodEntry) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference foodEntries = db.collection("common").document("food").collection("foodCollection");
        try {
            ApiFuture<DocumentReference> res = foodEntries.add(foodEntry);
            DocumentReference val = res.get();
            ApiFuture<DocumentSnapshot> future = val.get();
            DocumentSnapshot document = future.get();
            foodEntry = document.toObject(FoodEntry.class);
            foodEntry.setId(document.getId());
            return foodEntry;
        } catch (Exception e) {
        	// TODO Handle different type of exceptions
            LOGGER.error("FoodEntry insertion is failing", e);
            throw e;
        }
    }
    
    public String updateFoodEntry(FoodEntry foodEntry) throws Exception {
    	if(!StringUtils.hasLength(foodEntry.getId())) {
    		throw new Exception("Entry Id cannot be null for update operation!");
    	}
    	
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference foodEntries = db.collection("common").document("food").collection("foodCollection");
        try {
            ApiFuture<WriteResult> res = foodEntries.document(foodEntry.getId()).set(foodEntry);
            return res.get().getUpdateTime().toString();
        } catch (Exception e) {
        	// TODO Handle different type of exceptions
            LOGGER.error("FoodEntry update is failing", e);
            throw e;
        }
    }
    
    public String deleteFoodEntry(String id) throws Exception {
    	if(!StringUtils.hasLength(id)) {
    		throw new Exception("Entry Id cannot be null or empty for delete operation!");
    	}
    	
    	Firestore db = FirestoreClient.getFirestore();
    	CollectionReference foodEntries = db.collection("common").document("food").collection("foodCollection");
    	try {
    		ApiFuture<WriteResult> res = foodEntries.document(id).delete();
    		return res.get().getUpdateTime().toString();
    	} catch(Exception e) {
    		// TODO Handle different type of exceptions
    		LOGGER.error("FoodEntry delete is failing", e);
    		throw e;
    	}
    }
}
