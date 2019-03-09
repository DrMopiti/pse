package com.example.user.schachapp;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WSClient extends WebSocketClient {

	private String user;
	public WSClient(URI serverUri, Draft protocolDraft, String user) {
		super(serverUri, protocolDraft);
		this.user = user;
	}

	@Override
	public void onMessage(String message) {
		System.out.println("STUFF");
	}

	@Override
	public void onOpen(ServerHandshake handshake) {
		WebsocketService.setState(WebsocketService.State.CONNECTED);
		send(user);
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		System.out.println("CLose:   " + reason);
		WebsocketService.setState(WebsocketService.State.DISCONNECTED);
	}

	@Override
	public void onError(Exception ex) {
		System.out.println("exception   " + ex.toString());
		WebsocketService.setState(WebsocketService.State.DISCONNECTED);
	}

}
