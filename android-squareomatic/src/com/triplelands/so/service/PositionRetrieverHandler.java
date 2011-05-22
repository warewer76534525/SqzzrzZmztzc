package com.triplelands.so.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

public class PositionRetrieverHandler extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
		Log.i("RETRIEVING", "POSITION RETRIEVER RECEIVER");
		Location loc = intent.getExtras().getParcelable("location");
		if(loc != null){
//			Intent i = new Intent(context, PositionSenderService.class);
//			i.putExtra("location", loc);
//			context.startService(i);
			Intent i = new Intent(context, HistoryRetrieverService.class);
			i.putExtra("location", loc);
			context.startService(i);			
		} else {
			Toast.makeText(context, "location null. waiting next alarm", Toast.LENGTH_SHORT).show();
		}
	}
}
