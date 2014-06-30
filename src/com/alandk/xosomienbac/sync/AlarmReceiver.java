package com.alandk.xosomienbac.sync;

import com.example.xosomienbac.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
	private NotificationManager myNotificationManager;

	private int notificationIdOne = 111;
	private int notificationIdTwo = 112;
	private int numMessagesOne = 0;
	private int numMessagesTwo = 0;

	@Override
	public void onReceive(Context context, Intent arg1) {
		// For our recurring task, we'll just display a message
		//Toast.makeText(arg0, "I'm running", Toast.LENGTH_SHORT).show();
		displayNotificationOne(context);

	}

	protected void displayNotificationOne(Context context) {

		// Invoking the default notification service
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context);

		mBuilder.setContentTitle("New Message with explicit intent");
		mBuilder.setContentText("New message from javacodegeeks received");
		mBuilder.setTicker("Explicit: New Message Received!");
		mBuilder.setSmallIcon(R.drawable.ic_launcher);
		

		// Increase notification number every time a new notification arrives
		mBuilder.setNumber(++numMessagesOne);

		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(context, NotificationOne.class);
		resultIntent.putExtra("notificationId", notificationIdOne);

		// This ensures that navigating backward from the Activity leads out of
		// the app to Home page
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		// Adds the back stack for the Intent
		stackBuilder.addParentStack(NotificationOne.class);

		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_ONE_SHOT // can only be used once
				);
		// start the activity when the user clicks the notification text
		mBuilder.setContentIntent(resultPendingIntent);

		myNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		// pass the Notification object to the system
		myNotificationManager.notify(notificationIdOne, mBuilder.getNotification());
	}

}
