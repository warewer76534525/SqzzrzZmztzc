package com.triplelands.so.service;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class PositionRetriever implements LocationListener { // extends AsyncTask<Void, String, Void> implements LocationListener{

	private Context context;
	private PositionRetrieverService service;
	private LocationManager lm;
	
	public PositionRetriever(Context context, PositionRetrieverService service) {
		this.context = context;
		this.service = service;
	}
	
	public void start(){
		Log.i("START RETRIEVING", "START RETRIEVING THREAD");
		lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
	}

	@Override
	public void onLocationChanged(Location location) {
		if(location != null){
			Log.i("LOCATION CHANGED", "LOCATION RETRIEVED: " + location.getLatitude() + " " + location.getLongitude());
			
			Intent i = new Intent("com.triplelands.so.LOCATION_RETRIEVED");
			i.putExtra("location", location);
			service.getApplicationContext().sendBroadcast(i);
			lm.removeUpdates(this);
			service.stopSelf();
		} else {
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
		}
	}

	@Override
	public void onProviderDisabled(String provider) {		
	}

	@Override
	public void onProviderEnabled(String provider) {		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {		
	}

}
