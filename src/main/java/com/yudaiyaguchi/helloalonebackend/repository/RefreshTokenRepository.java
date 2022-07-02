package com.yudaiyaguchi.helloalonebackend.repository;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.Query;
import com.google.firebase.cloud.FirestoreClient;
import com.yudaiyaguchi.helloalonebackend.models.RefreshToken;
import com.yudaiyaguchi.helloalonebackend.models.User;
import com.google.cloud.Timestamp;

@Repository
public class RefreshTokenRepository {
    // Optional<RefreshToken> findByToken(String token);
    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshTokenRepository.class);

    @Value("${refreshTokenTableName}")
    private String refreshTokenTableName;

    public Optional<RefreshToken> findByToken(String token) {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference tokens = db.collection(this.refreshTokenTableName);
        Query query = tokens.whereEqualTo("token", token);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        RefreshToken refreshToken = null;
        try {
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                // Map<String, >
                final ObjectMapper mapper = new ObjectMapper();
                // mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
                // mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                LOGGER.info("document.getData() : {}", document.getData());
                Map<String, Object> data = document.getData();
                User user = mapper.convertValue(data.get("user"), User.class);
                Date expiryTime = ((Timestamp) data.get("expiryTime")).toDate();
                refreshToken = new RefreshToken(document.getId(), user, (String) data.get("token"), expiryTime);
                LOGGER.info("refreshToken : {}", refreshToken);
                // refreshToken = mapper.convertValue(document.getData(), RefreshToken.class);
                // refreshToken.setId(document.getId());
            }
            LOGGER.info("User object: {} {} {} {}", refreshToken.getId(), refreshToken.getUser(),
                    refreshToken.getToken(), refreshToken.getExpiryTime());
            return Optional.of(refreshToken);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    // @Modifying
    // int deleteByUser(User user);

    // public int deleteByUser(User user) {
    // //TODO: write some operation
    // return 0;
    // }
    // I think it is to delete the token by the User.
    public int deleteByUser(User user) {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference tokens = db.collection(this.refreshTokenTableName);
        Query query = tokens.whereEqualTo("user.username", user.getUsername());
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        try {
            int deleted = 0;
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                document.getReference().delete();
                deleted++;
            }
            LOGGER.info("Deleted number of documents: {}", deleted);
            return deleted;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    public void delete(RefreshToken token) {
        Firestore db = FirestoreClient.getFirestore();
        try {
            DocumentReference doc = db.collection(this.refreshTokenTableName).document(token.getId());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public boolean insert(RefreshToken token) {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference tokens = db.collection(this.refreshTokenTableName);
        try {
            tokens.add(token);
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }
}