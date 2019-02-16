package server;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.Assert;

/**
 * 
 * @author Daniel Helmig
 * 
 * Test class for BoardHandler
 * 
 */
public class TestBoardHandler {
	
	
	@Mock
	DatabaseHandler handler = mock(FirebaseHandler.class);
	
	@InjectMocks
	BoardHandler bh;
	
	/**
	 * Setup
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Player is in a game
	 */
	@Test
	public void trueTest() {
		bh = new BoardHandler("player", handler);
		when(handler.hasActiveGame("player")).thenReturn(true);
		when(handler.loadGame("player")).thenReturn("myBoardString");
		Assert.assertEquals("myBoardString", bh.getBoard());
	}
	
	/**
	 * Player is not in a game
	 */
	@Test
	public void falseTest() {
		bh = new BoardHandler("player", handler);
		when(handler.hasActiveGame("player")).thenReturn(false);
		Assert.assertEquals("Error", bh.getBoard());
	}
}
