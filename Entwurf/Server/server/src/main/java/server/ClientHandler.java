package server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


import io.javalin.Javalin;
import io.javalin.websocket.WsSession;

public class ClientHandler {
	private static Map<String, WsSession> userUsernameMap = new ConcurrentHashMap<>();
	public static void main(String[] args) {		
	        Javalin app = Javalin.create();
	        app.get("/newgame", ctx -> {
	        	ctx.result("HI");
	        });
	        app.get("/board/:param", ctx -> {
	        	ctx.result(ctx.pathParam("param"));
	        });
	        app.get("/move/:move", ctx -> {	   
	        	ctx.result(ctx.pathParam("move")+ userUsernameMap.toString());
	        	sendMessageTo("player");
	        });
	        app.ws("/socket", ws -> {
	        	ws.onConnect(session -> {
	        	System.out.println("Connected");
	        	userUsernameMap.put("player", session);
	        	});
	            ws.onMessage((session, message) -> {
	                System.out.println("Received: " + message);
	                session.getRemote().sendString("Echo: " + message);
	            });
	            ws.onClose((session, statusCode, reason) -> System.out.println("Closed"));
	            ws.onError((session, throwable) -> System.out.println("Errored"));
	        });
	        app.start(370);
	        
	}
	
	  private static void sendMessageTo(String user) {
	        WsSession session = userUsernameMap.get(user);
	        session.send("refresh faggot");	               
	    }
}
