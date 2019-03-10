package helmig.daniel.test;


import android.support.annotation.WorkerThread;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;


import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientSocket {

    private final ClientApi clientApi;

    Gson gson = new GsonBuilder().setLenient().create();


    public ClientSocket() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://sdq-pse-gruppe1.ipd.kit.edu/server/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        clientApi = retrofit.create(ClientApi.class);
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