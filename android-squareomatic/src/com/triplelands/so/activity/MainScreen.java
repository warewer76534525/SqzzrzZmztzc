package com.triplelands.so.activity;

import android.app.NotificationManager;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.triplelands.so.R;

public class MainScreen extends TabActivity {

	private TabHost mTabHost;
	private NotificationManager _mNotificationManager;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
		
		_mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		_mNotificationManager.cancelAll();
		
//		mTabHost.getTabWidget().setDividerDrawable(android.R.drawable.divider_horizontal_bright);
		Intent intent;
		intent = new Intent().setClass(this, NearbyHistoryActivity.class);
		setupTab(intent, "Near Me", R.drawable.tab_img_selector_history);
		intent = new Intent().setClass(this, SettingActivity.class);
		setupTab(intent, "My Preference", R.drawable.tab_img_selector_setting);
	}

	private void setupTab(final Intent intent, final String tag, int imageId) {
		View tabview = createTabView(mTabHost.getContext(), tag, imageId);
	        TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(intent);
		mTabHost.addTab(setContent);
	}

	private static View createTabView(final Context context, final String text, int imageId) {
		View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
		ImageView img = (ImageView)view.findViewById(R.id.tabsImg);
		img.setImageResource(imageId);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		Typeface font = Typeface.createFromAsset(context.getAssets(), "aescrawl.ttf");
		tv.setTypeface(font);
		return view;
	}
}
