package com.yudaiyaguchi.helloalonebackend.repository;

import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.yudaiyaguchi.helloalonebackend.controllers.AuthController;
import com.yudaiyaguchi.helloalonebackend.models.User;
import com.yudaiyaguchi.helloalonebackend.security.jwt.JwtUtils;

@Service
public class UserRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);
    // Optional<User> findByUsername(String username);
    //
    // Boolean existsByUsername(String username);
    //
    // Boolean existsByEmail(String email);

    // private final String user = "0gAk2mm1A8hi2sEY7mgBagqkbko2";

    // public User findById(String id) {
    // Firestore db = FirestoreClient.getFirestore();
    // CollectionReference user
    // }
    
    @Autowired
    JwtUtils jwtUtils;

    public User findById(String id) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference userRef = db.collection("users").document(id);
        ApiFuture<DocumentSnapshot> future = userRef.get();
        DocumentSnapshot document = future.get();
        
        if (document.exists()) {
            User user = document.toObject(User.class);
            user.setId(id);
            return user;
        } else {
            return null;
        }
    }

    public User findByUsername(String name) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference users = db.collection("users");
        Query query = users.whereEqualTo("username", name);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        try {
            User user = new User();
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                final ObjectMapper mapper = new ObjectMapper();
                user = mapper.convertValue(document.getData(), User.class);
                user.setId(document.getId());
            }
            LOGGER.info("User object: {} {} {} {}", user.getId(), user.getUsername(), user.getEmail(),
                    user.getPassword());
            return user;
        } catch (Exception e) {
            LOGGER.error("error message:", e);
        }
        return null;
    }

    public boolean existsByUsername(String username) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference users = db.collection("users");
        Query query = users.whereEqualTo("username", username);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        if (querySnapshot.get().getDocuments().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean existsByEmail(String email) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference users = db.collection("users");
        Query query = users.whereEqualTo("email", email);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        if (querySnapshot.get().getDocuments().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean insert(User user) {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference users = db.collection("users");
        try {
            users.add(user);
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }

        // // Add document data with an additional field ("middle")
        // Map<String, Object> data = new HashMap<>();
        // data.put("first", "Alan");
        // data.put("middle", "Mathison");
        // data.put("last", "Turing");
        // data.put("born", 1912);
        //
        // ApiFuture<WriteResult> result = docRef.set(data);
        // System.out.println("Update time : " + result.get().getUpdateTime());
    }
    
    public String getUserId(String jwt) {
        LOGGER.info("jwt token for getting user Info: :{}", jwt);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        User user = null;
        try {
            user = this.findByUsername(username);
        } catch (Exception e) {
            LOGGER.error("error getting user: {}", e);
        }
        String userId = user.getId();
        LOGGER.info("userId retrieved by jwt token : {}", userId);
        return userId;
    }
    
    public boolean isUserIdAdminByToken(String jwt) throws InterruptedException, ExecutionException {
        LOGGER.info("jwt token for getting user Info: :{}", jwt);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        User user = null;
        try {
            user = this.findByUsername(username);
        } catch (Exception e) {
            LOGGER.error("error getting user: {}", e);
        }
        return user.isAdmin();
    }
    
    public boolean isUserIdAdmin(String userId) throws InterruptedException, ExecutionException {
    	Firestore db = FirestoreClient.getFirestore();
    	DocumentReference userRef = db.collection("users").document(userId);
        ApiFuture<DocumentSnapshot> future = userRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            User user = document.toObject(User.class);
            return user.isAdmin();
        } else {
        	LOGGER.error("error getting user: " + userId);
            return false;
        }
    }
}