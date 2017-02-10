package com.hatchtact.pinwi;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class OnClearFromService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ClearFromRecentService", "Service Started");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ClearFromRecentService", "Service Destroyed");
    }

    public void onTaskRemoved(Intent rootIntent) {
        Log.e("ClearFromRecentService", "END");
        System.out.println("App ENd");
        //Code here
	   // AppEventsLogger.deactivateApp(this);
        stopSelf();
    }
}
