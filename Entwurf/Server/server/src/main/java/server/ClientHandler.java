package server;

import io.javalin.Javalin;
public class ClientHandler {

	public static void main(String[] args) {		
	        Javalin app = Javalin.create();
	        //app.get("/", ctx -> ctx.result("Hello World"));	   
	        app.ws("/", ws -> {
	            ws.onConnect(session -> System.out.println("Connected"));
	            ws.onMessage((session, message) -> {
	                System.out.println("Received: " + message);
	                session.getRemote().sendString("Echo: " + message);
	            });
	            ws.onClose((session, statusCode, reason) -> System.out.println("Closed"));
	            ws.onError((session, throwable) -> System.out.println("Errored"));
	        });
	        app.start(370);
	       
	}

}
