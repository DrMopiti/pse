package server;


import java.io.IOException;
import java.util.Set;




import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Daniel Helmig
 * The core of the server.
 * Handles all REST calls
 */
@RestController
@SpringBootApplication
public class ClientHandler{

	public static void main(String[] args) {
        SpringApplication.run(ClientHandler.class, args);
    }
	
	/**
	 * Calls newGame() with parameters
	 * @param white The white player
	 * @param black The black player
	 * @return Returns "Success" if the operation was successful and other messages on failure
	 */
	@RequestMapping("/newgame/{white}/{black}")
	public String createGame(@PathVariable String white, @PathVariable String black) {
		 return newGame(white, black);
	}
	
	/**
	 * Calls delete() with parametes
	 * @param player The player whose game should be deleted
	 * @return Returns "Success" if the operation was successful and other messages on failure
	 */
	@RequestMapping("/delete/{player}")
	public String deleteGame(@PathVariable String player) {
		return delete(player);
	}
	 
	/**
	 * Calls board()
	 * @param player The player
	 * @return Returns the board string on success and other messages on failure
	 */
	@RequestMapping("/board/{player}")
	public String getBoard(@PathVariable String player) {
		 return board(player);
	}
	
	/**
	 * Calls move() and tries to message the opponent via the websocket connection
	 * @param player The player
	 * @param move The move
	 * @return Returns "Success" if the operation was successful and other messages on failure
	 */
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
	
	/**
	 * Returns all players since server start
	 * @return Returns the playerSet of the SocketHandler
	 */
	@RequestMapping("/players")
	public Set<String> getPlayers() {
		 return SocketHandler.getPlayers();
	}
	
	/**
	 * Returns true if a player is online
	 * @param player The player to be checked
	 * @return Returns true if the player is online and else if he isn't
	 */
	@RequestMapping("isonline/{player}")
	public boolean isOnline(@PathVariable String player) {
		 return SocketHandler.isOnline(player);	 		
	}
	
	@RequestMapping("/hasgame/{player}")
	public boolean hasGame(@PathVariable String player) {
		DatabaseHandler handler = FirebaseHandler.getHandler();
		Boolean hasGame = handler.hasActiveGame(player);
		return hasGame;
	}
	       	 		 	
	
	/**
	 * Creates a new GameCreator with the correct parameters
	 * @param white The white player
	 * @param black The black player
	 * @return Returns "Success" if the operation was successful and other messages on failure
	 */
	 private static String newGame(String white, String black) {
		 GameCreator creator = new GameCreator(white, black);
		 String ifSuccess = creator.create();
		 return ifSuccess;
	 }
	 
	 /**
	  * Deletes a game document
	  * @param player The player whose game should be deleted
	  * @return Returns "Success" if the operation was successful and other messages on failure
	  */
	 private String delete(String player) {
		 DatabaseHandler handler = FirebaseHandler.getHandler();
		 return handler.deleteGame(player);
	 }
	 
	 /**
	  * Creates a new BoardHandler with the correct parameters
	  * @param player The player
	  * @return Returns the board string on success and other messages if the operation failed
	  */
	 private static String board(String player) {
		 BoardHandler handler = new BoardHandler(player);
		 String ifSuccess = handler.getBoard();
		 return ifSuccess;
	 }
	 
	 /**
	  * Creates a new MoveHandler with the correct parameters
	  * @param player The player
	  * @param move The move
	  * @return	Returns "Success" if the operation was successful and other messages on failure
	  */
	 private static String move(String player, String move) {
		 MoveHandler handler = new MoveHandler(player, move);
		 String ifSuccess = handler.processMove();
		 return ifSuccess;
	 }
}

	
	

