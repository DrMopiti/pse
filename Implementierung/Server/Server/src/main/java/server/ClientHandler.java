package server;


import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;


import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.server.standard.SpringConfigurator;

@RestController
@ServerEndpoint(value = "/socket", configurator = SpringConfigurator.class)
@SpringBootApplication
public class ClientHandler{
	private static BiMap<String, Session> sessionMap = HashBiMap.create();
	private static Set<String> playerSet = new TreeSet<String>();
	
	public static void main(String[] args) {
        SpringApplication.run(ClientHandler.class, args);
    }
			
		 	@RequestMapping("/newgame/{white}/{black}")
		 	public String createGame(@PathVariable String white, @PathVariable String black) {
		 		return newGame(white, black);
		 	}
	       
		 	@RequestMapping("/board{player}")
		 	public String getBoard(@PathVariable String player) {
		 		return board(player);
		 	}
	        
		 	@RequestMapping("/move/{player}/{move}")
		 	public String sendMove(@PathVariable String player, @PathVariable String move) {
		 		DatabaseHandler handler = FirebaseHandler.getHandler();
	        	String otherPlayer = handler.getOtherPlayer(player);
	        	try {
					sendMessageTo(otherPlayer);
				} catch (IOException e) {
					e.printStackTrace();
				}
		 		return move(player, move);
		 	}
		 	
		 	@RequestMapping("/players")
		 	public Set<String> getPlayers() {
		 		return playerSet;
		 	}
		 	
		 	@RequestMapping("isonline/{player}")
		 	public boolean isOnline(@PathVariable String player) {
		 		if (sessionMap.containsKey(player)) {
		 			return true;
		 		} else {
		 			return false;
		 		}
		 		
		 	}
	       
		 	@OnOpen
		    public void onOpen(Session session) throws IOException {
		        // Get session and WebSocket connection	 		
		    }
		 
		    @OnMessage
		    public void onMessage(Session session, String message) throws IOException {
		    	sessionMap.put(message, session);
		    	playerSet.add(message);
		    	System.out.println("DEBUG: ADDED "+ message+ " TO MAP");
		    }
		 
		    @OnClose
		    public void onClose(Session session) throws IOException {
		    	String key = sessionMap.inverse().get(session);
            	sessionMap.remove(key);
		        System.out.println("DEBUG: REMOVED "+ key+ " FROM MAP");
		    }
		 
		    @OnError
		    public void onError(Session session, Throwable throwable) {
		    	String key = sessionMap.inverse().get(session);
            	sessionMap.remove(key);
		    	System.out.println("DEBUG: REMOVED "+ key+ " FROM MAP");
		    }
	       
	
	
	
	
	 private static void sendMessageTo(String user) throws IOException {
	        Session session = sessionMap.get(user);
	        if (session != null) {
	        	session.getBasicRemote().sendText("New Move");
	        	System.out.println("DEBUG: SENT MESSAGE TO "+ user);
	        } else {
	        	System.out.println("DEBUG: "+ user + " IS OFFLINE");
	        }
	     
	    }

	 private static String newGame(String white, String black) {
		 GameCreator creator = new GameCreator(white, black);
		 String ifSuccess = creator.create();
		 return ifSuccess;
	 }
	 
	 private static String board(String player) {
		 BoardHandler handler = new BoardHandler(player);
		 String ifSuccess = handler.getBoard();
		 return ifSuccess;
	 }
	 
	 private static String move(String player, String move) {
		 MoveHandler handler = new MoveHandler(player, move);
		 String ifSuccess = handler.processMove();
		 return ifSuccess;
	 }
}

	
	

