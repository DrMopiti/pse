package com.example.user.schachapp;

import android.os.AsyncTask;

public class GetBoardTask extends AsyncTask<String, Integer, String> {


	@Override
	protected String doInBackground(String... strings) {
		ClientSocket cs = new ClientSocket(strings[0]);
		return cs.requestBoard(strings[0]);
	}
}
