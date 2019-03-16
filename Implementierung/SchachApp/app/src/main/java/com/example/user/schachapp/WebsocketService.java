package com.example.user.schachapp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

public class WebsocketService extends IntentService{

	public enum State {CONNECTED, DISCONNECTED, MOVE}

	public static State state = State.DISCONNECTED;

	private static final String url = "wss://sdq-pse-gruppe1.ipd.kit.edu/server/socket";

	//private static final String url = "ws://192.168.2.111:8080/socket";

	private WebSocketClient mWs;
	private Context context;
	public WebsocketService() {
		super("WSService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		while (true) {

			switch (state) {
				case DISCONNECTED:
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					connectToWS();
					break;
				case MOVE:
					System.out.println("new move found");
					sendMessage();
					state = State.CONNECTED;
					break;
				case CONNECTED:
					break;
				default:
					state = State.DISCONNECTED;
					break;
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

	private void sendMessage()  {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Intent intent = new Intent("move");
		String message;
		String name = getSharedPreferences("chessApp", 0).getString("Username", "");
		try {
			message = new GetBoardTask().execute(name).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			message = "Error";
		}
		intent.putExtra("message", message);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
		System.out.println("Sent +++   "+ message  );
	}



}
