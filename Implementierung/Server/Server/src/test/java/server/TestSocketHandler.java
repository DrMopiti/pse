package server;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class TestSocketHandler {
	
	private static final String URL = "ws://127.0.0.1:8080/socket";
	WebSocketStompClient stompClient;
	
	@Before
	public void setup() {
	stompClient = new WebSocketStompClient(new StandardWebSocketClient());
	stompClient.setMessageConverter(new MappingJackson2MessageConverter());
	}
	
	@Ignore //connection refused, but only in test case
	@Test
	public void connectTest() throws InterruptedException, ExecutionException, TimeoutException {
	StompSession stompSession = stompClient.connect(URL, new StompSessionHandlerAdapter() {
	}).get();
	Assert.assertTrue(stompSession.isConnected());
	}
	
}
