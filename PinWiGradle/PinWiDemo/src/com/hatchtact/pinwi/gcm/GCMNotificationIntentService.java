package com.hatchtact.pinwi.gcm;

import java.util.List;
import java.util.concurrent.ExecutionException;

import leolin.shortcutbadger.ShortcutBadger;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.hatchtact.pinwi.ExitActivity;
import com.hatchtact.pinwi.R;
import com.hatchtact.pinwi.SplashActivity;
import com.hatchtact.pinwi.utility.SharePreferenceClass;

public class GCMNotificationIntentService  extends IntentService {

	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;

	public static String GOOGLE_PROJECT_ID = "25079778482";
	public static String MESSAGE_KEY = "message";
	public static String SCORE_KEY = "score";

	public GCMNotificationIntentService() {
		super("GcmIntentService");
	}

	public static final String TAG = "GCMNotificationIntentService";

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) {
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
					.equals(messageType)) {
				sendNotification("Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
					.equals(messageType)) {
				sendNotification("Deleted messages on server: "
						+ extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType)) {

				/*for (int i = 0; i < 3; i++) {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}

				}*/
				/*sendNotification("Message Received from Google GCM Server: "
						+ extras.get(MESSAGE_KEY));*/
				SharePreferenceClass sharePref=null;

				try {
					sharePref=new SharePreferenceClass(this);
					//sharePref.setBadgeScore("0");
					//int localCount=Integer.parseInt(sharePref.getBadgeScore());
					int newCount=Integer.parseInt(extras.get(SCORE_KEY)+"");
					//int count=Integer.parseInt(sharePref.getBadgeScore()+extras.get(SCORE_KEY));
					int count=/*localCount+*/newCount;
					sharePref.setBadgeScore(count+"");
					if(!sharePref.getBadgeScore().trim().equalsIgnoreCase("0") && !sharePref.getBadgeScore().trim().equalsIgnoreCase("null"))
					{
						int badgeCount=Integer.parseInt(sharePref.getBadgeScore().trim());
						/*boolean success = */ShortcutBadger.applyCount(this, badgeCount);
						//Toast.makeText(getApplicationContext(), "Set count=" + badgeCount + ", success=" + success, Toast.LENGTH_SHORT).show();
					}
					else if(sharePref.getBadgeScore().trim().equalsIgnoreCase("null"))
					{
						//sharePref.setBadgeScore("0");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//sharePref.setBadgeScore("0");
				}


				String msg=extras.get(MESSAGE_KEY)+"";
				if(msg.equalsIgnoreCase("null"))
				{
					String title=extras.get("nm")+"";
					if(title.equalsIgnoreCase("null"))
					{

					}
					else
					{
						sendNotificationClevertap(extras.get("nm")+"",extras.get("nt")+"");
					}
				}
				else
				{
					sendNotification(msg);
				}
			}
		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void sendNotification(String msg) {
		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, SplashActivity.class), 0);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.launcher) .setLargeIcon(BitmapFactory.decodeResource(getResources(),
				R.drawable.launcher))
				.setContentTitle("PinWi Notification")
				.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
				.setContentText(msg).setAutoCancel(true).setDefaults(Notification.DEFAULT_SOUND)
				/*.setDefaults(Notification.DEFAULT_VIBRATE)*/;
		/*Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		mBuilder.setSound(uri);*/
		boolean foregroud=true;
		try {
			foregroud = new ForegroundCheckTask().execute(this).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(!foregroud)
			mBuilder.setContentIntent(contentIntent);
		else
		{
			PendingIntent contentIntentExit = PendingIntent.getActivity(this, 0,
					new Intent(this, ExitActivity.class), 0);
			mBuilder.setContentIntent(contentIntentExit);

		}

		Notification notification = mBuilder.build();
		//notification.flags |= Notification.FLAG_AUTO_CANCEL;
		//notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL|Notification.FLAG_ONLY_ALERT_ONCE;

		mNotificationManager.notify(NOTIFICATION_ID,notification);
	}

	class ForegroundCheckTask extends AsyncTask<Context, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Context... params) {
			final Context context = params[0].getApplicationContext();
			return isAppOnForeground(context);
		}

		private boolean isAppOnForeground(Context context) {
			ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
			if (appProcesses == null) {
				return false;
			}
			final String packageName = context.getPackageName();
			for (RunningAppProcessInfo appProcess : appProcesses) {
				if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
					return true;
				}
			}
			return false;
		}
	}

	private void sendNotificationClevertap(String msg,String title) {
		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, SplashActivity.class), 0);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.launcher) .setLargeIcon(BitmapFactory.decodeResource(getResources(),
				R.drawable.launcher))
				.setContentTitle(title)
				.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
				.setContentText(msg).setAutoCancel(true).setDefaults(Notification.DEFAULT_SOUND)
				/*.setDefaults(Notification.DEFAULT_VIBRATE)*/;
		/*Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		mBuilder.setSound(uri);*/
		boolean foregroud=true;
		try {
			foregroud = new ForegroundCheckTask().execute(this).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(!foregroud)
			mBuilder.setContentIntent(contentIntent);
		else
		{
			PendingIntent contentIntentExit = PendingIntent.getActivity(this, 0,
					new Intent(this, ExitActivity.class), 0);
			mBuilder.setContentIntent(contentIntentExit);

		}

		Notification notification = mBuilder.build();
		//notification.flags |= Notification.FLAG_AUTO_CANCEL;
		//notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL|Notification.FLAG_ONLY_ALERT_ONCE;

		mNotificationManager.notify(NOTIFICATION_ID,notification);
	}

}