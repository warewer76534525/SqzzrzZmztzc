package com.triplelands.so.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class PositionSenderService extends Service {

	private SharedPreferences appPreference;
	private long latitude, longitude;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Location loc = intent.getExtras().getParcelable("location");
		onPositionRetrieved(loc);
		return super.onStartCommand(intent, flags, startId);
	}
	
	public void onPositionRetrieved(Location location) {
		Log.i("INITIAL SENDER", "PREPARE SENDING");
		appPreference =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		latitude = appPreference.getLong("latitude", 0);
		longitude = appPreference.getLong("longitude", 0);
		
		Location latestLocation = new Location(LocationManager.GPS_PROVIDER);
		latestLocation.setLatitude(latitude);
		latestLocation.setLongitude(longitude);
		
		Log.i("LOCATION", "latitude:" + latitude + ", longitude:" + longitude);
		Log.i("LOCATION LATEST", "latitude:" + location.getLatitude() + ", longitude:" + location.getLongitude());
		
		if(location.getLatitude() != 0 || location.getLongitude() != 0){
			Log.i("LOCATION", "latitude longitude");
			Toast.makeText(getApplicationContext(), "Distance: " + location.distanceTo(latestLocation), Toast.LENGTH_LONG).show();
			if(location.distanceTo(latestLocation) > 1000){
				Log.i("LOCATION", "LOCATION DISTANCE > 1000");
				updateLatestLocation(location.getLatitude(), location.getLongitude());
				String token = appPreference.getString("actk", "");
				String url = "http://squareomatic.triplelands.com/checkin.php?lat=" + location.getLatitude() + "&long=" + location.getLongitude() + "&actk=" + token;
				Toast.makeText(getApplicationContext(), "checkin: " + url, Toast.LENGTH_LONG).show();				
				PositionSender sender = new PositionSender(url, this);
				sender.start();
			} else {
				Log.i("LOCATION", "LOCATION DISTANCE < 1000. STOPPING SERVICE. WAITING FOR NEXT ALARM");
				Toast.makeText(getApplicationContext(), "have not moved.", Toast.LENGTH_SHORT).show();
				stopSelf();
			}
		} else {
			Log.i("LOCATION 0", "latitude and longitude 0");
		}
	}
	
	private void updateLatestLocation(double updateLatitude, double updateLongitude){
		SharedPreferences.Editor editor = appPreference.edit();
        editor.putLong("latitude", (long)updateLatitude);
        editor.putLong("longitude", (long)updateLongitude);
        editor.commit();
	}
}
