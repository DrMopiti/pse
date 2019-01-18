package server;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.Arrays;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class FirebaseHandler implements DatabaseHandler {
	
	Firestore db = null;
	
	private static FirebaseHandler handler;
	
	private FirebaseHandler() {
	}
	
	public static FirebaseHandler getHandler() {
		if (handler == null) {
			handler = new FirebaseHandler();
			handler.connect();
		} 		
		return handler;
	}
	
	public void connect() {

	// Use a service account
	InputStream serviceAccount = null;
	try {
		serviceAccount = new FileInputStream("serviceAccount.json");
	} catch (FileNotFoundException e) {
		e.printStackTrace();
		System.out.println("DEBUG: ERROR WHILE READING SERVICE ACCOUNT JSON");
	}
	GoogleCredentials credentials = null;
	try {
		credentials = GoogleCredentials.fromStream(serviceAccount);
	} catch (IOException e) {
		e.printStackTrace();
		System.out.println("DEBUG: ERROR WHILE SETTING CREDENTIALS");
	}
	FirebaseOptions options = new FirebaseOptions.Builder()
	    .setCredentials(credentials).build();
	FirebaseApp.initializeApp(options);

	db = FirestoreClient.getFirestore();
	System.out.println("DEBUG: ESTABLISHED DATABASE CONNECTION");
	}
	
	
	@Override
	public void newEntry(String whitePlayer, String blackPlayer) {
		DocumentReference docRef = null;
		String id = whitePlayer + "-" +blackPlayer;
		try {
			docRef = db.collection("games").document(id);
			System.out.println("DEBUG: DOC REF CREATED");
		} catch (NullPointerException e) {
			System.out.println("DEBUG: ERROR WHILE GETTING DOC REF");
			return;
		}
		// Add document data with an additional field ("middle")
		Map<String, Object> data = new HashMap<>();
		data.put("board", "Testbrett");
		data.put("whitePlayer", whitePlayer);
		data.put("blackPlayer", blackPlayer);

		ApiFuture<WriteResult> result = docRef.set(data);
		System.out.println("DEBUG: CREATED NEW GAME");

	}

	@Override
	public String loadGame(String player) {
		String board = "";
	
		// asynchronously retrieve all users
		ApiFuture<QuerySnapshot> query = db.collection("games").get();
		// query.get() blocks on response
		QuerySnapshot querySnapshot = null;
		try {
			querySnapshot = query.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			System.out.println("DEBUG: ERROR WHILE GETTING QUERY");
			}
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			String name = document.getId();
			System.out.println("DEBUG: CHECKING GAME " + document.getId() + " FOR PLAYER " + player);
			String[] parts = name.split("-");
			System.out.println("DEBUG: PARTS " + Arrays.deepToString(parts));
			if (parts.length != 2) {
				System.out.println("DEBUG: ILLEGAL GAME ID FOUND");
				board = "ERROR";
			}
			if (parts[0].equalsIgnoreCase(player)) {
				System.out.println("DEBUG: FOUND OCCURENCE OF " +  player);
				board = document.getString("board");
			}
			if (parts[1].equalsIgnoreCase(player)) {
				System.out.println("DEBUG: FOUND OCCURENCE OF " +  player);
				board = document.getString("board");
			}
		}
		return board;
	}

	@Override
	public void saveGame(String player) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasActiveGame(String player) {
		int occurrence = 0;
		// asynchronously retrieve all users
		ApiFuture<QuerySnapshot> query = db.collection("games").get();
		// query.get() blocks on response
		QuerySnapshot querySnapshot = null;
		try {
			querySnapshot = query.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			System.out.println("DEBUG: ERROR WHILE GETTING QUERY");
			occurrence++; //assume the player already is in a game
		}
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			String name = document.getId();
			System.out.println("DEBUG: CHECKING GAME " + document.getId() + " FOR PLAYER " + player);
			String[] parts = name.split("-");
			System.out.println("DEBUG: PARTS " + Arrays.deepToString(parts));
			if (parts.length != 2) {
				System.out.println("DEBUG: ILLEGAL GAME ID FOUND");
				occurrence++;
			}
			if (parts[0].equalsIgnoreCase(player)) {
				System.out.println("DEBUG: FOUND OCCURENCE OF " +  player);
				occurrence++;
			}
			if (parts[1].equalsIgnoreCase(player)) {
				System.out.println("DEBUG: FOUND OCCURENCE OF " +  player);
				occurrence++;
			}
			
		}
		if (occurrence > 0) {
			return true;
		} else {
			return false;
		}
		
	}

}
