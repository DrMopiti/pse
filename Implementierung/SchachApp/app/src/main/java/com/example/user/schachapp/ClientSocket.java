package com.example.user.schachapp;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.drafts.Draft_6455;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientSocket {

	private String url = "127.0.0.1:370";
	private String user = "";
	private final ClientApi clientApi;

	public ClientSocket(String user) {

	    this.user = user;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sdq-pse-gruppe1.ipd.kit.edu/server/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        clientApi = retrofit.create(ClientApi.class);
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
					requestBoard(user);
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

	public Call<String> requestBoard(String user) {
	    Call<String> board = clientApi.getBoard(user);
	    return board;
    }

    public Call<String> newGame(String whitePlayer, String blackPlayer) {
	    Call<String> ifSuccess = clientApi.newGame(whitePlayer, blackPlayer);
	    return ifSuccess;
    }

    public Call<String> sendMove(String user, String move) {
        Call<String> ifSuccess = clientApi.sendMove(user, move);
        return ifSuccess;
    }

    public Call<Set<String>> getPlayers() {
	    return clientApi.getPlayers();
    }

    public Call<Boolean> isOnline(String player) {
	    return clientApi.isOnline(player);
    }



}