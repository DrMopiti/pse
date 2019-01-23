package server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import io.javalin.Javalin;
import io.javalin.websocket.WsSession;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class ClientHandler {
	private static BiMap<String, WsSession> sessionMap = HashBiMap.create();
	private static Set<String> playerSet = new TreeSet();
	
	public static void main(String[] args) {
		
		 Javalin app = Javalin.create();
	        app.get("/newgame/*/*", ctx -> {
	        	String white = ctx.splat(0);
	        	String black = ctx.splat(1);
	        	ctx.result(newGame(white, black));
	        	System.out.println("DEBUG: NEW GAME CREATED");
	        });
	        app.get("/board/:player", ctx -> {
	        	String player = ctx.pathParam("player");
	        	ctx.result(board(player));
	        	System.out.println("DEBUG: RETURNED BOARD");
	        });
	        app.get("/move/*/*", ctx -> {	
	        	String player = ctx.splat(0);
	        	String move = ctx.splat(1);
	        	move(player, move);
	        	String otherPlayer ="Netter Mensch";
	        	sendMessageTo(otherPlayer);
	        	System.out.println("DEBUG: APPLIED MOVE");
	        });
	        app.get("/players", ctx -> {
	        	ctx.result(playerSet.toString());
	        	System.out.println("DEBUG: PLAYER LIST");
	        });
	        app.ws("/socket", ws -> {
	        	
	        	ws.onConnect(session -> {	 
	        		
	        	});
	        	
	            ws.onMessage((session, message) -> {
	            	
	               sessionMap.put(message, session);
	               playerSet.add(message);
	               System.out.println("DEBUG: ADDED "+ message+ " TO MAP");
	               
	            });
	            
	            ws.onClose((session, statusCode, reason) -> {
	            	
	            	String key = sessionMap.inverse().get(session);
	            	sessionMap.remove(key);
	            	System.out.println("DEBUG: REMOVED "+ key + " FROM MAP -- DISCONNECT");
	            	
	            });
	            
	            ws.onError((session, throwable) -> {
	            	
	            	String key = sessionMap.inverse().get(session);
	            	sessionMap.remove(key);
	            	System.out.println("DEBUG: REMOVED "+ key + " FROM MAP -- ERROR");
	            	
	            });
	        });
	        app.start(370);

	}
	
	 private static void sendMessageTo(String user) {
	        WsSession session = sessionMap.get(user);
	        if (session != null) {
	        	session.send("New Move");
	        	System.out.println("DEBUG: SENT MESSAGE TO "+ user);
	        } else {
	        	System.out.println("DEBUG: "+ user + " IS OFFLINE");
	        }
	     
	    }

	 private static String newGame(String white, String black) {
		 GameCreator creator = new GameCreator(white, black);
		 String success = creator.create();
		 return success;
	 }
	 
	 private static String board(String player) {
		 BoardHandler handler = new BoardHandler(player);
		 String success = handler.getBoard();
		 return success;
	 }
	 
	 private static String move(String player, String move) {
		 MoveHandler handler = new MoveHandler(player, move);
		 String success = handler.processMove();
		 return success;
	 }
}
