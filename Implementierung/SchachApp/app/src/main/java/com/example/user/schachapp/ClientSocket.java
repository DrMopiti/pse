package com.example.user.schachapp;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.drafts.Draft_6455;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientSocket {

    private String url = "ws://sdq-pse-gruppe1.ipd.kit.edu/server/";
    private String user = "";
    private final ClientApi clientApi;
    private final ThreadHandler threadHandler = new ThreadHandler();



    public ClientSocket(String user) {
        this.user = user;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://sdq-pse-gruppe1.ipd.kit.edu/server/")
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
            mWs = new WebSocketClient(new URI( getUrl() +"socket" ), new Draft_6455())
            {
                @Override
                public void onMessage(String message) {
                    String board = requestBoard(user);
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    send(user);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    try {
                        Thread.sleep(5000);
                        connectToWS();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Exception ex) {
                    try {
                        Thread.sleep(5000);
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

    public String requestBoard(String user) {
        final StringAnswer ret = new StringAnswer();
        Call<String> call = clientApi.getBoard(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                 ret.setAnswer(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
        return ret.getAnswer();
    }

    public String newGame(String whitePlayer, String blackPlayer) {
        Call<String> ifSuccessCall = clientApi.newGame(whitePlayer, blackPlayer);
        Response response = null;
        try {
            response = ifSuccessCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String ifSuccess = response.body().toString();
        return ifSuccess;
    }

    public String sendMove(String user, String move) {
        Call<String> ifSuccessCall = clientApi.sendMove(user, move);
        Response response = null;
        try {
            response = ifSuccessCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String ifSuccess = response.body().toString();
        return ifSuccess;
    }

    public Set<String> getPlayers() {
        Call<Set<String>> playerSetCall = clientApi.getPlayers();
        Response response = null;
        try {
            response = playerSetCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<String> playerSet = ((Set<String>) response.body());
        return playerSet;
    }

    public boolean isOnline(String player) {
        Call<Boolean> isOnlineCall = clientApi.isOnline(player);
        Response response = null;
        try {
            response = isOnlineCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String boolString = response.body().toString();
        if (boolString.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public interface SuccessCallback {
        void notifySuccessful(String message);
    }



}