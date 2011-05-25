package com.triplelands.so.tools;

import java.io.InputStream;

public interface InternetConnectionListener {
	void onReceivedResponse(InputStream is, int length);
	void onConnectionError(Exception ex);
	void onConnectionResponseNotOk();
	void onConnectionTimeout();
	void onCancelledConnection();
	void onReceivedBodyString(String body);
}
