package com.yudaiyaguchi.helloalonebackend.repository;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
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
import com.yudaiyaguchi.helloalonebackend.models.CollegeEntry;


@Service
public class CollegeRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CollegeRepository.class);
	
	public CollegeEntry getCollegeEntryById(String userId, String collegeId)
		throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference collegeEntryRef = db.collection("users").document(userId).collection("college")
				.document(collegeId);
		ApiFuture<DocumentSnapshot> future = collegeEntryRef.get();
		DocumentSnapshot document = future.get();
		CollegeEntry collegeEntry = null;
		try {
			if (document.exists()) {
				LOGGER.info("College entry: {}", document.getData());
				collegeEntry = document.toObject(CollegeEntry.class);
				collegeEntry.setId(document.getId());
			}
		} catch (Exception e) {
			LOGGER.error("error getting CollegeEntry by id", e);
		}
		return collegeEntry;
	}
	
	public List<CollegeEntry> getCollegeEntries(String userId) {
        List<CollegeEntry> entryList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference entries = db.collection("users").document(userId).collection("college");
        ApiFuture<QuerySnapshot> future = entries.get();
        try {
            QuerySnapshot val = future.get();
            List<QueryDocumentSnapshot> docSnaps = val.getDocuments();
            for (int i = 0; i < docSnaps.size(); i++) {
                QueryDocumentSnapshot document = docSnaps.get(i);
                CollegeEntry entry = document.toObject(CollegeEntry.class);
                entry.setId(document.getId());
                entryList.add(entry);
            }
        } catch (Exception e) {
            // TODO Handle different type of exceptions
            LOGGER.error("Exception getting colleges", e);
        }
        return entryList;
	}
	
	public List<CollegeEntry> getCollegeEntries(String userId, List<String> collegeIds) throws InterruptedException, ExecutionException {
		if(collegeIds == null || collegeIds.size() == 0) {
			return null;
		}
        List<CollegeEntry> entryList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference entries = db.collection("users").document(userId).collection("college");
        // No batch operation for reading multiple documents from the same collection, so the code needs to traverse the list
        for(int i = 0; i < collegeIds.size(); i++) {
        	DocumentReference collegeEntryRef = entries.document(collegeIds.get(i));
    		ApiFuture<DocumentSnapshot> future = collegeEntryRef.get();
    		DocumentSnapshot document = future.get();
    		CollegeEntry collegeEntry = null;
    		try {
    			if (document.exists()) {
    				LOGGER.info("College entry: {}", document.getData());
    				collegeEntry = document.toObject(CollegeEntry.class);
    				collegeEntry.setId(document.getId());
    				entryList.add(collegeEntry);
    			} else {
    				LOGGER.error("Not able to find the college with id: " + collegeIds.get(i));
    			}
    		} catch (Exception e) {
    			LOGGER.error("error getting College by id", e);
    		}
        }
        return entryList;
	}
	
    public CollegeEntry insertCollegeEntry(String userId, CollegeEntry collegeEntry) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collegeEntries = db.collection("users").document(userId).collection("college");
        try {
            ApiFuture<DocumentReference> res = collegeEntries.add(collegeEntry);
            DocumentReference val = res.get();
            ApiFuture<DocumentSnapshot> future = val.get();
            DocumentSnapshot document = future.get();
            collegeEntry = document.toObject(CollegeEntry.class);
            collegeEntry.setId(document.getId());
            return collegeEntry;
        } catch (Exception e) {
        	// TODO Handle different type of exceptions
            LOGGER.error("CollegeEntry insertion is failing", e);
            throw e;
        }
    }
    
    public String updateCollegeEntry(String userId, CollegeEntry collegeEntry) throws Exception {
    	if(!StringUtils.hasLength(collegeEntry.getId())) {
    		throw new Exception("Entry Id cannot be null for update operation!");
    	}
    	
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collegeEntries = db.collection("users").document(userId).collection("college");
        try {
            ApiFuture<WriteResult> res = collegeEntries.document(collegeEntry.getId()).set(collegeEntry);
            return res.get().getUpdateTime().toString();
        } catch (Exception e) {
        	// TODO Handle different type of exceptions
            LOGGER.error("CollegeEntry update is failing", e);
            throw e;
        }
    }
    
    public String deleteCollegeEntry(String userId, String id) throws Exception {
    	if(!StringUtils.hasLength(id)) {
    		throw new Exception("Entry Id cannot be null or empty for delete operation!");
    	}
    	
    	Firestore db = FirestoreClient.getFirestore();
    	CollectionReference collegeEntries = db.collection("users").document(userId).collection("college");
    	try {
    		ApiFuture<WriteResult> res = collegeEntries.document(id).delete();
    		return res.get().getUpdateTime().toString();
    	} catch(Exception e) {
    		// TODO Handle different type of exceptions
    		LOGGER.error("CollegeEntry delete is failing", e);
    		throw e;
    	}
    }
}
