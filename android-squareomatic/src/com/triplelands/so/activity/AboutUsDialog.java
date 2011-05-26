package com.triplelands.so.activity;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.triplelands.so.R;

public class AboutUsDialog extends Dialog {

	private Context ctx;
	
	public AboutUsDialog(Context context) {
		super(context);
		ctx = context;
		setTitle("About");
		setCancelable(true);
		setContentView(R.layout.about);
		
		TextView txtWebsite = (TextView)findViewById(R.id.txtWebsite);
		txtWebsite.setText(Html.fromHtml("<a href='http://www.triplelands.com'>http://www.triplelands.com</a>"));
		txtWebsite.setMovementMethod(LinkMovementMethod.getInstance());
		
		((TextView)this.findViewById(R.id.txtAppName)).setText("SquareOmatic " + getVersionName() + " Beta");
		
	}
	
	private String getVersionName() {
		try {
			return ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionName;
		} catch (Exception e) {
			return "";
		}
	}

}
