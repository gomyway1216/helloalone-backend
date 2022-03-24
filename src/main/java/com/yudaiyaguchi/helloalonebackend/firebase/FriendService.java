//package com.yudaiyaguchi.helloalonebackend.firebase;
//
//import java.util.Arrays;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.ExecutionException;
//
//import org.springframework.stereotype.Service;
//import com.google.api.core.ApiFuture;
//import com.google.cloud.firestore.CollectionReference;
//import com.google.cloud.firestore.DocumentReference;
//import com.google.cloud.firestore.DocumentSnapshot;
//import com.google.cloud.firestore.Firestore;
//import com.google.cloud.firestore.Query;
//import com.google.cloud.firestore.QuerySnapshot;
//import com.google.firebase.cloud.FirestoreClient;
//import com.yudaiyaguchi.helloalonebackend.api.Person;
//
//@Service
//public class FriendService {
//	
//	private final String user = "0gAk2mm1A8hi2sEY7mgBagqkbko2";
//
//	public String addPerson(Person person) throws InterruptedException, ExecutionException {
//		Firestore db = FirestoreClient.getFirestore();
//		CollectionReference docRef = db.collection(user).document("friends").collection("itemCollection");
//		Map<String, Object> data = new HashMap<>();
//		data.put("created", new Date());
//		data.put("lastUpdated", new Date());
//		data.put("mainImage", person.getMainImage());
//		data.put("firstName", person.getFirstName());
//		data.put("lastName", person.getLastName());
//		data.put("tags", person.getOptionalFeatures());
//		for(Map.Entry<String, Object> entry : person.getOptionalFeatures().entrySet()) {
//			String key = entry.getKey();
//			// this can be async
//			if(!this.checkTagExists(key)) {
//				Map<String, String> val = new HashMap<>();
//				val.put("name", key);
//				db.collection(user).document("friends").collection("tagCollection").add(val);
//				Thread newThread = new Thread(() -> {
//					Map<String, >
//				});
//			}
//
//		}
//		
//		data.put("description", person.getDescription());
//		ApiFuture<DocumentReference> result = docRef.add(data);
//		return "success";
//	}
//	
//	public boolean checkTagExists(String userId, String key) throws InterruptedException, ExecutionException {
//		Firestore db = FirestoreClient.getFirestore();
//		DocumentReference doc = db.collection("users").document(userId);
//		doc.get().ge
//		Query query = tags.whereEqualTo("name", key);
//		ApiFuture<QuerySnapshot> querySnapshot = query.get();
//		
//		boolean docExists = false;
//		for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
//			docExists = true;
//			System.out.println(document.getId());
//		}
//		return docExists;
//	}
//	
////	public Map<String, String> getTags() {
////		
////	}
//}