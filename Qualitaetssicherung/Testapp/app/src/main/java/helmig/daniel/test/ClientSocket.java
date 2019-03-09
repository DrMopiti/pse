package helmig.daniel.test;


import android.support.annotation.WorkerThread;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.TreeSet;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.drafts.Draft_6455;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientSocket {

    private String url = "ws://sdq-pse-gruppe1.ipd.kit.edu/server/";
    private String user = "";
    private final ClientApi clientApi;

    Gson gson = new GsonBuilder().setLenient().create();


    public ClientSocket(String user) {
        this.user = user;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://sdq-pse-gruppe1.ipd.kit.edu/server/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        clientApi = retrofit.create(ClientApi.class);
    }

    private String getUser() {
        return this.user;
    }

    private String getUrl() {
        return this.url;
    }

    @WorkerThread
    public void connectToWS() {
        WebSocketClient mWs;
        try {
            System.out.println("Bitconnect");
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
                    System.out.println("CLose");
                    try {
                        Thread.sleep(5000);
                        connectToWS();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Exception ex) {
                    System.out.println("exception");
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

    @WorkerThread
    public String requestBoard(String user) {
        Call<String> ifSuccessCall = clientApi.getBoard(user);
        Response response = null;
        try {
            response = ifSuccessCall.execute();
            String ifSuccess = response.body().toString();
            return ifSuccess;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    @WorkerThread
    public String newGame(String whitePlayer, String blackPlayer) {
        Call<String> ifSuccessCall = clientApi.newGame(whitePlayer, blackPlayer);
        Response response = null;
        try {
            response = ifSuccessCall.execute();
            String ifSuccess = response.body().toString();
            return ifSuccess;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }

    }

    @WorkerThread
    public String sendMove(String user, String move) {
        Call<String> ifSuccessCall = clientApi.sendMove(user, move);
        Response response = null;
        try {
            response = ifSuccessCall.execute();
            String ifSuccess = response.body().toString();
            return ifSuccess;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }

    }

    @WorkerThread
    public Set<String> getPlayers() {
        Call<Set<String>> playerSetCall = clientApi.getPlayers();
        Response response = null;
        try {
            response = playerSetCall.execute();
            Set<String> playerSet = ((Set<String>) response.body());
            return playerSet;
        } catch (IOException e) {
            e.printStackTrace();
            return new TreeSet<>();

        }

    }

    @WorkerThread
    public boolean isOnline(String player) {
        Call<Boolean> isOnlineCall = clientApi.isOnline(player);
        Response response = null;
        try {
            response = isOnlineCall.execute();
            String boolString = response.body().toString();
            if (boolString.equals("true")) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @WorkerThread
    public String delete(String player) {
        Call<String> deleteCall = clientApi.deleteGame(player);
        Response response = null;
        try {
            response = deleteCall.execute();
            String ifSuccess = response.body().toString();
            return ifSuccess;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }

    }


    @WorkerThread
    public boolean hasGame(String player) {
        Call<Boolean> hasGameCall = clientApi.hasGame(player);
        Response response = null;
        try {
            response = hasGameCall.execute();
            String boolString = response.body().toString();
            if (boolString.equals("true")) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }




}