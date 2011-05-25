package com.triplelands.so.service;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.myjson.reflect.TypeToken;
import com.triplelands.so.R;
import com.triplelands.so.activity.MainScreen;
import com.triplelands.so.model.Location;
import com.triplelands.so.tools.InternetConnectionListener;
import com.triplelands.so.tools.InternetHttpConnection;
import com.triplelands.so.utils.JsonUtils;

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
	
	public void start() {
		Log.i("RECEIVE HISTORY", "RECEIVING HISTORY: " + url);
//		internetConnection.setAndAccessURL(url);
		internetConnection.get(url);
	}

	@Override
	public void onReceivedResponse(InputStream is, int length) {
	}
	
	@Override
	public void onReceivedBodyString(String body) {
		Log.i("HISTORY JSON", body);
		updateListHistory(body);
		fireNotification();
		Log.i("HISTORY", "HISTORY UPDATED: " + body);
		service.stopSelf();
	}

	private void updateListHistory(String data) {
		SharedPreferences.Editor editor = appPreference.edit();
        editor.putString("history", data);
        editor.commit();
	}
	
	private void fireNotification(){
		String json = appPreference.getString("history", "");
		Type listType = new TypeToken<List<Location>>(){}.getType();
		List<Location> listLocation = JsonUtils.toListObject2(json, listType);
		
		if(listLocation != null && listLocation.size() > 0){
			String alert = listLocation.size() + " spot(s) found!";
			NotificationManager _mNotificationManager;
			Notification _notifyDetails;
			_mNotificationManager = (NotificationManager) service.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
			_notifyDetails = new Notification(R.drawable.spots, alert, System.currentTimeMillis());

			long[] vibrate = { 100, 100, 200, 300 };
			_notifyDetails.vibrate = vibrate;
			_notifyDetails.defaults = Notification.DEFAULT_ALL;
			
			Intent notifyIntent = new Intent(service.getApplicationContext(), MainScreen.class);
			PendingIntent intent = PendingIntent.getActivity(service.getApplicationContext(), 0, notifyIntent, android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
			_notifyDetails.setLatestEventInfo(service.getApplicationContext(), "Spot(s) found!", listLocation.size() + " history spot(s) near you.", intent);
			_mNotificationManager.notify(0, _notifyDetails);
			
		} 
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
