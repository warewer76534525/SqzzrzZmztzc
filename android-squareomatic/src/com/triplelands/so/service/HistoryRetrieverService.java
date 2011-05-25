package com.triplelands.so.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

public class HistoryRetrieverService extends Service {

	private SharedPreferences appPreference;
	private double latitude;
	private double longitude;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("RETRIEVING", "START RETRIEVE HISTORY");
		
		Location loc = intent.getExtras().getParcelable("location");
		onPositionRetrieved(loc);
		return super.onStartCommand(intent, flags, startId);
	}
	
	public void onPositionRetrieved(Location location) {
		Log.i("INITIAL SENDER", "PREPARE GET HISTORY");
		appPreference =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		latitude = Double.parseDouble(appPreference.getString("latitude", "0"));
		longitude = Double.parseDouble(appPreference.getString("longitude", "0"));
		
		Location latestLocation = new Location(LocationManager.NETWORK_PROVIDER);
		latestLocation.setLatitude(latitude);
		latestLocation.setLongitude(longitude);
		
//		Log.i("LOCATION LATEST", "latitude:" + latitude + ", longitude:" + longitude);
//		Log.i("LOCATION", "latitude:" + location.getLatitude() + ", longitude:" + location.getLongitude());
		
		if(location.getLatitude() != 0 || location.getLongitude() != 0){
			if(location.distanceTo(latestLocation) > 1000){
				Log.i("LOCATION", "LOCATION CHANGES");
				updateLatestLocation(location.getLatitude(), location.getLongitude());
				String token = appPreference.getString("actk", "");
				
				String url = "http://202.51.96.41/som/history.php?lat=" + location.getLatitude() + "&long=" + location.getLongitude() + "&actk=" + token;
				final HistoryReceiver receiver = new HistoryReceiver(url, HistoryRetrieverService.this);
				Thread t = new Thread(){
					public void run() {
						receiver.start();
					}
				};
				t.setPriority(Thread.MAX_PRIORITY);
				t.start();
				
			} else {
				Log.i("LOCATION", "LOCATION NOT CHANGED. STOPPING SERVICE. WAITING FOR NEXT ALARM");
				stopSelf();
			}
		} else {
			Log.i("LOCATION 0", "latitude and longitude 0");
		}
	}
	
	private void updateLatestLocation(double updateLatitude, double updateLongitude){
		SharedPreferences.Editor editor = appPreference.edit();
        editor.putString("latitude", "" + updateLatitude);
        editor.putString("longitude", "" + updateLongitude);
        editor.commit();
	}

}
