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
import com.yudaiyaguchi.helloalonebackend.models.JobEntry;


@Service
public class JobRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JobRepository.class);
	
	public JobEntry getJobEntryById(String jobId)
		throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference jobEntryRef = db.collection("common").document("job").collection("jobCollection")
				.document(jobId);
		ApiFuture<DocumentSnapshot> future = jobEntryRef.get();
		DocumentSnapshot document = future.get();
		JobEntry jobEntry = null;
		try {
			if (document.exists()) {
				LOGGER.info("Job entry: {}", document.getData());
				jobEntry = document.toObject(JobEntry.class);
				jobEntry.setId(document.getId());
			}
		} catch (Exception e) {
			LOGGER.error("error getting JobEntry by id", e);
		}
		return jobEntry;
	}
	
	public List<JobEntry> getJobEntries() {
        List<JobEntry> entryList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference entries = db.collection("common").document("job").collection("jobCollection");
        ApiFuture<QuerySnapshot> future = entries.get();
        try {
            QuerySnapshot val = future.get();
            List<QueryDocumentSnapshot> docSnaps = val.getDocuments();
            for (int i = 0; i < docSnaps.size(); i++) {
                QueryDocumentSnapshot document = docSnaps.get(i);
                JobEntry entry = document.toObject(JobEntry.class);
                entry.setId(document.getId());
                entryList.add(entry);
            }
        } catch (Exception e) {
            // TODO Handle different type of exceptions
            LOGGER.error("Exception getting jobs", e);
        }
        return entryList;
	}
	
	public List<JobEntry> getJobEntries(List<String> jobIds) throws InterruptedException, ExecutionException {
		if(jobIds == null || jobIds.size() == 0) {
			return null;
		}
		
		List<JobEntry> entryList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference entries = db.collection("common").document("job").collection("jobCollection");
        // No batch operation for reading multiple documents from the same collection, so the code needs to traverse the list
        for(int i = 0; i < jobIds.size(); i++) {
        	DocumentReference collegeEntryRef = entries.document(jobIds.get(i));
    		ApiFuture<DocumentSnapshot> future = collegeEntryRef.get();
    		DocumentSnapshot document = future.get();
    		JobEntry jobEntry = null;
    		try {
    			if (document.exists()) {
    				LOGGER.info("Job entry: {}", document.getData());
    				jobEntry = document.toObject(JobEntry.class);
    				jobEntry.setId(document.getId());
    				entryList.add(jobEntry);
    			} else {
    				LOGGER.error("Not able to find the job with id: " + jobIds.get(i));
    			}
    		} catch (Exception e) {
    			LOGGER.error("error getting Job by id", e);
    		}
        }
        return entryList;
	}
	
    public JobEntry insertJobEntry(JobEntry jobEntry) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference jobEntries = db.collection("common").document("job").collection("jobCollection");
        try {
            ApiFuture<DocumentReference> res = jobEntries.add(jobEntry);
            DocumentReference val = res.get();
            ApiFuture<DocumentSnapshot> future = val.get();
            DocumentSnapshot document = future.get();
            jobEntry = document.toObject(JobEntry.class);
            jobEntry.setId(document.getId());
            return jobEntry;
        } catch (Exception e) {
        	// TODO Handle different type of exceptions
            LOGGER.error("JobEntry insertion is failing", e);
            throw e;
        }
    }
    
    public String updateJobEntry(JobEntry jobEntry) throws Exception {
    	if(!StringUtils.hasLength(jobEntry.getId())) {
    		throw new Exception("Entry Id cannot be null for update operation!");
    	}
    	
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference jobEntries = db.collection("common").document("job").collection("jobCollection");
        try {
            ApiFuture<WriteResult> res = jobEntries.document(jobEntry.getId()).set(jobEntry);
            return res.get().getUpdateTime().toString();
        } catch (Exception e) {
        	// TODO Handle different type of exceptions
            LOGGER.error("JobEntry update is failing", e);
            throw e;
        }
    }
    
    public String deleteJobEntry(String id) throws Exception {
    	if(!StringUtils.hasLength(id)) {
    		throw new Exception("Entry Id cannot be null or empty for delete operation!");
    	}
    	
    	Firestore db = FirestoreClient.getFirestore();
    	CollectionReference jobEntries = db.collection("common").document("job").collection("jobCollection");
    	try {
    		ApiFuture<WriteResult> res = jobEntries.document(id).delete();
    		return res.get().getUpdateTime().toString();
    	} catch(Exception e) {
    		// TODO Handle different type of exceptions
    		LOGGER.error("JobEntry delete is failing", e);
    		throw e;
    	}
    }
}