package com.triplelands.so.tools;

import java.io.InputStream;

import android.os.AsyncTask;
import android.util.Log;

public class IdSender extends AsyncTask<Void, String, Void> implements InternetConnectionListener {

	private String url;
	private InternetHttpConnection internetConnection;

	public IdSender(String url) {
		this.url = url;
		internetConnection = new InternetHttpConnection(this);
	}
	
	protected Void doInBackground(Void... params) {
		Log.i("CHECKIN", "CHECKING IN: " + url);
		internetConnection.setAndAccessURL(url);
		return null;
	}

	@Override
	public void onReceivedResponse(InputStream is, int length) {
	}

	@Override
	public void onConnectionError(Exception ex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionResponseNotOk() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionTimeout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCancelledConnection() {
		// TODO Auto-generated method stub
		
	}

}
