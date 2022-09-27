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
import com.yudaiyaguchi.helloalonebackend.models.NationalityEntry;


@Service
public class NationalityRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NationalityRepository.class);
	
	public NationalityEntry getNationalityEntryById(String nationalityId)
		throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference nationalityEntryRef = db.collection("common").document("nationality").collection("nationalityCollection")
				.document(nationalityId);
		ApiFuture<DocumentSnapshot> future = nationalityEntryRef.get();
		DocumentSnapshot document = future.get();
		NationalityEntry nationalityEntry = null;
		try {
			if (document.exists()) {
				LOGGER.info("Nationality entry: {}", document.getData());
				nationalityEntry = document.toObject(NationalityEntry.class);
				nationalityEntry.setId(document.getId());
			}
		} catch (Exception e) {
			LOGGER.error("error getting NationalityEntry by id", e);
		}
		return nationalityEntry;
	}
	
	public List<NationalityEntry> getNationalityEntries() {
        List<NationalityEntry> entryList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference entries = db.collection("common").document("nationality").collection("nationalityCollection");
        ApiFuture<QuerySnapshot> future = entries.get();
        try {
            QuerySnapshot val = future.get();
            List<QueryDocumentSnapshot> docSnaps = val.getDocuments();
            for (int i = 0; i < docSnaps.size(); i++) {
                QueryDocumentSnapshot document = docSnaps.get(i);
                NationalityEntry entry = document.toObject(NationalityEntry.class);
                entry.setId(document.getId());
                entryList.add(entry);
            }
        } catch (Exception e) {
            // TODO Handle different type of exceptions
            LOGGER.error("Exception getting nationalities", e);
        }
        return entryList;
	}
	
    public NationalityEntry insertNationalityEntry(NationalityEntry nationalityEntry) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference nationalityEntries = db.collection("common").document("nationality").collection("nationalityCollection");
        try {
            ApiFuture<DocumentReference> res = nationalityEntries.add(nationalityEntry);
            DocumentReference val = res.get();
            ApiFuture<DocumentSnapshot> future = val.get();
            DocumentSnapshot document = future.get();
            nationalityEntry = document.toObject(NationalityEntry.class);
            nationalityEntry.setId(document.getId());
            return nationalityEntry;
        } catch (Exception e) {
        	// TODO Handle different type of exceptions
            LOGGER.error("NationalityEntry insertion is failing", e);
            throw e;
        }
    }
    
    public String updateNationalityEntry(NationalityEntry nationalityEntry) throws Exception {
    	if(!StringUtils.hasLength(nationalityEntry.getId())) {
    		throw new Exception("Entry Id cannot be null for update operation!");
    	}
    	
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference nationalityEntries = db.collection("common").document("nationality").collection("nationalityCollection");
        try {
            ApiFuture<WriteResult> res = nationalityEntries.document(nationalityEntry.getId()).set(nationalityEntry);
            return res.get().getUpdateTime().toString();
        } catch (Exception e) {
        	// TODO Handle different type of exceptions
            LOGGER.error("NationalityEntry update is failing", e);
            throw e;
        }
    }
    
    public String deleteNationalityEntry(String id) throws Exception {
    	if(!StringUtils.hasLength(id)) {
    		throw new Exception("Entry Id cannot be null or empty for delete operation!");
    	}
    	
    	Firestore db = FirestoreClient.getFirestore();
    	CollectionReference nationalityEntries = db.collection("common").document("nationality").collection("nationalityCollection");
    	try {
    		ApiFuture<WriteResult> res = nationalityEntries.document(id).delete();
    		return res.get().getUpdateTime().toString();
    	} catch(Exception e) {
    		// TODO Handle different type of exceptions
    		LOGGER.error("NationalityEntry delete is failing", e);
    		throw e;
    	}
    }
}