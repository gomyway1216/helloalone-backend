package com.yudaiyaguchi.helloalonebackend.firebase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FirebaseAcess {
	
	@Autowired
	private Environment envVals;
	
	@PostConstruct
	public void init() throws IOException {
		
		System.out.println(envVals.getProperty("type"));
		
		// Use a service account
//		InputStream serviceAccount = new FileInputStream("src/main/resources/yudai-blog-firebase-adminsdk.json");
//		Map<String, Integer> map = new HashMap<>();
//		GoogleCredentials credentials = GoogleCredentials.fromStream(map.keySet().stream());
//		FirebaseOptions options = new FirebaseOptions.Builder()
//		    .setCredentials(credentials)
//		    .build();
//		FirebaseApp.initializeApp(options);
		
//		FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
//				.setProjectId("yudai-blog")
//				.set
//                .setApiKey("AIzaSyBys-YxxE7kON5PxZc5aY6JwVvreyx_owc")
//                .setDatabaseUrl(databaseUrl)
//                .build();
	}

}
