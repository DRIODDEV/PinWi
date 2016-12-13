package com.hatchtact.pinwi.utility;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.hatchtact.pinwi.R;

public class FutureNotificationAlarmForMe extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		try
		{
			String RepeatingType = intent.getStringExtra("RepeatingType");
			
			{
				// Do Nothing on notification click
				PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
						PendingIntent.FLAG_UPDATE_CURRENT);
				// Create the notification with a notification builder
				Notification notification = new Notification.Builder(context)
				.setSmallIcon(R.drawable.pinwi_smallicon)
				.setWhen(System.currentTimeMillis())
				.setContentTitle("Pinwi Notification")
				.setContentText(RepeatingType+" Notification")
				.setContentIntent(pIntent)
				.setDefaults(Notification.DEFAULT_SOUND)
				.getNotification();
				// Remove the notification on click
				notification.flags |= Notification.FLAG_AUTO_CANCEL;
				// 
				NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
				manager.notify(R.string.app_name, notification);
				// 
				{
					// Wake Android Device when notification received
					PowerManager pm = (PowerManager) context
							.getSystemService(Context.POWER_SERVICE);
					final PowerManager.WakeLock mWakelock = pm.newWakeLock(
							PowerManager.FULL_WAKE_LOCK
							| PowerManager.ACQUIRE_CAUSES_WAKEUP, "GCM_PUSH");
					mWakelock.acquire();
					// 
					// Timer before putting Android Device to sleep mode.
					Timer timer = new Timer();
					TimerTask task = new TimerTask() {
						public void run() {
							mWakelock.release();
						}
					};
					timer.schedule(task, 5000);
				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
}