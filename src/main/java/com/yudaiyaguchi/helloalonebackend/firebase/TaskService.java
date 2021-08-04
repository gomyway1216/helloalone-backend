package com.yudaiyaguchi.helloalonebackend.firebase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class TaskService {
	
	public String createTask(Task task) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference docRef = db.collection("tasks").document(task.getName());
		Map<String, Object> data = new HashMap<>();
		data.put("tag1", task.getTag());
		ApiFuture<WriteResult> result = docRef.set(data);
		System.out.println("Update time : " + result.get().getUpdateTime());
		return null;
	}
}