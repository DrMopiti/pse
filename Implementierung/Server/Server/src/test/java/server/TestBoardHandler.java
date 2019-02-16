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
 * Useless test class
 * 
 */
public class TestBoardHandler {
	
	
	@Mock
	DatabaseHandler handler = mock(FirebaseDummy.class);
	
	@InjectMocks
	BoardHandler bh;
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void trueTest() {
		bh = new BoardHandler("player", handler);
		when(handler.hasActiveGame("player")).thenReturn(true);
		when(handler.loadGame("player")).thenReturn("myBoardString");
		Assert.assertEquals("myBoardString", bh.getBoard());
	}
	
	@Test
	public void falseTest() {
		bh = new BoardHandler("player", handler);
		when(handler.hasActiveGame("player")).thenReturn(false);
		Assert.assertEquals("Error", bh.getBoard());
	}
}
