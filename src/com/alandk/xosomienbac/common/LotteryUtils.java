package com.alandk.xosomienbac.common;

import java.util.Scanner;

import com.alandk.xosomienbac.sync.AlarmReceiver;
import com.google.android.gms.ads.AdRequest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class LotteryUtils {
	
	public static boolean isConnectInternet(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}
	
	public static String convertStreamToString(java.io.InputStream is) {
		Scanner s = new Scanner(is, "UTF-8").useDelimiter("//A");		
		return s.hasNext() ? s.next() : "";
	}
	
	public static void setResultNotification(Context context) {
		Intent alarmIntent = new Intent(context, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		int interval = Constants.REFRESH_INTERVAL;
		manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
		// Toast.makeText(context, "Alarm Set", Toast.LENGTH_SHORT).show();
	}
	
	public static AdRequest getAdRequest() {
		AdRequest adRequest = new AdRequest.Builder().build();
		return adRequest;
	}

}
