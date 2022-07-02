package com.yudaiyaguchi.helloalonebackend.firebase;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

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
        String credential = envVals.getProperty("firebase_credential");
        byte[] decodedBytes = Base64.getDecoder().decode(credential);
        InputStream is = new ByteArrayInputStream(decodedBytes);
        GoogleCredentials credentials = GoogleCredentials.fromStream(is);
        FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(credentials).build();
        FirebaseApp.initializeApp(options);
    }
}
