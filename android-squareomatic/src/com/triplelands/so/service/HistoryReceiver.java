package com.triplelands.so.service;

import java.io.IOException;
import java.io.InputStream;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.triplelands.so.tools.InternetConnectionListener;
import com.triplelands.so.tools.InternetHttpConnection;

public class HistoryReceiver implements InternetConnectionListener {

	private String url;
	private HistoryRetrieverService service;
	private InternetHttpConnection internetConnection;
	private SharedPreferences appPreference;
	
	public HistoryReceiver(String url, HistoryRetrieverService service) {
		this.url = url;
		this.service = service;
		internetConnection = new InternetHttpConnection(this);
		appPreference =  PreferenceManager.getDefaultSharedPreferences(service.getApplicationContext());
	}
	
	public void start(){
		internetConnection.setAndAccessURL(url);
	}

	@Override
	public void onReceivedResponse(InputStream is, int length) {
		byte input[] = new byte[length];
		try {
			is.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String json = new String(input);
		updateListHistory(json);
		Log.i("HISTORY", "HISTORY UPDATED: " + json);
		service.stopSelf();
	}

	private void updateListHistory(String data) {
		SharedPreferences.Editor editor = appPreference.edit();
        editor.putString("history", data);
        editor.commit();
	}

	@Override
	public void onConnectionError(Exception ex) {
		service.stopSelf();
	}

	@Override
	public void onConnectionResponseNotOk() {
		service.stopSelf();
	}

	@Override
	public void onConnectionTimeout() {
		service.stopSelf();
	}

	@Override
	public void onCancelledConnection() {
		service.stopSelf();
	}

}
