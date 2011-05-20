package com.triplelands.so.scheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PositionScheduler {

	private AlarmManager alarmManager;
	private Context context;
	
	public PositionScheduler(Context context) {
		this.context = context;
		alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	}
	
	public void schedule(long interval){
		Log.i("SCHEDULE ", "Start Alarm repeat every: " + interval);
//		Intent intent = new Intent(context, PositionSchedulerHandler.class);
		Intent intent = new Intent("com.triplelands.so.START_UPDATE_LOCATION");
		
		PendingIntent senderIntent = PendingIntent.getBroadcast(context,
				0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, senderIntent);
	}
}
