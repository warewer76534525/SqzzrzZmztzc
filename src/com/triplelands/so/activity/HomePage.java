package com.triplelands.so.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.triplelands.so.R;
import com.triplelands.so.service.PositionRetrieverService;

public class HomePage extends Activity { //implements LocationListener 
	
	private Button btnSwitch, btnLogOff;
	private TextView txtStatus;
	private SharedPreferences appPreference;
	private boolean isRunning;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		appPreference =  PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
		
		isRunning = appPreference.getBoolean("status", false);
		btnSwitch = (Button)findViewById(R.id.btnSwitch);
		btnLogOff = (Button)findViewById(R.id.btnLogout);
		txtStatus = (TextView)findViewById(R.id.txtStatus);
		btnSwitch.setOnClickListener(new ButtonClickListener());
		btnLogOff.setOnClickListener(new ButtonClickListener());
		txtStatus.setText((isRunning) ? "Service is running" : "Service is stopped");
	}
	
	private class ButtonClickListener implements OnClickListener {
		public void onClick(View v) {
			if(v == btnSwitch){
				if(isRunning){
//					stopAlarmManager();
					isRunning = false;
				} else {
//					new PositionScheduler(getApplicationContext()).schedule(60000);
					isRunning = true;
				}
				getApplicationContext().startService(new Intent(getApplicationContext(), PositionRetrieverService.class));
				btnSwitch.setText((isRunning) ? R.string.strSwitchOff : R.string.strSwitchOn);
				updateStatus(isRunning);
			} else if (v == btnLogOff){
				stopAlarmManager();
				logOut();
				finish();
			}
		}
	};
	
	private void updateStatus(boolean status) {
		txtStatus.setText((isRunning) ? "Service is running" : "Service is stopped");
		SharedPreferences.Editor editor = appPreference.edit();
        editor.putBoolean("status", status);
        editor.commit();
	}
	
	private void stopAlarmManager(){
		Log.i("ALARM", "CANCELLING ALARM MANAGER");
		Intent intent = new Intent("com.triplelands.so.START_UPDATE_LOCATION");
		PendingIntent senderIntent = PendingIntent.getBroadcast(getApplicationContext(),
				0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(senderIntent);
	}
	
	private void logOut(){
		SharedPreferences.Editor editor = appPreference.edit();
        editor.putString("actk", "");
        editor.putBoolean("status", false);
        editor.putLong("latitude", 0);
        editor.putLong("longitude", 0);
        editor.commit();
	}
}
