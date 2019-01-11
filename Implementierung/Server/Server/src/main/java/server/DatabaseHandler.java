package server;

public interface DatabaseHandler {
	 public void newEntry(String whitePlayer, String blackPlayer);
	 public String loadGame(String player);
	 public void saveGame(String player);
	 public boolean hasActiveGame(String player);
}
