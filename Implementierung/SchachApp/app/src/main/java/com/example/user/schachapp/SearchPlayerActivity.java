package com.example.user.schachapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The type Search player activity.
 * This Activity opens the SearchPlayerActivity, when the player clicks the button Search-Player.
 * There you can search a player an challenge him to a new game.
 */
public class SearchPlayerActivity extends AppCompatActivity {

    private ListView listView;
    private SearchPlayerListViewAdapter adapter;
    private ArrayList<String> arrayList = new ArrayList<String>();
    private ClientSocket cs;
    private SharedPreferences sharedPrefs;
    private String user;
    private ThreadHandler threadHandler = new ThreadHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_player);
        sharedPrefs = getSharedPreferences("chessApp", 0);
        user = sharedPrefs.getString("Username", "NoUser");
        System.out.println("Service start");
        startService(new Intent(this, WebsocketService.class));
        // Search-Bar-Title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Spieler suchen");
        listView = findViewById(R.id.listView);

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backClicked();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://sdq-pse-gruppe1.ipd.kit.edu/server/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClientApi clientApi = retrofit.create(ClientApi.class);

        Call<Set<String>> call = clientApi.getPlayers();
        call.enqueue(new Callback<Set<String>>() {
            @Override
            public void onResponse(Call<Set<String>> call, Response<Set<String>> response) {
                if(response.isSuccessful()) {
                    arrayList.remove(response.body().remove("NoUser"));
                    arrayList.remove(response.body().remove(user));
                    arrayList.addAll(response.body());
                    adapter = new SearchPlayerListViewAdapter(SearchPlayerActivity.this, arrayList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Set<String>> call, Throwable t) {
                Log.d("Failure", "Failure :(");
                arrayList.add("Ruki");
                arrayList.add("Tim");
                arrayList.add("Daniel");
                arrayList.add("Orkhan");
                adapter = new SearchPlayerListViewAdapter(SearchPlayerActivity.this, arrayList);
                listView.setAdapter(adapter);
            }
        });
    }

    private void backClicked() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    // generates the list and gets form the ListViewAdapter the filter.

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_icon, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)){
                    adapter.filter("");
                    listView.clearTextFilter();
                }
                else {
                    adapter.filter(s);
                }
                return true;
            }
        });
        return true;
    }

}
