package com.triplelands.so.service;

import java.io.InputStream;
import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.triplelands.so.tools.InternetConnectionListener;
import com.triplelands.so.tools.InternetHttpConnection;

public class PositionSender implements InternetConnectionListener {

	private InternetHttpConnection internetConnection;
	private String url;
//	private PositionSenderService service;
	private Context context;
//	private List<NameValuePair> parameter;
	
	public PositionSender(String url, Context ctx, List<NameValuePair> parameter) {
		this.url = url;
		context = ctx;
//		this.parameter = parameter;
//		this.service = service;
		internetConnection = new InternetHttpConnection(this);
	}
	
	public void start() {
		Log.i("CHECKIN", "CHECKING IN: " + url);
//		internetConnection.setAndAccessURL(url);
//		internetConnection.postData(url, parameter);
		internetConnection.get(url);
	}

	@Override
	public void onReceivedResponse(InputStream is, int length) {
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
		Looper.prepare();
		Toast.makeText(context.getApplicationContext(), "Connection failed: " + msg, Toast.LENGTH_LONG).show();
//		cancel(true);
		Looper.loop();
	}
	
	private void showErrorMessage(Exception ex){
		Looper.prepare();
		Toast.makeText(context.getApplicationContext(), "" + ex.getMessage(), Toast.LENGTH_LONG).show();
//		cancel(true);
		Looper.loop();
	}

	@Override
	public void onReceivedBodyString(String body) {
		Looper.prepare();
		Toast.makeText(context.getApplicationContext(), body, Toast.LENGTH_LONG).show();
		Looper.loop();
	}

}
