package server;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.core.io.ClassPathResource;

import com.example.user.schachapp.chessLogic.BoardState;
import com.example.user.schachapp.chessLogic.ChessRuleProvider;
import com.example.user.schachapp.chessLogic.RuleProvider;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;


/**
 * 
 * @author Daniel Helmig
 * 
 * The FirebaseHandler is an implementation of the abstract class DatabaseHandler.
 * It connects, reads and writes to the Firebase database.
 * Uses the pattern Singleton because only one database reference is needed and
 * trying to get multiple references with the same name results in an error.
 *
 */
public class FirebaseHandler implements DatabaseHandler {
	
	Firestore db = null;
	
	private static FirebaseHandler handler;
	
	/**
	 * Constructor set to private
	 */
	private FirebaseHandler() {
	}
	
	/**
	 * Returns the Firebase handler with an established connection
	 * @return Returns the handler
	 */
	public static FirebaseHandler getHandler() {
		if (handler == null) {
			handler = new FirebaseHandler();
			handler.connect();
		} 		
		return handler;
	}
	
	/**
	 * Reads the private Key and other data and connects to the database
	 */
	public void connect() {

	// Use a service account
	InputStream serviceAccount = null;
	try {
		serviceAccount = new ClassPathResource("serviceAccount.json").getInputStream();
	} catch (IOException e1) {
		e1.printStackTrace();
	}	
	GoogleCredentials credentials = null;
	try {
		credentials = GoogleCredentials.fromStream(serviceAccount);
	} catch (IOException e2) {
		e2.printStackTrace();
		System.out.println("DEBUG: ERROR WHILE SETTING CREDENTIALS");
	}
	FirebaseOptions options = new FirebaseOptions.Builder()
	    .setCredentials(credentials).build();
	FirebaseApp.initializeApp(options);

	db = FirestoreClient.getFirestore();
	System.out.println("DEBUG: ESTABLISHED DATABASE CONNECTION");
	}
	
	/**
	 * Creates a new document in the "games" collection
	 */
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
		RuleProvider ruler = new ChessRuleProvider();		
		BoardState board = ruler.getStartState();
	
		
		Map<String, Object> data = new HashMap<>();
		data.put("board", board.toString());
		data.put("whitePlayer", whitePlayer);
		data.put("blackPlayer", blackPlayer);

		ApiFuture<WriteResult> result = docRef.set(data);
		System.out.println("DEBUG: CREATED NEW GAME");

	}
	
	/**
	 * Loads a document with the given string as a player
	 */
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
			String white = document.getString("whitePlayer");
			String black = document.getString("blackPlayer");

			System.out.println("DEBUG: CHECKING GAME " + document.getId() + " FOR PLAYER " + player);
			System.out.println("DEBUG: PARTS " + white + " & " + black);
			if (white.equalsIgnoreCase(player)) {
				System.out.println("DEBUG: FOUND OCCURENCE OF " +  player);
				board = document.getString("board");
			}
			if (black.equalsIgnoreCase(player)) {
				System.out.println("DEBUG: FOUND OCCURENCE OF " +  player);
				board = document.getString("board");
			}
		}
		return board;
	}

	
	/**
	 * Saves a document with both players and a board string
	 */
	@Override
	public String saveGame(String player, String board) {
		//asynchronously update doc, create the document if missing
		String otherPlayer = getOtherPlayer(player);
		Map<String, Object> update = new HashMap<>();
		update.put("board", board);
		ApiFuture<WriteResult> reuslt =  db.collection("games")
			.document(player+"-"+otherPlayer)			
		    .set(update, SetOptions.merge());
		
			
		return "Success";

	}
	
	/**
	 * Deletes a game document
	 */
	@Override 
	public String deleteGame(String player) {
		String otherPlayer = getOtherPlayer(player);
		if (amIWhite(player)) {
			ApiFuture<WriteResult> result = db.collection("games")
				.document(player+"-"+otherPlayer).delete();
		} else {
			ApiFuture<WriteResult> result = db.collection("games")
					.document(otherPlayer+"-"+player).delete();
		}
		
		return "Success";
	}

	/**
	 * Gets the opponent of a player
	 */
	public String getOtherPlayer(String player) {
		
		String other = "";
		
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
			String white = document.getString("whitePlayer");
			String black = document.getString("blackPlayer");

			System.out.println("DEBUG: CHECKING GAME " + document.getId() + " FOR PLAYER " + player);
			System.out.println("DEBUG: PLAYER " + player);
			if (white.equalsIgnoreCase(player)) {
				System.out.println("DEBUG: FOUND OCCURENCE OF " +  player);
				other = document.getString("blackPlayer");
			}
			if (black.equalsIgnoreCase(player)) {
				System.out.println("DEBUG: FOUND OCCURENCE OF " +  player);
				other = document.getString("whitePlayer");
			}
		}
		return other;
	}
	
	/**
	 * Checks if a given player is in an active game
	 */
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
			String white = document.getString("whitePlayer");
			String black = document.getString("blackPlayer");

			System.out.println("DEBUG: CHECKING GAME " + document.getId() + " FOR PLAYER " + player);
			System.out.println("DEBUG: PARTS " + white + " & " + black);
			if (white.equalsIgnoreCase(player)) {
				System.out.println("DEBUG: FOUND OCCURENCE OF " +  player);
				occurrence++;
			}
			if (black.equalsIgnoreCase(player)) {
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
	
	@Override
	public Boolean amIWhite(String player) {
		
		Boolean amI = null;		
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
			String white = document.getString("whitePlayer");
			String black = document.getString("blackPlayer");

			System.out.println("DEBUG: CHECKING GAME " + document.getId() + " FOR PLAYER " + player);
			System.out.println("DEBUG: PLAYER " + player);
			if (white.equalsIgnoreCase(player)) {
				System.out.println("DEBUG: FOUND OCCURENCE OF " +  player);
				amI = true;
			}
			if (black.equalsIgnoreCase(player)) {
				System.out.println("DEBUG: FOUND OCCURENCE OF " +  player);
				amI = false;
			}
		}
		return amI;
	}

}
