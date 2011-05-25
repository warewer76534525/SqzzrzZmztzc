package com.triplelands.so.activity;

import java.lang.reflect.Type;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.myjson.reflect.TypeToken;
import com.triplelands.so.R;
import com.triplelands.so.model.Location;
import com.triplelands.so.service.PositionSender;
import com.triplelands.so.utils.JsonUtils;
import com.triplelands.so.view.LocationAdapter;

public class NearbyHistoryActivity extends Activity {
	private ListView lvLocation;
	private TextView txtNothing;
	private LocationAdapter adapter;
	private SharedPreferences appPreference;
	private List<Location> listLocation;
	private String json;
	private NotificationManager _mNotificationManager;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_list_page);
		
		appPreference =  PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
		lvLocation = (ListView) findViewById(R.id.lvLocation);
		txtNothing = (TextView) findViewById(R.id.txtNothing);
		adapter = new LocationAdapter(this);
		
		lvLocation.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				Location loc = (Location)view.getTag();
				showCheckinDialog(loc);
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		_mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		_mNotificationManager.cancelAll();
		
		json = appPreference.getString("history", "");
		Type listType = new TypeToken<List<Location>>(){}.getType();
		listLocation = JsonUtils.toListObject2(json, listType);
		if(listLocation != null && listLocation.size() > 0 && isAlarmRunning()){
				adapter.setList(listLocation);
				lvLocation.setAdapter(adapter);
				hideMessage();
		} else {
			lvLocation.setAdapter(null);
			setMessage("No history spots near you.");
		}
	}
	
	private boolean isAlarmRunning(){
		Intent intent = new Intent("com.triplelands.so.START_UPDATE_LOCATION");
		return PendingIntent.getBroadcast(getApplicationContext(),
				0, intent, PendingIntent.FLAG_NO_CREATE) != null;
	}
	
	private void setMessage(String message){
		txtNothing.setText(message);
		txtNothing.setVisibility(View.VISIBLE);
		Typeface font = Typeface.createFromAsset(this.getAssets(), "aescrawl.ttf");  
		txtNothing.setTypeface(font);
	}
	
	private void hideMessage(){
		txtNothing.setVisibility(View.GONE);
	}
	
	private void showCheckinDialog(final Location loc){
		new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_info)
			.setTitle("Venue")
			.setMessage(loc.getName())
			.setNeutralButton("Chek In",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dlg, int sumthin) {
						String token = appPreference.getString("actk", "");
						String url = "http://202.51.96.41/som/checkin.php?placeid=" + loc.getId() + "&actk=" + token;
						final PositionSender sender = new PositionSender(url, NearbyHistoryActivity.this, null);
						Thread t = new Thread(){
							public void run() {
								sender.start();
							}
						};
						t.setPriority(Thread.MAX_PRIORITY);
						t.start();
					}
				})
			.show();
	}
	
}
