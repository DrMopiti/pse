package server;
/**
 * 
 * @author Daniel Helmig
 *	The GameCreator class create new game entries in the database
 */
public class GameCreator {
	private String whitePlayer;
	private String blackPlayer;
	
	public GameCreator(String whitePlayer, String blackPlayer) {
		this.whitePlayer = whitePlayer;
		this.blackPlayer = blackPlayer;
	}
	
	/**
	 * Checks if one player is already playing and creates a new game 
	 * if both players are available
	 * @return Returns if the creation was successful
	 */
	public String create() {
		String retString;
		DatabaseHandler handler = FirebaseHandler.getHandler();
		boolean whiteActiveGame = handler.hasActiveGame(whitePlayer);
		boolean blackActiveGame = handler.hasActiveGame(blackPlayer);
		if (whiteActiveGame || blackActiveGame) {
			retString = "Error";
			//System.out.println("DEBUG: PLAYER IS IN GAME");
		} else {
			handler.newEntry(whitePlayer, blackPlayer);
			retString = "Success";
			//System.out.println("DEBUG: PLAYER NOT IN GAME");
		}
		return retString;
	}
}
