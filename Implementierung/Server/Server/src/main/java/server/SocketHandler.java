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

@Component
public class SocketHandler extends TextWebSocketHandler {
		
	private static BiMap<String, WebSocketSession> sessionMap = HashBiMap.create();
	private static Set<String> playerSet = new TreeSet<String>();
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {
			
		sessionMap.put(message.getPayload(), session);
    	playerSet.add(message.getPayload());
    	System.out.println("DEBUG: ADDED "+ message.getPayload() + " TO MAP");
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("DEBUG: SOMEONE CONNECTED");
		//do nothing
	}
	
	@Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String key = sessionMap.inverse().get(session);
    	sessionMap.remove(key);
    	System.out.println("DEBUG: REMOVED "+ key + " FROM MAP");
	}
	
	public static Set<String> getPlayers() {
		return playerSet;
	}
	
	public static boolean isOnline(String player) {
		if (sessionMap.containsKey(player)) {
 			return true;
 		} else {
 			return false;
 		}
	}
	
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

