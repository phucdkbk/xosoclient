package com.alandk.xosomienbac.sync;

import com.alandk.xosomienbac.common.LotteryUtils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootUpReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
//		Intent alarmIntent = new Intent(context, AlarmReceiver.class);
//		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
//		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//		int interval = com.alandk.xosomienbac.common.Constants.REFRESH_INTERVAL;
//		manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
		LotteryUtils.setResultNotification(context);
		// Toast.makeText(context, "Alarm Set", Toast.LENGTH_SHORT).show();
	}

}