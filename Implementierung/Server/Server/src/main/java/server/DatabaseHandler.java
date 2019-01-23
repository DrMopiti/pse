package server;

public interface DatabaseHandler {
	 public void newEntry(String whitePlayer, String blackPlayer);
	 public String loadGame(String player);
	 public String saveGame(String player, String board);
	 public boolean hasActiveGame(String player);
	 public String getOtherPlayer(String player);
	 public void connect();
}
