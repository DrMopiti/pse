package com.example.user.schachapp;

import android.os.AsyncTask;

import java.util.Set;

public class GetPlayersTask extends AsyncTask<String, Integer, Set<String>> {


	@Override
	protected Set<String> doInBackground(String... strings) {
		ClientSocket cs = new ClientSocket();
		Set<String> set = cs.getPlayers();
		return set;
	}
}
