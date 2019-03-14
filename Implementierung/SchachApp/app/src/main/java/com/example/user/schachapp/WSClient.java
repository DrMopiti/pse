package com.example.user.schachapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import static okhttp3.internal.Internal.instance;

public class WSClient extends WebSocketClient {

	private String user;
	private Context context;

	public WSClient(URI serverUri, Draft protocolDraft, String user, Context context) {
		super(serverUri, protocolDraft);
		this.user = user;
		this.context = context;
	}

	@Override
	public void onMessage(String message) {
		Activity applicationContext = (Activity) context.getApplicationContext();
		applicationContext.recreate();
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
