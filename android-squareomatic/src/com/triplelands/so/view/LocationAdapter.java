package com.triplelands.so.view;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.triplelands.so.model.Location;

public class LocationAdapter extends BaseAdapter {

	private List<Location> listLocation;
	private Context context;

	public LocationAdapter(Context ctx) {
		this.context = ctx;
	}

	public int getCount() {
		return listLocation.size();
	}
	
	public void setList(List<Location> listLagu){
		this.listLocation = listLagu;
	}

	public Location getItem(int position) {
		return listLocation.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup viewGroup) {
		Location loc = listLocation.get(position);
		return new LocationAdapterView(context, loc);
	}

}
