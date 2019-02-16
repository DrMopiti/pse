package server;

public class FirebaseDummy implements DatabaseHandler {

	@Override
	public void newEntry(String whitePlayer, String blackPlayer) {}

	@Override
	public String loadGame(String player) {return null;}

	@Override
	public String saveGame(String player, String board) {return null;}

	@Override
	public String deleteGame(String player) {return null;}

	@Override
	public boolean hasActiveGame(String player) {return false;}

	@Override
	public String getOtherPlayer(String player) {return null;}

	@Override
	public void connect() {}

}
