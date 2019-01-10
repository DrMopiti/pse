package server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.javalin.Javalin;
import io.javalin.websocket.WsSession;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class ClientHandler {
	private static BiMap<String, WsSession> sessionMap = HashBiMap.create();
	
	public static void main(String[] args) {
		
		 Javalin app = Javalin.create();
	        app.get("/newgame", ctx -> {
	        	
	        });
	        app.get("/board/:param", ctx -> {
	        	
	        });
	        app.get("/move/:move", ctx -> {	   
	        	String otherPlayer ="";
	        	sendMessageTo(otherPlayer);
	        });
	        app.ws("/socket", ws -> {
	        	
	        	ws.onConnect(session -> {	 
	        		
	        	});
	        	
	            ws.onMessage((session, message) -> {
	            	
	               sessionMap.put(message, session);
	               
	            });
	            
	            ws.onClose((session, statusCode, reason) -> {
	            	
	            	String key = sessionMap.inverse().get(session);
	            	sessionMap.remove(key);
	            	
	            });
	            
	            ws.onError((session, throwable) -> {
	            	
	            	String key = sessionMap.inverse().get(session);
	            	sessionMap.remove(key);
	            	
	            });
	        });
	        app.start(370);

	}
	
	 private static void sendMessageTo(String user) {
	        WsSession session = sessionMap.get(user);
	        if (session != null) {
	        session.send("New Move");
	        System.out.println("SENT");
	        }
	    }

}
