package server;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;


import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;	

/**
 *  
 * @author Daniel Helmig
 * The SocketHandler class handles the websocket connections to the server 
 * and sends messages after an applied move
 *
 */
@Component
public class SocketHandler extends TextWebSocketHandler {
		
	private static BiMap<String, WebSocketSession> sessionMap = HashBiMap.create();
	private static Set<String> playerSet = new TreeSet<String>();
	
	/**
	 * Adds players to the set of all players and the map of online players
	 */
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {
			
		sessionMap.put(message.getPayload(), session);
    	playerSet.add(message.getPayload());
    	System.out.println("DEBUG: ADDED "+ message.getPayload() + " TO MAP");
	}
	
	/**
	 * Handles an incoming connection by doing nothing
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("DEBUG: SOMEONE CONNECTED");
		//do nothing
	}
	
	/**
	 * Removes a player from the map of online players
	 */
	@Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String key = sessionMap.inverse().get(session);
    	sessionMap.remove(key);
    	System.out.println("DEBUG: REMOVED "+ key + " FROM MAP");
	}
	
	/**
	 * Returns the set of players
	 * @return Returns the playerSet
	 */
	public static Set<String> getPlayers() {
		return playerSet;
	}
	
	/**
	 * Returns if a given player is online
	 * @param player The player to be checked
	 * @return Returns true if the playerMap contains the player
	 */
	public static boolean isOnline(String player) {
		if (sessionMap.containsKey(player)) {
 			return true;
 		} else {
 			return false;
 		}
	}
	
	/**
	 * Tries to send a message to a given player
	 * @param user Player which should receive the message
	 * @throws IOException
	 */
	protected static void sendMessageTo(String user) throws IOException {
	        WebSocketSession session = sessionMap.get(user);
	        if (session != null) {
	        	session.sendMessage(new TextMessage("New Move"));
	        	System.out.println("DEBUG: SENT MESSAGE TO "+ user);
	        } else {
	        	System.out.println("DEBUG: "+ user + " IS OFFLINE");
	        }
	     
	    }
}

