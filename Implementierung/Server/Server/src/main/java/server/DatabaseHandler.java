package server;
/**
 * 
 * @author Daniel Helmig
 * The abstract class DatabaseHandler
 * Every database handler should implement these methods
 *
 */
public interface DatabaseHandler {
	 public void newEntry(String whitePlayer, String blackPlayer);
	 public String loadGame(String player);
	 public String saveGame(String player, String board);
	 public String deleteGame(String player);
	 public boolean hasActiveGame(String player);
	 public String getOtherPlayer(String player);
	 public void connect();
}
