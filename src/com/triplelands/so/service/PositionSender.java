package com.triplelands.so.service;

import java.io.IOException;
import java.io.InputStream;

import android.util.Log;
import android.widget.Toast;

import com.triplelands.so.tools.InternetConnectionListener;
import com.triplelands.so.tools.InternetHttpConnection;

public class PositionSender implements InternetConnectionListener { //extends AsyncTask<Void, String, Void> implements InternetConnectionListener{

	private InternetHttpConnection internetConnection;
	private String url;
	private PositionSenderService service;
	
	public PositionSender(String url, PositionSenderService service) {
		this.url = url;
		this.service = service;
		internetConnection = new InternetHttpConnection(this);
	}
	
//	protected Void doInBackground(Void... params) {
	public void start(){
		Log.i("CHECKIN", "CHECKING IN: " + url);
		internetConnection.setAndAccessURL(url);
//		return null;
	}

	@Override
	public void onReceivedResponse(InputStream is, int length) {
		Log.i("CHECKIN", "COMPLETE CHECKED IN");
		byte input[] = new byte[length];
		try {
			is.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String data = new String(input);
//		Looper.prepare();
		Toast.makeText(service.getApplicationContext(), data, Toast.LENGTH_LONG).show();
		service.stopSelf();
//		cancel(true);
//		Looper.loop();
	}

	@Override
	public void onConnectionError(Exception ex) {
		showErrorMessage(ex);
		ex.printStackTrace();
	}

	@Override
	public void onConnectionResponseNotOk() {
		sendErrorMessage("Response Not Ok");
		
	}

	@Override
	public void onConnectionTimeout() {
		sendErrorMessage("Time Out Connection");
	}

	@Override
	public void onCancelledConnection() {
		sendErrorMessage("Cancelled");
	}
	
	private void sendErrorMessage(String msg){
//		Looper.prepare();
		Toast.makeText(service.getApplicationContext(), "Connection failed: " + msg, Toast.LENGTH_LONG).show();
//		cancel(true);
//		Looper.loop();
	}
	
	private void showErrorMessage(Exception ex){
//		Looper.prepare();
		Toast.makeText(service.getApplicationContext(), "" + ex.getMessage(), Toast.LENGTH_LONG).show();
//		cancel(true);
//		Looper.loop();
	}

}
