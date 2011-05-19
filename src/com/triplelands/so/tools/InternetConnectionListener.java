package com.triplelands.so.tools;

import java.io.InputStream;

public interface InternetConnectionListener {
	public void onReceivedResponse(InputStream is, int length);
	public void onConnectionError(Exception ex);
	public void onConnectionResponseNotOk();
	public void onConnectionTimeout();
	public void onCancelledConnection();
}
