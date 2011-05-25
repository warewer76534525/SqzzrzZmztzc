package com.triplelands.so.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.triplelands.so.R;
import com.triplelands.so.scheduler.PositionScheduler;

public class SettingActivity extends Activity { //implements LocationListener 
	
	private Button btnLogOff, btnTellFriend, btnAbout;
	private ImageView btnSwitch;
	private TextView txtStatus;
	private SharedPreferences appPreference;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preference);
		appPreference =  PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
		
		btnSwitch = (ImageView)findViewById(R.id.btnSwitch);
		btnLogOff = (Button)findViewById(R.id.btnLogout);
		btnTellFriend = (Button)findViewById(R.id.btnTellFriend);
		btnAbout = (Button)findViewById(R.id.btnAboutUs);
		txtStatus = (TextView)findViewById(R.id.txtStatus);
		btnSwitch.setOnClickListener(new ButtonClickListener());
		btnLogOff.setOnClickListener(new ButtonClickListener());
		btnAbout.setOnClickListener(new ButtonClickListener());
		btnTellFriend.setOnClickListener(new ButtonClickListener());
		Typeface font = Typeface.createFromAsset(this.getAssets(), "aescrawl.ttf");  
		txtStatus.setTypeface(font);
		btnLogOff.setTypeface(font);
		btnTellFriend.setTypeface(font);
		btnAbout.setTypeface(font);
	}
	
	protected void onResume() {
		updateButtonandStatus();
		super.onResume();
	}
	
	private class ButtonClickListener implements OnClickListener {
		public void onClick(View v) {
			if(v == btnSwitch){
				if(isAlarmRunning()){
					stopAlarmManager();
				} else {
					new PositionScheduler(getApplicationContext()).schedule(1500000);
				}
				updateButtonandStatus();
			} else if (v == btnLogOff){
				stopAlarmManager();
				logOut();
				finish();
			} else if (v == btnTellFriend){
				Intent i = new Intent(android.content.Intent.ACTION_SEND);
				i.setType("text/plain");
				i.putExtra(Intent.EXTRA_SUBJECT, "SquareOmatic");
				i.putExtra(Intent.EXTRA_TEXT, "Hey! Download SquareOmatic https://market.android.com/details?id=com.triplelands.so . Foursquare checkin getting easier! Enjoy!");
				startActivity(Intent.createChooser(i, "Tell Friend via"));
			} else if (v == btnAbout) {
				new AboutUsDialog(SettingActivity.this).show();
			}
		}
	};
	
	private void updateButtonandStatus(){
		btnSwitch.setImageResource((isAlarmRunning()) ? R.drawable.button_off_selector : R.drawable.button_on_selector);
		txtStatus.setText((isAlarmRunning()) ? "Service is RUNNING. Tap the button to turn off." : "Service is STOPPED. Tap the button to turn on.");
	}
	
//	private void clearLocationHistory() {
//		Log.i("CLEANING", "CLEANING HISTORY");
//		SharedPreferences.Editor editor = appPreference.edit();
//        editor.putString("history", "");
//        editor.putString("latitude", "0");
//        editor.putString("longitude", "0");
//        editor.commit();
//	}
	
	private void stopAlarmManager(){
		Log.i("ALARM", "CANCELLING ALARM MANAGER");
		Intent intent = new Intent("com.triplelands.so.START_UPDATE_LOCATION");
		PendingIntent senderIntent = PendingIntent.getBroadcast(getApplicationContext(),
				0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(senderIntent);
		senderIntent.cancel();
	}
	
	private boolean isAlarmRunning(){
		Intent intent = new Intent("com.triplelands.so.START_UPDATE_LOCATION");
		return PendingIntent.getBroadcast(getApplicationContext(),
				0, intent, PendingIntent.FLAG_NO_CREATE) != null;
	}
	
	private void logOut(){
		SharedPreferences.Editor editor = appPreference.edit();
        editor.putString("actk", "");
        editor.putBoolean("status", false);
        editor.putLong("latitude", 0);
        editor.putLong("longitude", 0);
        editor.putString("history", "");
        editor.commit();
	}

}
