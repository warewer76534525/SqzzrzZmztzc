package com.triplelands.so.activity;

import java.lang.reflect.Type;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
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
	private LocationAdapter adapter;
	private SharedPreferences appPreference;
	private List<Location> listLocation;
	private String json;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_list_page);
		
		appPreference =  PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
		lvLocation = (ListView) findViewById(R.id.lvLocation);
		adapter = new LocationAdapter(this);
		
		lvLocation.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				Location loc = (Location)view.getTag();
				String token = appPreference.getString("actk", "");
				String url = "http://squareomatic.triplelands.com/squareomatic_checkin.php?placeid=" + loc.getId() + "&actk=" + token;
				PositionSender sender = new PositionSender(url, NearbyHistoryActivity.this);
				sender.start();
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		json = appPreference.getString("history", "");
		Type listType = new TypeToken<List<Location>>(){}.getType();
		listLocation = JsonUtils.toListObject2(json, listType);
		if(listLocation != null && listLocation.size() > 0){
			adapter.setList(listLocation);
			lvLocation.setAdapter(adapter);
		} else {
			lvLocation.setAdapter(null);
		}
	}
	
}
