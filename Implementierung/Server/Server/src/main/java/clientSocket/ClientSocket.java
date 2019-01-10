package clientSocket;

public class ClientSocket {
	
	private String user = "";
	
	ClientSocket(String user) {
		this.user = user;
	}
	
	private String getUser() {
		return this.user;
	}
	
	public void connectToWS() {
		
	}
	
	public String requestBoard() {
		String board = "";
		return board;
		
	}
	
	public String sendMove(String move) {
		String success = "";
		return success;
		
	}
	
	public String newGame(String opponent) {
		String success = "";
		return success;
	}
	
}
