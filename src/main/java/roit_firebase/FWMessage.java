package roit_firebase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class FWMessage {
	Firestore db;
	
	public FWMessage() {
		// TODO Auto-generated constructor stub
	}
	
	public void initFireBase() {
		InputStream serviceAccount = null;
		try {
			serviceAccount = new FileInputStream("roit-discordtowechat-firebase-firebase-adminsdk-nis8h-710dff0e76.json");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GoogleCredentials credentials = null;
		try {
			credentials = GoogleCredentials.fromStream(serviceAccount);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FirebaseOptions options = new FirebaseOptions.Builder()
		    .setCredentials(credentials)
		    .build();
		FirebaseApp.initializeApp(options);

		db = FirestoreClient.getFirestore();
	}
	
	public void sendMessage(String user, String message) {
		Map<String, Object> docData = new HashMap<>();
		docData.put("user", user);
		docData.put("pop_message", message);
		
		// Add a new document (asynchronously)
		ApiFuture<WriteResult> future = db.collection("discord_batphone_message").document(String.valueOf(System.currentTimeMillis())).set(docData);
		
		try {
			System.out.println("Update time : " + future.get().getUpdateTime() + "result: " + future.isDone());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FWMessage message = new FWMessage();
		message.initFireBase();
		message.sendMessage("Koalala", "Zlandicar pop!");
	}

}
