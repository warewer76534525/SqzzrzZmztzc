package com.triplelands.so.view;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.triplelands.so.R;
import com.triplelands.so.model.Location;

public class LocationAdapterView extends LinearLayout {

	public LocationAdapterView(Context context, Location loc) {
		super(context);
		this.setOrientation(VERTICAL);
		
		View v = inflate(context, R.layout.location_row, null);
		
		setTag(loc);
		
		TextView txtName = (TextView)v.findViewById(R.id.txtLocationName);
		TextView txtDistance = (TextView)v.findViewById(R.id.txtLocationDistance);
		txtName.setText(loc.getName());
		txtDistance.setText("" + (int)Double.parseDouble(loc.getDistance()) + " m");
		
		Typeface font = Typeface.createFromAsset(context.getAssets(), "aescrawl.ttf");  
		txtName.setTypeface(font);
		txtDistance.setTypeface(font);
		
		addView(v);
	}

}
