package com.example.user.schachapp.com.example.user.schachapp.chessLogic;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.drafts.Draft_6455;

public class ClientSocket {

	private String url = "127.0.0.1:370";
	private String user = "";

	public ClientSocket(String user) {
		this.user = user;
	}

	private String getUser() {
		return this.user;
	}

	private String getUrl() {
		return this.url;
	}

	public void connectToWS() {
		WebSocketClient mWs;
		try {
			mWs = new WebSocketClient(new URI( getUrl() +"/socket" ), new Draft_6455())
			{
				@Override
				public void onMessage(String message) {
					requestBoard();
				}

				@Override
				public void onOpen(ServerHandshake handshake) {
					//do nothing
				}

				@Override
				public void onClose(int code, String reason, boolean remote) {
					try {
						Thread.sleep(500);
						connectToWS();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onError(Exception ex) {
					try {
						Thread.sleep(500);
						connectToWS();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			};

			mWs.connect();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}


	}

	public String requestBoard() {
		String board = "";
		board = connect(getUrl() + "/board/" + getUser());
		return board;
	}

	public String sendMove(String move) {
		String success = "";
		success = connect(getUrl() + "/move/" + getUser() + "/" + move);
		return success;

	}

	public String newGame(String opponent) {
		String success = "";
		success = connect(getUrl()  +"/newGame/" + getUser() + "/" + opponent);
		return success;
	}

	public String connect(String targetURL) {
		HttpURLConnection connection = null;
		try {

			//Create connection
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			//Send request
			DataOutputStream wr = new DataOutputStream (
					connection.getOutputStream());
			wr.close();

			//Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

}