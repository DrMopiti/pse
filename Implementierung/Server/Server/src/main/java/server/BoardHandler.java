package server;

public class BoardHandler {
	
	private String player;
	public BoardHandler(String player) {
		this.player = player;
	}
	
	public String getBoard() {
		String retString;
		DatabaseHandler handler = FirebaseHandler.getHandler();
		handler.connect();
		if (handler.hasActiveGame(player)) {
			retString = handler.loadGame(player);
		} else {
			retString = "Error";
		}
		return retString;
	}
}
