package server;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.example.user.schachapp.chessLogic.BoardState;
import com.example.user.schachapp.chessLogic.ChessRuleProvider;
import com.example.user.schachapp.chessLogic.Move;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;

import static org.mockito.Mockito.*;
/**
 * 
 * @author Daniel Helmig
 * 
 * Test class for MoveHandler
 *
 */
public class TestMoveHandler {

	@Mock
	DatabaseHandler handler = mock(FirebaseHandler.class);
	@Mock
	Move move = mock(Move.class);
	@Mock
	ChessRuleProvider rp = mock(ChessRuleProvider.class);
	@Mock
	BoardState board = mock(BoardState.class);
	
	@InjectMocks
	MoveHandler mh;
	
	/**
	 * Setup
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Player is not in a game
	 */
	@Test
	public void noGameTest() {
		mh = new MoveHandler("player","move", handler);
		when(handler.hasActiveGame("player")).thenReturn(false);
		Assert.assertEquals("Error", mh.processMove());
	}
	
	/**
	 * Player sent an illegal move
	 * Doesnt work because the rule provider and the board are set in processMove
	 */
	@Ignore
	@Test
	public void illegalMoveTest() {
		mh = new MoveHandler("player","move", handler);
		when(handler.hasActiveGame("player")).thenReturn(true);
		when(handler.loadGame("player")).thenReturn(null);
		when(rp.isLegalMove(move, board)).thenReturn(false);
		Assert.assertEquals("IllegalMove", mh.processMove());
	}
	
	/**
	 * Player sent a legal move
	 * Doesnt work because the rule provider and the board are set in processMove
	 */
	@Ignore
	@Test 
	public void legalMoveTest() {
		mh = new MoveHandler("player", "move", handler);
		when(rp.getStartState()).thenCallRealMethod();
		board = rp.getStartState();
		when(handler.hasActiveGame("player")).thenReturn(true);
		when(handler.loadGame("player")).thenReturn(null);
		when(handler.saveGame("player", "board")).thenReturn("Success");
		when(rp.isLegalMove(move, board)).thenReturn(true);
		Assert.assertEquals("Success", mh.processMove());
	}
}
