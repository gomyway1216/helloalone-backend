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
import com.yudaiyaguchi.helloalonebackend.models.Friend;
//import com.google.cloud.Timestamp;

@Service
public class FriendRepository {
	private static final Logger LOGGER = LoggerFactory.getLogger(FriendRepository.class);
	
	public Friend getFriendById(String userId, String friendId) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference friendRef = db.collection("users").document(userId)
				.collection("friends").document(friendId);
		ApiFuture<DocumentSnapshot> future = friendRef.get();
		DocumentSnapshot document = future.get();
		Friend friend = null;
		try {
			if(document.exists()) {
				LOGGER.info("friend: {}", document.getData());
				friend = document.toObject(Friend.class);
				friend.setId(document.getId());
			}
		} catch (Exception e) {
			LOGGER.error("error getting friend by id", e);
		}
		return friend;
	}
	
	public List<Friend> getFriends(String userId) {
		List<Friend> friendList = new ArrayList<>();
		Firestore db = FirestoreClient.getFirestore();
		CollectionReference friends = db.collection("users").document(userId)
				.collection("friends");
		ApiFuture<QuerySnapshot> future = friends.get();
		try {
			QuerySnapshot val = future.get();
			List<QueryDocumentSnapshot> v1 = val.getDocuments();
			for (int i = 0; i < v1.size(); i++) {
				QueryDocumentSnapshot document = v1.get(i);
				Friend friend = document.toObject(Friend.class);
				friend.setId(document.getId());
				LOGGER.info("friend values after: {}", friend.toString());
				friendList.add(friend);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Exception getting friends", e);
		}
		return friendList;
	}
	
	public Friend addFriend(String userId, Friend friend) throws Exception {
		Firestore db = FirestoreClient.getFirestore();
		CollectionReference friends = db.collection("users").document(userId)
				.collection("friends");
		try {
			ApiFuture<DocumentReference> res = friends.add(friend);
			// add features options
			this.appendOptionalFeature(userId, friend.getOptionalFeatures());
			DocumentReference val = res.get();
			ApiFuture<DocumentSnapshot> future = val.get();
			DocumentSnapshot document = future.get();
			friend = document.toObject(Friend.class);
			friend.setId(document.getId());
			return friend;
		} catch (Exception e) {
			LOGGER.error("insertion is failing", e);
			throw e;
		}
	}
	
	public void appendOptionalFeature(String userId, Map<String, Object> features) {
		if(features == null) {
			return;
		}
		
		try {
			Firestore db = FirestoreClient.getFirestore();
			DocumentReference userDocRef = db.collection("users").document(userId);
			DocumentSnapshot userDoc = userDocRef.get().get();
			Map<String, String> existingFeatureList = (HashMap<String, String>)userDoc.get("friendFeatureList");
			LOGGER.info("existingFeatureList : {}", existingFeatureList);
			boolean mapUpdated = false;
			for(Map.Entry<String, Object> feature : features.entrySet()) {
				String key = feature.getKey();
				LOGGER.info("key: {}, value: {}, type: {}", key, feature.getValue(), feature.getValue().getClass().getSimpleName());
				if(!existingFeatureList.containsKey(key)) {
					mapUpdated = true;
			        Object value = feature.getValue();
			        String valueClassName = feature.getValue().getClass().getSimpleName();
			        String type = "String";
			        if(valueClassName.equals("Long") || valueClassName.equals("Integer")|| valueClassName.equals("Double")) {
			        	type = "Double";
			        } else if(valueClassName.equals("Boolean")) {
			        	type = "Boolean";
			        } else if(valueClassName.equals("ArrayList")) {
			        	type = "List";
			        } else if(valueClassName.equals("ArrayList") && ((List)value).size() > 2 && ((List)value).get(0).equals("Timestamp")) {
			        	type = "Timestamp";
			        } 
			        existingFeatureList.put(key, type);
				}
			}
			if(mapUpdated) {
				Map<String, Object> data = new HashMap<>();
				data.put("friendFeatureList", existingFeatureList);
				ApiFuture<WriteResult> writeRes = userDocRef.set(data, SetOptions.merge());
				WriteResult result = writeRes.get();
				LOGGER.info("Write Result: {}", result);
			} else {
				LOGGER.info("the frieldFeatureList is matching");
			}			
		} catch (Exception e) {
			LOGGER.error("Appending optional Feature is failing.", e);
		}
	}
}
