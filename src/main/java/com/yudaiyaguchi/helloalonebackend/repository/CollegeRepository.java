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
import com.yudaiyaguchi.helloalonebackend.models.CollegeEntry;

public class CollegeRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CollegeRepository.class);
	
	public CollegeEntry getCollegeEntryById(String collegeId)
		throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference collegeEntryRef = db.collection("common").document("college").collection("collegeCollection")
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
	
	public List<CollegeEntry> getCollegeEntries() {
        List<CollegeEntry> entryList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference entries = db.collection("common").document("college").collection("collegeCollection");
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
	
    public CollegeEntry addCollegeEntry(CollegeEntry collegeEntry) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collegeEntries = db.collection("common").document("college").collection("collegeCollection");
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
}
