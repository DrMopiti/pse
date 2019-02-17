package com.example.user.schachapp;

import java.util.Set;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ClientApi {
    @GET("board/{user}")
    Call<String> getBoard(@Path("user") String user);

    @GET("move/{user}/{move}")
    Call<String> sendMove(@Path("user") String user, @Path("move") String move);

    @GET("newgame/{white}/{black}")
    Call<String> newGame(@Path("white") String white, @Path("black") String black);

    @GET("players")
    Call<Set<String>> getPlayers();

    @GET("isonline/{player}")
    Call<Boolean> isOnline(@Path("player") String player);

    @GET("delete/{player}")
    Call<String> deleteGame(@Path("player")String player);
}
