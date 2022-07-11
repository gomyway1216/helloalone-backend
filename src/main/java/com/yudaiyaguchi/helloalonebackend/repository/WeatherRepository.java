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
import com.yudaiyaguchi.helloalonebackend.models.WeatherEntry;


public class WeatherRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WeatherRepository.class);
	
	public WeatherEntry getWeatherEntryById(String weatherId)
		throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference weatherEntryRef = db.collection("common").document("weather").collection("weatherCollection")
				.document(weatherId);
		ApiFuture<DocumentSnapshot> future = weatherEntryRef.get();
		DocumentSnapshot document = future.get();
		WeatherEntry weatherEntry = null;
		try {
			if (document.exists()) {
				LOGGER.info("Weather entry: {}", document.getData());
				weatherEntry = document.toObject(WeatherEntry.class);
				weatherEntry.setId(document.getId());
			}
		} catch (Exception e) {
			LOGGER.error("error getting WeatherEntry by id", e);
		}
		return weatherEntry;
	}
	
	public List<WeatherEntry> getWeatherEntries() {
        List<WeatherEntry> entryList = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference entries = db.collection("common").document("weather").collection("weatherCollection");
        ApiFuture<QuerySnapshot> future = entries.get();
        try {
            QuerySnapshot val = future.get();
            List<QueryDocumentSnapshot> docSnaps = val.getDocuments();
            for (int i = 0; i < docSnaps.size(); i++) {
                QueryDocumentSnapshot document = docSnaps.get(i);
                WeatherEntry entry = document.toObject(WeatherEntry.class);
                entry.setId(document.getId());
                entryList.add(entry);
            }
        } catch (Exception e) {
            // TODO Handle different type of exceptions
            LOGGER.error("Exception getting weathers", e);
        }
        return entryList;
	}
	
    public WeatherEntry addWeatherEntry(WeatherEntry weatherEntry) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference weatherEntries = db.collection("common").document("weather").collection("weatherCollection");
        try {
            ApiFuture<DocumentReference> res = weatherEntries.add(weatherEntry);
            DocumentReference val = res.get();
            ApiFuture<DocumentSnapshot> future = val.get();
            DocumentSnapshot document = future.get();
            weatherEntry = document.toObject(WeatherEntry.class);
            weatherEntry.setId(document.getId());
            return weatherEntry;
        } catch (Exception e) {
        	// TODO Handle different type of exceptions
            LOGGER.error("WeatherEntry insertion is failing", e);
            throw e;
        }
    }
}