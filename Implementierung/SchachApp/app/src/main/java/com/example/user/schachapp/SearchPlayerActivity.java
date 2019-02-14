package com.example.user.schachapp;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * The type Search player activity.
 * This Activity opens the SearchPlayerActivity, when the player clicks the button Search-Player.
 * There you can search a player an challenge him to a new game.
 */
public class SearchPlayerActivity extends AppCompatActivity {

    private ListView listView;
    private SearchPlayerListViewAdapter adapter;
    private String[] title;
    private ArrayList<String> arrayList = new ArrayList<String>();
    private ClientSocket cs;
    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_player);
        sharedPrefs = getSharedPreferences("chessApp", 0);
        cs = new ClientSocket(sharedPrefs.getString("Username", ""));

        // Search-Bar-Title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Spieler suchen");

        // a set, where all Player are.
        Set<String> players;
        players = cs.getPlayers();//new HashSet<String>();
       // players.add("Ruki");
       // players.add("Tim");
       // players.add("Daniel");
       // players.add("Orkhan");
        Iterator<String> it = players.iterator();

        while (it.hasNext()) {
            arrayList.add(it.next());
        }

        listView = findViewById(R.id.listView);

        //pass results to listViewAdapter class
        adapter = new SearchPlayerListViewAdapter(this, arrayList);

        //bind the adapter to the listview
        listView.setAdapter(adapter);
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
