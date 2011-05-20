package com.triplelands.so.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.triplelands.so.R;

public class RegisterPage extends Activity {

	private WebView webView;
	private SharedPreferences appPreference;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		appPreference =  PreferenceManager.getDefaultSharedPreferences(RegisterPage.this.getApplicationContext());
		webView = (WebView) findViewById(R.id.webViewSquare);
		
		if(appPreference.getString("actk", "").equals("")){
			clearCookie();
			webView.getSettings().setJavaScriptEnabled(true);
			webView.setWebViewClient(new SquareWebViewClient());
			webView.loadUrl(" https://foursquare.com/oauth2/authenticate?client_id=OFQLSHGQYEBW345A12XRP1XZXD4NUVIMEYTYHEM4AH0BGIKB&response_type=code&redirect_uri=http://squareomatic.triplelands.com/?"
					+ getImei());
		} else {
			startActivity(new Intent(RegisterPage.this, HomePage.class));
			finish();
		}
	}

	private class SquareWebViewClient extends WebViewClient {

		private ProgressDialog pd;

		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
		
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			pd = new ProgressDialog(RegisterPage.this);
			pd.setMessage("Loading...");
			pd.show();
			super.onPageStarted(view, url, favicon);
		}

		public void onPageFinished(WebView view, String url) {
			pd.dismiss();
			String uri = webView.getUrl();
			Log.i("URL", uri);
			if(uri != null && uri.contains("actk")){
				String token = uri.substring(uri.lastIndexOf("=") + 1, uri.length());
				setToken(token);
				startActivity(new Intent(RegisterPage.this, HomePage.class));
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
	
	private void clearCookie(){
		CookieSyncManager.createInstance(this);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
	}

}