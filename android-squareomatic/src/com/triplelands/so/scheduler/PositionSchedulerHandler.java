package com.triplelands.so.scheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.triplelands.so.service.PositionRetrieverService;

public class PositionSchedulerHandler extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
		Log.i("BROADCAST ALARM RECEIVED", "BROADCAST ALARM RECEIVED, STARTING SERVICE");
//		Toast.makeText(context, "ALARM RECEIVED", Toast.LENGTH_SHORT).show();
		context.getApplicationContext().startService(new Intent(context.getApplicationContext(), PositionRetrieverService.class));
	}
}
