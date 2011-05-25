package com.triplelands.so.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.triplelands.so.R;
import com.triplelands.so.tools.IdSender;

public class RegisterPage extends Activity {

	private WebView webView;
	private SharedPreferences appPreference;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		setTitle("Loading. May take a view seconds...");
		appPreference =  PreferenceManager.getDefaultSharedPreferences(RegisterPage.this.getApplicationContext());
		webView = (WebView) findViewById(R.id.webViewSquare);
		
		if(appPreference.getString("actk", "").equals("")){
			clearCookie();
			webView.getSettings().setJavaScriptEnabled(true);
			webView.setWebViewClient(new SquareWebViewClient());
			webView.loadUrl("https://foursquare.com/oauth2/authenticate?client_id=OFQLSHGQYEBW345A12XRP1XZXD4NUVIMEYTYHEM4AH0BGIKB&response_type=code&redirect_uri=http://202.51.96.41/som/");
		} else {
			startActivity(new Intent(RegisterPage.this, MainScreen.class));
			finish();
		}
	}

	private class SquareWebViewClient extends WebViewClient {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
		
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			setTitle("Loading. May take a view seconds...");
			super.onPageStarted(view, url, favicon);
		}

		public void onPageFinished(WebView view, String url) {
			setTitle("SquareOmatic");
			String uri = webView.getUrl();
			Log.i("URL", uri);
			if(uri != null && uri.contains("actk")){
				String token = uri.substring(uri.lastIndexOf("=") + 1, uri.length());
				setToken(token);
				String urlId = "http://202.51.96.41/som/register.php?imei=" + getImei() + "&version=" + getOSVersion();
				new IdSender(urlId).execute();
				startActivity(new Intent(RegisterPage.this, MainScreen.class));
				finish();
			}			
		}
	}
	
	private void setToken(String token){
    	SharedPreferences.Editor editor = appPreference.edit();
        editor.putString("actk", token);
        editor.commit();
	}
	
	private String getImei(){
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}
	
	private String getOSVersion(){
		return "" + Build.VERSION.SDK_INT;
	}
	
	private void clearCookie(){
		CookieSyncManager.createInstance(this);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
	}

}