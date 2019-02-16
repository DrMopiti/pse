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
 * Test class for GameCreator
 *
 */
public class TestGameCreator {
	
	@Mock
	DatabaseHandler handler = mock(FirebaseHandler.class);
	
	@InjectMocks
	GameCreator gc;
	
	/**
	 * Setup
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * No player is in a game
	 */
	@Test
	public void bothFalseTest() {
		gc = new GameCreator("white","black", handler);
		when(handler.hasActiveGame("white")).thenReturn(false);
		when(handler.hasActiveGame("black")).thenReturn(false);
		Assert.assertEquals("Success", gc.create());
	}
	
	/**
	 * Only the black player is in a game
	 */
	@Test
	public void whiteFalseBlackTrueTest() {
		gc = new GameCreator("white","black", handler);
		when(handler.hasActiveGame("white")).thenReturn(false);
		when(handler.hasActiveGame("black")).thenReturn(true);
		Assert.assertEquals("Error", gc.create());
	}
	
	/**
	 * Only the white player is in a game
	 */
	@Test
	public void whiteTrueBlackFalseTest() {
		gc = new GameCreator("white","black", handler);
		when(handler.hasActiveGame("white")).thenReturn(true);
		when(handler.hasActiveGame("black")).thenReturn(false);
		Assert.assertEquals("Error", gc.create());
	}
	
	/**
	 * Both players are in a game
	 */
	@Test
	public void bothTrueTest() {
		gc = new GameCreator("white","black", handler);
		when(handler.hasActiveGame("white")).thenReturn(true);
		when(handler.hasActiveGame("black")).thenReturn(true);
		Assert.assertEquals("Error", gc.create());
	}

}
