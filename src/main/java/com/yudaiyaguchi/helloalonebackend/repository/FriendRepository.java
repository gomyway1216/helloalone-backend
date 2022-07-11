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
import com.yudaiyaguchi.helloalonebackend.models.FriendEntry;

public class FriendRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FriendRepository.class);
	
	public FriendEntry getFriendEntryById(String userId, String friendId)
		throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference friendEntryRef = db.collection("users").document(userId).collection("friend")
				.document(friendId);
		ApiFuture<DocumentSnapshot> future = friendEntryRef.get();
		DocumentSnapshot document = future.get();
		FriendEntry friendEntry = null;
		try {
			if (document.exists()) {
				LOGGER.info("Friend entry: {}", document.getData());
				friendEntry = document.toObject(FriendEntry.class);
				friendEntry.setId(document.getId());
			}
		} catch (Exception e) {
			LOGGER.error("error getting FriendEntry by id", e);
		}
		return friendEntry;
	}
	
	public List<FriendEntry> getFriendEntries(String userId) {
        List<FriendEntry> entryList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference entries = db.collection("users").document(userId).collection("friend");
        ApiFuture<QuerySnapshot> future = entries.get();
        try {
            QuerySnapshot val = future.get();
            List<QueryDocumentSnapshot> docSnaps = val.getDocuments();
            for (int i = 0; i < docSnaps.size(); i++) {
                QueryDocumentSnapshot document = docSnaps.get(i);
                FriendEntry entry = document.toObject(FriendEntry.class);
                entry.setId(document.getId());
                entryList.add(entry);
            }
        } catch (Exception e) {
            // TODO Handle different type of exceptions
            LOGGER.error("Exception getting friends", e);
        }
        return entryList;
	}
	
    public FriendEntry addFriendEntry(String userId, FriendEntry friendEntry) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference friendEntries = db.collection("users").document(userId).collection("friend");
        try {
            ApiFuture<DocumentReference> res = friendEntries.add(friendEntry);
            DocumentReference val = res.get();
            ApiFuture<DocumentSnapshot> future = val.get();
            DocumentSnapshot document = future.get();
            friendEntry = document.toObject(FriendEntry.class);
            friendEntry.setId(document.getId());
            return friendEntry;
        } catch (Exception e) {
        	// TODO Handle different type of exceptions
            LOGGER.error("FriendEntry insertion is failing", e);
            throw e;
        }
    }
}