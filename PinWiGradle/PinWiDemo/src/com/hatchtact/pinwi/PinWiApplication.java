package com.hatchtact.pinwi;

import android.app.Application;
import android.content.Context;

import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.applinks.AppLinkData;


/**
 * Created by ankur on 9/14/16.
 */
public class PinWiApplication extends Application {

	@Override
	public void onCreate() {
		ActivityLifecycleCallback.register(this);
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
		//new SharePreferenceClass(getApplicationContext()).setAppDownloaded(false);

		
	}
	
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
	}
}
