package server;

import com.example.user.schachapp.BoardState;
import com.example.user.schachapp.Move;

public class MoveHandler {
	private Move move;
	private BoardState board;
	
	public MoveHandler(BoardState board) {
		this.board = board;
	}
	public BoardState processMove(Move move, BoardState board) {
		return null;
	}
}
