package com.example.rukidev.chess;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class RankingStatisticsActivity extends android.app.Activity {

    /**
     *Called when the activity is starting
     * @param savedInstanceState if the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState.
     * Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Initialize the contents of the Activity's standard options menu.
     * @param menu The options menu in which to place the items.
     * @return true for the menu to be displayed; false it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /**
     * Called whenever an item in the options menu is selected.
     * @param item The menu item that was selected.
     * @return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
