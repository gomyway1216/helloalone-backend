package com.yudaiyaguchi.helloalonebackend.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.yudaiyaguchi.helloalonebackend.models.DictionaryEntry;
//import com.google.cloud.Timestamp;
import com.yudaiyaguchi.helloalonebackend.models.TagEntry;

@Service
public class DictionaryRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(DictionaryRepository.class);

    public DictionaryEntry getDictionaryEntryById(String userId, String dictionaryEntryId)
            throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference dicEntryRef = db.collection("users").document(userId).collection("dictionary")
                .document(dictionaryEntryId);
        ApiFuture<DocumentSnapshot> future = dicEntryRef.get();
        DocumentSnapshot document = future.get();
        DictionaryEntry dicEntry = null;
        try {
            if (document.exists()) {
                LOGGER.info("dictionary entry: {}", document.getData());
                dicEntry = document.toObject(DictionaryEntry.class);
                dicEntry.setId(document.getId());
            }
        } catch (Exception e) {
            LOGGER.error("error getting friend by id", e);
        }
        return dicEntry;
    }

    public List<DictionaryEntry> getDictionaryEntries(String userId) {
        List<DictionaryEntry> entryList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference entries = db.collection("users").document(userId).collection("dictionary");
        ApiFuture<QuerySnapshot> future = entries.get();
        try {
            QuerySnapshot val = future.get();
            List<QueryDocumentSnapshot> v1 = val.getDocuments();
            for (int i = 0; i < v1.size(); i++) {
                QueryDocumentSnapshot document = v1.get(i);
                DictionaryEntry entry = document.toObject(DictionaryEntry.class);
                entry.setId(document.getId());
                LOGGER.info("friend values after: {}", entry.toString());
                entryList.add(entry);
            }
        } catch (Exception e) {
            // TODO Handle different type of exceptions
            LOGGER.error("Exception getting friends", e);
        }
        return entryList;
    }

    public DictionaryEntry addDictionaryEntry(String userId, DictionaryEntry dictionaryEntry) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference dicEntries = db.collection("users").document(userId).collection("dictionary");
        try {
            ApiFuture<DocumentReference> res = dicEntries.add(dictionaryEntry);
            DocumentReference val = res.get();
            ApiFuture<DocumentSnapshot> future = val.get();
            DocumentSnapshot document = future.get();
            dictionaryEntry = document.toObject(DictionaryEntry.class);
            dictionaryEntry.setId(document.getId());
            return dictionaryEntry;
        } catch (Exception e) {
        	// TODO Handle different type of exceptions
            LOGGER.error("insertion is failing", e);
            throw e;
        }
    }
    
    public List<TagEntry> getTagEntries(String userId) {
    	List<TagEntry> entryList = new ArrayList<>();
    	Firestore db = FirestoreClient.getFirestore();
    	CollectionReference entries = db.collection("users").document(userId).collection("tag");
    	ApiFuture<QuerySnapshot> future = entries.get();
    	try {
    		QuerySnapshot val = future.get();
    		List<QueryDocumentSnapshot> v1 = val.getDocuments();
    		for (int i = 0; i < v1.size(); i++) {
    			QueryDocumentSnapshot document = v1.get(i);
    			TagEntry entry = document.toObject(TagEntry.class);
    			entry.setId(document.getId());
    			entryList.add(entry);		
    		}
    	} catch (Exception e) {
    		// TODO Handle different type of exceptions
            LOGGER.error("getting tags is failing", e);
    	}
    	return entryList;
    }
    
    public TagEntry addTagEntry(String userId, TagEntry tagEntry) throws Exception {
    	Firestore db = FirestoreClient.getFirestore();
    	CollectionReference tagEntries = db.collection("users").document(userId).collection("tag");
    	try {
    		ApiFuture<DocumentReference> res = tagEntries.add(tagEntry);
    		DocumentReference val = res.get();
    		ApiFuture<DocumentSnapshot> future = val.get();
    		DocumentSnapshot document = future.get();
    		tagEntry = document.toObject(TagEntry.class);
    		tagEntry.setId(document.getId());
    		LOGGER.info("creating tag successful");
    		return tagEntry;
    	} catch (Exception e) {
        	// TODO Handle different type of exceptions
            LOGGER.error("inserting tag is failing", e);
            throw e;
    	}
    }
}
