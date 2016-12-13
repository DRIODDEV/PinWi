package com.hatchtact.pinwi;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.applinks.AppLinkData;

import android.app.Application;
import android.content.Context;


/**
 * Created by ankur on 9/14/16.
 */
public class PinWiApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);
		AppLinkData.fetchDeferredAppLinkData(this, new AppLinkData.CompletionHandler() {
			
			@Override
			public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
				// TODO Auto-generated method stub
				//Process app link data
			}
		});
	}
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
	}
}
