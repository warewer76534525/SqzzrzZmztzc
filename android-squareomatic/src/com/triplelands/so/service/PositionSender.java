package com.triplelands.so.service;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.triplelands.so.tools.InternetConnectionListener;
import com.triplelands.so.tools.InternetHttpConnection;

public class PositionSender implements InternetConnectionListener { //extends AsyncTask<Void, String, Void> implements InternetConnectionListener{

	private InternetHttpConnection internetConnection;
	private String url;
//	private PositionSenderService service;
	private Context context;
	
	public PositionSender(String url, Context ctx) {
		this.url = url;
		context = ctx;
//		this.service = service;
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
		Log.i("RESPON", "respon: " + data);
//		Looper.prepare();
		Toast.makeText(context.getApplicationContext(), data, Toast.LENGTH_LONG).show();
//		service.stopSelf();
//		cancel(true);
//		Looper.loop();
	}

	@Override
	public void onConnectionError(Exception ex) {
		showErrorMessage(ex);
		ex.printStackTrace();
//		service.stopSelf();
	}

	@Override
	public void onConnectionResponseNotOk() {
		sendErrorMessage("Response Not Ok");
//		service.stopSelf();
	}

	@Override
	public void onConnectionTimeout() {
		sendErrorMessage("Time Out Connection");
//		service.stopSelf();
	}

	@Override
	public void onCancelledConnection() {
		sendErrorMessage("Cancelled");
//		service.stopSelf();
	}
	
	private void sendErrorMessage(String msg){
//		Looper.prepare();
		Toast.makeText(context.getApplicationContext(), "Connection failed: " + msg, Toast.LENGTH_LONG).show();
//		cancel(true);
//		Looper.loop();
	}
	
	private void showErrorMessage(Exception ex){
//		Looper.prepare();
		Toast.makeText(context.getApplicationContext(), "" + ex.getMessage(), Toast.LENGTH_LONG).show();
//		cancel(true);
//		Looper.loop();
	}

}
