package com.example.user.schachapp;

import android.os.AsyncTask;

public class SendMoveTask extends AsyncTask<String, Integer, String> {


	@Override
	protected String doInBackground(String... strings) {
		ClientSocket cs = new ClientSocket(strings[0]);
		return cs.sendMove(strings[0], strings [1]);
	}
}
