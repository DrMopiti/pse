package server;

/**
 * 
 * @author Daniel Helmig
 *	The BoardHandler class returns chessboards of given players
 */
public class BoardHandler {
	
	private String player;
	public BoardHandler(String player) {
		this.player = player;
	}
	/**
	 * getBoard loads the board from the database and returns the board string
	 * @return Returns the board string on success and "Error" on failure
	 */
	public String getBoard() {
		String retString;
		DatabaseHandler handler = FirebaseHandler.getHandler();
		if (handler.hasActiveGame(player)) {
			retString = handler.loadGame(player);
		} else {
			retString = "Error";
		}
		return retString;
	}
}
