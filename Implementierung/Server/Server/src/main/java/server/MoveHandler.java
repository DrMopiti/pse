package server;



import com.example.user.schachapp.chessLogic.BoardState;
import com.example.user.schachapp.chessLogic.ChessRuleProvider;
import com.example.user.schachapp.chessLogic.Move;
import com.example.user.schachapp.chessLogic.MoveFactory;
import com.example.user.schachapp.chessLogic.Position;
import com.example.user.schachapp.chessLogic.RuleProvider;

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
		Move move = MoveFactory.getMove(this.move);
		if(ruler.isLegalMove(move, board)) {
			board.applyMove(move);
		} else {
			return "IllegalMove";
		}
		retString = handler.saveGame(player, board.toString());
		return retString;
	}
}
