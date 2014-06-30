package com.alandk.xosomienbac.sync;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootUpReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// Intent i = new Intent(context, MainActivity.class);
		// i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// context.startActivity(i);

		Intent alarmIntent = new Intent(context, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				alarmIntent, 0);

		AlarmManager manager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		int interval = 6000; // 10 seconds

		manager.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), interval, pendingIntent);
		Toast.makeText(context, "Alarm Set", Toast.LENGTH_SHORT).show();
	}

}