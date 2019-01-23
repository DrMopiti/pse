package server;

import com.example.user.schachapp.BoardState;
import com.example.user.schachapp.ChessRuleProvider;
import com.example.user.schachapp.Move;
import com.example.user.schachapp.Position;
import com.example.user.schachapp.RuleProvider;

public class MoveHandler {
	private String player;
	private String move;
	
	public MoveHandler(String player, String move) {
		this.player = player;
		this.move = move;
	}
	
	public String processMove() {
		String retString = "";
		DatabaseHandler handler = FirebaseHandler.getHandler();
		String boardString="";
		if (handler.hasActiveGame(player)) {
			boardString = handler.loadGame(player);
		} else {
			return "Error";
		}
		BoardState board = new BoardState(boardString);
		RuleProvider ruler = new ChessRuleProvider();
		//Move move = MoveFactory.getMove(this.move);
		Move move = new Move(new Position(1,1), new Position(2,3));
		if(ruler.isLegalMove(move, board)) {
			board.applyMove(move);
		} else {
			return "IllegalMove";
		}
		retString = handler.saveGame(player, board.toString());
		return retString;
	}
}
