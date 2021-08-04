package com.yudaiyaguchi.helloalonebackend.firebase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class TaskService {
	
//	@Autowired
//	private Environment envVals;
	
	public String createTask(Task task) throws InterruptedException, ExecutionException {
		
		Map<String, String> env = System.getenv();
		
//		System.out.println(System.getenv("type"));
//		System.out.println(envVals.getProperty("type"));
//		System.out.println("type");
		Map<String, String> firebaseCredentials = new HashMap<>();
		firebaseCredentials.put("type", System.getenv("type"));
		firebaseCredentials.put("project_id", System.getenv("project_id"));
		firebaseCredentials.put("private_key_id", System.getenv("private_key_id"));
		firebaseCredentials.put("private_key", System.getenv("private_key"));
		firebaseCredentials.put("client_email", System.getenv("client_email"));
		firebaseCredentials.put("client_id", System.getenv("client_id"));
		firebaseCredentials.put("auth_uri", System.getenv("auth_uri"));
		firebaseCredentials.put("token_uri", System.getenv("token_uri"));
		firebaseCredentials.put("auth_provider_x509_cert_url", System.getenv("auth_provider_x509_cert_url"));
		firebaseCredentials.put("client_x509_cert_url", System.getenv("client_x509_cert_url"));
		

		for(String envName : env.keySet()){
		    System.out.println(String.format("%s : %s", envName, env.get(envName)));
		}
		
		for(String envName : firebaseCredentials.keySet()){
		    System.out.println(String.format("%s : %s", envName, firebaseCredentials.get(envName)));
		}
		
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference docRef = db.collection("tasks").document(task.getName());
		Map<String, Object> data = new HashMap<>();
		data.put("tag", task.getTag());
		ApiFuture<WriteResult> result = docRef.set(data);
		System.out.println("Update time : " + result.get().getUpdateTime());
		return null;
	}
}


//
//@Service  
//public class PatientService {  
//	
//	public static final String COL_NAME="users";  
//	
//	public String savePatientDetails(Patient patient) throws InterruptedException, ExecutionException {  
//		Firestore dbFirestore = FirestoreClient.getFirestore();  
//		ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(patient.getName()).set(patient);  
//		return collectionsApiFuture.get().getUpdateTime().toString();  
//	}  
//	public Patient getPatientDetails(String name) throws InterruptedException, ExecutionException {  
//		Firestore dbFirestore = FirestoreClient.getFirestore();  
//		DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(name);  
//		ApiFuture<DocumentSnapshot> future = documentReference.get();  
//		DocumentSnapshot document = future.get();  
//		Patient patient = null;  
//		if(document.exists()) {  
//		patient = document.toObject(Patient.class);  
//		return patient;  
//		}else {  
//		return null;  
//		}  
//	}  
//	public String updatePatientDetails(Patient person) throws InterruptedException, ExecutionException {  
//		Firestore dbFirestore = FirestoreClient.getFirestore();  
//		ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(person.getName()).set(person);  
//		return collectionsApiFuture.get().getUpdateTime().toString();  
//	}  
//	public String deletePatient(String name) {  
//		Firestore dbFirestore = FirestoreClient.getFirestore();  
//		ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(name).delete();  
//		return "Document with Patient ID "+name+" has been deleted";  
//	}
//}  