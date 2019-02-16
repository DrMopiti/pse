package server;



import com.example.user.schachapp.chessLogic.BoardState;
import com.example.user.schachapp.chessLogic.ChessRuleProvider;
import com.example.user.schachapp.chessLogic.Move;
import com.example.user.schachapp.chessLogic.MoveFactory;
import com.example.user.schachapp.chessLogic.Position;
import com.example.user.schachapp.chessLogic.RuleProvider;

/**
 * 
 * @author Daniel Helmig
 * The MoveHandler class checks if a move is legal, applies it
 * and writes the new board in the database
 * 
 *
 */
public class MoveHandler {
	private String player;
	private String move;
	private DatabaseHandler handler;
	
	public MoveHandler(String player, String move, DatabaseHandler handler) {
		this.player = player;
		this.move = move;
		this.handler = handler;
	}
	/**
	 * Checks if the player that wants to send a move is actually in a game
	 * If he is in a game, processMove checks if the move is valid
	 * @return Returns "Success" if the operation was successful and other messages on failure
	 */
	public String processMove() {
		String retString = "";
		String boardString="";
		if (handler.hasActiveGame(player)) {
			boardString = handler.loadGame(player);
		} else {
			return "Error";
		}
		BoardState board = new BoardState(boardString);
		RuleProvider ruler = new ChessRuleProvider();
		Move move = MoveFactory.getMove(this.move);
		if(ruler.isLegalMove(move, board)) {
			board.applyMove(move);
		} else {
			//System.out.println("DEBUG: ILLEGAL MOVE");
			return "IllegalMove";
		}
		retString = handler.saveGame(player, board.toString());
		return retString;
	}
}
