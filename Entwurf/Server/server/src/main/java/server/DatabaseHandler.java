package server;

public interface DatabaseHandler {	
	
	public void newEntry(String player1, String player2);
	public String loadGame(String player);
	public void saveGame(String player);
		
	
	
}
