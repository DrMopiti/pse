package server;


import java.io.IOException;
import java.util.Set;




import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
public class ClientHandler{

	public static void main(String[] args) {
        SpringApplication.run(ClientHandler.class, args);
    }
			
	@RequestMapping("/newgame/{white}/{black}")
	public String createGame(@PathVariable String white, @PathVariable String black) {
		 return newGame(white, black);
	}
	       
	@RequestMapping("/board/{player}")
	public String getBoard(@PathVariable String player) {
		 return board(player);
	}
	        	 	
	@RequestMapping("/move/{player}/{move}")
	public String sendMove(@PathVariable String player, @PathVariable String move) {
		DatabaseHandler handler = FirebaseHandler.getHandler();
	    String otherPlayer = handler.getOtherPlayer(player);
	    try {
			SocketHandler.sendMessageTo(otherPlayer);
		} catch (IOException e) {
			e.printStackTrace();
		}
			return move(player, move);
	}
		 	
	@RequestMapping("/players")
	public Set<String> getPlayers() {
		 return SocketHandler.getPlayers();
	}
		 	
	@RequestMapping("isonline/{player}")
	public boolean isOnline(@PathVariable String player) {
		 return SocketHandler.isOnline(player);	 		
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

	
	

