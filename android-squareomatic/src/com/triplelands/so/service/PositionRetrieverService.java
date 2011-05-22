package com.triplelands.so.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class PositionRetrieverService extends Service {

	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("RETRIEVING", "START RETRIEVE POSITION");
		// new PositionRetriever(this.getApplicationContext(), this).execute();
		PositionRetriever retriever = new PositionRetriever(
				this.getApplicationContext(), this);
		retriever.start();
		return super.onStartCommand(intent, flags, startId);
	}
}
