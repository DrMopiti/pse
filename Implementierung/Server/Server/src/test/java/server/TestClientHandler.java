package server;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Assert;
import org.junit.Before;

import static org.mockito.Mockito.*;

public class TestClientHandler {
	
	@InjectMocks
	ClientHandler cl;
	
	@Mock 
	GameCreator creator;
	
	@Before
	public void setUp() {
		cl = new ClientHandler();
	}
	@Test
	public void testCreateGame() {
		GameCreator creator = mock(GameCreator.class);
		when(creator.create()).thenReturn("Success");
		Assert.assertEquals("Success", cl.createGame("white", "black"));
		
		
		
	}

}
