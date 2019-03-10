package com.example.user.schachapp;

import android.app.IntentService;
import android.content.Intent;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class WebsocketService extends IntentService{

	public enum State {CONNECTED, DISCONNECTED}

	public static State state = State.DISCONNECTED;

	private static final String url = "wss://sdq-pse-gruppe1.ipd.kit.edu/server/socket";

	//private static final String url = "ws://192.168.2.111:8080/socket";

	private WebSocketClient mWs;

	public WebsocketService() {
		super("WSService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		while (true) {
			if (state == State.DISCONNECTED) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				connectToWS();
			}
		}
	}

	private static String getUrl() {
		return url;
	}

	public static void setState(State stateset) {
		state = stateset;
	}

	public void connectToWS() {
		try {
			String user = getSharedPreferences("chessApp", 0).getString("Username", "NoUser");
			System.out.println("Bitconnect");
			URI uri = new URI(getUrl());
			System.out.println(uri);
			mWs = new WSClient(uri, new Draft_6455(), user);
			mWs.connect();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}



}
